package com.example.exhaustwear;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.exhaustwear.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ActivityMainBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //toolbar makes func. of the actionbar
        setSupportActionBar(binding.toolbar);
        //for bottom bar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        //what user see first
        replaceFragment(new HomeFragment());
        //for fragments coming from bottom bar
        //when click on bottom items
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.catalog:
                    replaceFragment(new CatalogFragment());
                    break;
                case R.id.favourite:
                    replaceFragment(new FavouriteFragment());
                    break;
                case R.id.shop_basket:
                    replaceFragment(new BasketFragment());
                    break;
            }
            return true;
        });

    }
    //for choosing and replacing different fragments
    //for MainActivity
    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    //putting the icons in the top toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //for searching
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId()==R.id.search){
            Toast.makeText(this, "Do it later", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}