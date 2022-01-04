package com.example.codechefeventsapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.alan.alansdk.AlanCallback;
import com.alan.alansdk.AlanConfig;
import com.alan.alansdk.button.AlanButton;
import com.alan.alansdk.events.EventCommand;
import com.example.codechefeventsapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Codechef";

    NavController navController;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    public static AlanButton alanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomBar);
        toolbar = findViewById(R.id.toolbar);
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        setupBottomNavBar();
        setupToolbar();
        initAlan();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    void initAlan() {

        AlanConfig config = AlanConfig.builder()
                .setProjectId("a993331959af4b459980e8539f9879df2e956eca572e1d8b807a3e2338fdd0dc/stage")
                .build();
        alanButton = findViewById(R.id.alan_button);
        alanButton.initWithConfig(config);

        AlanCallback alanCallback = new AlanCallback() {
            /// Handle commands from Alan Studio
            @Override
            public void onCommand(final EventCommand eventCommand) {
                try {
                    JSONObject command = eventCommand.getData();
                    String route = command.getJSONObject("data").getString("route");
                    Log.d(TAG, "onCommand: Route = " + route);
                    if (route.equals("home")) navController.navigate(R.id.homeFragment);
                    if (route.equals("contests")) {
                        navController.navigate(R.id.contestFragment);
                        JSONObject params = new JSONObject();
//                        try {
//                            params.put("data", "your data");
//                        } catch (JSONException e) {
//                            Log.e("AlanButton", e.getMessage());
//                        }
                        alanButton.callProjectApi("script::contestPageOpened", params.toString());
                    }
                    if (route.equals("profile")) navController.navigate(R.id.profileFragment);

                } catch (JSONException e) {
                    Log.e("AlanButton", e.getMessage());
                }
            }
        };

/// Register callbacks
        alanButton.registerCallback(alanCallback);

    }

}

