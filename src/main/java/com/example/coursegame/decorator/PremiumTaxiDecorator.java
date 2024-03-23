package com.example.coursegame.decorator;

import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.strategy.PremiumPricingStrategy;
import com.example.coursegame.strategy.PricingStrategy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PremiumTaxiDecorator extends BaseTaxi {
    private final PricingStrategy pricingStrategy;
    @Getter
    private final int speed = 40;
    @Autowired
    public PremiumTaxiDecorator(@Qualifier("premiumPricingStrategy") PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }
    public String getTaxiType() {
        return "Premium";
    }
    @Override
    public double getRideCost(double distance) {
        return pricingStrategy.calculatePrice(distance);
    }

}
