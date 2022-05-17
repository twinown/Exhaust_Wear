package com.example.exhaustwear.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.example.exhaustwear.Model.CatalogDetailModel;
import com.example.exhaustwear.R;

import java.util.List;

public class CatalogDetailAdapter extends RecyclerView.Adapter<CatalogDetailAdapter.ViewHolder> {

    Context context;
    List<CatalogDetailModel> list;

    public CatalogDetailAdapter(Context context, List<CatalogDetailModel> list) {
        this.context = context;
        this.list = list;
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
                Navigation.findNavController(view).navigate(R.id.action_catalog_detail_Fragment_to_stuffDetailFragment, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView price, name,group, description, size;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cat_nav_det_img);
            price = itemView.findViewById(R.id.nav_cat_det_price);
            name = itemView.findViewById(R.id.nav_cat_det_name);
            group = itemView.findViewById(R.id.nav_cat_det_group);
            description = itemView.findViewById(R.id.nav_des);
            size = itemView.findViewById(R.id.nav_size);

        }
    }
}
