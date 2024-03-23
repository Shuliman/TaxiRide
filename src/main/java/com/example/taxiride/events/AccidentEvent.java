package com.example.taxiride.events;

import com.example.taxiride.gameplay.Trip;

import java.util.Arrays;
import java.util.List;

public class AccidentEvent extends Event {
    public AccidentEvent() {
        super("Автомобіль потрапив у ДТП");
    }
    private static final List<String> OPTIONS = Arrays.asList(
            "Сісти в найближче таксі",
            "Викликати швидку і полцію"
    );

    @Override
    public void execute(Trip trip, int userChoice) {
        switch (userChoice){
            case 0:
                sitIntoNearestTaxi().applyEffect(trip);
                break;
            case 1:
                callThe911().applyEffect(trip);
                break;
            default:
                System.out.println("Невідома дія. Нічого не змінено.");
                break;
        }
    }

    private EventEffect sitIntoNearestTaxi() {
        return trip -> {
            System.out.println("Ви дали сіли у найближче таксі. Час поїздки збільшився на 10.");
            trip.addTime(10);
        };
    }

    private EventEffect callThe911() {
        return trip -> {
            System.out.println("Ви викликали швидку і поліцію. Час поїздки збільшився на 20.");
            trip.addTime(20);
        };
    }
    @Override
    public List<String> getOptions() {
        return OPTIONS;
    }
}
