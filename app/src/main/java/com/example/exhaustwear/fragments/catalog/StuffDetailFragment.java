package com.example.exhaustwear.fragments.catalog;


import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
    ViewPager2 viewPager2;
    VpAdapterStuff vpAdapterStuff;
    List<VpModelStuff> vpModelStuff;
    ScrollView scrollView;
    ImageView iv1, iv2, iv3;
    LinearLayout linearLayout;
    int dotsCount;
    List<ImageView> dots;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stuff_detail, container, false);


        scrollView = view.findViewById(R.id.scrollView2);
        scrollView.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);


        //    Bundle bundle = this.getArguments();
        //   String detail = requireArguments().getString("detail");

      /*  assert getArguments() != null;
        final Object object = getArguments().getSerializable("detail");
     if (object instanceof StuffDetailModel){
        catalogDetailModel = (CatalogDetailModel) object;
        }*/


        viewPager2 = view.findViewById(R.id.vp2_stuff);
        vpModelStuff = new ArrayList<>();


        price = view.findViewById(R.id.stuff_detail_price);
        description = view.findViewById(R.id.stuff_detail_description);
        addToCart = view.findViewById(R.id.add_to_cart_but);
        size = view.findViewById(R.id.stuff_detail_size);

        String img = requireArguments().getString("img");
        String img2 = requireArguments().getString("img2");
        String img3 = requireArguments().getString("img3");
        String img4 = requireArguments().getString("img4");
        vpModelStuff.add(new VpModelStuff(img));
        vpModelStuff.add(new VpModelStuff(img2));
        vpModelStuff.add(new VpModelStuff(img3));
        vpModelStuff.add(new VpModelStuff(img4));
        vpAdapterStuff = new VpAdapterStuff(getActivity(), vpModelStuff, viewPager2);
        viewPager2.setAdapter(vpAdapterStuff);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);


        linearLayout = view.findViewById(R.id.dots_lay);
        dotsCount = vpAdapterStuff.getItemCount();
        dots = new ArrayList<>();
        for (int i = 0; i < dotsCount; i++) {
           dots.add(new ImageView(getActivity()));
            dots.get(i).setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            linearLayout.addView(dots.get(i), params);
        }
        dots.get(0).setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.active_dot));

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots.get(i).setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.nonactive_dot));

                }
                dots.get(position).setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.active_dot));
                super.onPageSelected(position);
            }
        });

        price.setText(requireArguments().getString("price"));
        description.setText(requireArguments().getString("description"));
        size.setText(requireArguments().getString("size"));






       /* iv1 = view.findViewById(R.id.iv1);
        iv2 = view.findViewById(R.id.iv2);
        iv3 = view.findViewById(R.id.iv3);*/


     /*   swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

   // public void gettingStr() {



  //  }

/*    private void changeColor() {
        switch (viewPager2.getCurrentItem()) {
            case 0:
                iv1.setBackgroundColor(getResources().getColor(R.color.green));
                iv2.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
                iv3.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
            case 1:
                iv1.setBackgroundColor(getResources().getColor(R.color.green));
                iv2.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
                iv3.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
            case 2:
                iv1.setBackgroundColor(getResources().getColor(R.color.green));
                iv2.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
                iv3.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
        }
    }*/

}

