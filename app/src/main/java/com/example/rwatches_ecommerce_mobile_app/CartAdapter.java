package com.example.rwatches_ecommerce_mobile_app;

import android.content.Context;
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
        ProductModel product = productsList.get(position);

        Context actContext = holder.itemView.getContext();

        holder.cartProductName.setText(product.getProductName());
        holder.cartProductPrice.setText(String.valueOf(product.getProductPrice()));

        int resId = actContext.getResources().getIdentifier(product.getProductImageUrl(), "drawable", actContext.getPackageName());
        holder.cartProductImage.setImageResource(resId);

        holder.cartProductCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, ProductDetailsScreen.class);
//                intent.putExtra("selectedIndex", shoppingModelList.get(holder.getAdapterPosition()));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent); todo
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
        CardView cartProductCardView;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cart_product_view, parent, false));
            cartProductName = itemView.findViewById(R.id.cartProductName);
            cartProductImage = itemView.findViewById(R.id.cartProductImage);
            cartProductPrice = itemView.findViewById(R.id.cartProductPrice);
            cartProductCardView = itemView.findViewById(R.id.productCartCardView);
            btnRemoveProduct = itemView.findViewById(R.id.btnRemoveProduct);
        }
    }
}
