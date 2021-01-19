package com.example.quakereport;

public class Earthquake {
    private final Double mMagnitude;

    private final long mTimeInMiliseconds;
    private final String mLocation;
    private String mUrl;


    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMiliseconds = timeInMilliseconds;
        mUrl = url;
    }

    public Double getMagnitude() {
        return mMagnitude;
    }


    public String getLocation() {
        return mLocation;
    }

    public long getTime() {
        return mTimeInMiliseconds;
    }

    public String getUrl() {
        return mUrl;
    }
}
