package PedramK.PedramBot.service;


import PedramK.PedramBot.classes.MySqlFunctions;
import PedramK.PedramBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static PedramK.PedramBot.classes.MyFunctions.*;
import static PedramK.PedramBot.classes.MySqlFunctions.*;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    private void generalCommand(String command, long chatId, String userName) {
        switch (command) {
            case "/start":
                sayStart(chatId, userName);
                break;
            case "/lang":
                String langSettings = "your current settings:\n\n";
                langSettings += loadLangSetting(userName);
                sendMessage(chatId, langSettings);
                break;
            case "/Off_eng":
                setUserState(userName, "en", 0);
                break;
            case "/On_eng":
                setUserState(userName, "en", 1);
                break;
            case "/Off_de":
                setUserState(userName, "de", 0);
                break;
            case "/On_de":
                setUserState(userName, "de", 1);
                break;
            case "/Off_fa":
                setUserState(userName, "fa", 0);
                break;
            case "/On_fa":
                setUserState(userName, "fa", 1);
                break;
            case "/Off_ru":
                setUserState(userName, "ru", 0);
                break;
            case "/On_ru":
                setUserState(userName, "ru", 1);
                break;

        }
    }

    private MySqlFunctions mySqlFunctions = new MySqlFunctions();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String ms = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getFirstName();
            if (ms.startsWith("/")) {
                generalCommand(ms, chatId, userName);
                return;
            }

            switch (ms) {
                default:
                    String s = encodePedramDictionary(onlineDic(ms.toLowerCase()), userName);
                    System.out.println(s);
                    if (!s.isEmpty() || !s.isBlank()) {
                        sendMessage(chatId, s);
                    } else {
                        sendMessage(chatId, "Error");
                    }
            }

        }
    }


    private void sayStart(long chatId, String firstName) {
        sendMessage(chatId, "Hello " + firstName);
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setParseMode(ParseMode.HTML);
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }


}
