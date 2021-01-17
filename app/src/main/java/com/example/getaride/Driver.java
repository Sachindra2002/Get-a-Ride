package com.example.getaride;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Driver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new DriverHomeFragment()).commit();
        }
    }
}