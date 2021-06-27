package com.example.codechefeventsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.PopupMenu;

import com.example.codechefeventsapp.model.Event;
import com.example.codechefeventsapp.adapters.EventAdapter;
import com.example.codechefeventsapp.R;

import java.util.ArrayList;
import java.util.List;

import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    List<Event> eventList;
    ViewPager viewPager;

    public static final String TAG = "Codechef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavBar();
    }

    private void setupBottomNavBar() {
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        SmoothBottomBar bottomBar = findViewById(R.id.bottomBar);
        PopupMenu popupMenu = new PopupMenu(this, null);
        popupMenu.inflate(R.menu.menu_bottom);
        bottomBar.setupWithNavController(popupMenu.getMenu(), navController);
    }

}

