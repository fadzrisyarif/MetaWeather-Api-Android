package com.fadzri.weatherapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView data;
    private Button button2;
    private Button button3;
    private TextInputEditText cityInput;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = findViewById(R.id.data);
        cityInput = findViewById(R.id.cityInput);

        WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener((v -> {
            // Instantiate the RequestQueue.
            weatherDataService.getCityID(cityInput.getText().toString(), data);
        }));
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            weatherDataService.getCityForcastByID(cityInput.getText().toString(), data);
        });
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            weatherDataService.getCityForcastByName(cityInput.getText().toString(), data);
        });


    }
}