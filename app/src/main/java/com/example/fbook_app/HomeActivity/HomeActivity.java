package com.example.fbook_app.HomeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fbook_app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_fav) {
                    Toast.makeText(HomeActivity.this, "Favorite", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_cart) {
                    Toast.makeText(HomeActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.action_info) {
                    Toast.makeText(HomeActivity.this, "Info", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}