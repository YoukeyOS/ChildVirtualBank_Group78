package ui;

import control.taskControl;
import model.Task;
import control.accountControl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SetTaskGUI extends JFrame {
    private JLabel promptLabel;
    private JLabel kidUsernameLabel;
    private JTextField kidUsernameTextField;
    private JLabel taskDescriptionLabel;
    private JTextField taskDescriptionTextField;
    private JLabel rewardAmountLabel;
    private JTextField rewardAmountTextField;
    private JButton setButton;
    private JButton goBackButton;

    private ParentHomepageGUI parentHomepageGUI;

    public SetTaskGUI(ParentHomepageGUI parentHomepageGUI) {
        super("Set Task");
        this.parentHomepageGUI = parentHomepageGUI;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        promptLabel = new JLabel("Please set your task!");
        kidUsernameLabel = new JLabel("Please enter your kid's username:");
        kidUsernameTextField = new JTextField();
        taskDescriptionLabel = new JLabel("Enter your task description:");
        taskDescriptionTextField = new JTextField();
        rewardAmountLabel = new JLabel("Enter your reward amount:");
        rewardAmountTextField = new JTextField();
        setButton = new JButton("Set");
        goBackButton = new JButton("Go Back");

        setButton.addActionListener(e -> {

            String kidUsername = kidUsernameTextField.getText();
            String taskDescription = taskDescriptionTextField.getText();
            double rewardAmount = Double.parseDouble(rewardAmountTextField.getText());

            if (accountControl.getAccountByUsername(kidUsername) == false) {
                JOptionPane.showMessageDialog(this, "Your child has not registered an account yet!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                Task task = new Task();
                task.setTaskID(accountControl.getAccountIDByUsername(kidUsername));
                task.setTaskDescription(taskDescription);
                task.setRewardAmount(rewardAmount);
                task.setTaskStatus("Active");
                try {
                    taskControl.setTask(task, kidUsername);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
                this.parentHomepageGUI.setVisible(true);
            }
        });

        goBackButton.addActionListener(e -> {
            // Go back button action
            setVisible(false);
            this.parentHomepageGUI.setVisible(true);
        });

        add(promptLabel);
        add(kidUsernameLabel);
        add(kidUsernameTextField);
        add(taskDescriptionLabel);
        add(taskDescriptionTextField);
        add(rewardAmountLabel);
        add(rewardAmountTextField);
        add(setButton);
        add(goBackButton);

        pack();
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
