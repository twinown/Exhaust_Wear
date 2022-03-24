package com.example.exhaustwear.navigation;

import androidx.fragment.app.Fragment;

/*
  Interface used for app navigation/updating the bottom tab fragments
 */
public interface FragmentUpdateCallback {
    void addFragment(Fragment fragment, int tabPosition);
     void openAccountFragment();
     void openLoginFragment();
     void backFragment();

}
