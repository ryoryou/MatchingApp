package com.example.ryo.matchingapp;

/**
 * Created by yuki19920716 on 2015/05/18.
 */
public class GPS {

    public double calculateTwoGPS(double pointA_latitude, double pointA_longitude, double pointB_latitude, double pointB_longitude){
        return Coords.calcDistHubeny(pointA_latitude, pointA_longitude, pointB_latitude, pointB_longitude);
    }

    public int conversionFromMiToKm(double gps_distance){
        int distance = (int)gps_distance / 1000;
        return distance;
    }
}
