package ui;

import javax.swing.*;
import com.csvreader.CsvReader;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewGoalGUI extends JFrame {
    private static final String USER_DIR = "src/storage/user";

    private ChildHomepageGUI childHomepageGUI;

    public ViewGoalGUI(ChildHomepageGUI childHomepageGUI, String username) throws IOException {
        super("Goal Status");
        this.childHomepageGUI = childHomepageGUI;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Your goal status", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        File goalFile = new File(USER_DIR, username + "/goal.csv");
        CsvReader csvReader = new CsvReader(new FileReader(goalFile), ',');
        csvReader.readHeaders();
        List<String[]> allElements = new ArrayList<>();
        while (csvReader.readRecord()) {
            allElements.add(csvReader.getValues());
        }
        csvReader.close();

        String[][] data = new String[allElements.size()][];
        allElements.toArray(data);

        String[] columnNames = {"GoalID", "AccountID", "GoalAmount", "GoalStartAmount", "GoalStatus"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            setVisible(false);
            this.childHomepageGUI.setVisible(true);
        });
        add(goBackButton, BorderLayout.SOUTH);

        pack();
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}