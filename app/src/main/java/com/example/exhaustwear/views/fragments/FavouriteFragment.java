package com.example.exhaustwear.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exhaustwear.R;
import com.example.exhaustwear.adapters.FavouriteAdapter;
import com.example.exhaustwear.models.FavouriteModel;
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


public class FavouriteFragment extends Fragment {

    View view;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    FavouriteAdapter favouriteAdapter;
    List<FavouriteModel> favouriteModelList;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout3;
    ConstraintLayout constraintLayout4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.favourite_rec);
        progressBar = view.findViewById(R.id.progress_bar_favourite);
        constraintLayout3 = view.findViewById(R.id.constraint3);
        constraintLayout4 = view.findViewById(R.id.constraint4);
        favouriteModelList = new ArrayList<>();
        favouriteAdapter = new FavouriteAdapter(getActivity(), favouriteModelList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        // for saving recyclerview position
        favouriteAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        recyclerView.setAdapter(favouriteAdapter);

        //is collection exist ?
        //if true, show cards
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .collection("AddToFavourite").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                visibilityEmpty();
                            } else {
                                loadingFavourite();
                            }
                        }
                    });
        } else visibilityEmpty();

        return view;

    }

    private void loadingFavourite() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore.collection("CurrentUser").document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                    .collection("AddToFavourite").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()
                                ) {
                                    constraintLayout3.setVisibility(View.GONE);
                                    constraintLayout4.setVisibility(View.VISIBLE);

                                    // id for removing item from cart
                                    String documentId = documentSnapshot.getId();
                                    FavouriteModel favouriteModel = documentSnapshot.toObject(FavouriteModel.class);
                                    Objects.requireNonNull(favouriteModel).setDocumentId(documentId);
                                    favouriteModelList.add(favouriteModel);
                                    favouriteAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                }

                            }
                        }

                    });
        } else visibilityEmpty();
    }

    //for layouts` visibility
    private void visibilityEmpty() {
        constraintLayout3.setVisibility(View.VISIBLE);
        constraintLayout4.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }
}