package com.example.exhaustwear.views.fragments.catalog;


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
import com.example.exhaustwear.forviewpager_sliding_img.VpAdapterStuff;
import com.example.exhaustwear.forviewpager_sliding_img.VpModelStuff;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class StuffDetailFragment extends Fragment {

    private View view;
    private TextView price, description, size, quantity;
    private Button addToCart;
    private ViewPager2 viewPager2;
    private VpAdapterStuff vpAdapterStuff;
    private List<VpModelStuff> vpModelStuff;
    private LinearLayout linearLayout;
    private int dotsCount;
    private int totalQuantity = 1;
    private int totalPrice = 0;
    private List<ImageView> dots;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private ImageView addQuantity, removeQuantity, addToFavourite;

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
        addToFavourite = view.findViewById(R.id.stuff_favourite);
        quantity = view.findViewById(R.id.quantity);
        addQuantity = view.findViewById(R.id.add_quantity);
        removeQuantity = view.findViewById(R.id.remove_quantity);
        viewPager2 = view.findViewById(R.id.vp2_stuff);
        vpModelStuff = new ArrayList<>();
        vpAdapterStuff = new VpAdapterStuff(getActivity(), vpModelStuff, viewPager2);
        checkingExc();
        int numPrice = Integer.parseInt(requireArguments().getString("price"));
        totalPrice = numPrice * totalQuantity;

        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuant(numPrice);
            }
        });

        removeQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeQuant(numPrice);
            }
        });

        String img = requireArguments().getString("img");
        String img2 = requireArguments().getString("img2");
        String img3 = requireArguments().getString("img3");
        String img4 = requireArguments().getString("img4");
        vpModelStuff.add(new VpModelStuff(img));
        vpModelStuff.add(new VpModelStuff(img2));
        vpModelStuff.add(new VpModelStuff(img3));
        vpModelStuff.add(new VpModelStuff(img4));
        viewPager2.setAdapter(vpAdapterStuff);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        price.setText(requireArguments().getString("price"));
        description.setText(requireArguments().getString("description"));
        size.setText(requireArguments().getString("size"));

        makingDots(img2, img4);

        //adding to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(getActivity(), "Для добавления в корзину войдите в аккаунт", Toast.LENGTH_SHORT).show();
                } else {
                    addingToCart();
                }
            }
        });

        //adding to favourite
        addToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToFavourite();
            }
        });
        return view;
    }

    //creating dots
    //actually this is for dots
    private void makingDots(String img2, String img4) {
        if (img2 == null) {
            while (vpModelStuff.size() > 1)
                vpModelStuff.remove(1);
        } else if (img4 == null) {
            vpModelStuff.remove(3);
        }
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
    }

    private void addingToFavourite() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .collection("AddToFavourite").whereEqualTo("productName", requireArguments().getString("name")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.getResult().isEmpty()) {
                                final HashMap<String, Object> cartMap = new HashMap<>();
                                cartMap.put("productName", requireArguments().getString("name"));
                                cartMap.put("productImg", requireArguments().getString("img"));
                                cartMap.put("productPrice", requireArguments().getString("price"));
                                firebaseFirestore.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                                        .collection("AddToFavourite")
                                        .add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    addToFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(),
                                                            R.drawable.ic_baseline_favorite_green));
                                                    Toast.makeText(getContext(), "Добавлено в избранное", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else
                                Toast.makeText(getContext(), "Товар уже добавлен в избранное", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else
            Toast.makeText(getContext(), "Для добавления в избранное войдите в аккаунт", Toast.LENGTH_SHORT).show();
    }

    private void removeQuant(int numPrice) {
        if (totalQuantity > 1) {
            totalQuantity--;
            quantity.setText(String.valueOf(totalQuantity));
            totalPrice = numPrice * totalQuantity;
        } else Toast.makeText(getActivity(), "Лучше больше, чем нуль",
                Toast.LENGTH_SHORT).show();
    }

    private void addQuant(int numPrice) {
        if (totalQuantity < 5) {
            totalQuantity++;
            quantity.setText(String.valueOf(totalQuantity));
            totalPrice = numPrice * totalQuantity;
        } else
            Toast.makeText(getActivity(), "Максимальное количсетво товара для заказа - 5",
                    Toast.LENGTH_SHORT).show();
    }

    private void addingToCart() {
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", requireArguments().getString("name"));
        cartMap.put("productImg", requireArguments().getString("img"));
        cartMap.put("productPrice", requireArguments().getString("price"));
        cartMap.put("productQuantity", quantity.getText());
        cartMap.put("totalPrice", totalPrice);
        firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(getActivity(), "Добавлено в корзину", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkingExc() {
        if (firebaseAuth.getCurrentUser() == null) {
            addToFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite));
        } else {
            firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .collection("AddToFavourite").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                //if in the collection "CurrentUser"--> "AddToFavourite" already exists chosen item, show green heart
                                firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                                        .collection("AddToFavourite").whereEqualTo("productName", requireArguments().getString("name"))
                                        .get().addOnCompleteListener(
                                                new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (!task.getResult().isEmpty()) {
                                                            addToFavourite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_green));
                                                        }
                                                    }
                                                });
                            }
                        }
                    });
        }
    }
}

