package com.example.coursegame.decorator;

import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.strategy.PremiumPricingStrategy;
import com.example.coursegame.strategy.PricingStrategy;

public class PremiumTaxiDecorator extends BaseTaxi {
    private PremiumPricingStrategy pricingStrategy;
    public String getTaxiType() {
        return "Premium";
    }
    @Override
    public double getRideCost(double distance) {
        return pricingStrategy.calculatePrice(distance);
    }

}
