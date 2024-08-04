package com.example.rwatches_ecommerce_mobile_app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    List<ProductModel> getProductsList();

    @Insert
    void insertProduct(ProductModel product);

    @Query("SELECT * FROM products WHERE product_id = :productID")
    ProductModel getProductsList(int productID);

}
