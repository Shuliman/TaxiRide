package com.example.coursegame.events;

import com.example.coursegame.gameplay.Trip;

public class DriverHitTrampolineEvent extends Event {
    public DriverHitTrampolineEvent() {
        super("Водій натрапив на трамплін");
    }

    @Override
    public void execute(Trip trip) {
        System.out.println("Водій натрапив на трамплін. Час поїздки скоротився на 30.");
        trip.reduceTime(30);
    }
}

