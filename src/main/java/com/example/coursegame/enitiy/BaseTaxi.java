package com.example.coursegame.enitiy;

import com.example.coursegame.decorator.Taxi;

public abstract class BaseTaxi implements Taxi {
    private String taxiType;
    private int speed;
    public  abstract int getSpeed();
    public abstract double getRideCost(double distance);
}
