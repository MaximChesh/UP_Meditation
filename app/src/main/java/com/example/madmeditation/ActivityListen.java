package com.example.madmeditation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivityListen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        getSupportActionBar().hide();
    }
}