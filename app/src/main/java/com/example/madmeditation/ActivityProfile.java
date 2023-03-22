package com.example.madmeditation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class ActivityProfile extends AppCompatActivity {

    ImageButton p;
    ImageButton m;
    ImageButton l;
    TextView ex;
    ImageView zak;
    ImageView ico;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        ico = (ImageView) findViewById(R.id.Avatar1);
        zak = (ImageView) findViewById(R.id.Photozakat);
        p = (ImageButton) findViewById(R.id.btn_menu);
        ex = (TextView) findViewById(R.id.exit);
        l = (ImageButton) findViewById(R.id.playlist);
        m = (ImageButton) findViewById(R.id.meditation);
        new ActivityProfile.DownloadImageTask((ImageView) ico).execute(ActivityLogin.maskaUser.getAvatar());
        zak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ActivityProfile.this, ActivityPhotoProfile.class);
                startActivity(intent);
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityProfile.this, ActivityMain.class);
                startActivity(intent);
            }
        });

        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityProfile.this, ActivityMenu.class);
                startActivity(intent);
            }
        });

        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityProfile.this, ActivityLogin.class);
                startActivity(intent);
            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityProfile.this, ActivityListen.class);
                startActivity(intent);
            }
        });

    }
    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}