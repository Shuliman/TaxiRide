package com.example.coursegame.state;

public interface TaxiStateChanger {
    void setWaiting();
    void setInTransit();
    void setArrived();
}