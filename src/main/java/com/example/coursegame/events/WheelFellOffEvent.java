package com.example.coursegame.events;

import com.example.coursegame.gameplay.Trip;

public class WheelFellOffEvent extends Event {
    public WheelFellOffEvent() {
        super("Відвалилося колесо");
    }

    @Override
    public void execute(Trip trip, int userChoice) {
        switch (userChoice){
        case 1:
        giveWrenchEffect().applyEffect(trip);
        break;
        case 2:
            giveHoseEffect().applyEffect(trip);
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
}
