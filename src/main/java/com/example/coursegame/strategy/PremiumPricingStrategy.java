package com.example.coursegame.strategy;

public class PremiumPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice, double distance) {
        return basePrice + (distance * 1.2);
    }
}