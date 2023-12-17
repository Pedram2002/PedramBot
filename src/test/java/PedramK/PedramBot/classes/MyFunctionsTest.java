package PedramK.PedramBot.classes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static PedramK.PedramBot.classes.MyFunctions.encodePedramDictionary;
import static PedramK.PedramBot.classes.MyFunctions.onlineDic;

public class MyFunctionsTest {
    @Test
    void testOnlineDic() {
        String result = MyFunctions.onlineDic("hi");
        Assertions.assertEquals("☻enru☺hi=◘Гавайи◙☻enfa☺hi=◘فرياد خوش امد مثل هالو و چطورى و همچنين بجاى اهاى بکار ميرود◙", result);
    }
    @Test
    void testEncodePedramDictionary() {
        String result = MyFunctions.encodePedramDictionary(onlineDic("hi"), "testUser");

        Assertions.assertEquals("<b>hi</b> <i></i>\n <code>فرياد خوش امد مثل هالو و چطورى و همچنين بجاى اهاى بکار ميرود</code>", result.trim());
    }
}
