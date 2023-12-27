package skypro.course4.homework.telegrambot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import jakarta.validation.constraints.NotNull;
import skypro.course4.homework.telegrambot.configuration.CommandType;
import skypro.course4.homework.telegrambot.entity.animal.Animal;
import skypro.course4.homework.telegrambot.entity.animal.person.Customer;
import skypro.course4.homework.telegrambot.entity.animal.person.report.AnimalReport;
import skypro.course4.homework.telegrambot.entity.animal.person.report.shelter.AnimalShelter;
import skypro.course4.homework.telegrambot.exception.MessageException;
import skypro.course4.homework.telegrambot.exception.ReportException;
import skypro.course4.homework.telegrambot.listener.BotUpdatesListener;
import skypro.course4.homework.telegrambot.properties.TelegramProperties;
import skypro.course4.homework.telegrambot.repository.person.CustomerRepository;
import skypro.course4.homework.telegrambot.service.bot.BotCommandService;
import skypro.course4.homework.telegrambot.service.report.AnimalReportService;
import skypro.course4.homework.telegrambot.service.report.AnimalReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;import static skypro.course4.homework.telegrambot.configuration.CommandType.*;
import static skypro.course4.homework.telegrambot.service.ValidationRegularService.validateTelephone;

@Service
@RequiredArgsConstructor
public class BotCommandServiceImpl implements BotCommandService {

    private final Logger logger = LoggerFactory.getLogger(BotCommandServiceImpl.class);

    private final TelegramBot telegramBot;

    private final TelegramProperties telegramProperties;

    private final CustomerRepository customerRepository;

    @Autowired
    private AnimalReportService animalReportService;


    public static final String PHONE = "Чтобы мы могли с Вами связаться, напишите в чат Ваш номер телефона.";
    public static final String PHONE_AGAIN = "Номер телефона не прошел проверку, пожалуйста, попробуйте еще раз.";
    public static final String VOLUNTEER_MESSAGE = "Пожалуйста, напишите в чат по какому вопросу Вы обращаетесь.";

    private static final String MESSAGE = """
            (ID животного:)(\\s+)(\\d+)(;|;\\s+)
            (Рацион:)(\\s+)([А-я\\d\\s.,!?:]+)(;|;\\s+)
            (Здоровье:)(\\s+)([А-я\\d\\s.,!?:]+)(;|;\\s+)
            (Поведение:)(\\s+)([А-я\\d\\s.,!?:]+)(\\.|\\.\\s+)""";

    private static final String EXAMPLE_REPORT = """
            ID животного: 1;
            Рацион: Ваш текст;
            Здоровье: Ваш текст;
            Поведение: Ваш текст.""";

    private static final String INFO_REPORT = """
            <b>Для отчета нужна следующая информация:</b>
            Фото животного
            ID животного
            Рацион
            Общее самочувствие и привыкание к новому месту
            Изменение в поведении: отказ от старых привычек, приобретение новых
            Скопируйте следующий пример. Не забудьте прикрепить фото!""";

    @Override
    public void runAbout(@NotNull Customer customer) {
        String welcomeMessage = String.format("""
                        *Добро пожаловать, %s*\\!
                        Вас приветствует _*бот*_, который поможет сделать доброе дело\\.""",
                customer.getFirstName());

        sendMessage(customer.getChatId(), welcomeMessage);
    }

    @Override
    public void runAdopt(Long chatId, AnimalShelter shelter) {
        try {
            byte[] pdf = Files.readAllBytes(Paths.get(
                    Objects.requireNonNull(BotUpdatesListener.class.getResource(
                            "/" + shelter.getSafetyAdvice())).toURI()));
            SendDocument sendDocument = new SendDocument(chatId, pdf).fileName(shelter.getSafetyAdvice());

            sendDocument.caption(
                    "Рекомендации для будущих хозяев от приюта <b>\"" + shelter.getName() + "\"</b>!"
            );
            sendDocument.parseMode(ParseMode.HTML);
            telegramBot.execute(sendDocument);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void runStart(Long chatId) {

        InlineKeyboardButton catShelterButton = new InlineKeyboardButton(CATS.getDescription());
        catShelterButton.callbackData(CATS.toString());

        InlineKeyboardButton dogShelterButton = new InlineKeyboardButton(DOGS.getDescription());
        dogShelterButton.callbackData(DOGS.toString());


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(catShelterButton, dogShelterButton);


        SendMessage sendMessage = new SendMessage(chatId, "*Выберите приют*");
        sendMessage.replyMarkup(inlineKeyboardMarkup);


        prepareAndExecuteMessage(sendMessage);
    }

    @Override
    public void runCats(Long chatId, AnimalShelter shelter) {
        // Отправка картинки
        sendPhotoShelter(chatId, shelter);

        // Отображение кнопок
        runDialogAnimalShelter(chatId);
    }

    @Override
    public void runDogs(Long chatId, AnimalShelter shelter) {

        sendPhotoShelter(chatId, shelter);


        runDialogAnimalShelter(chatId);
    }

    @Override
    public void runInfo(Long chatId, AnimalShelter shelter) {

        InlineKeyboardButton shelterButton = new InlineKeyboardButton(SHELTER.getDescription());
        shelterButton.callbackData(SHELTER.toString());

        InlineKeyboardButton locationButton = new InlineKeyboardButton(LOCATION.getDescription());
        locationButton.callbackData(LOCATION.toString());

        InlineKeyboardButton contactButton = new InlineKeyboardButton(CONTACT.getDescription());
        contactButton.callbackData(CONTACT.toString());

        InlineKeyboardButton securityButton = new InlineKeyboardButton(SECURITY.getDescription());
        securityButton.callbackData(SECURITY.toString());

        InlineKeyboardButton safetyButton = new InlineKeyboardButton(SAFETY.getDescription());
        safetyButton.callbackData(SAFETY.toString());


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup
                .addRow(shelterButton)
                .addRow(locationButton)
                .addRow(contactButton)
                .addRow(securityButton)
                .addRow(safetyButton);


        SendMessage sendMessage = new SendMessage(chatId, "*Выберите дополнительное действие*");
        sendMessage.replyMarkup(inlineKeyboardMarkup);


        prepareAndExecuteMessage(sendMessage);
    }

    @Override
    public void runShelter(Long chatId, AnimalShelter shelter) {
        String message = "Кратко о приюте: <b>"
                + shelter.getDescription()
                + " </b><tg-emoji emoji-id=\"5368324170671202286\">\uD83D\uDC4D</tg-emoji>";
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void runSecurity(Long chatId, AnimalShelter shelter) {
        String message = String.format("""
                        <b>Контактные данные охраны приюта "%s":</b>
                        %s""",
                shelter.getName(),
                shelter.getSecurityContact());
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void runSafety(Long chatId, AnimalShelter shelter) {
        try {
            byte[] pdf = Files.readAllBytes(Paths.get(
                    Objects.requireNonNull(BotUpdatesListener.class.getResource(
                            "/" + shelter.getShelterSafetyAdvice())).toURI()));
            SendDocument sendDocument = new SendDocument(chatId, pdf).fileName(shelter.getShelterSafetyAdvice());

            sendDocument.caption(
                    "Общие рекомендации о технике безопасности в приюте <b>\"" + shelter.getName() + "\"</b>!"
            );
            sendDocument.parseMode(ParseMode.HTML);
            telegramBot.execute(sendDocument);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void runReport(Long chatId) {
        SendMessage sendInfo = new SendMessage(chatId, INFO_REPORT);
        sendInfo.parseMode(ParseMode.HTML);
        telegramBot.execute(sendInfo);

        SendMessage sendExample = new SendMessage(chatId, EXAMPLE_REPORT);
        telegramBot.execute(sendExample);
    }

    @Override
    public void saveReport(Message message) {
        Long chatId = message.chat().id();
        String text = message.caption();

        Pattern pattern = Pattern.compile(MESSAGE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) {
            int id = Integer.parseInt(matcher.group(3));
            String diet = matcher.group(7);
            String wellBeing = matcher.group(11);
            String behavior = matcher.group(15);

            GetFile getFileRequest = new GetFile(message.photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                file.fileSize();
                String fullPathPhoto = file.filePath();
                LocalDateTime dateTime = LocalDateTime.now();

                Customer customer = customerRepository.findByChatId(chatId).orElseThrow();
                Animal animal = new Animal();
                animal.setId(id);

                AnimalReport animalReport = new AnimalReport();
                animalReport.setPhoto(fullPathPhoto);
                animalReport.setDiet(diet);
                animalReport.setWellBeing(wellBeing);
                animalReport.setBehavior(behavior);
                animalReport.setDateCreate(dateTime);
                animalReport.setAnimal(animal);
                animalReport.setCustomer(customer);

                animalReportService.save(animalReport);

                telegramBot.execute(new SendMessage(message.chat().id(), "Отчет успешно отправлен!"));
            } catch (Exception e) {
                telegramBot.execute(new SendMessage(
                        message.chat().id(), "Загрузка не удалась! Пожалуйста, попробуйте снова."));
            }
        } else {
            telegramBot.execute(new SendMessage(
                    message.chat().id(), "Загрузка не удалась! Пожалуйста, убедитесь, что вы заполнили отчет согласно шаблону."));
            throw new ReportException();
        }
    }

    @Override
    public void runTelephone(Long chatId) {

        SendMessage sendMessage = new SendMessage(chatId, PHONE);


        sendMessage.replyMarkup(new ForceReply());
        telegramBot.execute(sendMessage);
    }

    @Override
    public void saveTelephone(long chatId, String phone) {
        Customer customer = customerRepository.findByChatId(chatId).orElseThrow();
        if (validateTelephone(phone)) {
            customer.setPhone(phone);
            customerRepository.save(customer);
            SendMessage sendMessage = new SendMessage(chatId, "Номер телефона принят!");
            telegramBot.execute(sendMessage);
        } else {

            SendMessage sendMessage = new SendMessage(chatId, PHONE_AGAIN);
            sendMessage.replyMarkup(new ForceReply());
            telegramBot.execute(sendMessage);
        }
    }

    @Override
    public void runVolunteer(Long chatId) {

        SendMessage sendMessage = new SendMessage(chatId, VOLUNTEER_MESSAGE);
        sendMessage.replyMarkup(new ForceReply());
        telegramBot.execute(sendMessage);
    }

    @Override
    public void sendMessageToVolunteer(Long chatId, String text) {
        Customer customer = customerRepository.findByChatId(chatId).get();


        String volunteerMessage = String.format("""
                        <b>%s</b> зовёт волонтёра!
                        Номер телефона: %s.
                        Прикрепленное сообщение: %s""",
                customer.getFirstName(),
                customer.getPhone(),
                text);
        SendMessage sendVolunteerMessage = new SendMessage(telegramProperties.volunteerChatId(), volunteerMessage);
        sendVolunteerMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendVolunteerMessage);
    }

    @Override
    public void runContact(Long chatId, AnimalShelter shelter) {
        String message = "Номер телефона приюта: <b>" + shelter.getContacts() + "</b>";
        SendMessage sendMessage = new SendMessage(chatId, message);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
    }

    @Override
    public void runLocation(Long chatId, AnimalShelter shelter) {
        sendAddressAndWorkSchedule(chatId, shelter);
        try {
            byte[] photo = Files.readAllBytes(Paths.get(
                    BotUpdatesListener.class.getResource("/" + shelter.getDrivingDirections()).toURI()));
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            sendPhoto.caption("Схема проезда к приюту");
            telegramBot.execute(sendPhoto);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(@NotNull Long chatId, String message) {
        prepareAndExecuteMessage(new SendMessage(chatId, message));
    }


    private void sendAddressAndWorkSchedule(Long chatId, AnimalShelter shelter) {
        String first = "<b>Расписание работы, адрес и схема проезда приюта \"" + shelter.getName() + "\"</b>";
        String second = "Приют работает: <b>" + shelter.getWorkSchedule() + "</b>!";
        String third = "Наш адрес: <b>" + shelter.getAddress() + "</b>";

        SendMessage sendMessage = new SendMessage(chatId, first);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);

        sendMessage = new SendMessage(chatId, second);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);

        sendMessage = new SendMessage(chatId, third);
        sendMessage.parseMode(ParseMode.HTML);
        telegramBot.execute(sendMessage);
    }


    private void runDialogAnimalShelter(Long chatId) {

        InlineKeyboardButton infoShelterButton = new InlineKeyboardButton(INFO.getDescription());
        infoShelterButton.callbackData(INFO.toString());

        InlineKeyboardButton adoptButton = new InlineKeyboardButton(ADOPT.getDescription());
        adoptButton.callbackData(ADOPT.toString());

        InlineKeyboardButton reportAnimalButton = new InlineKeyboardButton(REPORT.getDescription());
        reportAnimalButton.callbackData(REPORT.toString());

        InlineKeyboardButton getUserPhoneButton = new InlineKeyboardButton(CommandType.PHONE.getDescription());
        getUserPhoneButton.callbackData(CommandType.PHONE.toString());

        InlineKeyboardButton volunteerButton = new InlineKeyboardButton(VOLUNTEER.getDescription());
        volunteerButton.callbackData(VOLUNTEER.toString());


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup
                .addRow(infoShelterButton)
                .addRow(adoptButton)
                .addRow(reportAnimalButton)
                .addRow(getUserPhoneButton)
                .addRow(volunteerButton);


        SendMessage sendMessage = new SendMessage(chatId, "*Выберите действие*");
        sendMessage.replyMarkup(inlineKeyboardMarkup);


        prepareAndExecuteMessage(sendMessage);
    }


    private void sendPhotoShelter(Long chatId, AnimalShelter shelter) {
        try {
            byte[] photo = Files.readAllBytes(Paths.get(
                    BotUpdatesListener.class.getResource("/" + shelter.getImageName()).toURI()));
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            sendPhoto.caption(
                    "Приветствуем Вас в приюте <b>\"" + shelter.getName() + "\"</b>!"
            );
            sendPhoto.parseMode(ParseMode.HTML);
            telegramBot.execute(sendPhoto);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    private void prepareAndExecuteMessage(SendMessage sendMessage) {
        sendMessage.parseMode(ParseMode.MarkdownV2);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            String msg = String.format("Ошибка вывода сообщения: %s", sendResponse.description());
            logger.error(msg);
            throw new MessageException(msg);
        }
    }
}