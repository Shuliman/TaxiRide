package com.example.coursegame.enitiy;

import com.example.coursegame.state.TaxiState;
import com.example.coursegame.state.TaxiStateChanger;
import lombok.Data;

@Data
public class Taxi implements TaxiStateChanger {
    private String taxiNumber;
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
}
