package com.example.vkongv.crowd.foursquareApi;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.bouncycastle.crypto.tls.ConnectionEnd.client;

/**
 * Created by vkongv on 24/10/2016.
 */

public class FoursquareBase {
    private final String TAG = getClass().getName();
    private final String CLIENT_ID = "0TR2UB1WW3OAKDR0GLNINKSUD2U0XOXLMVSAUHWLFI2X02GY";
    private final String CLIENT_SECRET = "NF0XQUWB3KK21WUIOYZVPRZUQUMDNKNYMH30JFD3CZC543SN";
    private final String BASE_URL = "https://api.foursquare.com/v2/";
    private final String API_VER = "20161025";
    public final int RESULT_LIMIT = 1;
    private String mMainUrl;

    public static final String sVenueSearchCall = "venues/search";
    public static final String sVenueDetailCall = "venues/";
    public static final String sVenueImageCall = "venues/{VENUE_ID}/photos";
    private final String[] sSupportCalls = {sVenueSearchCall, sVenueDetailCall};

    private void buildMainUrl(String call, String param) {
        //Build url accordingly
        HttpUrl.Builder urlBuilder;
        switch (call) {
            case sVenueDetailCall:
                urlBuilder = HttpUrl.parse(BASE_URL + sVenueDetailCall + param).newBuilder();
                urlBuilder.addQueryParameter("client_id", CLIENT_ID);
                urlBuilder.addQueryParameter("client_secret", CLIENT_SECRET);
                urlBuilder.addQueryParameter("v", API_VER);
                break;
            case sVenueSearchCall:
                urlBuilder = HttpUrl.parse(BASE_URL + sVenueSearchCall).newBuilder();
                urlBuilder.addQueryParameter("client_id", CLIENT_ID);
                urlBuilder.addQueryParameter("client_secret", CLIENT_SECRET);
                urlBuilder.addQueryParameter("v", API_VER);
                //TODO: Change to param `ll` by getting GPS
                urlBuilder.addQueryParameter("near", "Kuala Lumpur");
                urlBuilder.addQueryParameter("limit", Integer.toString(RESULT_LIMIT));
                urlBuilder.addQueryParameter("query", param);
                break;
            case sVenueImageCall:
                String imageCall = sVenueImageCall.replace("{VENUE_ID}", param);
                urlBuilder = HttpUrl.parse(BASE_URL + imageCall).newBuilder();
                urlBuilder.addQueryParameter("client_id", CLIENT_ID);
                urlBuilder.addQueryParameter("client_secret", CLIENT_SECRET);
                urlBuilder.addQueryParameter("v", API_VER);
                break;
            default:
                return;
        }
        //Assign URL to main url
        mMainUrl = urlBuilder.build().toString();
    }

    private boolean checkInSupportCall(String call) {
        for (String item : sSupportCalls) {
            if (call.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public JSONObject callVenueSearch(String keyword) {
        buildMainUrl(sVenueSearchCall, keyword);
        return apiCall();
    }

    public JSONObject callVenueDetail(String id) {
        buildMainUrl(sVenueDetailCall, id);
        return apiCall();
    }

    public JSONObject callVenueImage(String id){
        buildMainUrl(sVenueImageCall, id);
        return apiCall();

    }

    private JSONObject apiCall() {
        //Build API call url
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(mMainUrl)
                .build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            //Check if the request is success or not
            JSONObject meta = json.getJSONObject("meta");
            if(meta.getInt("code") == 200){
                Log.i(TAG,"response is" + json.getJSONObject("response"));
                return json.getJSONObject("response");
            }else{
                Log.e(TAG, "Request fail with code `" + meta.getInt("code") + "`, ErrorType `" + meta.getString("errorType") + "`, ErrorDetail `" + meta.getString("errorDetail") + "`");
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
