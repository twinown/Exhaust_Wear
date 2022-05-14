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
import androidx.viewpager2.widget.ViewPager2;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.exhaustwear.R;
import com.example.exhaustwear.forpager.VpModel;
import com.example.exhaustwear.forpager.Vpadapter;
import com.example.exhaustwear.fragments.account.LoginFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {

    View view;
    ImageSlider imageSlider;
    ViewPager2 viewPager2;

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
        viewPager2 = view.findViewById(R.id.view_pager2_home);
        List<VpModel> vpModels = new ArrayList<>();
        vpModels.add(new VpModel(R.drawable.one));
        vpModels.add(new VpModel(R.drawable.two));

        viewPager2.setAdapter(new Vpadapter(vpModels, viewPager2));
        viewPager2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

      /*  imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.one, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.two, ScaleTypes.CENTER_CROP));


        imageSlider.setImageList(imageList);

        imageSlider.setItemClickListener(i -> {
            if (i == 0) {
                gotoUrl("https://www.youtube.com/c/EXHAUSTWEAR");
            } else gotoUrl("https://vk.com/exhst");
        });

*/

    return view;
    }
    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}