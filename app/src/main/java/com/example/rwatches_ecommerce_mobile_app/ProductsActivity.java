package com.example.rwatches_ecommerce_mobile_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity{
    RecyclerView rvProductsList;
    ImageView ivCartIcon, ivUserProfileIcon;
    RecyclerView.LayoutManager layoutManager;
    List<ProductModel> productsList = new ArrayList<>();
    RecyclerView.Adapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_products);
        Log.d("ProductsActivity", "Layout set successfully.");
        fetchControls();
        loadProducts();
        Log.d("ProductsActivity", "Products loaded size: " + productsList.size());

        layoutManager = new LinearLayoutManager(this);
        rvProductsList.setLayoutManager(layoutManager);

        productAdapter = new ProductAdapter(productsList);
        rvProductsList.setAdapter(productAdapter);

        ivCartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MyCartScreen.class);
//                startActivity(intent);
            }
        });
        ivUserProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MyProfileScreen.class);
//                startActivity(intent);
            }
        });
    }

    void fetchControls()
    {
        rvProductsList = (RecyclerView) findViewById(R.id.productsList);
        ivCartIcon  = (ImageView) findViewById(R.id.cartIcon);
        ivUserProfileIcon  = (ImageView) findViewById(R.id.userProfileIcon);
    }

    public void loadProducts()
    {
        productsList.add(new ProductModel("1", "MVMT Rose Gold Watch", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis faucibus elit at mi porttitor laoreet.", "rose_gold_watch", 600.99, 1));
        productsList.add(new ProductModel("1", "MVMT Rose Gold Watch", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis faucibus elit at mi porttitor laoreet.", "rose_gold_watch", 601.99, 1));
        productsList.add(new ProductModel("1", "MVMT Rose Gold Watch", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis faucibus elit at mi porttitor laoreet.", "rose_gold_watch", 602.99, 1));
        productsList.add(new ProductModel("1", "MVMT Rose Gold Watch", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis faucibus elit at mi porttitor laoreet.", "rose_gold_watch", 603.99, 1));
        productsList.add(new ProductModel("1", "MVMT Rose Gold Watch", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis faucibus elit at mi porttitor laoreet.", "rose_gold_watch", 604.99, 1));
    }
}
