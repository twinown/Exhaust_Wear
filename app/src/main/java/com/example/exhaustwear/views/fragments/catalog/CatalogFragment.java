package com.example.exhaustwear.views.fragments.catalog;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exhaustwear.R;
import com.example.exhaustwear.adapters.CatalogAdapter;
import com.example.exhaustwear.adapters.CatalogDetailAdapter;
import com.example.exhaustwear.models.CatalogDetailModel;
import com.example.exhaustwear.models.CatalogModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CatalogFragment extends Fragment {

    View view;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    List<CatalogModel> catalogModelList;
    CatalogAdapter catalogAdapter;
    ProgressBar progressBar;

    SearchView searchView;
   // List<CatalogDetailModel> catalogDetailModelList;
  //  CatalogDetailAdapter catalogDetailAdapter;
   RecyclerView recyclerViewSearch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);

        view = inflater.inflate(R.layout.fragment_catalog, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.cat_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        catalogModelList = new ArrayList<>();
        catalogAdapter = new CatalogAdapter(getActivity(), catalogModelList);

        // for saving recyclerview position
        catalogAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);

        recyclerView.setAdapter(catalogAdapter);

        firebaseFirestore.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                CatalogModel catalogModel = queryDocumentSnapshot.toObject(CatalogModel.class);
                                catalogModelList.add(catalogModel);
                                catalogAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
/*

        //for searching
      //  catalogDetailAdapter = new CatalogDetailAdapter(getContext(), catalogDetailModelList);
        recyclerViewSearch = view.findViewById(R.id.search_rec);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(catalogAdapter);
        recyclerViewSearch.setHasFixedSize(true);
//catalogDetailModelList = new ArrayList<>();
        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                //showStuff(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
searchProduct(s);
                return false;
            }
        });




*/


        return view;
    }



  /*  private void searchProduct(String name) {
        if (!name.isEmpty()) {
            firebaseFirestore.collection("NavCategory").whereEqualTo("name", name).
                    get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                catalogModelList.clear();
                                catalogAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc: task.getResult().getDocuments()
                                     ) {
                                    CatalogModel catalogModel = doc.toObject(CatalogModel.class);
                                    catalogModelList.add(catalogModel);
                                    catalogAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }*/


   /* private void showStuff(String s) {
        catalogDetailModelList = new ArrayList<>();
        for (CatalogModel catalogModel: catalogModelList
             ) {
            if (catalogModel.getName().toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))){
                catalogModelList.add(catalogModel);
            }
        }
        if (catalogModelList.isEmpty()){
            Toast.makeText(getContext(), "empty", Toast.LENGTH_SHORT).show();
        } else {

        }
    }*/



}