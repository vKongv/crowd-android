package com.example.vkongv.crowd;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vkongv.crowd.foursquareApi.FoursquareBase;
import com.example.vkongv.crowd.model.Venue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getName();
    private JSONObject response;
    private TextView tv1;
    private EditText et1;
    private Button btn1;
    private Venue mVenue;

    private boolean isTest = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.searchText);
        et1 = (EditText) findViewById(R.id.responseText);
        if(isTest){
            btn1 = (Button) findViewById(R.id.testLayoutButton);
            btn1.setVisibility(View.VISIBLE);
        }
    }

    public void callVenueSearch(View v){
        setResponse(tv1.getText().toString());
    }

    public void openTestLayout(View v){
        Intent i = new Intent(this, TestActivity.class);
        startActivity(i);
    }

    private void setResponse(final String query) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    FoursquareBase fb = new FoursquareBase();
                    if((response = fb.callVenueSearch(params[0])) == null){
                        throw new NullPointerException();
                    }else {
                        makeVenue();
                        setResponseOnEt();
                        Log.d("Response", response.toString());
                    }
                }catch (NullPointerException e){
                    Log.e(TAG, "`response` is null");
                    generateErrorToast();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(query);
    }

    private void makeVenue(){
        if(response == null){
            return;
        }
        try{
            mVenue = new Venue(response.getJSONArray("venues").getJSONObject(0));
        }catch (JSONException e){
            Log.e(TAG, "JSON Exception during getting list of venue");
            generateErrorToast();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setResponseOnEt(){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    et1.setText(mVenue.getAddress());
                }catch (NullPointerException e){
                    generateErrorToast();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void generateErrorToast(){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), "Oops! Something went wrong", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void generateErrorToast(final String msg){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
