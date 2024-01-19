package skypro.course4.homework.telegrambot.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
@Getter
@RequiredArgsConstructor
public enum CommandType {
    ABOUT("/about", "Информация о боте"),
    ADOPT("/adopt", "Как взять животное из приюта"),
    CATS("/cats", "Приют для кошек"),
    DOGS("/dogs", "Приют для собак"),
    START("/start", "Старт бота и приветствие"),
    INFO("/info", "Информация о приюте"),
    REPORT("/report", "Отчет о питомце"),
    VOLUNTEER("/volunteer", "Позвать волонтера"),
    SHELTER("/shelter", "О приюте"),
    LOCATION("/location","Расписание работы приюта, адрес и схема проезда"),
    CONTACT("/contact", "Телефон приюта"),
    PHONE("/phone", "Оставить номер телефона для связи"),
    SECURITY("/security","Контактные данные охраны для оформления пропуска на машину"),
    SAFETY("/safety","Общие рекомендации о технике безопасности на территории приюта");


    private final String command;
    private final String description;


    @Nullable
    public static CommandType fromCommand(String command) {
        for (CommandType commandType : CommandType.values()) {
            if (commandType.getCommand().equals(command)) {
                return commandType;
            }
        }
        return null;
    }
}