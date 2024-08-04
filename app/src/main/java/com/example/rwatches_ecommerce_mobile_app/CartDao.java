package com.example.rwatches_ecommerce_mobile_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * from cart where user_id = :user_id")
    List<CartModel> geUserCartList(int user_id);

    @Insert
    void insertUserCartProduct(CartModel userCartProduct);

    @Update
    void updateUserCartProduct(CartModel userCartProduct);

    @Delete
    void deleteUserCartProduct(CartModel userCartProduct);
}
