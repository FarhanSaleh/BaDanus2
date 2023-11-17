package com.farhan.badanus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.farhan.badanus.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard){
                fragment = new DashboardFragment();
            } else if (itemId == R.id.nav_sell) {
                fragment = new SellFragment();
            } else if (itemId == R.id.nav_account) {
                fragment = new AccountFragment();
            }
            if (fragment != null){
                switchFragment(fragment);
                return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        if (savedInstanceState == null){
            binding.nav.setSelectedItemId(R.id.nav_dashboard);
        }
    }

    private void switchFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName()).commit();
    }
}