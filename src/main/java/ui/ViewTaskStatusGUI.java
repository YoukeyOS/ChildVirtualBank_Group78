package ui;

import javax.swing.*;
import com.csvreader.CsvReader;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewTaskStatusGUI extends JFrame {
    private static final String TASK_DIR = "src/storage/Task";

    private ParentHomepageGUI parentHomepageGUI;

    public ViewTaskStatusGUI(ParentHomepageGUI parentHomepageGUI, String username) throws IOException {
        super("Task Status");
        this.parentHomepageGUI = parentHomepageGUI;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Your task status", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        File taskFile = new File(TASK_DIR, username + ".csv");
        CsvReader csvReader = new CsvReader(new FileReader(taskFile), ',');
        csvReader.readHeaders();
        List<String[]> allElements = new ArrayList<>();
        while (csvReader.readRecord()) {
            allElements.add(csvReader.getValues());
        }
        csvReader.close();

        String[][] data = new String[allElements.size()][];
        allElements.toArray(data);

        String[] columnNames = {"TaskID", "TaskDescription", "TaskStatus", "RewardAmount"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            setVisible(false);
            this.parentHomepageGUI.setVisible(true);
        });
        add(goBackButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //pack();
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
