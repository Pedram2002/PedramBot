package PedramK.PedramBot.classes;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySqlFunctions {
    static Connection connection;
    static Statement statement;

    private static int getUserId(String userName) {
        try {
            String sql = "SELECT id FROM airport.users WHERE username = '" + userName + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return -1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MySqlFunctions() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/airport", "root", "123456");
            statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getUserState(String userName, String lang) {
        try {
            String sql = "SELECT * FROM airport.users WHERE username = '" + userName + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt(lang);
            } else {
                return 1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String getUserState(String userName) {
        try {
            String sResult ;
            String sql = "SELECT * FROM airport.users WHERE username = '" + userName + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                sResult = "fa" + resultSet.getInt("fa");
                sResult += "ru" + resultSet.getInt("ru");
                sResult += "de" + resultSet.getInt("de");
                sResult += "en" + resultSet.getInt("en");
                return  sResult ;
            } else {
                return "fa1ru1de1en1";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void setUserState(String userName, String lang, int state) {
        try {
            int userid = getUserId(userName);

            if (userid > -1) {
                String sql = "UPDATE airport.users SET " + lang + "= ? WHERE id =" + userid;

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, state);
                    statement.executeUpdate();
                }

                System.out.println(userName + " was updated");
            } else {
                String sql = "INSERT INTO airport.users (username, " + lang + ") VALUES (?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, userName);
                    statement.setInt(2, state);
                    statement.executeUpdate();
                }
                System.out.println(userName + " was added");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadLangSetting(String user) {
        String sResult;

        int sFa = getUserState(user, "fa");
        int sRu = getUserState(user, "ru");
        int sDe = getUserState(user, "de");
        int sEn = getUserState(user, "en");

        if (sEn == 1) {
            sResult = "English is On , /Off_eng\n";
        } else {
            sResult = "English is Off , /On_eng\n";
        }

        if (sDe == 1) {
            sResult += "German is On , /Off_de\n";
        } else {
            sResult += "German is Off , /On_de\n";
        }

        if (sFa == 1) {
            sResult += "Persian is On , /Off_fa\n";
        } else {
            sResult += "Persian is Off , /On_fa\n";
        }

        if (sRu == 1) {
            sResult += "Russian is On , /Off_ru";
        } else {
            sResult += "Russian is Off , /On_ru";
        }

        return sResult;
    }
}
