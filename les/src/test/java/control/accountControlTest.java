package control;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class accountControlTest {
    @Test
    public void testDeposit() {
        accountControl ac = new accountControl();
        try {
            ac.deposit("test", 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReward() {
        accountControl ac = new accountControl();
        try {
            ac.reward("test", 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


















}