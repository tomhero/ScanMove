package com.example.android.scanmove.appmodel;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by 'Chayut' on 20/10/2559.
 * This class is used to represent place or establishment
 * that involve in application's database
 */

@Parcel
public class Landmark{

    ArrayList<Double> coordinates;
    String mSpotThaiName;
    String mSpotEngName;
    String imageUrl;

    public Landmark(ArrayList<Double> coordinates, String mSpotThaiName, String mSpotEngName, String imageUrl) {
        this.coordinates = coordinates;
        this.mSpotThaiName = mSpotThaiName;
        this.mSpotEngName = mSpotEngName;
        this.imageUrl = imageUrl;
    }

    public Landmark() {
        // empty constructor
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getmSpotThaiName() {
        return mSpotThaiName;
    }

    public void setmSpotThaiName(String mSpotThaiName) {
        this.mSpotThaiName = mSpotThaiName;
    }

    public String getmSpotEngName() {
        return mSpotEngName;
    }

    public void setmSpotEngName(String mSpotEngName) {
        this.mSpotEngName = mSpotEngName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
