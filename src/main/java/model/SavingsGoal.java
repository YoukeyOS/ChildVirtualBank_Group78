package model;

public class SavingsGoal {
    // Private member variables to store the details of a savings goal
    private String goalID;
    private String accountID; // Associated account ID
    private double goalAmount; // The target amount for the savings goal
    private double goalStartAmount; // The account balance when the goal was set
    private String goalStatus; // Status of the goal, either "Active" or "Completed"

    // Constructor to initialize a savings goal with specific values
    public SavingsGoal(String goalID, String accountID, double goalAmount, double goalStartAmount, String goalStatus) {
        this.goalID = goalID;
        this.accountID = accountID;
        this.goalAmount = goalAmount;
        this.goalStartAmount = goalStartAmount;
        this.goalStatus = goalStatus;
    }

    // Default constructor
    public SavingsGoal() {
    }

    // Getter method for goalID
    public String getGoalID() {
        return goalID;
    }

    // Getter method for accountID
    public String getAccountID() {
        return accountID;
    }

    // Getter method for goalAmount
    public double getGoalAmount() {
        return goalAmount;
    }

    // Getter method for goalStartAmount
    public double getGoalStartAmount() {
        return goalStartAmount;
    }

    // Getter method for goalStatus
    public String getGoalStatus() {
        return goalStatus;
    }

    // Setter method for goalID
    public void setGoalID(String goalID) {
        this.goalID = goalID;
    }

    // Setter method for accountID
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    // Setter method for goalAmount
    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    // Setter method for goalStartAmount
    public void setGoalStartAmount(double goalStartAmount) {
        this.goalStartAmount = goalStartAmount;
    }

    // Setter method for goalStatus
    public void setGoalStatus(String goalStatus) {
        this.goalStatus = goalStatus;
    }
}
