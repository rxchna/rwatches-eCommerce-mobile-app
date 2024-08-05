package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
    Button btnCheckout;
    RecyclerView.LayoutManager layoutManager;
    List<ProductModel> productsList = new ArrayList<>();
    List<CartModel> cartProductsList = new ArrayList<>();
    RecyclerView.Adapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        rvCartProductsList = findViewById(R.id.rvCartProductsList);
        ivHomeIcon  = findViewById(R.id.homeIcon);
        ivUserProfileIcon  = findViewById(R.id.userProfileIcon2);
        btnCheckout = findViewById(R.id.btnCheckout);

        loadProducts();

        Log.d("ProductsActivity", "Products loaded size: " + productsList.size());

        layoutManager = new LinearLayoutManager(this);
        rvCartProductsList.setLayoutManager(layoutManager);

        cartAdapter = new CartAdapter(productsList, cartProductsList);
        rvCartProductsList.setAdapter(cartAdapter);

        ivHomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProducts = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intentProducts);
            }
        });
        ivUserProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intentUserProfile = new Intent(getApplicationContext(), MyProfileScreen.class);
//                startActivity(intentUserProfile); todo
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCheckout = new Intent(getApplicationContext(), CheckoutActivity.class);
                startActivity(intentCheckout);
            }
        });
    }

    public void loadProducts()
    {
        productsList.add(new ProductModel(1, "MVMT Rose Gold Watch1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis faucibus elit at mi porttitor laoreet.", "rose_gold_watch", 600.99));
        productsList.add(new ProductModel(4, "MVMT Rose Gold Watch2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis faucibus elit at mi porttitor laoreet.", "rose_gold_watch", 603.99));
        productsList.add(new ProductModel(5, "MVMT Rose Gold Watch3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis faucibus elit at mi porttitor laoreet.", "rose_gold_watch", 604.99));

        cartProductsList.add(new CartModel(1,1, 3));
        cartProductsList.add(new CartModel(1,4, 2));
    }
}
