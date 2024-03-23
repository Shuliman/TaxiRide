package com.example.coursegame.events;

import com.example.coursegame.gameplay.Trip;

import java.util.Arrays;
import java.util.List;

public class DriverHitTrampolineEvent extends Event {
    public DriverHitTrampolineEvent() {
        super("Водій натрапив на трамплін");
    }
    private static final List<String> OPTIONS = Arrays.asList(
            "Сказати водію що він здурів!"
    );

    @Override
    public void execute(Trip trip, int userChoice) {
        System.out.println("Водій натрапив на трамплін. Час поїздки скоротився на 30.");
        if (userChoice == 1)
            reaction().applyEffect(trip);
    }

    private EventEffect reaction(){
        return trip -> {
            trip.reduceTime(30);
        };
    }
    @Override
    public List<String> getOptions() {
        return OPTIONS;
    }
}