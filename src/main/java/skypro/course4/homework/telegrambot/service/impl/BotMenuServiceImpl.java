package skypro.course4.homework.telegrambot.service.impl;

import skypro.course4.homework.telegrambot.service.bot.BotMenuService;
import com.pengrad.telegrambot.model.BotCommand;
import org.springframework.stereotype.Service;

import static skypro.course4.homework.telegrambot.configuration.CommandType.ABOUT;
import static skypro.course4.homework.telegrambot.configuration.CommandType.START;

@Service
public class BotMenuServiceImpl implements BotMenuService{
    @Override
    public BotCommand[] createMainMenuCommand() {
        return new BotCommand[]{
                new BotCommand(START.getCommand(), START.getDescription()),
                new BotCommand(ABOUT.getCommand(), ABOUT.getDescription())
        };
    }
}