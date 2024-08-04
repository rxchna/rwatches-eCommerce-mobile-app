package com.example.rwatches_ecommerce_mobile_app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insertUser(UserModel user);

    @Query("SELECT * FROM users WHERE user_email = :userEmail")
    UserModel getUser(String userEmail);

    @Query("SELECT * FROM users WHERE user_email = :userEmail AND user_password = :userPassword")
    UserModel getUserByEmailAndPassword(String userEmail, String userPassword);
}
