package com.example.fbook_app.HomeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fbook_app.Adapter.ViewPagerAdapter;
import com.example.fbook_app.HomeActivity.FavoriteFragment.FavoriteFragment;
import com.example.fbook_app.HomeActivity.HomeFragment.HomeFragment;
import com.example.fbook_app.HomeActivity.InfomationFragment.InfomationFragment;
import com.example.fbook_app.Interface.FragmentReload;
import com.example.fbook_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FragmentReload fragment = (FragmentReload) adapter.instantiateItem(viewPager, position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        fragment.reloadFragmentData();
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_fav).setChecked(true);
                        fragment.reloadFragmentData();
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_info).setChecked(true);
                        fragment.reloadFragmentData();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Fragment selectedFragment = null;
                if (itemId == R.id.action_home) {
                    viewPager.setCurrentItem(0);
                } else if (itemId == R.id.action_fav) {
                    viewPager.setCurrentItem(1);
                }else if (itemId == R.id.action_info) {
                    viewPager.setCurrentItem(2);
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (checkBackPressed()) {
            checkQuestionBack();
        } else {
            super.onBackPressed();
        }

    }

    private boolean checkBackPressed() {
        Fragment currentFragment = fragmentManager.findFragmentById(android.R.id.content);
        return currentFragment == null;
    }

    private void checkQuestionBack() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, " Back lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}