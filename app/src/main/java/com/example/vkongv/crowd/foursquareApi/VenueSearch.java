package com.example.vkongv.crowd.foursquareApi;

import com.example.vkongv.crowd.MainActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vkongv on 24/10/2016.
 */

public class VenueSearch {

    public void apiCall(){
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

            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
