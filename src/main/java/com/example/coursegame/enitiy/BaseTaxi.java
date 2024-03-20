package com.example.coursegame.enitiy;

import com.example.coursegame.state.TaxiState;
import com.example.coursegame.decorator.Taxi;
import lombok.Data;

@Data
public abstract class BaseTaxi implements Taxi {
    private String taxiType;
    private String driverStatus;
    private TaxiState taxiState;


    @Override
    public void setWaiting() {
        this.taxiState = TaxiState.WAITING;
    }

    @Override
    public void setInTransit() {
        this.taxiState = TaxiState.IN_TRANSIT;
    }

    @Override
    public void setArrived() {
        this.taxiState = TaxiState.ARRIVED;
    }

    public abstract double getRideCost(double distance);
}
