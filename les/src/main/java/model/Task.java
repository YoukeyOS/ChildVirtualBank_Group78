package model;

public class Task {
    private String taskID;
    private String taskDescription;
    private String taskStatus;//两种状态：Active和Completed
    private double rewardAmount;

    public Task(String taskID, String taskDescription, String taskStatus, double rewardAmount) {
        this.taskID = taskID;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.rewardAmount = rewardAmount;
    }

    public Task() {
    }

    //Getters
    public String getTaskID() {
        return taskID;
    }
    public String getTaskDescription() {
        return taskDescription;
    }
    public String getTaskStatus() {
        return taskStatus;
    }
    public double getRewardAmount() {
        return rewardAmount;
    }
    //Setters
    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
    public void setRewardAmount(double rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

}
