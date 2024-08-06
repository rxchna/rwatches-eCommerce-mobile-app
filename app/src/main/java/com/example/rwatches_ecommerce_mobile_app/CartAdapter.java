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
    AppDatabase appDatabase;
    private Context context;
    int curr_userID;
    CartActivity cartActivity;
    TextView tvTotalValue;
    TextView tvSubtotalValue;

    public CartAdapter(List<ProductModel> productsList, List<CartModel> cartProductsList, AppDatabase appDatabase, int curr_userID, CartActivity cartActivity) {
        this.productsList = productsList;
        this.cartProductsList = cartProductsList;
        this.appDatabase = appDatabase;
        this.curr_userID = curr_userID;
        this.cartActivity = cartActivity;
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
                    intent.putExtra("user_id", curr_userID);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    actContext.startActivity(intent);
                }
            });

            holder.ivIncrementIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int updatedQuantity = cartProductModel.getProductQty() + 1;

                    // Update new quantity
                    updateCartProductQty(cartProductModel, holder.cartProdQty, updatedQuantity);
                }
            });

            holder.ivDecrementIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cartProductModel.getProductQty() > 1) {
                        int updatedQuantity = cartProductModel.getProductQty() - 1;

                        // Update new quantity
                        updateCartProductQty(cartProductModel, holder.cartProdQty, updatedQuantity);
                    }
                }
            });

            holder.ivRemoveProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Remove item from cart
                    removeCartItem(cartProductModel, holder.getAdapterPosition());
                }
            });
        }
    }

    // Return each product detail in productsList by ID
    private ProductModel getProductById(int productID) {
        // Return the ProductModel corresponding to the productID
        for (ProductModel product : productsList) {
            if (product.getProductID() == productID) {
                return product;
            }
        }
        return null;
    }

    // Update quantity of cart product
    private void updateCartProductQty(CartModel cartProductModel, TextView cartProdQty, int updatedQty){
        // Update Quantity on Card view
        cartProductModel.setProductQty(updatedQty);
        cartProdQty.setText(String.valueOf(updatedQty));

        // Update total price on cartActivity
        cartActivity.updateTotalPrice();

        // Update the quantity in the database
        appDatabase.cartDao().updateUserCartProduct(cartProductModel);
    }

    private void removeCartItem (CartModel cartProductModel, int position) {
        // Remove item from the list
        cartProductsList.remove(position);

        // Notify adapter about item removal
        notifyItemRemoved(position);

        // Update total price on cartActivity
        cartActivity.updateTotalPrice();

        // Remove the item from the database
        appDatabase.cartDao().deleteUserCartProduct(cartProductModel);
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
