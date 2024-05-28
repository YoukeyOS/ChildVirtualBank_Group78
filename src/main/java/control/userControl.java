package control;

import model.Account;
import model.Transaction;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import javax.swing.JOptionPane;

    public class userControl {

        private static final String USER_DIR = "src/storage/user";

        // 用户注册
        public static boolean register(String username, String password, String userType,String accountType,String email) throws IOException {
            // 检查用户是否存在
            if (exists(username)) {
                return false;
            }
            // 检查密码长度
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(null, "Error: Password must be at least 6 characters long.");
                return false;
            }

            // 检查密码是否同时包含字母和数字
            if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d).+$")) {
                JOptionPane.showMessageDialog(null, "Error: Password must contain both letters and numbers.");
                return false;
            }

            // 检查密码是否和用户名相同
            if (password.equals(username)) {
                JOptionPane.showMessageDialog(null, "Error: Password cannot be the same as the username.");
                return false;
            }

            // 创建用户文件夹
            File userDir = new File(USER_DIR, username);
            userDir.mkdirs();

            String userID = User.generateUniqueUserID();
            User user = new User(userID, username, password, userType,email);

            // 创建用户信息文件
            File userFile = new File(userDir, "user.csv");

            CsvWriter csvWriter = null;
            try {
                csvWriter = new CsvWriter(new FileWriter(userFile), ',');
                String[] headers = {"UserID", "Username", "Password", "UserType","email"};
                csvWriter.writeRecord(headers);
                String[] userInfo = {user.getUserID(), user.getUserName(), user.getPassword(), user.getUserType(),user.getEmail()};
                csvWriter.writeRecord(userInfo);
            } finally {
                if (csvWriter != null) {
                    csvWriter.close();
                }
            }

            // 创建交易记录文件
            File transactionFile = new File(userDir, "transaction.csv");

            try {
                csvWriter = new CsvWriter(new FileWriter(transactionFile), ',');
                String[] headers = {"TransactionID", "Type", "Amount", "Date"};
                csvWriter.writeRecord(headers);
            } finally {
                if (csvWriter != null) {
                    csvWriter.close();
                }
            }
            // 创建账户文件
            File accountFile = new File(userDir, "account.csv");

            try {
                csvWriter = new CsvWriter(new FileWriter(accountFile), ',');
                String[] headers = {"accountID", "accountName", "balance", "accountType"};
                csvWriter.writeRecord(headers);
                String[] accountInfo = {user.getUserID(), user.getUserName(), "0", accountType};
                csvWriter.writeRecord(accountInfo);
            } finally {
                if (csvWriter != null) {
                    csvWriter.close();
                }
            }

            return true;
        }

        // 用户登录
        public static boolean login(String username, String password) throws IOException {
            // 检查用户是否存在
            if (!exists(username)) {
                return false;
            }

            // 读取账户信息文件
            File userFile = new File(USER_DIR, username + "/user.csv");
            CsvReader csvReader = new CsvReader(new FileReader(userFile), ',');
            csvReader.readHeaders(); // skip header
            while (csvReader.readRecord()) {
                // 验证账户信息
                if (csvReader.get("Password").equals(password)) {
                    return true;
                }
            }
            csvReader.close();

            return false;
        }

        // 查看名下账户信息
        public static List<Account> getAccounts(String username) throws IOException {
            List<Account> accounts = new ArrayList<>();

            // 读取账户信息文件
            File accountFile = new File(USER_DIR, username + "/account.csv");
            try (BufferedReader br = new BufferedReader(new FileReader(accountFile))) {
                String line = br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    // 解析账户信息
                    String[] parts = line.split(",");//账户类型,账户余额
                    //Account account = new Account(parts[0], Double.parseDouble(parts[1]));
                    Account account = new Account(parts[0], String.valueOf(Double.parseDouble(parts[1])));

                    accounts.add(account);
                }
            }

            return accounts;
        }

        // 查看交易记录
        public static List<Transaction> getTransactions(String username) throws IOException {
            List<Transaction> transactions = new ArrayList<>();

            // 读取交易记录文件
            File transactionFile = new File(USER_DIR, username + "/transaction.csv");
            try (BufferedReader br = new BufferedReader(new FileReader(transactionFile))) {
                String line = br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    // 解析交易记录
                    String[] parts = line.split(",");
                    Transaction transaction = new Transaction(parts[0], parts[1],
                            Double.parseDouble(parts[2]), parts[3]);
                    transactions.add(transaction);
                }
            }

            return transactions;
        }

        public static boolean exchange(String username, String newPassword, String email) throws IOException {
            boolean result = false;
            // 检查用户是否存在
            if (!exists(username)) {
                return false;
            }

            File userFile = new File(USER_DIR, username + "/user.csv");
            List<String[]> allElements = new ArrayList<>();
            CsvReader csvReader = new CsvReader(new FileReader(userFile), ',');
            String[] headers = csvReader.getHeaders(); // get header
            while (csvReader.readRecord()) {
                allElements.add(csvReader.getValues());
            }
            csvReader.close();

            for (String[] strings : allElements) {
                if (strings[4].equals(email)) {
                    strings[2] = newPassword;
                    result = true;
                }
            }
            CsvWriter csvWriter = new CsvWriter(new FileWriter(userFile), ',');
            csvWriter.writeRecord(headers); // write header
            for (String[] strings : allElements) {
                csvWriter.writeRecord(strings);
            }
            csvWriter.close();
            return result;
        }

        // 注销账户
        public static boolean unregister(String username) throws IOException {
            // 删除用户文件夹
            File userDir = new File(USER_DIR, username);
            return deleteDir(userDir);
        }

        // 判断用户是否存在
        private static boolean exists(String username) {
            File userDir = new File(USER_DIR, username);
            return userDir.exists();
        }

        // 删除文件夹
        private static boolean deleteDir(File dir) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
            return dir.delete();
        }

        public static String getUserTypeByUserName(String username) throws IOException {
            // 检查用户是否存在
            if (!exists(username)) {
                return null;
            }

            // 读取账户信息文件
            File userFile = new File(USER_DIR, username + "/user.csv");
            try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
                String line = br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    // 解析账户信息
                    String[] parts = line.split(",");
                    if (parts[1].equals(username)) {
                        return parts[3]; // return user type
                    }
                }
            }

            return null;
        }



    }



