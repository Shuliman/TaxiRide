package com.example.taxiride.strategy;

import org.springframework.stereotype.Component;

@Component
public class ComfortPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double distance) {
        return distance * 0.8;
    }
}