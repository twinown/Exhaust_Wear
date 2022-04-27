package com.example.exhaustwear.fragments.catalog;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.exhaustwear.Model.CatalogModel;
import com.example.exhaustwear.R;
import com.example.exhaustwear.adapters.CatalogAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CatalogFragment extends Fragment {

    View view;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    List<CatalogModel> catalogModelList;
    CatalogAdapter catalogAdapter;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        view =  inflater.inflate(R.layout.fragment_catalog, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.cat_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        catalogModelList = new ArrayList<>();
        catalogAdapter = new CatalogAdapter(getActivity(), catalogModelList);
        recyclerView.setAdapter(catalogAdapter);

        firebaseFirestore.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                CatalogModel catalogModel = queryDocumentSnapshot.toObject(CatalogModel.class);
                                catalogModelList.add(catalogModel);
                                catalogAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return view;
    }


}