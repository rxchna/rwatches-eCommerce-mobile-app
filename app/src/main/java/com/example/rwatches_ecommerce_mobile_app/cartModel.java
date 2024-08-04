package com.example.rwatches_ecommerce_mobile_app;

import java.io.Serializable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data Transfer Object (DTO) class to hold information about each user's products in their cart
 */
@Entity(tableName = "cart")
public class cartModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userID;
    @ColumnInfo(name = "product_id")
    private int productID;
    @ColumnInfo(name = "product_qty")
    private int productQty;

    public cartModel(int id, int userID, int productID, int productQty) {
        this.id = id;
        this.userID = userID;
        this.productID = productID;
        this.productQty = productQty;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductQty() {
        return productQty;
    }
    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }
}
