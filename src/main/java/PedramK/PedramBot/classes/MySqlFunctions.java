package PedramK.PedramBot.classes;

import java.sql.*;

public class MySqlFunctions {
    static Connection connection;
    static Statement statement;
    public static void saveUserState(String userName, String address) {
        try {
            int userid = getUserId(userName);

            if (userid > -1) {
                String sql = "UPDATE airport.users SET state = ? WHERE id =" + userid;

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, address);
                    statement.executeUpdate();
                }
                System.out.println(userName + " was updated");
            } else {
                String sql = "INSERT INTO airport.users (username, state) VALUES (?, ?)";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, userName);
                    statement.setString(2, address);
                    statement.executeUpdate();
                }
                System.out.println(userName + " was added");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public static String getUserState(String userName) {
        try {
            String sql = "SELECT state FROM airport.users WHERE username = '" + userName + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getString("state");
            } else {
                return "";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static int getUserId(String userName) {
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
}
