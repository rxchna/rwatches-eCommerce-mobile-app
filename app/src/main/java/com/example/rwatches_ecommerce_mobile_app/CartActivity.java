package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
import android.graphics.Color;
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

            // Calculate total/subtotal and update UI
            updateCartTotal();
        }
        else {
            // Disable checkout button if no products are added to cart
            disableCheckoutBtn(btnCheckout);
        }
    }

    void updateCartTotal() {
        double total = 0;

        if (!cartProductsList.isEmpty()) {
            // Add fixed tax value is cart contains products
            taxFee = 10.95;
            // Calculate cart total
            total = calculateCartTotal();

            // Update Cart UI total/tax elements
            setCartTotal(total);
        }
        else {
            taxFee = 0;
            // Update Cart UI total/tax elements
            setCartTotal(0);

            // Disable checkout button if no products are added to cart
            btnCheckout = findViewById(R.id.btnCheckout);
            disableCheckoutBtn(btnCheckout);
        }
    }

    double calculateCartTotal() {
        double total = 0;

        // Calculate total price of cart items
        for (CartModel cartItem : cartProductsList) {
            // Find the corresponding product
            for (ProductModel product : productsList) {
                if (product.getProductID() == cartItem.getProductID()) {
                    total += product.getProductPrice() * cartItem.getProductQty();
                    break;
                }
            }
        }
        return total;
    }

    void setCartTotal(double total) {
        // Update Total and Subtotal elements
        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvSubtotalValue = findViewById(R.id.tvSubtotalValue);
        tvTotalValue.setText(String.format("%.2f", total));
        tvTaxValue.setText(String.format("%.2f", taxFee));
        tvSubtotalValue.setText(String.format("%.2f", total + taxFee));
    }

    void disableCheckoutBtn(Button btnCheckout) {
        // Disable checkout button
        btnCheckout.setEnabled(false);
        btnCheckout.setBackgroundColor(Color.GRAY);
        btnCheckout.setTextColor(Color.DKGRAY);

    }
}
