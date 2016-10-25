package com.example.vkongv.crowd.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vkongv on 24/10/2016.
 */

public class Venue {
    private final String NO_ID = "Unknown ID";
    private final String NO_NAME = "Unknown Name";
    private final String NO_ADDRESS = "Unknown Address";

    private String mVenueId;
    private String mVenueName;
    private String mVenueAddress;
//    private String mVenue;

    public Venue(JSONObject json){
        try{
            mVenueId = json.getString("id");
            mVenueName = json.getString("name");
            mVenueAddress = generateAddress(json.getJSONObject("location").getJSONArray("formattedAddress"));
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

    public String getAddress(){
        return mVenueAddress;
    }
}
