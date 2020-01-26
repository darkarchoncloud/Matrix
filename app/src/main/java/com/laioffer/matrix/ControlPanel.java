package com.laioffer.matrix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

public class ControlPanel extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_control_panel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.baseline_home_black_18dp);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.drawer_logout) {
                    Config.username = null;
                    logout();

                }
                return true;
            }
        });
        final LocationTracker locationTracker = new LocationTracker(this);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                final TextView userTextView = drawerView.findViewById(R.id.user_name);
                final TextView locationTextView = drawerView.findViewById(R.id.user_location);

                locationTracker.getLocation();
                final double longitude = locationTracker.getLongitude();
                final double latitude = locationTracker.getLatitude();
                if (Config.username == null) {
                    userTextView.setText("");
                    locationTextView.setText("");
                } else {
                    userTextView.setText(Config.username);
                    locationTextView.setText("Lat=" + new DecimalFormat(".##").
                            format(latitude) + ",Lon=" + new DecimalFormat(".##").
                            format(longitude));
                }


            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,  MainFragment.newInstance()).commit();
    }
    private void logout() {
        finish();
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    drawerLayout.openDrawer(GravityCompat.START);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }



}
