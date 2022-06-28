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
import com.example.exhaustwear.models.FavouriteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;
import java.util.Objects;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private Context context;
    private List<FavouriteModel> favouriteModelList;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    public FavouriteAdapter(Context context, List<FavouriteModel> favouriteModelList) {
        this.context = context;
        this.favouriteModelList = favouriteModelList;
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_card_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(favouriteModelList.get(position).getProductImg()).into(holder.stuffImage);
        holder.name.setText(favouriteModelList.get(position).getProductName());
        holder.price.setText(String.valueOf(favouriteModelList.get(position).getProductPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("price", String.valueOf(favouriteModelList.get(position).getProductPrice()));
                bundle.putString("img", favouriteModelList.get(position).getProductImg());
                bundle.putString("description", favouriteModelList.get(position).getProductName());
                bundle.putString("name", favouriteModelList.get(position).getProductName());
                Navigation.findNavController(view).navigate(R.id.action_favouriteFragment_to_stuffDetailFragment2, bundle);
            }
        });

        //deleting from favourite
        holder.deleteFromFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                        .collection("AddToFavourite")
                        .document(favouriteModelList.get(position).getDocumentId())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @SuppressLint({"NotifyDataSetChanged"})
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    favouriteModelList.remove(favouriteModelList.remove(position));
                                    Navigation.findNavController(view).navigate(R.id.action_favouriteFragment_self);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Удалено из избранного", Toast.LENGTH_SHORT).show();
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
        return favouriteModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView stuffImage, deleteFromFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_fav_card);
            price = itemView.findViewById(R.id.price_fav_card);
            stuffImage = itemView.findViewById(R.id.img_fav_card);
            deleteFromFav = itemView.findViewById(R.id.deleteFavourite);
        }
    }
}
