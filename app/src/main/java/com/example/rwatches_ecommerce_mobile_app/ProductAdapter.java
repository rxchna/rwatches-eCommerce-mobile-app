package com.example.rwatches_ecommerce_mobile_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    List<ProductModel> productsList;
    AppDatabase appDatabase;
    int curr_userID;

    public ProductAdapter(List<ProductModel> productsList, AppDatabase appDatabase, int curr_userID)
    {
        this.productsList = productsList;
        this.appDatabase = appDatabase;
        this.curr_userID = curr_userID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductModel product = productsList.get(position);

        Context actContext = holder.itemView.getContext();

        holder.productName.setText(product.getProductName());
        holder.productDescription.setText(product.getProductDescription());

        DecimalFormat df = new DecimalFormat("#.00");
        String fProductPrice = df.format(product.getProductPrice());
        holder.productPrice.setText(fProductPrice);

        int resId = actContext.getResources().getIdentifier(product.getProductImageUrl(), "drawable", actContext.getPackageName());
        holder.productImage.setImageResource(resId);

        holder.productCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProductDetail = new Intent(actContext.getApplicationContext(), ProductDetailActivity.class);
                intentProductDetail.putExtra("selectedIndex", productsList.get(holder.getAdapterPosition()));
                intentProductDetail.putExtra("user_id", curr_userID);
                intentProductDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                actContext.startActivity(intentProductDetail);
            }
        });

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add product to cart
                addProductToCart(actContext, product);
            }
        });
    }

    private void addProductToCart(Context actContext, ProductModel product) {
        // Check if product has already been added to user's cart
        CartModel existingCartItem = appDatabase.cartDao().getCartProduct(curr_userID, product.getProductID());

        if (existingCartItem == null) {
            // Add new product to cart
            CartModel cartItem = new CartModel(curr_userID, product.getProductID(), 1); // Default quantity to 1
//        new Thread(() -> appDatabase.cartDao().insertUserCartProduct(cartItem)).start(); // TODO
            appDatabase.cartDao().insertUserCartProduct(cartItem);
        }
        else {
            // Update existing cart product's quantity
            int updatedQuantity = existingCartItem.getProductQty() + 1;
            existingCartItem.setProductQty(updatedQuantity);
            appDatabase.cartDao().updateUserCartProduct(existingCartItem);
        }

        // Show Toast message
        Toast.makeText(actContext, "Product added to cart!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() { return productsList.size(); }

    class MyViewHolder extends RecyclerView.ViewHolder {
        Button btnAddToCart;
        ImageView productImage;
        TextView productDescription;
        TextView productPrice;
        TextView productName;
        CardView productCardView;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.product_card_view, parent, false));
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productImage = itemView.findViewById(R.id.productImage);
            productPrice = itemView.findViewById(R.id.productPrice);
            productCardView = itemView.findViewById(R.id.productCardView);
            btnAddToCart = itemView.findViewById(R.id.btnAddProductToCart);
        }
    }
}