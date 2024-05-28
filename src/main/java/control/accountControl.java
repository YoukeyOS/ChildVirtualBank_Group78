package control;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import model.Transaction;
import model.Account;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class accountControl {
    private static final String USER_DIR = "src/storage/user";

    /**
     * Deposit money to the account
     * @param username
     * @param amount
     * @throws IOException
     */
    public static void deposit(String username, double amount) throws IOException {
        if (amount <= 0) {
            JOptionPane.showMessageDialog(null, "Deposit amount must be positive");
        }

        File accountFile = new File(USER_DIR, username + "/account.csv");
        List<String[]> allElements = new ArrayList<>();
        CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
        String[] headers = csvReader.getHeaders(); // get header
        while (csvReader.readRecord()) {
            allElements.add(csvReader.getValues());
        }
        csvReader.close();

        for (String[] strings : allElements) {
            if (strings[1].equals(username)) {
                double balance = Double.parseDouble(strings[2]);
                balance += amount;
                strings[2] = String.valueOf(balance);
            }
        }

        CsvWriter csvWriter = new CsvWriter(new FileWriter(accountFile), ',');
        csvWriter.writeRecord(headers); // write header
        for (String[] strings : allElements) {
            csvWriter.writeRecord(strings);
        }
        csvWriter.close();

        File transactionFile = new File(USER_DIR, username + "/transaction.csv");
        csvWriter = new CsvWriter(new FileWriter(transactionFile, true), ',');
        String[] transactionData = {Transaction.generateUniqueTransactionID(), "deposit", String.valueOf(amount), new Date().toString()};
        csvWriter.writeRecord(transactionData);
        csvWriter.close();
    }



    /**
     * Reward money to the account
     * @param username
     * @param amount
     * @throws IOException
     */
    public static void reward(String username, double amount) throws IOException {
        if (amount <= 0) {
            JOptionPane.showMessageDialog(null, "Deposit amount must be positive");
        }

        File accountFile = new File(USER_DIR, username + "/account.csv");
        List<String[]> allElements = new ArrayList<>();
        CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
        String[] headers = csvReader.getHeaders(); // get header
        while (csvReader.readRecord()) {
            allElements.add(csvReader.getValues());
        }
        csvReader.close();

        for (String[] strings : allElements) {
            if (strings[1].equals(username)) {
                double balance = Double.parseDouble(strings[2]);
                balance += amount;
                strings[2] = String.valueOf(balance);
            }
        }

        CsvWriter csvWriter = new CsvWriter(new FileWriter(accountFile), ',');
        csvWriter.writeRecord(headers); // write header
        for (String[] strings : allElements) {
            csvWriter.writeRecord(strings);
        }
        csvWriter.close();

        File transactionFile = new File(USER_DIR, username + "/transaction.csv");
        csvWriter = new CsvWriter(new FileWriter(transactionFile, true), ',');
        String[] transactionData = {Transaction.generateUniqueTransactionID(), "reward", String.valueOf(amount), new Date().toString()};
        csvWriter.writeRecord(transactionData);
        csvWriter.close();
    }

    /**
     * Withdraw money from the account
     * @param username
     * @param amount
     * @throws IOException
     */
    public static void withdraw(String username, double amount) throws IOException {
        if (amount <= 0) {
            JOptionPane.showMessageDialog(null, "Withdraw amount must be positive");
        }

        File accountFile = new File(USER_DIR, username + "/account.csv");
        List<String[]> allElements = new ArrayList<>();
        CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
        String[] headers = csvReader.getHeaders(); // get header
        while (csvReader.readRecord()) {
            allElements.add(csvReader.getValues());
        }
        csvReader.close();

        for (String[] strings : allElements) {
            if (strings[1].equals(username)) {
                double balance = Double.parseDouble(strings[2]);
                if (balance < amount) {
                    JOptionPane.showMessageDialog(null, "Your balance is not enough!");
                    return;
                }
                balance -= amount;
                strings[2] = String.valueOf(balance);
            }
        }

        CsvWriter csvWriter = new CsvWriter(new FileWriter(accountFile), ',');
        csvWriter.writeRecord(headers); // write header
        for (String[] strings : allElements) {
            csvWriter.writeRecord(strings);
        }
        csvWriter.close();

        File transactionFile = new File(USER_DIR, username + "/transaction.csv");
        csvWriter = new CsvWriter(new FileWriter(transactionFile, true), ',');
        String[] transactionData = {Transaction.generateUniqueTransactionID(), "withdraw", String.valueOf(amount), new Date().toString()};
        csvWriter.writeRecord(transactionData);
        csvWriter.close();
    }
    
    
    
   /**
    * Get the balance of the account
    * @param username
    * @return
    */
    public static double getBalance(String username) {
        
        double balance = 0.0;
        try {
            File accountFile = new File(USER_DIR, username + "/account.csv");
            CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
            csvReader.readHeaders(); // skip header
            while (csvReader.readRecord()) {
                if (csvReader.get("accountName").equals(username)) {
                    balance = Double.parseDouble(csvReader.get("balance"));
                    break;
                }
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balance;
    }

    /**
     * Get the account ID by username
     * @param username
     * @return
     */
    public static String getAccountIDByUsername(String username) {
        String accountID = "";
        try {
            File accountFile = new File(USER_DIR, username + "/account.csv");
            CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
            csvReader.readHeaders(); // skip header
            while (csvReader.readRecord()) {
                if (csvReader.get("accountName").equals(username)) {
                    accountID = csvReader.get("accountID");
                    break;
                }
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountID;
    }
    

    /**
     * Get the account by username
     * @param username
     * @return
     */
    public static boolean getAccountByUsername(String username) {
        try {
            File accountFile = new File(USER_DIR, username + "/account.csv");
            CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
            csvReader.readHeaders(); // skip header
            while (csvReader.readRecord()) {
                if (csvReader.get("accountName").equals(username)) {
                    return true;
                }
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    

    /**
     * Increase the balance of the saving account
     * @param username
     * @throws IOException
     */
    public void increaseBalance(String username) throws IOException {
        // Get the account details
        Account account = getAccount(username);

        // Check if the account is a saving account
        if (account.getAccountType().equals("saving")) {
            // Increase the balance by 100.05%
            double newBalance = account.getBalance() * 1.0005;

            // Update the account balance
            account.setBalance(newBalance);
            updateAccount(account);
        }
    }


    /**
     * Update the account balance
     * @param account
     */
    private void updateAccount(Account account) {
        try {
            File accountFile = new File(USER_DIR, account.getAccountName() + "/account.csv");
            List<String[]> allElements = new ArrayList<>();
            CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
            String[] headers = csvReader.getHeaders(); // get header
            while (csvReader.readRecord()) {
                allElements.add(csvReader.getValues());
            }
            csvReader.close();

            for (String[] strings : allElements) {
                if (strings[1].equals(account.getAccountName())) {
                    strings[2] = String.valueOf(account.getBalance());
                }
            }

            CsvWriter csvWriter = new CsvWriter(new FileWriter(accountFile), ',');
            csvWriter.writeRecord(headers); // write header
            for (String[] strings : allElements) {
                csvWriter.writeRecord(strings);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Get the account details
     * @param username
     * @return
     */
    private Account getAccount(String username) {
        // Get the account details
        Account account = new Account();
        try {
            File accountFile = new File(USER_DIR, username + "/account.csv");
            CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
            csvReader.readHeaders(); // skip header
            while (csvReader.readRecord()) {
                if (csvReader.get("accountName").equals(username)) {
                    account.setAccountID(csvReader.get("accountID"));
                    account.setAccountName(csvReader.get("accountName"));
                    account.setBalance(Double.parseDouble(csvReader.get("balance")));
                    account.setCurrency(csvReader.get("currency"));
                    account.setAccountType(csvReader.get("accountType"));
                    account.setAccountStatus(csvReader.get("accountStatus"));
                    break;
                }
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }
    
    /**
     * Get the saving account usernames
     * @return
     */
    public List<String> getSavingAccountUsernames() {
        List<String> usernames = new ArrayList<>();
        try {
            File accountDir = new File(USER_DIR);
            File[] userDirs = accountDir.listFiles();
            if (userDirs != null) {
                for (File userDir : userDirs) {
                    // Ignore the .DS_Store file
                    if (userDir.isDirectory()) {
                        File accountFile = new File(userDir, "account.csv");
                        CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
                        csvReader.readHeaders();
                        while (csvReader.readRecord()) {
                            if (csvReader.get("accountType").equals("saving")) {
                                usernames.add(userDir.getName());
                                break;
                            }
                        }
                        csvReader.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usernames;
    }
    
    /**
     * Get the account type by username
     * @param username
     * @return
     */
    public String getAccountType(String username) {
        String accountType = "";
        try {
            File accountFile = new File(USER_DIR, username + "/account.csv");
            CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
            csvReader.readHeaders(); // skip header
            while (csvReader.readRecord()) {
                if (csvReader.get("accountName").equals(username)) {
                    accountType = csvReader.get("accountType");
                    break;
                }
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountType;
    }
}