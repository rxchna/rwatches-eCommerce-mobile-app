package com.example.rwatches_ecommerce_mobile_app;

import java.io.Serializable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data Transfer Object (DTO) class to hold information about each user
 */
@Entity(tableName = "users")
public class UserModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private int userID;
    @ColumnInfo(name = "user_email")
    private String userEmail;
    @ColumnInfo(name = "user_password")
    private String userPassword;
    @ColumnInfo(name = "user_fullname")
    private String userFullName;

    public UserModel(String userEmail, String userPassword, String userFullName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFullName = userFullName;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFullName() {
        return userFullName;
    }
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}
