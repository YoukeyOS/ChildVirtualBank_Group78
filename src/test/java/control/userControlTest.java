package control;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class userControlTest {
    @Test
    public void testRegister() {
        userControl uc = new userControl();
        try {
            uc.register("test", "aa123456", "test", "test", "test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogin() {
        userControl uc = new userControl();
        try {
            uc.login("test", "aa123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}