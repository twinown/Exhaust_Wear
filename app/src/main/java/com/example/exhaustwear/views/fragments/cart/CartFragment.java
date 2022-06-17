package com.example.exhaustwear.views.fragments.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exhaustwear.R;
import com.example.exhaustwear.adapters.CartAdapter;
import com.example.exhaustwear.models.CartModel;
import com.example.exhaustwear.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class CartFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<CartModel> cartModelList;
    ProgressBar progressBar;
    View view;
    TextView overTotalAmount;
    ConstraintLayout constraintLayout1;
    ConstraintLayout constraintLayout2;
    RelativeLayout relativeLayout;
    Button buyNow;

    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
      //  getUser();
        constraintLayout1 = view.findViewById(R.id.constraint1);
        constraintLayout2 = view.findViewById(R.id.constraint2);
        relativeLayout = view.findViewById(R.id.relative);
        progressBar = view.findViewById(R.id.progress_bar_cart);
        recyclerView = view.findViewById(R.id.cart_rec);
        buyNow = view.findViewById(R.id.make_an_order_butt);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        overTotalAmount = view.findViewById(R.id.over_total_amount);

        cartModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), cartModelList);
        // for saving recyclerview position
        cartAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        recyclerView.setAdapter(cartAdapter);

        //is collection exist ?
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .collection("AddToCart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                visibilityEmpty();
                            } else {
                                loadingCart();
                            }
                        }
                    });
        } else visibilityEmpty();

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makingOrder();
                Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_placedOrderFragment);

            }
        });

        return view;
    }


    private void loadingCart() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()
                                ) {
                                    constraintLayout1.setVisibility(View.GONE);
                                    constraintLayout2.setVisibility(View.VISIBLE);
                                    relativeLayout.setVisibility(View.VISIBLE);

                                    // id for removing item from cart
                                    String documentId = documentSnapshot.getId();

                                    CartModel cartModel = documentSnapshot.toObject(CartModel.class);
                                    Objects.requireNonNull(cartModel).setDocumentId(documentId);
                                    cartModelList.add(cartModel);
                                    cartAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                }
                                calculateTotalAmount(cartModelList);
                            }
                        }

                    });
        } else visibilityEmpty();
    }

    //for layouts` visibility
    private void visibilityEmpty() {
        constraintLayout1.setVisibility(View.VISIBLE);
        constraintLayout2.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    //count overTotalPrice
    @SuppressLint("SetTextI18n")
    private void calculateTotalAmount(List<CartModel> cartModelList) {
        int totalAmount = 0;
        for (CartModel cartModel : cartModelList
        ) {
            totalAmount += cartModel.getTotalPrice();
        }
        overTotalAmount.setText("К оплате: " + totalAmount + " Руб.");
    }

    private void makingOrder() {
        if (cartModelList != null && cartModelList.size() > 0) {
            for (CartModel cartModel : cartModelList
            ) {
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("productName", cartModel.getProductName());
                cartMap.put("productPrice", cartModel.getProductPrice());
                cartMap.put("productQuantity", cartModel.getProductQuantity());
                cartMap.put("totalPrice", cartModel.getTotalPrice());
              /*  cartMap.put("nameUser",);
                cartMap.put("emailUser", );
                cartMap.put("phoneUser", );*/
                firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                        .collection("ClientsOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Заказ оформлен!", Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(getActivity(), "Ошибка: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        }
    }

    private void getUser() {
        String userId = firebaseAuth.getUid();
        assert userId != null;
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    Bundle bundle = new Bundle();
                    String name = user.getName();
                    String email = user.getEmail();
                    String phone = user.getPhone();
                    bundle.putString("nameUser", name);
                    bundle.putString("emailUser", email);
                    bundle.putString("phoneUser", phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}