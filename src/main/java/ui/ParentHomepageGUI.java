package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class ParentHomepageGUI extends JFrame {
    private JLabel welcomeLabel;
    private JButton viewTaskStatusButton;
    private JButton setTaskButton;
    private JButton viewAccountDetailsButton;
    private JButton signOutButton;
    private JTextField kidUsernameTextField;
    private JLabel kidUsernameLabel;

    private MainGUI mainGUI;

    private static final String TASK_DIR = "src/storage/Task";

    public ParentHomepageGUI(String username, MainGUI mainGUI) {
        super("Parent Virtual Bank");
        this.mainGUI = mainGUI;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


        welcomeLabel = new JLabel(username + ", welcome to the Parent Virtual Bank!");
        kidUsernameLabel = new JLabel("Your kid's name :");
        kidUsernameTextField = new JTextField();
        kidUsernameTextField.setColumns(15);
        kidUsernameTextField.setMaximumSize(kidUsernameTextField.getPreferredSize());
        viewTaskStatusButton = new JButton("View Task Status");
        setTaskButton = new JButton("Set Task");
        //viewAccountDetailsButton = new JButton("View Account Details");
        signOutButton = new JButton("Sign Out");



        viewTaskStatusButton.addActionListener(e -> {
            String kidUsername = kidUsernameTextField.getText();
            if (kidUsername != null && !kidUsername.isEmpty()) {
                File kidFile = new File(TASK_DIR, kidUsername + ".csv");
                if (!kidFile.exists()) {
                    JOptionPane.showMessageDialog(this, "You have not set tasks for your child yet!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        this.setVisible(false);
                        ViewTaskStatusGUI viewTaskStatusGUI = new ViewTaskStatusGUI(this, kidUsername);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        setTaskButton.addActionListener(e -> {
            SetTaskGUI setTaskGUI = new SetTaskGUI(this);
            setVisible(false);
        });

       // viewAccountDetailsButton.addActionListener(e -> {
            // TODO: Implement view account details action
        //});

        signOutButton.addActionListener(e -> {
            setVisible(false);
            this.mainGUI.setVisible(true);
        });


        add(welcomeLabel);
        add(kidUsernameLabel);
        add(kidUsernameTextField);
        add(viewTaskStatusButton);
        add(setTaskButton);
        //add(viewAccountDetailsButton);
        add(signOutButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(400, 300);
        setVisible(true);
    }
}
