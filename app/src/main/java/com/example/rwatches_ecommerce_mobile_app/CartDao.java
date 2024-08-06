package com.example.rwatches_ecommerce_mobile_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart WHERE user_id = :user_id")
    List<CartModel> getUserCartList(int user_id);

    @Query("SELECT * FROM cart WHERE user_id = :user_id AND product_id = :product_id")
    CartModel getCartProduct(int user_id, int product_id);

    @Insert
    void insertUserCartProduct(CartModel userCartProduct);

    @Update
    void updateUserCartProduct(CartModel userCartProduct);

    @Delete
    void deleteUserCartProduct(CartModel userCartProduct);

    @Query("DELETE FROM cart WHERE user_id = :userId")
    void clearUserCart(int userId);
}
