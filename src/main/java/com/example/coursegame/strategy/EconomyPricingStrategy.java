package com.example.coursegame.strategy;

public class EconomyPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double distance) {
        return distance * 0.5;
    }
}