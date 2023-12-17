package com.example.fbook_app.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.fbook_app.HomeActivity.FavoriteFragment.FavoriteFragment;
import com.example.fbook_app.HomeActivity.HomeFragment.HomeFragment;
import com.example.fbook_app.HomeActivity.InfomationFragment.InfomationFragment;
import com.example.fbook_app.HomeActivity.LibraryFragment.LibraryFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new FavoriteFragment();
            case 2:
                return new LibraryFragment();
            case 3:
                return new InfomationFragment();
            default:
                return new HomeFragment();

        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
