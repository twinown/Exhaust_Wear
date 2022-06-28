package com.example.exhaustwear.views.fragments.catalog;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
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
    ImageView imageView;
    LinearLayout linearLayout;

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

        //for searching
        linearLayout = view.findViewById(R.id.linear_lin);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_CatalogFragment_to_searchFragment);
            }
        });
/*imageView = view.findViewById(R.id.searchView);
imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_CatalogFragment_to_searchFragment);
    }
});*/
     //   searchView = view.findViewById(R.id.searchView);
     /*   recyclerViewSearch = view.findViewById(R.id.search_rec);

        catalogDetailModelList = new ArrayList<>();
        catalogDetailAdapter = new CatalogDetailAdapter(getContext(), catalogDetailModelList);
        // recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewSearch.setAdapter(catalogDetailAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        searchView.clearFocus();*/
     /*   searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Navigation.findNavController(view).navigate(R.id.action_CatalogFragment_to_searchFragment);
              //  searchProduct(newText);
                return false;
            }
        });*/
        return view;
    }


    /*  private void searchProduct(String name) {

        if (!name.isEmpty()) {
            firebaseFirestore.collection("NavCategoryDetailed").whereEqualTo("type", name).
                    get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                catalogDetailModelList.clear();
                                catalogDetailAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc: task.getResult().getDocuments()
                                ) {
                                    CatalogDetailModel catalogDetailModel = doc.toObject(CatalogDetailModel.class);
                                    catalogDetailModelList.add(catalogDetailModel);
                                    catalogDetailAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }*/




}