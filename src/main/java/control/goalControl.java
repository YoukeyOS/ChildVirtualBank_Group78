package control;

// Imports necessary classes for file handling, CSV reading/writing, and the SavingsGoal model
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import model.SavingsGoal;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class goalControl {
    // Directory where user files are stored
    private static final String USER_DIR = "src/storage/user";

    // Method to generate a unique ID for a savings goal
    public static void generateGoalID(SavingsGoal goal) {
        goal.setGoalID(java.util.UUID.randomUUID().toString());
    }

    // Method to set a new savings goal for a user
    public static void setGoal(SavingsGoal goal, String username) throws IOException {
        File goalFile = new File(USER_DIR, username + "/goal.csv");
        // Create the file if it does not exist
        if (!goalFile.exists()) {
            goalFile.createNewFile();
        }
        List<String[]> allElements = new ArrayList<>();
        CsvReader csvReader = new CsvReader(new FileReader(goalFile), ',');
        csvReader.readHeaders(); // Skip header
        // Read all existing records from the CSV file and store them in a list
        while (csvReader.readRecord()) {
            allElements.add(csvReader.getValues());
        }
        csvReader.close();

        // Set the new goal information
        String[] goalInfo = {goal.getGoalID(), goal.getAccountID(), String.valueOf(goal.getGoalAmount()), String.valueOf(goal.getGoalStartAmount()), goal.getGoalStatus()};
        allElements.add(goalInfo);

        // Write the updated goal records back to the CSV file
        CsvWriter csvWriter = new CsvWriter(new FileWriter(goalFile, false), ',');
        csvWriter.writeRecord(new String[]{"GoalID", "AccountID", "GoalAmount", "GoalStartAmount", "GoalStatus"});
        for (String[] strings : allElements) {
            csvWriter.writeRecord(strings);
        }
        csvWriter.close();
    }

    // Method to get all savings goals for a user by username
    public static List<SavingsGoal> getGoalsByUsername(String username) throws IOException {
        File goalFile = new File(USER_DIR, username + "/goal.csv");
        CsvReader csvReader = new CsvReader(new FileReader(goalFile), ',');
        csvReader.readHeaders(); // Skip header
        List<SavingsGoal> goals = new ArrayList<>();
        // Read each record and create SavingsGoal objects to store in the list
        while (csvReader.readRecord()) {
            SavingsGoal goal = new SavingsGoal();
            goal.setGoalID(csvReader.get("GoalID"));
            goal.setAccountID(csvReader.get("AccountID"));
            goal.setGoalAmount(Double.parseDouble(csvReader.get("GoalAmount")));
            goal.setGoalStartAmount(Double.parseDouble(csvReader.get("GoalStartAmount")));
            goal.setGoalStatus(csvReader.get("GoalStatus"));
            goals.add(goal);
        }
        csvReader.close();
        return goals;
    }

    // Method to check and update the status of each savings goal for a user
    public static void checkGoalStatus(List<SavingsGoal> goals, String username) throws IOException {
        File accountFile = new File(USER_DIR, username + "/account.csv");
        CsvReader csvReader = new CsvReader(new FileReader(accountFile), ',');
        csvReader.readHeaders(); // Skip header
        double balance = 0.0;
        // Read the user's balance from the account CSV file
        while (csvReader.readRecord()) {
            if (csvReader.get("accountName").equals(username)) {
                balance = Double.parseDouble(csvReader.get("balance"));
                break;
            }
        }
        csvReader.close();

        // Check if the user's balance meets or exceeds the goal amount plus start amount
        for (SavingsGoal goal : goals) {
            if (balance >= goal.getGoalStartAmount() + goal.getGoalAmount()) {
                goal.setGoalStatus("Completed");
            }
        }

        // Write the updated goal statuses back to the goal CSV file
        File goalFile = new File(USER_DIR, username + "/goal.csv");
        CsvWriter csvWriter = new CsvWriter(new FileWriter(goalFile, false), ',');
        csvWriter.writeRecord(new String[]{"GoalID", "AccountID", "GoalAmount", "GoalStartAmount", "GoalStatus"});
        for (SavingsGoal goal : goals) {
            String[] goalInfo = {goal.getGoalID(), goal.getAccountID(), String.valueOf(goal.getGoalAmount()), String.valueOf(goal.getGoalStartAmount()), goal.getGoalStatus()};
            csvWriter.writeRecord(goalInfo);
        }
        csvWriter.close();
    }
}
