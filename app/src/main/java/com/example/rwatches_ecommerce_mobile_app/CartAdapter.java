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

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    List<ProductModel> productsList;

    public CartAdapter(List<ProductModel> productsList) { this.productsList = productsList; }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CartAdapter.MyViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        ProductModel productModel = productsList.get(position);

        Context actContext = holder.itemView.getContext();

        holder.cartProductName.setText(productModel.getProductName());
        holder.cartProductPrice.setText(String.valueOf(productModel.getProductPrice()));

        int resId = actContext.getResources().getIdentifier(productModel.getProductImageUrl(), "drawable", actContext.getPackageName());
        holder.cartProductImage.setImageResource(resId);

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
                int newQuantity = productModel.getProductQty() + 1;
                productModel.setProductQty(newQuantity);
                holder.cartProdQty.setText(String.valueOf(newQuantity));
                // todo: update total and subtotal
            }
        });

        holder.ivDecrementIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productModel.getProductQty() > 1) {
                    int newQuantity = productModel.getProductQty() - 1;
                    productModel.setProductQty(newQuantity);
                    holder.cartProdQty.setText(String.valueOf(newQuantity));
                    // todo: update total and subtotal
                }
            }
        });
    }

    @Override
    public int getItemCount() { return productsList.size(); }

    class MyViewHolder extends RecyclerView.ViewHolder {
        Button btnRemoveProduct;
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
            btnRemoveProduct = itemView.findViewById(R.id.btnRemoveProduct);
            ivIncrementIcon = itemView.findViewById(R.id.ivIncrementIcon);
            ivDecrementIcon = itemView.findViewById(R.id.ivDecrementIcon);
            cartProdQty = itemView.findViewById(R.id.cartProdQty);
        }
    }
}
