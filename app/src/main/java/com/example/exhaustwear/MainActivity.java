package com.example.exhaustwear;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.exhaustwear.databinding.ActivityMainBinding;
import com.example.exhaustwear.fragments.BasketFragment;
import com.example.exhaustwear.fragments.FavouriteFragment;
import com.example.exhaustwear.fragments.account.AccountFragment;
import com.example.exhaustwear.fragments.account.LoginFragment;
import com.example.exhaustwear.fragments.catalog.CatalogFragment;
import com.example.exhaustwear.fragments.HomeFragment;
import com.example.exhaustwear.navigation.FragmentUpdateCallback;
import com.example.exhaustwear.navigation.MainFragmentPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements FragmentUpdateCallback {
     MainFragmentPagerAdapter mPagerAdapter;
     //private BottomBarAdapter pagerAdapter;
     private ViewPager2 mViewPager;
     private FirebaseAuth firebaseAuth;
     private BottomNavigationView bottomNavigationView;
     ActivityMainBinding binding;
     private int mCurrentTabPosition;

/*

     Fragment homeFragment = new HomeFragment();
     Fragment catalogFragment = new CatalogFragment();
     Fragment basketFragment = new BasketFragment();
     Fragment accountFragment = new AccountFragment();
     Fragment favouriteFragment = new FavouriteFragment();
     Fragment loginFragment = new LoginFragment();
     Fragment forgotPassword = new ForgotPassword();
     Fragment signupFragment = new SignupFragment();
*/

    //common fragmentManager, will help to save instance
  //    FragmentManager fm = getSupportFragmentManager();

    public ViewPager2 getmViewPager() {
        return mViewPager;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //toolbar makes func. of the actionbar
        setSupportActionBar(binding.toolbar);
        //for the bottom bar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        mViewPager = findViewById(R.id.viewPager);

        mPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        if (firebaseAuth.getCurrentUser() != null) {
            mViewPager.setOffscreenPageLimit(7);
        }
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setUserInputEnabled(false);


  /*
        //optimisation
        if (firebaseAuth.getCurrentUser() != null){
            viewPager.setOffscreenPageLimit(7);
            viewPager.setPagingEnabled(false);
        }

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());

        pagerAdapter.addFragments(homeFragment);
        pagerAdapter.addFragments(catalogFragment);
        pagerAdapter.addFragments(loginFragment);
        pagerAdapter.addFragments(favouriteFragment);
        pagerAdapter.addFragments(basketFragment);
        pagerAdapter.addFragments(forgotPassword);
        pagerAdapter.addFragments(signupFragment);
        pagerAdapter.addFragments(accountFragment);
        viewPager.setAdapter(pagerAdapter);

*/

    /*    //what user sees first
        replaceFragment(new HomeFragment());
*/
        //adding fragments to the fragment manager
      /*  fm.beginTransaction().add(R.id.frame_layout, signupFragment, "7").commit();
        if (firebaseAuth.getCurrentUser() != null){
            fm.beginTransaction().add(R.id.frame_layout, accountFragment, "6").commit();
        }
        fm.beginTransaction().add(R.id.frame_layout, basketFragment, "5").commit();
        fm.beginTransaction().add(R.id.frame_layout, favouriteFragment, "4").commit();
       // fm.beginTransaction().add(R.id.frame_layout, loginFragment, "3").commit();
        fm.beginTransaction().add(R.id.frame_layout, catalogFragment, "2").commit();
        fm.beginTransaction().add(R.id.frame_layout, homeFragment, "1").commit();*/

        //for fragments coming from the bottom bar
        // clicking on the bottom items
        binding.bottomNavigationView.setOnItemSelectedListener((MenuItem item) -> {
            switch (item.getItemId()) {
                case R.id.home:
                    mCurrentTabPosition = HomeFragment.TAB_POSITION;
                    mViewPager.setCurrentItem(mCurrentTabPosition);
                    break;
                case R.id.catalog:
                    mCurrentTabPosition = CatalogFragment.TAB_POSITION;
                    mViewPager.setCurrentItem(mCurrentTabPosition);
                    break;

                case R.id.favourite:
                    mCurrentTabPosition = FavouriteFragment.TAB_POSITION;
                    mViewPager.setCurrentItem(mCurrentTabPosition);
                    break;
                case R.id.shop_basket:
                    mCurrentTabPosition = BasketFragment.TAB_POSITION;
                    mViewPager.setCurrentItem(mCurrentTabPosition);
                    break;
                case R.id.account:
                    if (firebaseAuth.getCurrentUser() == null) {
                        mCurrentTabPosition = LoginFragment.TAB_POSITION;
                        mViewPager.setCurrentItem(mCurrentTabPosition);
                    } else {
                        mCurrentTabPosition = AccountFragment.TAB_POSITION;
                        mViewPager.setCurrentItem(mCurrentTabPosition);
                    }
                    break;
            }
            return true;
        });
    }

    //for choosing and replacing different fragments
    /*private void replaceFragment (@NonNull Fragment fragment){
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.viewPager, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }*/

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
    @Override
    public void onBackPressed() {
      if (!mPagerAdapter.removeFragment(mPagerAdapter.createFragment(mCurrentTabPosition), mCurrentTabPosition)) {
          if (mViewPager.getCurrentItem() != 0){
              mViewPager.setCurrentItem(0);
            binding.bottomNavigationView.setSelectedItemId(R.id.home);
          }else{ finish();}
        }
    }

    @Override
    public void addFragment(Fragment fragment, int tabPosition) {
        mPagerAdapter.updateFragment(fragment, tabPosition);
    }

    @Override
    public void openAccountFragment() {
        mCurrentTabPosition = AccountFragment.TAB_POSITION;
        mViewPager.setCurrentItem(mCurrentTabPosition);
    }

    @Override
    public void openLoginFragment() {
        mCurrentTabPosition = LoginFragment.TAB_POSITION;
        mViewPager.setCurrentItem(mCurrentTabPosition);
    }

    @Override
    public void backFragment() {
        if (!mPagerAdapter.removeFragment(mPagerAdapter.createFragment(mCurrentTabPosition), mCurrentTabPosition)) {
            finish();
        }
    }

    //for back system bottom
   /* @Override
    public void onBackPressed() {
        if(fm.getBackStackEntryCount() != 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }*/

    }





