package com.example.coursegame.strategy;

public class ComfortPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice, double distance) {
        return basePrice + (distance * 0.8);
    }
}