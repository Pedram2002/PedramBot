package PedramK.PedramBot.classes;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkWithSettingsFile {

    private static String readFromSettingFile() {
        String line;
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("langSettings.txt"))) {
            while ((line = reader.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
            return resultStringBuilder.toString();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return "";
        }
    }

    private static void SaveToSettingFile(String str) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("langSettings.txt"))) {
            writer.write(str);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    public static String loadLangSetting(String user) {
        String sResult = "English is On , /Off_eng\nGerman is On , /Off_de\nPersian is On , /Off_fa\nRussian is On , /Off_ru";
        String line = readFromSettingFile();
        if (line.isEmpty()) return sResult;

        Pattern pattern = Pattern.compile("☻" + user + "☺en(?<en>.*?)de(?<de>.*?)fa(?<fa>.*?)ru(?<ru>.*?)◙");
        Matcher matcher = pattern.matcher(line);

        if (!matcher.find()) return sResult;

        String sEn = matcher.group("en");
        String sDe = matcher.group("de");
        String sFa = matcher.group("fa");
        String sRu = matcher.group("ru");

        if (sEn.equals("1")) {
            sResult = "English is On , /Off_eng\n";
        } else {
            sResult = "English is Off , /On_eng\n";
        }

        if (sDe.equals("1")) {
            sResult += "German is On , /Off_de\n";
        } else {
            sResult += "German is Off , /On_de\n";
        }

        if (sFa.equals("1")) {
            sResult += "Persian is On , /Off_fa\n";
        } else {
            sResult += "Persian is Off , /On_fa\n";
        }

        if (sRu.equals("1")) {
            sResult += "Russian is On , /Off_ru";
        } else {
            sResult += "Russian is Off , /On_ru";
        }

        return sResult;
    }
    public static String loadUserSetting(String user) {
        String line = readFromSettingFile();
        if (line.isEmpty()) return "";

        Pattern pattern = Pattern.compile("☻" + user + "☺en(?<en>.*?)de(?<de>.*?)fa(?<fa>.*?)ru(?<ru>.*?)◙");
        Matcher matcher = pattern.matcher(line);

        if (!matcher.find()) return "";

        return matcher.group(0);
    }
    public static void saveLangSetting(String user, String lang, String state) {
        String sResult = "☻" + user + "☺en1de1fa1ru1◙";

        String line = readFromSettingFile();

        Pattern pattern = Pattern.compile("☻" + user + "☺(?<en>.*?)de(?<de>.*?)fa(?<fa>.*?)ru(?<ru>.*?)◙");
        Matcher matcher = pattern.matcher(line);

        if (line.isEmpty() || !matcher.find()) {
            SaveToSettingFile(sResult.replaceAll(lang + "[0-9]", lang + state) + "\n" + line);
            return;
        }


        String s = matcher.group(0);
        String s1 = s.replaceAll(lang + "[0-9]", lang + state);
        String s2 = line.replace(s, s1);

        SaveToSettingFile(line.replace(s, s1));
    }
}
