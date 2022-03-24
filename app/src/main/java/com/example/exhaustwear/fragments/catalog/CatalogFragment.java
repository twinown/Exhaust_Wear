package com.example.exhaustwear.fragments.catalog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.exhaustwear.navigation.FragmentUpdateCallback;
import com.example.exhaustwear.R;


public class CatalogFragment extends Fragment {

    //region Statics
    public static final int TAB_POSITION = 1;
    //endregion

    Button button;
    EditText catalog;
    View view;


    //region newInstance
    public static CatalogFragment newInstance() {
        return new CatalogFragment();
    }
    //endregion

    //region Fields
    private FragmentUpdateCallback mFragmentUpdateCallback;
    //endregion

    //region Fragment lifecycle
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof FragmentUpdateCallback) {
            mFragmentUpdateCallback = (FragmentUpdateCallback)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_catalog, container, false);
       catalog = view.findViewById(R.id.catalog_edit);
       button = view.findViewById(R.id.catButton);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mFragmentUpdateCallback.addFragment(TestFragment.newInstance(), TAB_POSITION);
           }
       });

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}