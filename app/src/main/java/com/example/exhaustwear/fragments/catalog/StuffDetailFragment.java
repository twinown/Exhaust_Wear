package com.example.exhaustwear.fragments.catalog;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.exhaustwear.Model.CatalogDetailModel;
import com.example.exhaustwear.Model.StuffDetailModel;
import com.example.exhaustwear.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;


public class StuffDetailFragment extends Fragment {
    View view;
    ImageView detailedImg;
    TextView price, description;
    Spinner spinner;
    Button addToCart;
    ImageView addItem, removeItem;
    CatalogDetailModel catalogDetailModel = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stuff_detail, container, false);
    //    Bundle bundle = this.getArguments();
     //   String detail = requireArguments().getString("detail");

      /*  assert getArguments() != null;
        final Object object = getArguments().getSerializable("detail");
     if (object instanceof StuffDetailModel){
        catalogDetailModel = (CatalogDetailModel) object;
        }*/

        detailedImg = view.findViewById(R.id.stuff_detail_img);
        price = view.findViewById(R.id.stuff_detail_price);
        description = view.findViewById(R.id.stuff_detail_description);
       // spinner = view.findViewById(R.id.spinner_detail_stuff);
        addToCart = view.findViewById(R.id.add_to_cart_but);
        addItem = view.findViewById(R.id.add_item);
        removeItem = view.findViewById(R.id.remove_item);


            price.setText(requireArguments().getString("price"));
            description.setText(requireArguments().getString("description"));
          //  detailedImg.setImageURI(requireArguments().getString("img"));
            Glide.with(requireContext()).load(requireArguments().getString("img")).into(detailedImg);







      /*  if (catalogDetailModel != null){
            Glide.with(requireContext()).load(catalogDetailModel.getImg_url()).into(detailedImg);
            price.setText(catalogDetailModel.getPrice());
            description.setText(catalogDetailModel.getDescription());

        }*/

        return view;
    }

    }

