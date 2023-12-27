package skypro.course4.homework.telegrambot.service.bot;

import jakarta.validation.constraints.NotNull;
import skypro.course4.homework.telegrambot.configuration.CommandType;
import skypro.course4.homework.telegrambot.entity.animal.person.Customer;
import skypro.course4.homework.telegrambot.entity.animal.person.report.shelter.AnimalShelter;
import com.pengrad.telegrambot.model.Message;

public interface BotCommandService {

    void runAbout(@NotNull Customer customer);

    void runAdopt(Long chatId, AnimalShelter shelter);

    void runCats(Long chatId, AnimalShelter shelter);

    void runDogs(Long chatId, AnimalShelter shelter);

    void runStart(Long chatId);

    void runInfo(Long chatId, AnimalShelter shelter);

    void runShelter(Long chatId, AnimalShelter shelter);

    void runSecurity(Long chatId, AnimalShelter shelter);

    void runSafety(Long chatId, AnimalShelter shelter);

    void runReport(Long chatId);

    void saveReport(Message message);

    void runVolunteer(Long chatId);

    void runTelephone(Long chatId);

    void saveTelephone(long chatId, String phone);

    void sendMessageToVolunteer(Long chatId, String text);

    void runContact(Long chatId, AnimalShelter shelter);

    void runLocation(Long chatId, AnimalShelter shelter);

    void sendMessage(@NotNull Long chatId, String message);
}