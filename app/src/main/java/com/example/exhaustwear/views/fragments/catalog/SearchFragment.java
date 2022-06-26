package com.example.exhaustwear.views.fragments.catalog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exhaustwear.R;
import com.example.exhaustwear.adapters.CatalogDetailAdapter;
import com.example.exhaustwear.adapters.SearchAdapter;
import com.example.exhaustwear.models.CatalogDetailModel;
import com.example.exhaustwear.models.SearchModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchFragment extends Fragment {

    View view;
    FirebaseFirestore firebaseFirestore;

    //EditText searchView;
    private List<SearchModel> catalogDetailModelList;
    private RecyclerView recyclerViewSearch;
    private SearchAdapter catalogDetailAdapter;
    SearchView searchView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);


        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewSearch = view.findViewById(R.id.search_rec);
        searchView = view.findViewById(R.id.searchView);
        catalogDetailModelList = new ArrayList<>();
        catalogDetailAdapter = new SearchAdapter(getContext(), catalogDetailModelList);
        recyclerViewSearch.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerViewSearch.setAdapter(catalogDetailAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
//первая буква
            @Override
            public boolean onQueryTextChange(String newText) {
                searchProduct(newText.trim());
                return false;
            }
        });

        return view;
    }



    private void searchProduct(String name) {

        if (!name.isEmpty()) {
            firebaseFirestore.collection("AllProducts").whereEqualTo("search", name).
                    get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                catalogDetailModelList.clear();
                                catalogDetailAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc: task.getResult().getDocuments()
                                ) {
                                    SearchModel searchModel = doc.toObject(SearchModel.class);
                                    catalogDetailModelList.add(searchModel);
                                    catalogDetailAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }
}