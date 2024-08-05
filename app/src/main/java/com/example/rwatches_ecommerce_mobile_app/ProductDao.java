package com.example.rwatches_ecommerce_mobile_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    List<ProductModel> getProductsList();

    @Insert
    void insertProduct(ProductModel product);

    @Update
    void updateProduct(ProductModel product);

    @Delete
    void deleteProduct(ProductModel product);

    @Query("SELECT * FROM products WHERE product_id = :productID")
    ProductModel getProductsList(int productID);

    @Query("SELECT COUNT(*) FROM products")
    int getProductsCount();

}
