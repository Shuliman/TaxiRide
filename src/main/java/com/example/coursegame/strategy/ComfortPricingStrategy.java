package com.example.coursegame.strategy;

public class ComfortPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(double distance) {
        return distance * 0.8;
    }
}