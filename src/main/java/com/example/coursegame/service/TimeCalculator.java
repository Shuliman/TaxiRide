package com.example.coursegame.service;

public class TimeCalculator {
    public static int calculateInitialTime(double distance, int speed) {
        return (int) Math.ceil(distance / speed);
    }

    public static int calculateMinimalTime(double distance, int minSpeed) {
        return (int) Math.ceil(distance / minSpeed);
    }
}

