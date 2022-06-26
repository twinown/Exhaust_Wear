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
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exhaustwear.R;
import com.example.exhaustwear.models.SearchModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    List<SearchModel> list;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;


    public SearchAdapter(Context context, List<SearchModel> list) {
        this.context = context;
        this.list = list;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search_item, parent, false));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loadData(position, holder);


        //checking if collections exist
        if (firebaseAuth.getCurrentUser() == null) {
            loadData(position, holder);
        } else {
            //loadData(position, holder);
            checkingCollEx(position, "AddToFavourite", holder, R.drawable.ic_baseline_favorite_green);
            checkingCollEx(position, "AddToCart", holder, R.drawable.ic_baseline_add_shopping_cart_green);
        }



        //packing fields for fragmentStuffDetail
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundling(position, view);

            }
        });


        holder.addToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToFavourite(position, holder);

            }
        });


        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking of the stuff`s existing by name
                //if already exists say about it
                //or add it
                addingToCart(position, holder);
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, addToCart, addToFavourite;
        TextView price, name, group, description, size;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cat_nav_det_img);
            price = itemView.findViewById(R.id.nav_cat_det_price);
            name = itemView.findViewById(R.id.nav_cat_det_name);
            group = itemView.findViewById(R.id.nav_cat_det_group);
            description = itemView.findViewById(R.id.nav_des);
            size = itemView.findViewById(R.id.nav_size);
            addToCart = itemView.findViewById(R.id.addToCart);
            addToFavourite = itemView.findViewById(R.id.addToFavourite);
        }
    }

    private void bundling(int position, View view) {
        Bundle bundle = new Bundle();
        bundle.putString("img", list.get(position).getImg_url());
        bundle.putString("img2", list.get(position).getImg_url2());
        bundle.putString("img3", list.get(position).getImg_url3());
        bundle.putString("img4", list.get(position).getImg_url4());
        bundle.putString("price", list.get(position).getPrice());
        bundle.putString("description", list.get(position).getDescription());
        bundle.putString("size", list.get(position).getSize());
        bundle.putString("name", list.get(position).getName());

        Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_stuffDetailFragment, bundle);
    }


    @SuppressLint("CheckResult")
    private void loadData(int position, SearchAdapter.ViewHolder holder) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        Glide.with(context).load(list.get(position).getImg_url2());
        Glide.with(context).load(list.get(position).getImg_url3());
        Glide.with(context).load(list.get(position).getImg_url4());
        holder.price.setText(list.get(position).getPrice());
        holder.name.setText(list.get(position).getName());
        holder.group.setText(list.get(position).getGroup());
        holder.description.setText(list.get(position).getDescription());
        holder.size.setText(list.get(position).getSize());
    }

    private void addingToFavourite(int position, SearchAdapter.ViewHolder holder) {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .collection("AddToFavourite").whereEqualTo("productName", list.get(position)
                            .getName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.getResult().isEmpty()) {
                                final HashMap<String, Object> cartMap = new HashMap<>();
                                cartMap.put("productName", list.get(position).getName());
                                cartMap.put("productImg", list.get(position).getImg_url());
                                cartMap.put("productPrice", list.get(position).getPrice());
                                firebaseFirestore.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                                        .collection("AddToFavourite")
                                        .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    holder.addToFavourite.setImageDrawable(ContextCompat.getDrawable(context,
                                                            R.drawable.ic_baseline_favorite_green));
                                                    Toast.makeText(context, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else
                                Toast.makeText(context, "Товар уже добавлен в избранное", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else
            Toast.makeText(context, "Для добавления в избранное войдите в аккаунт", Toast.LENGTH_SHORT).show();
    }


    private void addingToCart(int position, SearchAdapter.ViewHolder holder) {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .collection("AddToCart").whereEqualTo("productName", list.get(position)
                            .getName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.getResult().isEmpty()) {
                                int num = 1;
                                String quantity = Integer.toString(num);
                                final HashMap<String, Object> cartMap = new HashMap<>();
                                cartMap.put("productName", list.get(position).getName());
                                cartMap.put("productImg", list.get(position).getImg_url());
                                cartMap.put("productPrice", list.get(position).getPrice());
                                cartMap.put("totalPrice", Integer.parseInt(list.get(position).getPrice()));
                                cartMap.put("productQuantity", quantity);
                                firebaseFirestore.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                                        .collection("AddToCart")
                                        .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    holder.addToCart.setImageDrawable(ContextCompat.getDrawable(context,
                                                            R.drawable.ic_baseline_add_shopping_cart_green));
                                                    Toast.makeText(context, "Добавлено в корзину", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else
                                Toast.makeText(context, "Товар уже в корзине", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else
            Toast.makeText(context, "Для добавления в корзину войдите в аккаунт", Toast.LENGTH_SHORT).show();
    }

    private void checkingCollEx(int position, String collection, SearchAdapter.ViewHolder holder, int id) {
        firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .collection(collection).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            //if in the collection "CurrentUser" already exists chosen item show green cart in the concrete card
                            firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                                    .collection(collection).whereEqualTo("productName", list.get(position).getName())
                                    .get().addOnCompleteListener(
                                            new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (!task.getResult().isEmpty()) {
                                                        if (collection.equals("AddToCart")){
                                                            holder.addToCart.setImageDrawable(ContextCompat.getDrawable(context, id));
                                                        }else holder.addToFavourite.setImageDrawable(ContextCompat.getDrawable(context, id));
                                                    }
                                                }
                                            });
                        }
                    }
                });




    }
}
