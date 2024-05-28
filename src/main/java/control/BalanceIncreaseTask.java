package control;

import java.io.IOException;
import java.util.TimerTask;

public class BalanceIncreaseTask extends TimerTask {

    private accountControl accountControl;
    private String username;
    
    /**
     * Constructor for BalanceIncreaseTask
     * @param accountControl
     * @param username
     */
    public BalanceIncreaseTask(accountControl accountControl, String username) {
        this.accountControl = accountControl;
        this.username = username;
    }

    @Override
    /**
     * Run the task to increase the balance of the user
     */
    public void run() {
        try {
            accountControl.increaseBalance(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
