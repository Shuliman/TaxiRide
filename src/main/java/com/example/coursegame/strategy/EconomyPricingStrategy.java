package com.example.coursegame.strategy;

public class EconomyPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice, double distance) {
        return basePrice + (distance * 0.5);
    }
}