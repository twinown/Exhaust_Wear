package com.example.exhaustwear.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exhaustwear.Model.CatalogDetailModel;
import com.example.exhaustwear.Model.StuffDetailModel;
import com.example.exhaustwear.R;

import java.util.List;

public class StuffDetailAdapter extends RecyclerView.Adapter<StuffDetailAdapter.ViewHolder> {
    Context context;
    List <StuffDetailModel> list;

    public StuffDetailAdapter(Context context, List<StuffDetailModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_stuff_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StuffDetailAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.price.setText(list.get(position).getPrice());
        holder.description.setText(list.get(position).getDescription());

    }


    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView price,description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // imageView = itemView.findViewById(R.id.stuff_detail_img);
            price = itemView.findViewById(R.id.stuff_detail_price);
       //     spinner = itemView.findViewById(R.id.stuff_detail_spinner);
            description = itemView.findViewById(R.id.stuff_detail_description);
        }
    }
}
