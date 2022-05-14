package com.example.exhaustwear.forpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.exhaustwear.R;

import java.util.List;

public class Vpadapter extends RecyclerView.Adapter<Vpadapter.VpModelHolder> {
    private List<VpModel> vpModels;
    private ViewPager2 viewPager2;

    public Vpadapter(List<VpModel> vpModels, ViewPager2 viewPager2) {
        this.vpModels = vpModels;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public VpModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VpModelHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.vp_container, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VpModelHolder holder, int position) {
        holder.setImage(vpModels.get(position));
    }

    @Override
    public int getItemCount() {
        return vpModels.size();
    }

    class VpModelHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        VpModelHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.container_vp);
        }

        void setImage(VpModel vpModel) {
            imageView.setImageResource(vpModel.getImage());
        }
    }
}