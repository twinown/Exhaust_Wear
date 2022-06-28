package com.example.exhaustwear.views.fragments.catalog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.exhaustwear.R;
import com.example.exhaustwear.adapters.CatalogDetailAdapter;
import com.example.exhaustwear.models.CatalogDetailModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class CatalogDetailFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private List<CatalogDetailModel> list;
    private CatalogDetailAdapter catalogDetailAdapter;
    private FirebaseFirestore firebaseFirestore;
    private ProgressBar progressBar;

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_catalog, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar = view.findViewById(R.id.progress_bar_detail);
        String type = requireArguments().getString("type");
        recyclerView = view.findViewById(R.id.nav_cat_det_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        list = new ArrayList<>();
        catalogDetailAdapter = new CatalogDetailAdapter(getActivity(), list);

        // for saving recyclerview position
        catalogDetailAdapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);

        recyclerView.setAdapter(catalogDetailAdapter);

        String aroma = "aroma";
        String baseball = "baseball";
        String bracelets = "bracelets";
        String toys = "toys";
        String carpets = "carpets";
        String tshirts = "tshirts";
        String caps = "caps";
        String cups = "cups";
        String pants = "pants";
        String japnums = "japnums";
        if (type != null && type.equalsIgnoreCase(aroma)) {
            openDetail(aroma, "NavCategoryDetailed");
        } else if (type != null && type.equalsIgnoreCase(baseball)) {
            openDetail(baseball, "NavCategoryDetailedBaseball");
        } else if (type != null && type.equalsIgnoreCase(bracelets)) {
            openDetail(bracelets, "NavCategoryDetailedBracelets");
        } else if (type != null && type.equalsIgnoreCase(toys)) {
            openDetail(toys, "NavCategoryDetailedToys");
        } else if (type != null && type.equalsIgnoreCase(carpets)) {
            openDetail(carpets, "NavCategoryDetailedCarpets");
        } else if (type != null && type.equalsIgnoreCase(cups)) {
            openDetail(cups, "NavCategoryDetailedCups");
        } else if (type != null && type.equalsIgnoreCase(tshirts)) {
            openDetail(tshirts, "NavCategoryDetailedTshirts");
        } else if (type != null && type.equalsIgnoreCase(caps)) {
            openDetail(caps, "NavCategoryDetailedCaps");
        } else if (type != null && type.equalsIgnoreCase(pants)) {
            openDetail(pants, "NavCategoryDetailedPants");
        } else if (type != null && type.equalsIgnoreCase(japnums)) {
            openDetail(japnums, "NavCategoryDetailedJapnums");
        }
        return view;
    }

    public void openDetail(String value, String category) {
        firebaseFirestore.collection(category).whereEqualTo("type", value).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    CatalogDetailModel catalogDetailModel = documentSnapshot.toObject(CatalogDetailModel.class);
                    list.add(catalogDetailModel);
                    catalogDetailAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}