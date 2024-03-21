package com.example.coursegame.decorator;

import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.strategy.PricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EconomyTaxiDecorator extends BaseTaxi {
    private final PricingStrategy pricingStrategy;
    private final int speed = 8;

    @Autowired
    public EconomyTaxiDecorator(@Qualifier("economyPricingStrategy") PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public String getTaxiType() {
        return "Economy";
    }

    @Override
    public double getRideCost(double distance) {
        return pricingStrategy.calculatePrice(distance);
    }
}