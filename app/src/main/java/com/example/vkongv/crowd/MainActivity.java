package com.example.vkongv.crowd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.condesales.EasyFoursquare;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EasyFoursquare test = new EasyFoursquare(this);
    }
}
