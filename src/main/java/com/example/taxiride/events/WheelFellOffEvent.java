package com.example.taxiride.events;

import com.example.taxiride.gameplay.Trip;

import java.util.Arrays;
import java.util.List;

public class WheelFellOffEvent extends Event {
    public WheelFellOffEvent() {
        super("Відвалилося колесо");
    }
    private static final List<String> OPTIONS = Arrays.asList(
            "Подати викрутку",
            "Подати шланг"
    );

    @Override
    public void execute(Trip trip, int userChoice) {
        switch (userChoice){
        case 0:
        giveWrenchEffect().applyEffect(trip);
        break;
        case 1:
            giveHoseEffect().applyEffect(trip);
            break;
        default:
            System.out.println("Невідома дія. Нічого не змінено.");
            break;
            }
    }

    private EventEffect giveWrenchEffect() {
        return trip -> {
            System.out.println("Ви дали викрутку. Час поїздки збільшився на 20.");
            trip.addTime(20);
        };
    }

    private EventEffect giveHoseEffect() {
        return trip -> {
            System.out.println("Ви дали шланг. Час поїздки збільшився на 40.");
            trip.addTime(40);
        };
    }
    @Override
    public List<String> getOptions() {
        return OPTIONS;
    }
}
