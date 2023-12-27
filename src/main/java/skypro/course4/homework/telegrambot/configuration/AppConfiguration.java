package skypro.course4.homework.telegrambot.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan("skypro.course4.homework.telegrambot")
@EntityScan("skypro.course4.homework.telegrambot.entity")
@ConfigurationPropertiesScan("skypro.course4.homework.telegrambot.properties")
public class AppConfiguration {
}