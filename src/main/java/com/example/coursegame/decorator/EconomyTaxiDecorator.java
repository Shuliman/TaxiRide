package com.example.coursegame.decorator;

import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.strategy.PricingStrategy;

public class EconomyTaxiDecorator extends BaseTaxi {
    private PricingStrategy pricingStrategy;

    public EconomyTaxiDecorator(String taxiType, String driverStatus, PricingStrategy pricingStrategy) {
        super(taxiType, driverStatus);
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public double getRideCost(double distance) {
        return pricingStrategy.calculatePrice(distance);
    }
}
