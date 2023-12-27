package skypro.course4.homework.telegrambot.configuration;

import skypro.course4.homework.telegrambot.properties.TelegramProperties;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@RequiredArgsConstructor
public class TelegramConfiguration {


    private final TelegramProperties telegramProperties;
    @Value("${telegram.token}")
    private String token;

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(telegramProperties.token());
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}