package com.example.rwatches_ecommerce_mobile_app;

import java.io.Serializable;

public class ProductModel implements Serializable {

    private String productID;
    private String productName;
    private String productDescription;
    private String productImageUrl;
    private double productPrice;
    private int productQty;

    public ProductModel(String productID, String productName, String productDescription, String productImageUrl, double productPrice, int productQty) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productQty = productQty;
    }

    public String getProductID() {
        return productID;
    }
    public void setProductID(String productID) {
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

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    public int getProductQty() {
        return productQty;
    }
    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }
}
