package PedramK.PedramBot.config;

import PedramK.PedramBot.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.logging.Level;

import static PedramK.PedramBot.PedramBotApplication.logger;

@Component
public class BotInt {
    @Autowired
    TelegramBot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init(){
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

}
