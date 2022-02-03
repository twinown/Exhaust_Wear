package com.example.exhaustwear;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
ImageSlider imageSlider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.one, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.two, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imageList);

        imageSlider.setItemClickListener(i -> {
            if (i == 0) {
                gotoUrl("https://www.youtube.com/c/EXHAUSTWEAR");
            } else gotoUrl("https://vk.com/exhst");
        });

    return view;
    }
    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}