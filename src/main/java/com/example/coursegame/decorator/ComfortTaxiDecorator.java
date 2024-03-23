package com.example.coursegame.decorator;

import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.strategy.ComfortPricingStrategy;
import com.example.coursegame.strategy.PricingStrategy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ComfortTaxiDecorator extends BaseTaxi {
    private final PricingStrategy pricingStrategy;
    @Getter
    private final int speed = 30;
    @Autowired
    public ComfortTaxiDecorator(@Qualifier("comfortPricingStrategy") PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }
    @Override
    public String getTaxiType() {
        return "Comfort";
    }

    @Override
    public double getRideCost(double distance) {
        return pricingStrategy.calculatePrice(distance);
    }
}
