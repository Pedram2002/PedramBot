package PedramK.PedramBot.classes;
import org.junit.jupiter.api.*;

public class MySqlFunctionsTest {
    @Test
    void testGetUserId() {
        int result = MySqlFunctions.getUserId("testUser");

        Assertions.assertEquals(1, result);
    }

    @Test
    void testGetUserState() {
        String result = MySqlFunctions.getUserState("testUser");

        Assertions.assertEquals("fa1ru0de1en0", result);
    }

    @Test
    void testGetUserStateWithParameters() {
        int result = MySqlFunctions.getUserState("testUser", "fa");

        Assertions.assertEquals(1, result);
    }

    @Test
    void testLoadLangSetting() {
        String result = MySqlFunctions.loadLangSetting("testUser");

        Assertions.assertEquals("English is Off , /On_eng\nGerman is On , /Off_de\nPersian is On , /Off_fa\nRussian is Off , /On_ru", result);
    }
}
