package com.example.coursegame.decorator;

import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.strategy.PricingStrategy;

public class PremiumTaxiDecorator extends BaseTaxi {
    private PricingStrategy pricingStrategy;

    public PremiumTaxiDecorator(String taxiNumber, String taxiType, String driverStatus) {
        super(taxiNumber, taxiType, driverStatus);
    }

    @Override
    public double getRideCost(double distance) {
        return pricingStrategy.calculatePrice(distance);
    }

}
