package com.example.taxiride.enitiy;

import com.example.taxiride.decorator.Taxi;

public abstract class BaseTaxi implements Taxi {
    private String taxiType;
    private int speed;
    public  abstract int getSpeed();
    public abstract double getRideCost(double distance);
}
