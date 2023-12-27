package skypro.course4.homework.telegrambot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "telegram")
public record TelegramProperties(String token,
                                 long volunteerChatId,
                                 String ReportCron) {
}