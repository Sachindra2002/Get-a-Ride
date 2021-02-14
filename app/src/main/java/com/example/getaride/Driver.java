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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Driver extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    String fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("fullName").getValue().toString();
                fullname = dataSnapshot.child("fullName").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toolbar toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        NavigationView navigationView = findViewById(R.id.navigation_view3);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout3);

        ActionBarDrawerToggle actionBarDrawerToggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new DriverHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home3);
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
            case R.id.nav_pendingridesdriver:
                Bundle bundle = new Bundle();
                bundle.putString("key", fullname);
                ViewUpcomingRidesDriverFragment fragment = new ViewUpcomingRidesDriverFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, fragment).addToBackStack(null).commit();
                break;
            case R.id.nav_pastridesdriver:
                Bundle bundle1 = new Bundle();
                bundle1.putString("key", fullname);
                ViewCompletedRidesDriverFragment fragment1 = new ViewCompletedRidesDriverFragment();
                fragment1.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, fragment1).addToBackStack(null).commit();
                break;
            case R.id.nav_ongoingridesdriver:
                Bundle bundle2 = new Bundle();
                bundle2.putString("key", fullname);
                AllOngoingRidesDriverFragment fragment2 = new AllOngoingRidesDriverFragment();
                fragment2.setArguments(bundle2);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, fragment2).addToBackStack(null).commit();
                break;

            case R.id.nav_myscheduledriver: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new DriverScheduleFragment()).addToBackStack(null).commit();
                break;

            case R.id.nav_profiledriver: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new DriverProfileFragment()).addToBackStack(null).commit();
                break;

            case R.id.nav_home3: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new DriverHomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_mysettingsdriver: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3, new DriverSettings()).addToBackStack(null).commit();
                break;

            case R.id.nav_signoutdriver:
                FirebaseAuth.getInstance().signOut();
                Intent intent  = new Intent(Driver.this, LoginActivity.class);
                startActivity(intent);
                finish();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}