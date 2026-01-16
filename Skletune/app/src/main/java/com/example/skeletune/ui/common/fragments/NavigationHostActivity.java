package com.example.skeletune.ui.common.fragments;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.skletune.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationHostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_host);

        BottomNavigationView navView = findViewById(R.id.bottom_nav_view);

        // Find the NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            // Get the NavController from the NavHostFragment
            NavController navController = navHostFragment.getNavController();

            // Set up the BottomNavigationView with the NavController
            // This single line handles all navigation clicks automatically!
            NavigationUI.setupWithNavController(navView, navController);
        }
    }
}
