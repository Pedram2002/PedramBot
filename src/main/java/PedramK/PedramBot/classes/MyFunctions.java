package PedramK.PedramBot.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static PedramK.PedramBot.PedramBotApplication.logger;
import static PedramK.PedramBot.classes.MySqlFunctions.*;


public class MyFunctions {
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
        }
        return s.toString();
    }
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
