package com.example.coursegame.strategy;

import org.springframework.stereotype.Component;

@Component
public class PremiumPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double distance) {
        return distance * 1.2;
    }
}