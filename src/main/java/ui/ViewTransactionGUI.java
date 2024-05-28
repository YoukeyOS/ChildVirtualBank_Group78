package ui;

import javax.swing.*;
import com.csvreader.CsvReader;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewTransactionGUI extends JFrame {
    private static final String USER_DIR = "src/storage/user";

    private ChildHomepageGUI childHomepageGUI;

    public ViewTransactionGUI(ChildHomepageGUI childHomepageGUI, String username) throws IOException {
        super("Transaction History");
        this.childHomepageGUI = childHomepageGUI;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Your transaction history", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        File transactionFile = new File(USER_DIR, username + "/transaction.csv");
        CsvReader csvReader = new CsvReader(new FileReader(transactionFile), ',');
        csvReader.readHeaders();
        List<String[]> allElements = new ArrayList<>();
        while (csvReader.readRecord()) {
            allElements.add(csvReader.getValues());
        }
        csvReader.close();

        String[][] data = new String[allElements.size()][];
        allElements.toArray(data);

        String[] columnNames = {"TransactionID", "Type", "Amount", "Date"};

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
