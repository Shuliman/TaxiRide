package com.example.coursegame.decorator;

public interface Taxi {
    void setWaiting();
    void setInTransit();
    void setArrived();
    String getTaxiNumber();
    String getTaxiType();
    String getDriverStatus();
}