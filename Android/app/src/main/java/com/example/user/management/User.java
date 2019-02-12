package com.example.user.management;

/**
 * Created by user on 2017-08-01.
 */

public class User {

    String userID;
    String userAge;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public User(String userID, String userAge) {
        this.userID = userID;
        this.userAge = userAge;
    }
}

