package PedramK.PedramBot.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static PedramK.PedramBot.PedramBotApplication.logger;
import static PedramK.PedramBot.repository.SqlFunctions.*;

/**
 * A utility class containing various functions, including online dictionary lookup and dictionary encoding.
 */
public class MyFunctions {
    /**
     * Retrieves information from an online dictionary based on the specified word.
     * <p>
     * This method sends a request to an HTTP listener, providing the specified word as a parameter.
     * The response from the server is processed, and if the response is not empty, it is returned.
     * If the response is empty, the method returns "oops."
     *
     * @param word The word for which information is requested from the online dictionary.
     * @return A string containing information retrieved from the online dictionary or "oops" if the word is not found.
     */
    public static String onlineDic(String word) {
        String sResult;

        String str = downloadString("http://45.142.212.28:8080/HttpListener/?word=" + word);
        if (str.isEmpty()) {
            sResult = "oops";
        } else {//word is not found
            sResult = str;
        }

        logger.log(Level.INFO, word);
        return sResult;
    }

    /**
     * Downloads a string from a specified URL.
     * <p>
     * This method opens a connection to the specified URL, reads the content, and returns it as a string.
     * If an IOException occurs during the process, a warning is logged, and an empty string is returned.
     *
     * @param url The URL from which to download the string.
     * @return The downloaded string or an empty string if an error occurs.
     */
    private static String downloadString(String url) {
        StringBuilder s = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String ss;
            while ((ss = reader.readLine()) != null) {
                s.append(ss);
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return "";
        }
        return s.toString();
    }

    /**
     * Encodes information from the Pedram dictionary based on user settings.
     * <p>
     * This method takes a translation string, extracts information based on a predefined pattern,
     * and formats the result based on the user's language settings.
     *
     * @param translate The translation string to be encoded.
     * @param userName  The username for which the user settings are retrieved.
     * @return A formatted string containing information from the Pedram dictionary based on user preferences.
     */
    public static String encodePedramDictionary(String translate, String userName) {
        // ☻deen☺essen=verbum◘eat◙
        String[] tr = translate.split("☻");
        Pattern pattern = Pattern.compile("☻(?<lang>.*?)☺(?<word>.*?)=(?<sub>.*?)◘(?<translate>.*?)◙");
        Matcher matcher = pattern.matcher(translate);
        StringBuilder sResult = new StringBuilder();
        String userSetting = getUserState(userName);

        while (matcher.find()) {
            String sLang = matcher.group("lang");
            if (sLang.length() > 2) sLang = sLang.substring(2);

            if (!userSetting.contains(sLang + "1")) continue;

            String sWord = "<b>" + matcher.group("word") + "</b> <i>" + matcher.group("sub") + "</i>";
            String sTranslate = " <code>" + matcher.group("translate") + "</code>";
            int i = sResult.indexOf(sWord);
            if (i > -1) {
                sResult.insert(i + sWord.length(), "\n" + sTranslate.trim());

            } else {
                sResult.append(sWord).append("\n").append(sTranslate).append("\n\n");
            }

        }
        return sResult.toString().replaceAll("<small>|</small>|<p((?!</p>).)*</p>", "")
                .replaceAll("<br>", "");
    }
}
