package com.example.exhaustwear.fragments.catalog;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.exhaustwear.R;

import java.util.Objects;


public class CatalogFragment extends Fragment {

    private Button button;
    private EditText catalog;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        view =  inflater.inflate(R.layout.fragment_catalog, container, false);
      // catalog = view.findViewById(R.id.catalog_edit);
     //  button = view.findViewById(R.id.catButton);
     //  NavController navController =  Navigation.findNavController(view);


        return view;
    }


}