package com.example.rwatches_ecommerce_mobile_app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    RecyclerView rvProductsList;
    ImageView ivCartIcon;
    ImageView ivUserProfileIcon;
    RecyclerView.LayoutManager layoutManager;
    List<ProductModel> productsList = new ArrayList<>();
    RecyclerView.Adapter productAdapter;
    AppDatabase appDatabase;
    int curr_userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_products);

        // Retrieve user_id from the Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user_id")) {
            curr_userID = intent.getIntExtra("user_id", -1);
        }

        rvProductsList = findViewById(R.id.productsList);
        ivCartIcon  = findViewById(R.id.cartIcon);
        ivUserProfileIcon  = findViewById(R.id.userProfileIcon);

        appDatabase = AppDatabase.getInstance(this);

        // Manually add products if products table from DB is empty
        createProductsIfEmpty(appDatabase);

        // Retrieve all products from database
        loadProducts(appDatabase);

        Log.d("ProductsActivity", "Products loaded size: " + productsList.size());

        // get orientation
        int orientation = getResources().getConfiguration().orientation;

        // Get screen layout size
        int screenSize = getResources().getConfiguration().screenLayout;
        boolean isTablet = (screenSize > Configuration.SCREENLAYOUT_SIZE_LARGE);

        // Set the GridLayoutManager based on orientation and screen size
        GridLayoutManager layoutManager;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(this, 3); // 3 columns for landscape
        } else {
            if (isTablet) {
                layoutManager = new GridLayoutManager(this, 2); // 2 columns for tablets in portrait
            } else {
                layoutManager = new GridLayoutManager(this, 1); // 1 column for phones in portrait
            }
        }

        rvProductsList.setLayoutManager(layoutManager);

        productAdapter = new ProductAdapter(productsList, appDatabase, curr_userID);
        rvProductsList.setAdapter(productAdapter);

        ivCartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCart = new Intent(getApplicationContext(), CartActivity.class);
                intentCart.putExtra("user_id", curr_userID);
                startActivity(intentCart);
            }
        });
        ivUserProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
//                intentCart.putExtra("user_id", curr_userID);
//                startActivity(intent);
            }
        });
    }

    public void loadProducts(AppDatabase appDatabase)
    {
        // Read all products from table
        productsList = appDatabase.productDao().getProductsList();
    }

    public void createProductsIfEmpty(AppDatabase appDatabase) {
        // Check if "products" table is empty
        int prodCount = appDatabase.productDao().getProductsCount();
        if (prodCount == 0) {
            // Add products to db
            // Product 1
            ProductModel product1 = new ProductModel(0, "MVMT Nova Orion Rose", "Glamorous rose gold meets bold titanium, a cosmic pairing. The 38mm Orion Rose channels an awe for the celestial with a future-forward design sensibility and dual timezone functionality.", "watch_orion_rose_1", 385.00);
            appDatabase.productDao().insertProduct(product1);

            // Product 2
            ProductModel product2 = new ProductModel(0, "MVMT Airhawk Stealth Black", "The 42mm Airhawk's clean dial reads easy and features multi-function sub dials for your timekeeping experience. The Stealth Black balances a black steel colorway with a pop of red on the second hand. ", "watch_matte_black_1", 415.00);
            appDatabase.productDao().insertProduct(product2);

            // Product 3
            ProductModel product3 = new ProductModel(0, "MVMT Avenue Chelsea Gold", "Inspired by the classic style of New York, the Avenue has a petite 28mm case and minimalist dial. The Chelsea Gold wears an all-over gold colorway. Its case material features yellow Gold ionic-plated stainless steel.", "watch_gold_1", 335.00);
            appDatabase.productDao().insertProduct(product3);

            // Product 4
            ProductModel product4 = new ProductModel(0, "MVMT International Waters", "As serene and mysterious as the open ocean. The International Waters features aquatic blues and silver steel tones. With a 36mm case made of hardened mineral crystal.", "watch_silver_blue_1", 371.50);
            appDatabase.productDao().insertProduct(product4);

            // Product 5
            ProductModel product5 = new ProductModel(0, "MVMT Galaxy Midnight", "Run on spacetime in the Galaxy Midnight color story. Explore the starry golds of space and the vibrations of blue goldstone on the best selling Signature Square, a West Coast revamp of an iconic 90's square shape.", "watch_galaxy_midnight_1", 345.00);
            appDatabase.productDao().insertProduct(product5);

            // Product 6
            ProductModel product6 = new ProductModel(0, "MVMT Voyager Bronze Age", "The Voyager Bronze Age's 42mm case is built for adventure, with 10 ATM water resistant technology and dual timezone functionality for any hour, any latitude. Featuring MVMT's Nordic design with pebbled sage grey leather and a muted bronze case.", "watch_bronze_1", 252.00);
            appDatabase.productDao().insertProduct(product6);

            // Product 7
            ProductModel product7 = new ProductModel(0, "MVMT Raptor Electric Blue", "Our sleek and resistant self-winding Raptor features a Japanese Miyota 8250 automatic movement (open heart + exhibition case back), Waterproof guarantee (10 ATM + screw-down crown), Japanese super lume, and scratch resistant K1 crystal + shock resistance.", "watch_ceramic_blue_1", 560.00);
            appDatabase.productDao().insertProduct(product7);

            // Product 8
            ProductModel product8 = new ProductModel(0, "MVMT Ceramic Matte Green", "The California-clean Element Collection meets advanced ceramic technology. Featuring a Matte Green pigment, a finish so rich it could only be achieved by meticulously diamond-blasting our rare and resistant ceramic material.", "watch_matte_green_1", 425.50);
            appDatabase.productDao().insertProduct(product8);
        }
    }
}
