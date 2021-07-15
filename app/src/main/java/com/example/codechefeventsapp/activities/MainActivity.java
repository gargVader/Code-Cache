package com.example.codechefeventsapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.codechefeventsapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Codechef";

    NavController navController;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomBar);
        toolbar = findViewById(R.id.toolbar);
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        setupBottomNavBar();
        setupToolbar();
    }

    private void setupBottomNavBar() {
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.homeFragment, R.id.contestFragment, R.id.profileFragment)
                .build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

}

