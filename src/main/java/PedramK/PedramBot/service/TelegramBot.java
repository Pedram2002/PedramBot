package PedramK.PedramBot.service;
import PedramK.PedramBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.logging.Level;
import static PedramK.PedramBot.PedramBotApplication.logger;
import static PedramK.PedramBot.classes.MyFunctions.*;
import static PedramK.PedramBot.repository.SqlFunctions.*;

/**
 * A Telegram bot implementation that extends TelegramLongPollingBot.
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;
    /**
     * Constructor for TelegramBot.
     *
     * @param config The configuration for the Telegram bot.
     */
    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    /**
     * Handles general commands received by the bot.
     *
     * @param command  The command received.
     * @param chatId   The chat ID where the command was received.
     * @param userName The username associated with the chat.
     */
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

    /**
     * Callback method invoked when an update is received.
     *
     * @param update The received update.
     */
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
    /**
     * Sends a greeting message to the specified chat.
     *
     * @param chatId    The chat ID where the greeting should be sent.
     * @param firstName The first name associated with the chat.
     */
    private void sayStart(long chatId, String firstName) {
        sendMessage(chatId, "Hello " + firstName);
    }
    /**
     * Sends a message to the specified chat.
     *
     * @param chatId The chat ID where the message should be sent.
     * @param text   The text of the message.
     */
    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setParseMode(ParseMode.HTML);
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
    /**
     * Gets the bot token used for authentication.
     *
     * @return The bot token.
     */
    @Override
    public String getBotToken() {
        return config.getToken();
    }

    /**
     * Gets the bot's username.
     *
     * @return The bot's username.
     */
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }


}
