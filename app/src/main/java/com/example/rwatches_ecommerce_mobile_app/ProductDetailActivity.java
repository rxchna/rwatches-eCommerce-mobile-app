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

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivBackIconProd;
    ImageView ivCartIconProd;
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
        ivProductImage = findViewById(R.id.ivProductImage);
        tvProductName = findViewById(R.id.tvProductName);
        tvProdDescription = findViewById(R.id.tvProdDescription);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvCartProdQty = findViewById(R.id.tvCartProdQty);
        ivDecrementIcon = findViewById(R.id.ivDecrementIcon);
        ivIncrementIcon = findViewById(R.id.ivIncrementIcon);
        btnAddProductToCart = findViewById(R.id.btnAddProductToCart);

        ivBackIconProd.setOnClickListener(this);
        ivCartIconProd.setOnClickListener(this);
        ivDecrementIcon.setOnClickListener(this);
        ivIncrementIcon.setOnClickListener(this);
        btnAddProductToCart.setOnClickListener(this);

        Intent i = getIntent();
        productModel = (ProductModel) i.getSerializableExtra("selectedIndex");
        tvProductName.setText(productModel.getProductName());
        tvProductPrice.setText(String.valueOf(productModel.getProductPrice()));
        tvProdDescription.setText(productModel.getProductDescription());
        prodQty = Integer.parseInt(tvCartProdQty.getText().toString());
        int resid = getApplicationContext().getResources().getIdentifier(productModel.getProductImageUrl(), "drawable", getApplicationContext().getPackageName());
        ivProductImage.setImageResource(resid);
    }
    
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivBackIconProd) {
            finish();
        } 
        else if (view.getId() == R.id.ivCartIconProd) {
            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(intent);
        } 
        else if (view.getId() == R.id.ivDecrementIcon) {
            if (prodQty >= 1) {
                prodQty--;
                setValuesInCartCount();
            }
        } 
        else if (view.getId() == R.id.ivIncrementIcon) {
            prodQty++;
            setValuesInCartCount();
        }
        else if (view.getId() == R.id.btnAddProductToCart) {
            addProductToCart();
        }
    }

    void addProductToCart() {
        CartModel cartItem = new CartModel(curr_userID, productModel.getProductID(), prodQty);
//        new Thread(() -> appDatabase.cartDao().insertUserCartProduct(cartItem)).start(); // TODO
        appDatabase.cartDao().insertUserCartProduct(cartItem);

        // Show Toast message
        Toast.makeText(this, "Product added to cart!", Toast.LENGTH_SHORT).show();
    }

    void setValuesInCartCount() {
        tvCartProdQty.setText(String.valueOf(prodQty));
        double price = Double.valueOf(String.valueOf(tvProductPrice.getText()));
        double finalPrice = price * prodQty;
    }
}
