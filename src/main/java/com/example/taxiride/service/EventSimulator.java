package com.example.taxiride.service;

import com.example.taxiride.events.Event;
import com.example.taxiride.gameInterface.GameInterface;
import com.example.taxiride.gameplay.Trip;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class EventSimulator {
    public static void simulate(GameInterface gameInterface, List<Event> events, Trip trip) throws IOException {
        Random random = new Random();
        for (Event event : events) {
            List<String> options = event.getOptions();
            if (!options.isEmpty()) {
                int userChoice = gameInterface.getUserChoice(event.getDescription(), options);
                event.execute(trip, userChoice);
            }
        }
    }
}
