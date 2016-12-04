package com.example.vkongv.crowd.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vkongv on 24/10/2016.
 */

public class Venue {
    public static final String NO_ID = "Unknown ID";
    public static final String NO_NAME = "Unknown Name";
    public static final String NO_ADDRESS = "Unknown Address";

    private String mVenueId;
    private String mVenueName;
    private String mVenueAddress;
    private String mVenueImage;
//    private String mVenue;

    public Venue(JSONObject json){
        try{
            if(json.has("id")){
                mVenueId = json.getString("id");
            }else{
                mVenueId = NO_ID;
            }

            if(json.has("name")){
                mVenueName = json.getString("name");
            }else{
                mVenueName = NO_NAME;
            }

            if(json.has("location")){
                mVenueAddress = generateAddress(json.getJSONObject("location").getJSONArray("formattedAddress"));
            }else{
                mVenueAddress = NO_ADDRESS;
            }
        }catch (JSONException e){
            if(mVenueId == null){
                mVenueId = NO_ID;
            }
            if(mVenueName == null){
                mVenueName = NO_NAME;
            }
            if(mVenueAddress == null){
                mVenueAddress = NO_ADDRESS;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private String generateAddress(JSONArray addressArr){
        if(addressArr.length() <= 0){
            return NO_ADDRESS;
        }

        String address = "";
        for(int i = 0; i < addressArr.length(); i++){
            try {
                if(i == 0){
                    address = addressArr.getString(i);
                }else{
                    address = address + ", " + addressArr.getString(i);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return address;
    }

    public String getId(){
        return mVenueId;
    }

    public String getAddress(){
        return mVenueAddress;
    }

    public String getImage(){
        return mVenueImage;
    }

    public void setImage(String url){
        this.mVenueImage = url;
    }
}
