package com.example.madmeditation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityPhotoProfile extends AppCompatActivity {

    @Override @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_photo_profile);
        TextView cl = (TextView) findViewById(R.id.close);
        TextView del = (TextView) findViewById(R.id.deletephoto);
        ImageView z = findViewById(R.id.Photozakat);
        TextView t = findViewById(R.id.TimeP);

        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ActivityPhotoProfile.this, ActivityProfile.class);
                startActivity(intent);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override @SuppressLint("MissingInflatedId")
            public void onClick(View view) {
                Intent intent = new Intent( ActivityPhotoProfile.this, ActivityProfile.class);
                startActivity(intent);
            }
        });
    }
}