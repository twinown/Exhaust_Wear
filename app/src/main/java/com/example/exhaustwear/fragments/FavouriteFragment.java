package com.example.exhaustwear.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exhaustwear.R;
import com.example.exhaustwear.fragments.catalog.CatalogFragment;
import com.google.firebase.auth.FirebaseAuth;


public class FavouriteFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    View view;
    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }
    public static final int TAB_POSITION = 3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

            view = inflater.inflate(R.layout.fragment_favourite, container, false);

        return view;
       //
    }
}