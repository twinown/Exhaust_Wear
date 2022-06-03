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
import com.example.exhaustwear.Model.CatalogDetailModel;
import com.example.exhaustwear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CatalogDetailAdapter extends RecyclerView.Adapter<CatalogDetailAdapter.ViewHolder> {

    Context context;
    List<CatalogDetailModel> list;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public CatalogDetailAdapter(Context context, List<CatalogDetailModel> list) {
        this.context = context;
        this.list = list;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_card_detail_item, parent, false));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        Glide.with(context).load(list.get(position).getImg_url2());
        Glide.with(context).load(list.get(position).getImg_url3());
        Glide.with(context).load(list.get(position).getImg_url4());
        holder.price.setText(list.get(position).getPrice());
        holder.name.setText(list.get(position).getName());
        holder.group.setText(list.get(position).getGroup());
        holder.description.setText(list.get(position).getDescription());
        holder.size.setText(list.get(position).getSize());


        //checking if collections exists
        firebaseFirestore.collection("AddToCart").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .collection("CurrentUser").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            //if in the collection "CurrentUser" already exists chosen item show green cart in the concrete card
                            firebaseFirestore.collection("AddToCart").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                                    .collection("CurrentUser").whereEqualTo("productName", list.get(position).getName())
                                    .get().addOnCompleteListener(
                                            new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (!task.getResult().isEmpty()) {
                                                        holder.addToCart.setImageDrawable(ContextCompat.getDrawable(context,
                                                                R.drawable.ic_baseline_add_shopping_cart_green));
                                                    }
                                                }
                                            });
                        }
                    }
                });

        //packing fields for fragmentStuffDetail
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("img", list.get(position).getImg_url());
                bundle.putString("img2", list.get(position).getImg_url2());
                bundle.putString("img3", list.get(position).getImg_url3());
                bundle.putString("img4", list.get(position).getImg_url4());
                bundle.putString("price", list.get(position).getPrice());
                bundle.putString("description", list.get(position).getDescription());
                bundle.putString("size", list.get(position).getSize());
                bundle.putString("name", list.get(position).getName());
                Navigation.findNavController(view).navigate(R.id.action_catalog_detail_Fragment_to_stuffDetailFragment, bundle);
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("AddToCart").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                        .collection("CurrentUser").whereEqualTo("productName", list.get(position)
                                .getName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.getResult().isEmpty()){
                                    int num = 1;
                                    String quantity = Integer.toString(num);
                                    if (firebaseAuth.getCurrentUser() == null) {
                                        Toast.makeText(context, "Для добавления в корзину войдите в аккаунт", Toast.LENGTH_SHORT).show();
                                    } else {
                                        final HashMap<String, Object> cartMap = new HashMap<>();
                                        cartMap.put("productName", list.get(position).getName());
                                        cartMap.put("productImg", list.get(position).getImg_url());
                                        cartMap.put("productPrice", list.get(position).getPrice());
                                        cartMap.put("productQuantity", quantity);
                                        firebaseFirestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid())
                                                .collection("CurrentUser")
                                                .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()){
                                                            holder.addToCart.setImageDrawable(ContextCompat.getDrawable(context,
                                                                    R.drawable.ic_baseline_add_shopping_cart_green));
                                                            Toast.makeText(context, "Добавлено в корзину", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                } else{
                                    //ОСТАВИТЬ ЭТО НА ПОТОМ, СНАЧАЛА ОБЫЧНАЯ КОРЗИНА, ТАМ УЖЕ ПОЙМЁМ, КАК ПОЛУЧИТЬ ЭТОТ СНАПЧОТ
                                    //чтоб удалить товар по иконке

                                                         /*   DocumentSnapshot documentSnapshot;

                                                            for (DocumentSnapshot document: task.getResult().getD
                                                                 ) {

                                                            }*/
                                    // y
                                    firebaseFirestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid())
                                            .collection("CurrentUser").document().delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    holder.addToCart.setImageDrawable(ContextCompat.getDrawable(context,
                                                            R.drawable.ic_baseline_add_shopping_cart));
                                                    Toast.makeText(context, "удалён", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                            }
                        });
            }
        });

    /*    holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("AddToCart").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                        .collection("CurrentUser").whereEqualTo("productName", list.get(position).getName()).get().addOnCompleteListener(
                                new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (!task.getResult().isEmpty()) {
                                            firebaseFirestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid())

                                                    .delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @SuppressLint("NotifyDataSetChanged")
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(context, "Удалено из корзины", Toast.LENGTH_SHORT).show();
                                                                holder.addToCart.setImageDrawable(ContextCompat.getDrawable(context,
                                                                        R.drawable.ic_baseline_add_shopping_cart));
                                                            } else {
                                                                Toast.makeText(context, "Error " + task.getException().getMessage(),
                                                                        Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            int num = 1;
                                            String quantity = Integer.toString(num);
                                            if (firebaseAuth.getCurrentUser() == null) {
                                                Toast.makeText(context, "Для добавления в корзину войдите в аккаунт", Toast.LENGTH_SHORT).show();
                                            } else {
                                                final HashMap<String, Object> cartMap = new HashMap<>();
                                                cartMap.put("productName", list.get(position).getName());
                                                cartMap.put("productImg", list.get(position).getImg_url());
                                                cartMap.put("productPrice", list.get(position).getPrice());
                                                cartMap.put("quantity", quantity);
                                                firebaseFirestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid())
                                                        .collection("CurrentUser")
                                                        .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                holder.addToCart.setImageDrawable(ContextCompat.getDrawable(context,
                                                                        R.drawable.ic_baseline_add_shopping_cart_green));
                                                                Toast.makeText(context, "Добавлено в корзину", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                });
            }
        });*/
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
}
