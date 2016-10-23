package com.example.vkongv.crowd;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.foursquare.com/v2/venues/search?ll=40.7,-74&oauth_token=OQU3ZDGJTQK1IFXVIGK3ENW3VPWPNIQQX4XN3JND55BSWSJI&v=20161023")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    // ... check for failure using `isSuccessful` before proceeding

                    // Read data on the worker thread
                    final String responseData = response.body().string();

                    // Run view-related code back on the main thread
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                TextView tv1 = (TextView) findViewById(R.id.tv1);
                                tv1.setText(responseData.toCharArray(), 100, 100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
