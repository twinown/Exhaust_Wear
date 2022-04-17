package com.example.exhaustwear.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.exhaustwear.R;
import com.example.exhaustwear.fragments.account.LoginFragment;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {
    //region Statics
    public static final int TAB_POSITION = 0;
    //endregion
    View view;
    ImageSlider imageSlider;
    //region newInstance
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    //endregion

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This callback will only be called when HomeFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
                //NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.navigation_home1);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.image_slider);
        // backend for homeFragment
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