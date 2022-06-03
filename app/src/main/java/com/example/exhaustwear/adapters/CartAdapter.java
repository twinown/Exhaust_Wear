package com.example.exhaustwear.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exhaustwear.Model.CartModel;
import com.example.exhaustwear.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
  Context context;
  List<CartModel>cartModelList;
  int totalPrice = 0;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(cartModelList.get(position).getProductImg()).into(holder.image);
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));
        holder.quantity.setText(cartModelList.get(position).getProductQuantity());

        //pass data of totalPriceBill to CartFragment for count overTotalPrice
        totalPrice = totalPrice + cartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("totalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        //deleting from cart
        holder.deleteFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, totalPrice;
        ImageView image, deleteFromCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_cart);
            totalPrice = itemView.findViewById(R.id.total_price_cart);
            quantity = itemView.findViewById(R.id.quantity_cart);
            image = itemView.findViewById(R.id.img_cart);
            deleteFromCart = itemView.findViewById(R.id.remove_from_cart);

        }
    }
}
