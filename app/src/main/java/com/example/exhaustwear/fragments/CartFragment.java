package com.example.exhaustwear.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exhaustwear.Model.CartModel;
import com.example.exhaustwear.R;
import com.example.exhaustwear.adapters.CartAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        constraintLayout1 = view.findViewById(R.id.constraint1);
        constraintLayout2 = view.findViewById(R.id.constraint2);
        relativeLayout = view.findViewById(R.id.relative);
        progressBar = view.findViewById(R.id.progress_bar_cart);
        recyclerView = view.findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //count overTotalPrice
        overTotalAmount = view.findViewById(R.id.over_total_amount);
        LocalBroadcastManager.getInstance(requireActivity())
                .registerReceiver(mMessageReceiver, new IntentFilter("totalAmount"));

        cartModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), cartModelList);
        // for saving recyclerview position
        cartAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        recyclerView.setAdapter(cartAdapter);

        //is collection exist ?
       if (firebaseAuth.getCurrentUser() != null) {
           firebaseFirestore.collection("AddToCart").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                   .collection("CurrentUser").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
        return view;
    }



    private void loadingCart() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("AddToCart").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
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


    // for counting overTotalPrice
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount", 0);
            overTotalAmount.setText("К оплате: " + totalBill + " Р");
        }
    };
}