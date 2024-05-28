package ui;

// Imports necessary classes for goal control, account control, and UI components
import control.goalControl;
import model.SavingsGoal;
import control.accountControl;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SetGoalGUI extends JFrame {
    // UI components
    private JLabel promptLabel;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JButton setButton;
    private JButton goBackButton;

    // Reference to the child homepage GUI
    private ChildHomepageGUI childHomepageGUI;

    // Constructor for SetGoalGUI
    public SetGoalGUI(ChildHomepageGUI childHomepageGUI, String username) {
        super("Set Goal"); // Set the window title
        this.childHomepageGUI = childHomepageGUI;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Set the layout manager

        // Initialize UI components
        promptLabel = new JLabel("Please set your deposit goal!");
        amountLabel = new JLabel("Enter your goal amount:");
        amountTextField = new JTextField();
        setButton = new JButton("Set");
        goBackButton = new JButton("Go Back");

        // Action listener for the set button
        setButton.addActionListener(e -> {
            // Get the goal amount entered by the user
            double goalAmount = Double.parseDouble(amountTextField.getText());
            // Get the current balance for the user
            double goalStartAmount = accountControl.getBalance(username);

            // Create a new savings goal and set its properties
            SavingsGoal goal = new SavingsGoal();
            goalControl.generateGoalID(goal);
            goal.setAccountID(username);
            goal.setGoalAmount(goalAmount);
            goal.setGoalStartAmount(goalStartAmount);
            goal.setGoalStatus("Active");
            try {
                // Save the goal using the goalControl class
                goalControl.setGoal(goal, username);
                // Print the goal information to the console
                System.out.println("Goal set: " + goal.getGoalID() + ", " + goal.getAccountID() + ", " + goal.getGoalAmount() + ", " + goal.getGoalStartAmount() + ", " + goal.getGoalStatus());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Hide the current window and show the child homepage GUI
            setVisible(false);
            this.childHomepageGUI.updateBalance(username);
            this.childHomepageGUI.setVisible(true);
        });

        // Action listener for the go back button
        goBackButton.addActionListener(e -> {
            // Hide the current window and show the child homepage GUI
            setVisible(false);
            this.childHomepageGUI.updateBalance(username);
            this.childHomepageGUI.setVisible(true);
        });

        // Add components to the frame
        add(promptLabel);
        add(amountLabel);
        add(amountTextField);
        add(setButton);
        add(goBackButton);

        // Configure the frame
        pack();
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

