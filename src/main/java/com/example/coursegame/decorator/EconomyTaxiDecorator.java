package com.example.coursegame.decorator;

import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.strategy.EconomyPricingStrategy;
import com.example.coursegame.strategy.PricingStrategy;

public class EconomyTaxiDecorator extends BaseTaxi {
    private EconomyPricingStrategy pricingStrategy;
    public String getTaxiType() {
        return "Economy";
    }

    @Override
    public double getRideCost(double distance) {
        return pricingStrategy.calculatePrice(distance);
    }
}
