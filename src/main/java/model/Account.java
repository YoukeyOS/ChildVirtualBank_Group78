package model;

public class Account {
    private String accountID;
    private String accountName;
    private double balance;
    private String currency;
    private String accountType;//true for current, false for saving.
    private String accountStatus;

    public Account(String accountID, String accountName, double balance, String currency, String accountType, String accountStatus) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.balance = balance;
        this.currency = currency;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }

    public Account(String part, String accountName) {
    }

    public Account() {

    }

    //Getters
    public String getAccountID() {
        return accountID;
    }
    public String getAccountName() {
        return accountName;
    }
    public double getBalance() {
        return balance;
    }
    public String getCurrency() {
        return currency;
    }
    public String getAccountType() {
        return accountType;
    }
    public String getAccountStatus() {
        return accountStatus;
    }
    //Setters
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }


}
