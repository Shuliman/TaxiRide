package com.example.coursegame.decorator;

import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.strategy.ComfortPricingStrategy;
import com.example.coursegame.strategy.PricingStrategy;

public class ComfortTaxiDecorator extends BaseTaxi {
    private ComfortPricingStrategy pricingStrategy;
    @Override
    public String getTaxiType() {
        return "Comfort";
    }

    @Override
    public double getRideCost(double distance) {
        return pricingStrategy.calculatePrice(distance);
    }
}
