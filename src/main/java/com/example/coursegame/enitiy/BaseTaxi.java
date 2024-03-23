package com.example.coursegame.enitiy;

import com.example.coursegame.state.TaxiState;
import com.example.coursegame.decorator.Taxi;

public abstract class BaseTaxi implements Taxi {
    private String taxiType;
    private String driverStatus;
    private TaxiState taxiState;
    private int speed;
    public  abstract int getSpeed();
    public abstract double getRideCost(double distance);
}
