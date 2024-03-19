package com.example.coursegame.strategy;

public class PremiumPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double distance) {
        return distance * 1.2;
    }
}