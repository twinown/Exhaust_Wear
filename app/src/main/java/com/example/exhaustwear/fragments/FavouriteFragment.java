package com.example.exhaustwear.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exhaustwear.R;
import com.example.exhaustwear.fragments.catalog.CatalogFragment;
import com.google.firebase.auth.FirebaseAuth;


public class FavouriteFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
            view = inflater.inflate(R.layout.fragment_favourite, container, false);

        return view;
       //
    }
}