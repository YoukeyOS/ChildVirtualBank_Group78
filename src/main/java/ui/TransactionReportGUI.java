package ui;

import com.csvreader.CsvReader;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionReportGUI extends JFrame {
    private static final String USER_DIR = "src/storage/User";
    

    /**
     * Constructor for TransactionReportGUI
     * @param childHomepageGUI
     * @param username
     * @throws IOException
     */
    public TransactionReportGUI(ChildHomepageGUI childHomepageGUI,String username) throws IOException {
        super("Transaction Report");
        setLayout(new BorderLayout());

        JPanel datePanel = new JPanel();
        SpinnerDateModel model1 = new SpinnerDateModel();
        JSpinner dateSpinner1 = new JSpinner(model1);
        SpinnerDateModel model2 = new SpinnerDateModel();
        JSpinner dateSpinner2 = new JSpinner(model2);
        JButton generateButton = new JButton("Generate Report");
        datePanel.add(new JLabel("From:"));
        datePanel.add(dateSpinner1);
        datePanel.add(new JLabel("To:"));
        datePanel.add(dateSpinner2);
        datePanel.add(generateButton);
        add(datePanel, BorderLayout.NORTH);

        JTextArea reportArea = new JTextArea();
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        // 添加返回按钮
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> {
            setVisible(false);
            childHomepageGUI.setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(goBackButton, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);

        generateButton.addActionListener(e -> {
            LocalDate startDate = ((java.util.Date) dateSpinner1.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = ((java.util.Date) dateSpinner2.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            try {
                File transactionFile = new File(USER_DIR, username + "/transaction.csv");
                CsvReader csvReader = new CsvReader(new FileReader(transactionFile), ',');
                csvReader.readHeaders();
                List<String[]> allElements = new ArrayList<>();
                while (csvReader.readRecord()) {
                    String dateString = csvReader.get("Date"); // 确认CSV文件中的列名
                    if (dateString != null && !dateString.isEmpty()) {
                        DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                        ZonedDateTime dateTime = ZonedDateTime.parse(dateString, parseFormatter);
                        LocalDate date = dateTime.toLocalDate();
                        if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
                            allElements.add(csvReader.getValues());
                        }
                    }
                }
                csvReader.close();

                // 初始化统计数据
                double maxAmount = Double.MIN_VALUE;
                String maxTransaction = "";
                double minAmount = Double.MAX_VALUE;
                String minTransaction = "";
                double totalAmount = 0.0;

                // 遍历所有交易记录
                for (String[] record : allElements) {
                    double amount = Double.parseDouble(record[2]);
                    totalAmount += amount;
                    if (amount > maxAmount) {
                        maxAmount = amount;
                        maxTransaction = String.join(", ", record);
                    }
                    if (amount < minAmount) {
                        minAmount = amount;
                        minTransaction = String.join(", ", record);
                    }
                }

                // 清空报告区域并显示统计数据
                reportArea.setText("");
                reportArea.append("Max Transaction: " + maxTransaction + "\n");
                reportArea.append("Min Transaction: " + minTransaction + "\n");
                reportArea.append("Total Amount: " + totalAmount + "\n");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        pack();
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
