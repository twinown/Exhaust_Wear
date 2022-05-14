package com.example.exhaustwear.fragments.catalog;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.exhaustwear.Model.CatalogDetailModel;
import com.example.exhaustwear.Model.StuffDetailModel;
import com.example.exhaustwear.R;
import com.example.exhaustwear.adapters.StuffDetailAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class StuffDetailFragment extends Fragment {
    View view;
    ImageView detailedImg;
    TextView price, description, size;
    Button addToCart;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageSlider imageSlider;
    FirebaseFirestore firebaseFirestore;
    StuffDetailAdapter stuffDetailAdapter;
    List<StuffDetailModel> list;
    ImageView addItem, removeItem;
    int quantityNum = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stuff_detail, container, false);
       swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
    //    Bundle bundle = this.getArguments();
     //   String detail = requireArguments().getString("detail");

      /*  assert getArguments() != null;
        final Object object = getArguments().getSerializable("detail");
     if (object instanceof StuffDetailModel){
        catalogDetailModel = (CatalogDetailModel) object;
        }*/


       imageSlider = view.findViewById(R.id.stuff_detail_img_slide);
     //   detailedImg = view.findViewById(R.id.stuff_detail_img);



        price = view.findViewById(R.id.stuff_detail_price);
        description = view.findViewById(R.id.stuff_detail_description);
        addToCart = view.findViewById(R.id.add_to_cart_but);
        size = view.findViewById(R.id.stuff_detail_size);
     /*   addItem = view.findViewById(R.id.add_item);
        removeItem = view.findViewById(R.id.remove_item);
        quantity = view.findViewById(R.id.quantity);*/

       gettingStr();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gettingStr();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    /*    addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    quantityNum = quantityNum + 1;
                    String text = Integer.toString(quantityNum);
                    quantity.setText(text);
                    if (quantityNum>5){
                        quantity.setText("5");
                        Toast.makeText(getActivity(), "Максимальное количсетво товара для заказа - 5", Toast.LENGTH_SHORT).show();
                    }
            }
        });
*/
        return view;
    }



    public void gettingStr(){
        String img = requireArguments().getString("img");
        String img2 = requireArguments().getString("img2");
        String img3 = requireArguments().getString("img3");
        String img4 = requireArguments().getString("img4");
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(img, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(img2, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(img3, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(img4, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(imageList);


        price.setText(requireArguments().getString("price"));
        description.setText(requireArguments().getString("description"));
        size.setText(requireArguments().getString("size"));
       // Glide.with(requireContext()).load(requireArguments().getString("img")).into(detailedImg);
    }

    }

