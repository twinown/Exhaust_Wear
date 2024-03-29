package com.example.exhaustwear.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.exhaustwear.R;
import com.example.exhaustwear.models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<CartModel> cartModelList;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(cartModelList.get(position).getProductImg()).into(holder.image);
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));
        holder.quantity.setText(cartModelList.get(position).getProductQuantity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("price", String.valueOf(cartModelList.get(position).getProductPrice()));
                bundle.putString("img", cartModelList.get(position).getProductImg());
                bundle.putString("description", cartModelList.get(position).getProductName());
                bundle.putString("name", cartModelList.get(position).getProductName());
                Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_stuffDetailFragment1, bundle);
            }
        });

        //deleting from cart
        holder.deleteFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                        .collection("AddToCart")
                        .document(cartModelList.get(position).getDocumentId())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @SuppressLint({"NotifyDataSetChanged"})
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    cartModelList.remove(cartModelList.remove(position));
                                    Navigation.findNavController(view).navigate(R.id.action_cartFragment_self);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Удалено из корзины", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
