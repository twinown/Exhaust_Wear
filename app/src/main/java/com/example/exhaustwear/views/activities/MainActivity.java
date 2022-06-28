package com.example.exhaustwear.views.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.exhaustwear.R;
import com.example.exhaustwear.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @SuppressLint({"NonConstantResourceId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //toolbar makes func. of the actionbar
        setSupportActionBar(binding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_container);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.toolbar, navController);

       /* AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home,R.id.navigation_catalog,
                R.id.navigation_account,R.id.navigation_favourite,  R.id.navigation_cart).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);


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
        //    if (item.getItemId()==R.id.search){
        //  Toast.makeText(this, "Do it later", Toast.LENGTH_SHORT).show();
        //   Navigation.findNavController(this, R.id.navigation_catalog).navigate(R.id.action_CatalogFragment_to_catalogSearchFragment);
       /*   FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
          fragmentTransaction.add(R.id.nav_host_container, new SearchFragment()).hide(new SearchFragment()).show(new SearchFragment()).addToBackStack("").commit();*/
        //   fragmentTransaction.replace(, new SearchFragment()).addToBackStack("").commit();
        //    }
        return super.onOptionsItemSelected(item);
    }


}





