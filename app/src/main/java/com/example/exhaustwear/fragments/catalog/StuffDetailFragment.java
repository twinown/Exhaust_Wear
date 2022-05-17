package com.example.exhaustwear.fragments.catalog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.exhaustwear.R;
import com.example.exhaustwear.forpager.VpAdapterStuff;
import com.example.exhaustwear.forpager.VpModelStuff;

import java.util.ArrayList;
import java.util.List;


public class StuffDetailFragment extends Fragment {

    View view;
    TextView price, description, size;
    Button addToCart;
    SwipeRefreshLayout swipeRefreshLayout;
    ViewPager2 viewPager2;
    VpAdapterStuff vpAdapterStuff;
    List<VpModelStuff> vpModelStuff;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stuff_detail, container, false);
       // swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        //    Bundle bundle = this.getArguments();
        //   String detail = requireArguments().getString("detail");

      /*  assert getArguments() != null;
        final Object object = getArguments().getSerializable("detail");
     if (object instanceof StuffDetailModel){
        catalogDetailModel = (CatalogDetailModel) object;
        }*/


        viewPager2 = view.findViewById(R.id.vp2_stuff);
        vpModelStuff = new ArrayList<>();
        //imageSlider = view.findViewById(R.id.stuff_detail_img_slide);
        //   detailedImg = view.findViewById(R.id.stuff_detail_img);

        price = view.findViewById(R.id.stuff_detail_price);
        description = view.findViewById(R.id.stuff_detail_description);
        addToCart = view.findViewById(R.id.add_to_cart_but);
        size = view.findViewById(R.id.stuff_detail_size);

     /*   addItem = view.findViewById(R.id.add_item);
        removeItem = view.findViewById(R.id.remove_item);
        quantity = view.findViewById(R.id.quantity);*/

        gettingStr();
       /* swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gettingStr();
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/
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


    public void gettingStr() {

        String img = requireArguments().getString("img");
        String img2 = requireArguments().getString("img2");
        String img3 = requireArguments().getString("img3");
        String img4 = requireArguments().getString("img4");
        vpModelStuff.add(new VpModelStuff(img));
        vpModelStuff.add(new VpModelStuff(img2));
        vpModelStuff.add(new VpModelStuff(img3));
        vpModelStuff.add(new VpModelStuff(img4));
        vpAdapterStuff = new VpAdapterStuff(getActivity(), vpModelStuff);
        viewPager2.setAdapter(vpAdapterStuff);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(1);

       // viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);


        price.setText(requireArguments().getString("price"));
        description.setText(requireArguments().getString("description"));
        size.setText(requireArguments().getString("size"));

    }

}

