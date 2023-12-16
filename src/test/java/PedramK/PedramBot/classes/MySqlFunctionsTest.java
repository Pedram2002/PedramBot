package PedramK.PedramBot.classes;


import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Statement;
import static org.junit.Assert.assertEquals;
//import static PedramK.PedramBot.classes.MySqlFunctions.getUserState;

public class MySqlFunctionsTest {
    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Connection connection;
    private Statement statement;

    @Before
    public void setup() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost/airport", "root", "123456");
        statement = connection.createStatement();
        // Создайте таблицу и добавьте тестовые данные
        createTable();
        insertTestData();
    }

    @After
    public void tearDown() throws SQLException {
        statement.execute("DROP TABLE airport.users");
        statement.close();
        connection.close();
    }

    @Test
    public void testGetUserState() {
        String result = MySqlFunctions.getUserState("testUser");

        // Проверяем, что результат соответствует ожидаемому значению
        Assertions.assertEquals("fa0ru0de0en0", result);
    }

    private void createTable() throws SQLException {
        statement.execute("CREATE TABLE airport.users (username VARCHAR(255), fa INT, ru INT, de INT, en INT)");
    }

    private void insertTestData() throws SQLException {
        statement.execute("INSERT INTO airport.users (username, fa, ru, de, en) VALUES ('testUser', 1, 1, 1, 1)");
    }

}
