package com.example.rwatches_ecommerce_mobile_app;

import java.io.Serializable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Data Transfer Object (DTO) class to hold information about each product
 */
@Entity(tableName = "products")
public class ProductModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    private int productID;
    @ColumnInfo(name = "product_name")
    private String productName;
    @ColumnInfo(name = "product_desc")
    private String productDescription;
    @ColumnInfo(name = "product_image_url")
    private String productImageUrl;
    @ColumnInfo(name = "product_images_url")
    private String productImagesList;
    @ColumnInfo(name = "product_price")
    private double productPrice;

    public ProductModel(int productID, String productName, String productDescription, String productImageUrl, String productImagesList, double productPrice) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productImagesList = productImagesList;
        this.productPrice = productPrice;
    }

    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductImagesList() {
        return productImagesList;
    }

    public void setProductImagesList(String productImagesList) {
        this.productImagesList = productImagesList;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
