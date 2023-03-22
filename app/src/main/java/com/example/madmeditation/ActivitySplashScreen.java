package com.example.madmeditation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

public class ActivitySplashScreen extends AppCompatActivity {

    private static final int DELAY = 1000;
    int defTimeOut = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( ActivitySplashScreen.this, ActivityOnboarding.class);
                startActivity(intent);
            }
        }, DELAY);
    }
}