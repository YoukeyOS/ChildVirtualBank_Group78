package model;

import java.util.UUID;
public class User {
    private String userID;
    private String userName;
    private String password;
    private String userType;
    private String email;


    public User(String userID, String userName, String password, String userType,String email) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.email = email;
    }

    //Getters
    public String getUserID() {
        return userID;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getUserType() {
        return userType;
    }
    public String getEmail() {
        return email;
    }

    //Setters
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public static String generateUniqueUserID() {
        return UUID.randomUUID().toString();
    }//generate unique userID
    public void setEmail(String email) {
        this.email = email;
    }


}
