package ui;

import com.csvreader.CsvReader;
import control.goalControl;
import model.SavingsGoal;
import control.accountControl;
import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class ChildHomepageGUI extends JFrame {

    private static final String USER_DIR = "src/storage/user";
    private JLabel welcomeLabel;
    JLabel balanceLabel;
    private JLabel accountTypeLable;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton viewTransactionButton;
    private JButton transactionReportButton; 
    private JButton setGoalButton;
    private JButton viewGoalButton;
    private JButton viewGoalStatusButton;
    private JButton viewTaskButton;
    private JButton signOutButton;
    private accountControl accountControl = new accountControl();

    private MainGUI mainGUI;

    double balance;

    /**
     * Constructor for ChildHomepageGUI
     * @param username
     * @param mainGUI
     */

    public ChildHomepageGUI(String username, MainGUI mainGUI) {
        super("Child Virtual Bank");
        this.mainGUI = mainGUI;
        this.balance = getBalance(username);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        welcomeLabel = new JLabel(username + ", welcome to the Child Virtual Bank!");
        balanceLabel = new JLabel("Your balance is: " + balance);
        accountTypeLable = new JLabel("Your account type is: " + accountControl.getAccountType(username));
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        viewTransactionButton = new JButton("View Transaction");
        transactionReportButton = new JButton("Transaction Report"); 
        setGoalButton = new JButton("Set Goal");
        viewGoalButton = new JButton("View Goal Status");
        viewGoalStatusButton = new JButton("View Goal Progress");
        viewTaskButton = new JButton("View Task");
        //viewAccountDetailsButton = new JButton("View Account Details");
        signOutButton = new JButton("Sign Out");


        depositButton.addActionListener(e -> {
            DepositGUI depositGUI = new DepositGUI(this, username);
            setVisible(false);
            updateBalance(username);
        });
        withdrawButton.addActionListener(e -> {
            WithdrawGUI withdrawGUI = new WithdrawGUI(this, username);
            setVisible(false);
        });
        viewTransactionButton.addActionListener(e -> {
            try {
                ViewTransactionGUI viewTransactionGUI = new ViewTransactionGUI(this, username);
                setVisible(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        transactionReportButton.addActionListener(e -> {
            try {
                TransactionReportGUI transactionReportGUI = new TransactionReportGUI(this,username);
            } catch (IOException e1) {
                
                e1.printStackTrace();
            } // Create a new TransactionReportGUI instance
            setVisible(false); // Set the current GUI to invisible
        });
        setGoalButton.addActionListener(e -> {

            SetGoalGUI setGoalGUI = new SetGoalGUI(this, username);
            setVisible(false);
        });
        viewGoalButton.addActionListener(e -> {

            List<SavingsGoal> goals = null;
            try {
                goals = goalControl.getGoalsByUsername(username);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try {
                goalControl.checkGoalStatus(goals,username);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                ViewGoalStatusGUI viewGoalStatusGUI = new ViewGoalStatusGUI(this, username);
                setVisible(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        viewGoalStatusButton.addActionListener(e -> {
            try {
                ViewGoalGUI viewGoalGUI = new ViewGoalGUI(this, username);
                setVisible(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        viewTaskButton.addActionListener(e -> {
            try {
                ViewTaskGUI viewTaskGUI = new ViewTaskGUI(this, username);
                updateBalance(username);
                setVisible(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
        //viewAccountDetailsButton.addActionListener(e -> {
            //TODO  view account details action
        //});

        signOutButton.addActionListener(e -> {
            setVisible(false);
            this.mainGUI.setVisible(true);
        });

        add(welcomeLabel);
        add(balanceLabel);
        add(accountTypeLable);
        add(depositButton);
        add(withdrawButton);
        add(viewTransactionButton);
        add(transactionReportButton); 
        add(setGoalButton);
        add(viewGoalButton);
        add(viewGoalStatusButton);
        add(viewTaskButton);
        //add(viewAccountDetailsButton);
        add(signOutButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(400, 300);
        setVisible(true);
    }



    static double getBalance(String username) {
        double balance = 0.0;
        try {
            File accountFile = new File(USER_DIR, username + "/account.csv");
            CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
            csvReader.readHeaders(); // 跳过csv文件头
            while (csvReader.readRecord()) {
                if (csvReader.get("accountName").equals(username)) {
                    DecimalFormat df = new DecimalFormat("#.###");
                    balance = Double.parseDouble(df.format(Double.parseDouble(csvReader.get("balance"))));
                    break;
                }
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public void updateBalance(String username) {
        double balance = getBalance(username);
        balanceLabel.setText("Your balance is: " + balance);
    }
}
