package com.example.madmeditation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    ImageView ico;
    ImageButton m;
    ImageButton l;
    ImageButton p;
    private TextView text;
    private AdapterQuote adapterQuote;
    private List<MaskaQuote> maskaQuotes = new ArrayList<>();

    private AdapterFeelings adapterFeelings;
    private List<MaskaFeelings> maskaFeelings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ico = (ImageView) findViewById(R.id.Avatar);
        m = (ImageButton) findViewById(R.id.btn_menu);
        l = (ImageButton) findViewById(R.id.playlist);
        p = (ImageButton) findViewById(R.id.profile);
        text = findViewById(R.id.HelloP);

        text.setText("С возращением, "+ ActivityLogin.maskaUser.getNickName() + "!");
        new DownloadImageTask((ImageView) ico).execute(ActivityLogin.maskaUser.getAvatar());

        ListView listView = findViewById(R.id.ListQuote);
        adapterQuote = new AdapterQuote(ActivityMain.this, maskaQuotes);
        listView.setAdapter(adapterQuote);

        RecyclerView recyclerView = findViewById(R.id.ListFeelings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       adapterFeelings = new AdapterFeelings(maskaFeelings, ActivityMain.this);
        recyclerView.setAdapter(adapterFeelings);

        new Get_quotes().execute();
        new Get_feelings().execute();

        ico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityMain.this, ActivityProfile.class);
                startActivity(intent);
            }
        });

        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityMain.this, ActivityProfile.class);
                startActivity(intent);
            }
        });

        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityMain.this, ActivityMenu.class);
                startActivity(intent);
            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityMain.this, ActivityListen.class);
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
    @SuppressLint("StaticFieldLeak")
    private  class  Get_quotes extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object=new JSONObject(s);
                JSONArray tempArray= object.getJSONArray("data") ;
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject Json = tempArray.getJSONObject(i);
                    MaskaQuote temp = new MaskaQuote(
                            Json.getInt("id"),
                            Json.getString("title"),
                            Json.getString("image"),
                            Json.getString("description")
                    );

                    maskaQuotes.add(temp);
                    adapterQuote.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {
                Toast.makeText(ActivityMain.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }

    }
    @SuppressLint("StaticFieldLeak")
    private class Get_feelings extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                maskaFeelings.clear();
                adapterFeelings.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    MaskaFeelings tempProduct = new MaskaFeelings(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getInt("position"),
                            productJson.getString("image")
                    );
                    maskaFeelings.add(tempProduct);
                    adapterFeelings.notifyDataSetChanged();
                }
                maskaFeelings.sort(Comparator.comparing(MaskaFeelings::getPosition));
                adapterFeelings.notifyDataSetChanged();
            }
            catch (Exception exception)
            {
                Toast.makeText(ActivityMain.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

}