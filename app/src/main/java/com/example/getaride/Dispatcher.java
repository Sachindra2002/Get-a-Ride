package com.example.getaride;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.getaride.Fragments.AddDriverFragment;
import com.example.getaride.Fragments.DispatcherHomeFragment;
import com.example.getaride.Fragments.DispatcherProfileFragment;
import com.example.getaride.Fragments.DispatcherScheduleFragment;
import com.example.getaride.Fragments.DispatcherSettings;
import com.example.getaride.Recyclerviews.ManageComplaintsFragment;
import com.example.getaride.Recyclerviews.ManageDriversFragment;
import com.example.getaride.Recyclerviews.ManageRidesFragment;
import com.example.getaride.Recyclerviews.ManageScheduleFragment;
import com.example.getaride.Recyclerviews.ViewAllPastRidesFragment;
import com.example.getaride.Recyclerviews.ViewOngoingRidesDispatcherFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dispatcher extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher);

        Toolbar toolbar =findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        NavigationView navigationView = findViewById(R.id.navigation_view2);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout2);

        ActionBarDrawerToggle actionBarDrawerToggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new DispatcherHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home2);
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_home2: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new DispatcherHomeFragment()).commit();
                break;
            case R.id.nav_adddriver: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new AddDriverFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_managedriver: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ManageDriversFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_complaints: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ManageComplaintsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_schedule: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ManageScheduleFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_pendingrides: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ManageRidesFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_myschedule: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new DispatcherScheduleFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_profiledispatcher: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new DispatcherProfileFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_mysettingsdispatcher: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new DispatcherSettings()).addToBackStack(null).commit();
                break;
            case R.id.nav_pastrides: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ViewAllPastRidesFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_ongoingrides: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new ViewOngoingRidesDispatcherFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_signout:
                FirebaseAuth.getInstance().signOut();
                Intent intent  = new Intent(Dispatcher.this, LoginActivity.class);
                startActivity(intent);
                finish();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}