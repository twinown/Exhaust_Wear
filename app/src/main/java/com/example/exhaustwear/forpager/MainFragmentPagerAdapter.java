/*
package com.example.exhaustwear.navigationn;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.exhaustwear.fragments.BasketFragment;
import com.example.exhaustwear.fragments.FavouriteFragment;
import com.example.exhaustwear.fragments.HomeFragment;
import com.example.exhaustwear.fragments.account.AccountFragment;
import com.example.exhaustwear.fragments.account.LoginFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

*/
/*
  Pager adapter that keeps state of the fragments inside the bottom page navigation tabs
 *//*

public class MainFragmentPagerAdapter extends FragmentStateAdapter {
    //Statics




   */
/* private static final List<Fragment> BASE_FRAGMENTS = Arrays.asList(HomeFragment.newInstance(),  LoginFragment.newInstance(),
            FavouriteFragment.newInstance(), BasketFragment.newInstance(), AccountFragment.newInstance());*//*

  //  SignupFragment.newInstance()
//CatalogFragment.newInstance(),
    private static final int HOME_POSITION = 0;
    private static final int CATALOG_POSITION = 1;
    private static final int LOGIN_POSITION = 2;
    private static final int FAVOURITE_POSITION = 3;
    private static final int BASKET_POSITION = 4;
    private static final int ACCOUNT_POSITION = 5;
    private static final int SIGNUP_POSITION = 6;
    //end statics

    //Fields
    private List<Fragment> mHomeFragments;
    private List<Fragment> mCatalogFragments;
    private List<Fragment> mLoginFragments;
    private List<Fragment> mFavouriteFragments;
    private List<Fragment> mBasketFragments;
   // private List<Fragment> mAccountFragment;
  //  private List<Fragment> mSignupFragment;

    //end fields

    //constructor
    public MainFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        mHomeFragments = new ArrayList<>();
        mCatalogFragments = new ArrayList<>();
        mLoginFragments = new ArrayList<>();
        mFavouriteFragments = new ArrayList<>();
        mBasketFragments = new ArrayList<>();
       // mAccountFragment = new ArrayList<>();
      //  mSignupFragment = new ArrayList<>();
    }

    // FragmentPagerAdapter overridden methods
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == HOME_POSITION) {
            if (mHomeFragments.isEmpty()) {
                return BASE_FRAGMENTS.get(position);
            }
            return mHomeFragments.get(mHomeFragments.size() - 1);
        } else if (position == CATALOG_POSITION) {
            if (mCatalogFragments.isEmpty()) {
                return BASE_FRAGMENTS.get(position);
            }
            return mCatalogFragments.get(mCatalogFragments.size() - 1);
        } else if (position == LOGIN_POSITION) {
            if (mLoginFragments.isEmpty()) {
                return BASE_FRAGMENTS.get(position);
            }
            return mLoginFragments.get(mLoginFragments.size() - 1);
        } else if (position == FAVOURITE_POSITION) {
            if (mFavouriteFragments.isEmpty()) {
                return BASE_FRAGMENTS.get(position);
            }
            return mFavouriteFragments.get(mFavouriteFragments.size() - 1);
        } else
            if (mBasketFragments.isEmpty()) {
                return BASE_FRAGMENTS.get(position);
            }
            return mBasketFragments.get(mBasketFragments.size() - 1);
         */
/*else if (position == ACCOUNT_POSITION){
                if (mAccountFragment.isEmpty()){
                    return BASE_FRAGMENTS.get(position);
                }
            return mAccountFragment.get(mAccountFragment.size() - 1);
        }*//*
 */
/*else {
            if (mSignupFragment.isEmpty()) {
                return BASE_FRAGMENTS.get(position);
            }
                return mSignupFragment.get(mSignupFragment.size() - 1);
        }*//*

    }


    @Override
    public int getItemCount() {
        return BASE_FRAGMENTS.size();
    }

    @Override
    public long getItemId(int position) {
        if (position == HOME_POSITION
                && createFragment(position).equals(BASE_FRAGMENTS.get(position))) {
            return HOME_POSITION;
        } else if (position == CATALOG_POSITION
                && createFragment(position).equals(BASE_FRAGMENTS.get(position))) {
            return CATALOG_POSITION;
        } else if (position == LOGIN_POSITION
                && createFragment(position).equals(BASE_FRAGMENTS.get(position))) {
            return LOGIN_POSITION;
        } else if (position == FAVOURITE_POSITION
                && createFragment(position).equals(BASE_FRAGMENTS.get(position))) {
            return FAVOURITE_POSITION;
        } else if (position == BASKET_POSITION
                && createFragment(position).equals(BASE_FRAGMENTS.get(position))) {
            return BASKET_POSITION;
        } */
/*else if (position == ACCOUNT_POSITION
                && createFragment(position).equals(BASE_FRAGMENTS.get(position))){
            return ACCOUNT_POSITION;
        }*//*
   */
/*else if (position == SIGNUP_POSITION
                && createFragment(position).equals(BASE_FRAGMENTS.get(position))) {
            return SIGNUP_POSITION;
        }*//*

        return createFragment(position).hashCode();
    }
    //end


    //helper methods
    @SuppressLint("NotifyDataSetChanged")
    public void updateFragment(Fragment fragment, int position) {
        if (!BASE_FRAGMENTS.contains(fragment)) {
            addInnerFragment(fragment, position);
        }
        notifyDataSetChanged();
    }

  */
/*  @SuppressLint("NotifyDataSetChanged")
    public void openFragment(Fragment fragment, int position){
        if (!BASE_FRAGMENTS.contains(fragment)) {
           createFragment(position);
        }
    }*//*


    public boolean removeFragment(Fragment fragment, int position) {
        if (position == HOME_POSITION) {
            if (mHomeFragments.contains(fragment)) {
                removeInnerFragment(fragment, mHomeFragments);
                return true;
            }
        } else if (position == CATALOG_POSITION) {
            if (mCatalogFragments.contains(fragment)) {
                removeInnerFragment(fragment, mCatalogFragments);
                return true;
            }
        } else if (position == LOGIN_POSITION) {
            if (mLoginFragments.contains(fragment)) {
                removeInnerFragment(fragment, mLoginFragments);
                return true;
            }
        } else if (position == FAVOURITE_POSITION){
            if (mFavouriteFragments.contains(fragment)){
                removeInnerFragment(fragment, mFavouriteFragments);
                return true;
            }
        } else
            if (mBasketFragments.contains(fragment)){
                removeInnerFragment(fragment, mBasketFragments);
                return true;
            }
         */
/*else if (position == ACCOUNT_POSITION){
                if (mAccountFragment.contains(fragment)){
                    removeInnerFragment(fragment,mAccountFragment);
                    return true;
                }
            }*//*
   */
/*else if (position == SIGNUP_POSITION){
            if (mSignupFragment.contains(fragment)){
                removeInnerFragment(fragment, mSignupFragment);
                return true;
            }
        }*//*

        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void removeInnerFragment(Fragment fragment, List<Fragment> tabFragments) {
        tabFragments.remove(fragment);
        notifyDataSetChanged();
    }


    private void addInnerFragment(Fragment fragment, int position) {
        if (position == HOME_POSITION) {
            mHomeFragments.add(fragment);
        } else if (position == CATALOG_POSITION) {
            mCatalogFragments.add(fragment);
        } else if (position == LOGIN_POSITION){
            mLoginFragments.add(fragment);
        }else if (position == FAVOURITE_POSITION){
            mFavouriteFragments.add(fragment);
        }else if (position == BASKET_POSITION){
            mBasketFragments.add(fragment);
        } */
/*else if (position == ACCOUNT_POSITION){
            mAccountFragment.add(fragment);
        }*//*
 */
/*else if (position == SIGNUP_POSITION) {
            mSignupFragment.add(fragment);
        }*//*

    }

    //endhelpers
}
*/
