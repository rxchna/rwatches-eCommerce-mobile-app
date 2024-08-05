package com.example.rwatches_ecommerce_mobile_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    List<ProductModel> productsList;
    List<CartModel> cartProductsList;

    public CartAdapter(List<ProductModel> productsList, List<CartModel> cartProductsList) {
        this.productsList = productsList;
        this.cartProductsList = cartProductsList;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CartAdapter.MyViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        Context actContext = holder.itemView.getContext();
        CartModel cartProductModel = cartProductsList.get(position); // Product in cart
        ProductModel productModel = getProductById(cartProductModel.getProductID()); // Retrieve product details from product table

        if (productModel != null) {
            holder.cartProductName.setText(productModel.getProductName());

            DecimalFormat df = new DecimalFormat("#.00");
            String fProductPrice = df.format(productModel.getProductPrice());
            holder.cartProductPrice.setText(fProductPrice);

            int resId = actContext.getResources().getIdentifier(productModel.getProductImageUrl(), "drawable", actContext.getPackageName());
            holder.cartProductImage.setImageResource(resId);

            holder.cartProdQty.setText(String.valueOf(cartProductModel.getProductQty()));

            holder.cartProductCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(actContext.getApplicationContext(), ProductDetailActivity.class);
                    intent.putExtra("selectedIndex", productsList.get(holder.getAdapterPosition()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    actContext.startActivity(intent);
                }
            });

            holder.ivIncrementIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int newQuantity = cartProductModel.getProductQty() + 1;
                    cartProductModel.setProductQty(newQuantity);
                    holder.cartProdQty.setText(String.valueOf(newQuantity));
                    // todo: update total and subtotal
                }
            });

            holder.ivDecrementIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cartProductModel.getProductQty() > 1) {
                        int newQuantity = cartProductModel.getProductQty() - 1;
                        cartProductModel.setProductQty(newQuantity);
                        holder.cartProdQty.setText(String.valueOf(newQuantity));
                        // todo: update total and subtotal
                    }
                }
            });
        }
    }

    // Temp method to return product in productsList by ID
    private ProductModel getProductById(int productID) {
        // Return the ProductModel corresponding to the productID
        for (ProductModel product : productsList) {
            if (product.getProductID() == productID) {
                return product;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() { return cartProductsList.size(); }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRemoveProduct;
        ImageView cartProductImage;
        TextView cartProductPrice;
        TextView cartProductName;
        TextView cartProdQty;
        CardView cartProductCardView;
        ImageView ivIncrementIcon;
        ImageView ivDecrementIcon;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cart_product_view, parent, false));
            cartProductName = itemView.findViewById(R.id.cartProductName);
            cartProductImage = itemView.findViewById(R.id.cartProductImage);
            cartProductPrice = itemView.findViewById(R.id.cartProductPrice);
            cartProductCardView = itemView.findViewById(R.id.productCartCardView);
            ivRemoveProduct = itemView.findViewById(R.id.ivRemoveProduct);
            ivIncrementIcon = itemView.findViewById(R.id.ivIncrementIcon);
            ivDecrementIcon = itemView.findViewById(R.id.ivDecrementIcon);
            cartProdQty = itemView.findViewById(R.id.cartProdQty);
        }
    }
}
