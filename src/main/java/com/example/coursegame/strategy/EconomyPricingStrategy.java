package com.example.coursegame.strategy;

import org.springframework.stereotype.Component;

@Component
public class EconomyPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double distance) {
        return distance * 0.5;
    }
}