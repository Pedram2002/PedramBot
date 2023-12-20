package PedramK.PedramBot.classes;
import PedramK.PedramBot.repository.SqlFunctions;
import org.junit.jupiter.api.*;

public class SqlFunctionsTest {
    @Test
    void testGetUserId() {
        int result = SqlFunctions.getUserId("testUser");

        Assertions.assertEquals(1, result);
    }

    @Test
    void testGetUserState() {
        String result = SqlFunctions.getUserState("testUser");

        Assertions.assertEquals("fa1ru0de1en0", result);
    }

    @Test
    void testGetUserStateWithParameters() {
        int result = SqlFunctions.getUserState("testUser", "fa");

        Assertions.assertEquals(1, result);
    }

    @Test
    void testLoadLangSetting() {
        String result = SqlFunctions.loadLangSetting("testUser");

        Assertions.assertEquals("English is Off , /On_eng\nGerman is On , /Off_de\nPersian is On , /Off_fa\nRussian is Off , /On_ru", result);
    }
}
