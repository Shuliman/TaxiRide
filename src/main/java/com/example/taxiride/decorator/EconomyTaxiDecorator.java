package com.example.taxiride.decorator;

import com.example.taxiride.enitiy.BaseTaxi;
import com.example.taxiride.strategy.PricingStrategy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EconomyTaxiDecorator extends BaseTaxi {
    private final PricingStrategy pricingStrategy;
    @Getter
    private final int speed = 20;

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