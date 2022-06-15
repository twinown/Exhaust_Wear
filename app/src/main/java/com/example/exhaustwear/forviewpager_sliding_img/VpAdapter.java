package com.example.exhaustwear.forviewpager_sliding_img;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.exhaustwear.R;
import java.util.List;

public class VpAdapter extends RecyclerView.Adapter<VpAdapter.ViewHolder> {

    List<VpModel> vpModels;
    ViewPager2 viewPager2;
    Handler slideHandler = new Handler();

    public VpAdapter(List<VpModel> vpModels, ViewPager2 viewPager2) {
        this.vpModels = vpModels;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.vp_container, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(vpModels.get(position).getImage());
        //for making backLoop in viewPager2
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(runnable);
                slideHandler.postDelayed(runnable, 1700);
            }
        });
        if (position == 0) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoUrl("https://www.youtube.com/c/EXHAUSTWEAR", view);
                }
            });
        } else {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoUrl("https://vk.com/exhst", view);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return vpModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.container_vp);
        }

    }

    //for backLoop slider
    private final Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);

        }
    };

    //for intents
    private void gotoUrl(String s, View view) {
        Uri uri = Uri.parse(s);
        Context context = view.getContext();
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

}