package com.example.coursegame.events;

import com.example.coursegame.gameplay.Trip;

public class DriverHitTrampolineEvent extends Event {
    public DriverHitTrampolineEvent() {
        super("Водій натрапив на трамплін");
    }

    @Override
    public void execute(Trip trip, int userChoice) {
        System.out.println("Водій натрапив на трамплін. Час поїздки скоротився на 30.");
        if (userChoice == 1)
            reaction().applyEffect(trip);
    }

    private EventEffect reaction(){
        return trip -> {
            System.out.println("Сказати водію що він здурів!");
            trip.reduceTime(30);
        };
    }
}

