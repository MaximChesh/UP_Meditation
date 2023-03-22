package com.example.madmeditation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityOnboarding extends AppCompatActivity {

    Button btnLogin;
    TextView reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        getSupportActionBar().hide();
        btnLogin = (Button) findViewById(R.id.btn_LogIn);
        reg = (TextView) findViewById(R.id.Reg);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityOnboarding.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityOnboarding.this, ActivityRegister.class);
                startActivity(intent);
            }
        });
    }

}