package com.example.peerhub.model;
import com.google.firebase.Timestamp;

public class UserModel {

    private String username;
    private Timestamp createdTimestamp;
    private String userId;


    public UserModel() {
    }

    public UserModel( String username, Timestamp createdTimestamp, String userId) {

        this.username = username;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
    }





    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
