package ui;

import javax.swing.*;
import java.awt.*;
import control.accountControl;
import java.io.IOException;

/**
 * Deposit GUI
 */
public class DepositGUI extends JFrame {
    private JLabel promptLabel;
    private JTextField amountTextField;
    private JButton depositButton;
    private JButton goBackButton;
    private ChildHomepageGUI childHomepageGUI;

    public DepositGUI(ChildHomepageGUI childHomepageGUI, String username) {
        super("Deposit");
        this.childHomepageGUI = childHomepageGUI;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        promptLabel = new JLabel("Please enter how much you want to deposit:");
        amountTextField = new JTextField();
        depositButton = new JButton("Deposit");
        goBackButton = new JButton("Go Back");

        depositButton.addActionListener(e -> {
            // 存款事件处理
            double amount = Double.parseDouble(amountTextField.getText());
            try {
                accountControl.deposit(username, amount);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            setVisible(false);
            childHomepageGUI.updateBalance(username);
            childHomepageGUI.setVisible(true);
        });

        goBackButton.addActionListener(e -> {
            // 返回
            setVisible(false);
            childHomepageGUI.updateBalance(username);
            childHomepageGUI.setVisible(true);
        });

        add(promptLabel);
        add(amountTextField);
        add(depositButton);
        add(goBackButton);

        pack();
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
