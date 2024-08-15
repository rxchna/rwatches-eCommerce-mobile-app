package com.example.rwatches_ecommerce_mobile_app;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivBackIconProd;
    ImageView ivCartIconProd;
    ImageView ivHomeIconProd;
    ImageView ivProductImage;
    TextView tvProductName;
    TextView tvProdDescription;
    TextView tvProductPrice;
    TextView tvCartProdQty;
    ImageView ivDecrementIcon;
    ImageView ivIncrementIcon;
    Button btnAddProductToCart;
    ProductModel productModel;
    AppDatabase appDatabase;
    int prodQty = 0;
    int curr_userID;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);

        appDatabase = AppDatabase.getInstance(this);

        // Retrieve user_id from the Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user_id")) {
            curr_userID = intent.getIntExtra("user_id", -1);
        }

        ivBackIconProd = findViewById(R.id.ivBackIconProd);
        ivCartIconProd = findViewById(R.id.ivCartIconProd);
        ivHomeIconProd = findViewById(R.id.ivHomeIconProd);
        tvProductName = findViewById(R.id.tvProductName);
        tvProdDescription = findViewById(R.id.tvProdDescription);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvCartProdQty = findViewById(R.id.tvCartProdQty);
        ivDecrementIcon = findViewById(R.id.ivDecrementIcon);
        ivIncrementIcon = findViewById(R.id.ivIncrementIcon);
        btnAddProductToCart = findViewById(R.id.btnAddProductToCart);
        viewPager = findViewById(R.id.viewPager);

        ivBackIconProd.setOnClickListener(this);
        ivCartIconProd.setOnClickListener(this);
        ivHomeIconProd.setOnClickListener(this);
        ivDecrementIcon.setOnClickListener(this);
        ivIncrementIcon.setOnClickListener(this);
        btnAddProductToCart.setOnClickListener(this);

        Intent i = getIntent();
        productModel = (ProductModel) i.getSerializableExtra("selectedIndex");
        tvProductName.setText(productModel.getProductName());
        tvProductPrice.setText(String.format("%.2f", productModel.getProductPrice()));
        tvProdDescription.setText(productModel.getProductDescription());
        prodQty = Integer.parseInt(tvCartProdQty.getText().toString());

        // Get product images list
        List<String> imagesList = getImageUrls();

        ProductImageAdapter adapter = new ProductImageAdapter(this, imagesList);
        viewPager.setAdapter(adapter);
    }
    
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivBackIconProd) {
            finish();
        } 
        else if (view.getId() == R.id.ivHomeIconProd) {
            Intent intentCart = new Intent(getApplicationContext(), ProductsActivity.class);
            intentCart.putExtra("user_id", curr_userID);
            startActivity(intentCart);
        }
        else if (view.getId() == R.id.ivCartIconProd) {
            Intent intentCart = new Intent(getApplicationContext(), CartActivity.class);
            intentCart.putExtra("user_id", curr_userID);
            startActivity(intentCart);
        }
        else if (view.getId() == R.id.ivDecrementIcon) {
            if (prodQty >= 1) {
                prodQty--;
                tvCartProdQty.setText(String.valueOf(prodQty));
            }
        }
        else if (view.getId() == R.id.ivIncrementIcon) {
            prodQty++;
            tvCartProdQty.setText(String.valueOf(prodQty));
        }
        else if (view.getId() == R.id.btnAddProductToCart) {
            addProductToCart();
        }
    }

    void addProductToCart() {
        // Check if product has already been added to user's cart
        CartModel existingCartItem = appDatabase.cartDao().getCartProduct(curr_userID, productModel.getProductID());

        if (existingCartItem == null) {
            // Add new product to cart
            CartModel cartItem = new CartModel(curr_userID, productModel.getProductID(), prodQty);
            appDatabase.cartDao().insertUserCartProduct(cartItem);
        }
        else {
            // Update existing cart product's quantity
            int updatedQuantity = existingCartItem.getProductQty() + prodQty;
            existingCartItem.setProductQty(updatedQuantity);
            appDatabase.cartDao().updateUserCartProduct(existingCartItem);
        }

        // Show Toast message
        Toast.makeText(this, "Product added to cart!", Toast.LENGTH_SHORT).show();
    }

    // Method to retrieve product images list
    List<String> getImageUrls() {
        // Retrieve and split the image URLs
        String imageUrlString = productModel.getProductImagesList();

        // Split string by ","
        return Arrays.asList(imageUrlString.split(","));
    }
}
