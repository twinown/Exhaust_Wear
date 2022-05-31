package com.example.exhaustwear.fragments.catalog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.exhaustwear.R;
import com.example.exhaustwear.forpager.VpAdapterStuff;
import com.example.exhaustwear.forpager.VpModelStuff;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StuffDetailFragment extends Fragment {

    View view;
    TextView price, description, size, quantity;
    Button addToCart;
    ViewPager2 viewPager2;
    VpAdapterStuff vpAdapterStuff;
    List<VpModelStuff> vpModelStuff;
    LinearLayout linearLayout;
    int dotsCount;
    int quantityNum;
    List<ImageView> dots;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ImageView addQuantity, removeQuantity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stuff_detail, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        price = view.findViewById(R.id.stuff_detail_price);
        description = view.findViewById(R.id.stuff_detail_description);
        addToCart = view.findViewById(R.id.add_to_cart_but);
        size = view.findViewById(R.id.stuff_detail_size);

        quantity = view.findViewById(R.id.quantity);
        addQuantity = view.findViewById(R.id.add_quantity);
        removeQuantity = view.findViewById(R.id.remove_quantity);

        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityNum = quantityNum + 1;
                String text = Integer.toString(quantityNum);
                quantity.setText(text);
                if (quantityNum > 5) {
                    quantity.setText("5");
                    Toast.makeText(getActivity(), "Максимальное количсетво товара для заказа - 5", Toast.LENGTH_SHORT).show();
                }
            }
        });
        removeQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityNum = quantityNum - 1;
                String text = Integer.toString(quantityNum);
                quantity.setText(text);
                if (quantityNum < 0) {
                    quantity.setText("0");
                    Toast.makeText(getActivity(), "Тормози", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewPager2 = view.findViewById(R.id.vp2_stuff);
        vpModelStuff = new ArrayList<>();

        String img = requireArguments().getString("img");
        String img2 = requireArguments().getString("img2");
        String img3 = requireArguments().getString("img3");
        String img4 = requireArguments().getString("img4");
        vpModelStuff.add(new VpModelStuff(img));
        vpModelStuff.add(new VpModelStuff(img2));
        vpModelStuff.add(new VpModelStuff(img3));
        vpModelStuff.add(new VpModelStuff(img4));

        //actually this is for dots
        if (img2 == null) {
            while (vpModelStuff.size() > 1)
                vpModelStuff.remove(1);
        } else if (img4 == null) {
            vpModelStuff.remove(3);
        }

        vpAdapterStuff = new VpAdapterStuff(getActivity(), vpModelStuff, viewPager2);
        viewPager2.setAdapter(vpAdapterStuff);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        price.setText(requireArguments().getString("price"));
        description.setText(requireArguments().getString("description"));
        size.setText(requireArguments().getString("size"));

        //making dots
        linearLayout = view.findViewById(R.id.dots_lay);
        dotsCount = vpAdapterStuff.getItemCount();
        dots = new ArrayList<>();
        for (int i = 0; i < dotsCount; i++) {
            dots.add(new ImageView(getActivity()));
            dots.get(i).setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
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

//adding to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(getActivity(), "Для добавления в корзину войдите в аккаунт", Toast.LENGTH_SHORT).show();
                } else {
                    addToCart();
                }
            }
        });

        return view;
    }

    private void addToCart() {
        //проверить , есть ли товар уже в корзине сначала
        //если есть есть , написать , что уже добавлен
        //иконка уже загорается

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", requireArguments().getString("name"));
        cartMap.put("productImg", requireArguments().getString("img"));
        cartMap.put("productPrice", requireArguments().getString("price"));
        cartMap.put("quantity", quantity.getText());
        firebaseFirestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid())
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(getActivity(), "Добавлено в корзину", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

