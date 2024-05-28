package model;

import java.util.UUID;

public class Transaction {
    private String transactionID;
    private String accountID;
    private String transactionType;
    private double amount;
    private String currency;
    private String transactionDate;
    private String transactionStatus;
    private String transactionDescription;

    public Transaction(String transactionID, String accountID, String transactionType, double amount, String currency, String transactionDate, String transactionStatus, String transactionDescription) {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.transactionType = transactionType;
        this.amount = amount;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.transactionStatus = transactionStatus;
        this.transactionDescription = transactionDescription;
    }

    public Transaction(String part, String part1, double v, String part2) {
    }

    //Getters
    public String getTransactionID() {
        return transactionID;
    }
    public String getAccountID() {
        return accountID;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public double getAmount() {
        return amount;
    }
    public String getCurrency() {
        return currency;
    }
    public String getTransactionDate() {
        return transactionDate;
    }
    public String getTransactionStatus() {
        return transactionStatus;
    }
    public String getTransactionDescription() {
        return transactionDescription;
    }
    //Setters
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    //generate unique transactionID
    public static String generateUniqueTransactionID() {
        return UUID.randomUUID().toString();
    }
}
