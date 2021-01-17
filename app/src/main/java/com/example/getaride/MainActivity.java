package com.example.getaride;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    LottieAnimationView lottieAnimationView;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo);
        lottieAnimationView = findViewById(R.id.lottie);
        logo.animate().translationY(-2000).setDuration(500).setStartDelay(2000);
        lottieAnimationView.animate().translationY(-2000).setDuration(500).setStartDelay(2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent homeIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}