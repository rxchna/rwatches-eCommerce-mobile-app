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
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    List<ProductModel> productsList;

    public ProductAdapter(List<ProductModel> productsList) { this.productsList = productsList; }

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
        holder.productPrice.setText(String.valueOf(product.getProductPrice()));
        holder.productDescription.setText(product.getProductDescription());

        int resId = actContext.getResources().getIdentifier(product.getProductImageUrl(), "drawable", actContext.getPackageName());
        holder.productImage.setImageResource(resId);

        holder.productCardView.setOnClickListener(new View.OnClickListener() {
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
            btnAddToCart = itemView.findViewById(R.id.btnRemoveProduct);
        }
    }
}