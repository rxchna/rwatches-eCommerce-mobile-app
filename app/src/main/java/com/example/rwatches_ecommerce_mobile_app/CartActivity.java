package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCartProductsList;
    ImageView ivHomeIcon;
    ImageView ivUserProfileIcon;
    TextView tvTotalValue;
    TextView tvSubtotalValue;
    TextView tvTaxValue;
    Button btnCheckout;
    RecyclerView.LayoutManager layoutManager;
    List<ProductModel> productsList = new ArrayList<>();
    List<CartModel> cartProductsList = new ArrayList<>();
    RecyclerView.Adapter cartAdapter;
    AppDatabase appDatabase;
    int curr_userID;

    double taxFee = 10.95;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        appDatabase = AppDatabase.getInstance(this);

        // Retrieve user_id from the Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user_id")) {
            curr_userID = intent.getIntExtra("user_id", -1);
        }

        rvCartProductsList = findViewById(R.id.rvCartProductsList);
        ivHomeIcon  = findViewById(R.id.homeIcon);
        ivUserProfileIcon  = findViewById(R.id.userProfileIcon2);
        btnCheckout = findViewById(R.id.btnCheckout);
        tvTaxValue = findViewById(R.id.tvTaxValue);
        tvTaxValue.setText(String.format("%.2f", taxFee));

        // Get products from user cart and their product details
        getUserCartProducts(appDatabase, curr_userID);

        Log.d("ProductsActivity", "Products loaded size: " + productsList.size());

        layoutManager = new LinearLayoutManager(this);
        rvCartProductsList.setLayoutManager(layoutManager);

        cartAdapter = new CartAdapter(productsList, cartProductsList, appDatabase, curr_userID, this);
        rvCartProductsList.setAdapter(cartAdapter);

        ivHomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProducts = new Intent(getApplicationContext(), ProductsActivity.class);
                intentProducts.putExtra("user_id", curr_userID);
                startActivity(intentProducts);
            }
        });
        ivUserProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intentUserProfile = new Intent(getApplicationContext(), MyProfileScreen.class);
//                intentProducts.putExtra("user_id", curr_userID);
//                startActivity(intentUserProfile); todo
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCheckout = new Intent(getApplicationContext(), CheckoutActivity.class);
                intentCheckout.putExtra("user_id", curr_userID);
                startActivity(intentCheckout);
            }
        });
    }

    void getUserCartProducts(AppDatabase appDatabase, int curr_userID) {
        // Retrieve products from user's cart
        cartProductsList = appDatabase.cartDao().getUserCartList(curr_userID);

        if (!cartProductsList.isEmpty()) {
            // Get product IDs from the cartProductsList
            List<Integer> productIds = new ArrayList<>();
            for (CartModel cartItem : cartProductsList) {
                productIds.add(cartItem.getProductID());
            }

            // Retrieve product details
            productsList = appDatabase.productDao().getProductsByIds(productIds);

            // Calculate total and subtotal
            updateTotalPrice();
        }
    }

    void updateTotalPrice() {
        double total = 0;
        double tax_fee = 10.95;
        for (CartModel cartItem : cartProductsList) {
            // Find the corresponding product
            for (ProductModel product : productsList) {
                if (product.getProductID() == cartItem.getProductID()) {
                    total += product.getProductPrice() * cartItem.getProductQty();
                    break;
                }
            }
        }

        // Update Total and Subtotal elements
        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvSubtotalValue = findViewById(R.id.tvSubtotalValue);
        tvTotalValue.setText(String.format("%.2f", total));
        tvSubtotalValue.setText(String.format("%.2f", total + taxFee));
    }
}
