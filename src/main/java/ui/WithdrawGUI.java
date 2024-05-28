package ui;

import javax.swing.*;
import java.awt.*;
import control.accountControl;
import java.io.IOException;

public class WithdrawGUI extends JFrame {
    private JLabel promptLabel;
    private JTextField amountTextField;
    private JButton withdrawButton;
    private JButton goBackButton;
    private ChildHomepageGUI childHomepageGUI;
    /**
     * Constructor for WithdrawGUI
     * @param childHomepageGUI
     * @param username
     */
    public WithdrawGUI(ChildHomepageGUI childHomepageGUI, String username) {
        super("Withdraw");
        this.childHomepageGUI = childHomepageGUI;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        promptLabel = new JLabel("Please enter how much you want to withdraw:");
        amountTextField = new JTextField();
        withdrawButton = new JButton("Withdraw");
        goBackButton = new JButton("Go Back");

        withdrawButton.addActionListener(e -> {
            double amount = Double.parseDouble(amountTextField.getText());
            try {
                accountControl.withdraw(username, amount);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            setVisible(false);
            childHomepageGUI.updateBalance(username);
            childHomepageGUI.setVisible(true);
        });

        goBackButton.addActionListener(e -> {
            setVisible(false);
            childHomepageGUI.updateBalance(username);
            childHomepageGUI.setVisible(true);
        });

        add(promptLabel);
        add(amountTextField);
        add(withdrawButton);
        add(goBackButton);

        pack();
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
