package com.example.madmeditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityLogin extends AppCompatActivity {

    Button signIn;
    Button profile;
    TextView registration;
    Users users = new Users( "","");
    final static String userVariableKey = "USER_VARIABLE";
    public static MaskaUser maskaUser;
    TextInputEditText email, password;
    SharedPreferences preferences;
    final static String Email = "Email";
    final static String Password = "Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        signIn = (Button) findViewById(R.id.Sign_in);
        profile = (Button) findViewById(R.id.Prof);
        registration = (TextView) findViewById(R.id.regis);
        email =(TextInputEditText) findViewById(R.id.Email);
        password =(TextInputEditText) findViewById(R.id.Password);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    Toast.makeText(ActivityLogin.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Pattern pattern = Pattern.compile("@", Pattern.CASE_INSENSITIVE);
                    Matcher m = pattern.matcher(email.getText().toString());
                    boolean b = m.find();
                    if(b)
                    {
                        Avtoriz();
                    }
                    else
                    {
                        Toast.makeText(ActivityLogin.this, "Поле Email обязательно должен содержать символ '@'", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    Toast.makeText(ActivityLogin.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Pattern pattern = Pattern.compile("@", Pattern.CASE_INSENSITIVE);
                    Matcher m = pattern.matcher(email.getText().toString());
                    boolean b = m.find();
                    if(b)
                    {
                        Avtoriz();
                    }
                    else
                    {
                        Toast.makeText(ActivityLogin.this, "Поле Email обязательно должен содержать символ '@'", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        getInfo();
    }

    public void Avtoriz()
    {
        String email_str = String.valueOf(email.getText());
        String password_str = String.valueOf(password.getText());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        MaskaLogin modelSendUser = new MaskaLogin(email_str, password_str);
        Call<MaskaUser> call = retrofitAPI.createU(modelSendUser);
        call.enqueue(new Callback<MaskaUser>() {
            @Override
            public void onResponse(Call<MaskaUser> call, Response<MaskaUser> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ActivityLogin.this, "Пользователь с такой почтой и паролем не найден", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body() != null)
                {
                    if(response.body().getToken() != null)
                    {
                        saveInfo();
                        maskaUser = response.body();
                        Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
                        Bundle b = new Bundle();
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<MaskaUser> call, Throwable t) {
                Toast.makeText(ActivityLogin.this, "При авторизации возникла ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(userVariableKey, users);
        saveInfo();
        super.onSaveInstanceState(outState);
    }
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // получаем объект User в переменную
        users=(Users)savedInstanceState.getSerializable(userVariableKey);
        EditText etLogin=findViewById(R.id.Email);
        EditText etPassword=findViewById(R.id.Password);
        etLogin.setText(users.getLogin());
        etPassword.setText(users.getPassword());
    }
    public void saveInfo(){
        preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Email, email.getText().toString());
        editor.putString(Password,password.getText().toString());
        editor.commit();
    }

    public void getInfo(){
        preferences = getPreferences(MODE_PRIVATE);
        String emailu = preferences.getString(Email,"");
        String passwordu = preferences.getString(Password,"");
        email.setText(emailu);
        password.setText(passwordu);
    }
    public void click_registration_login(View v){
        Intent intent = new Intent(ActivityLogin.this,ActivityRegister.class);
        startActivity(intent);
    }
}