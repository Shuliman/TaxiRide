package com.example.coursegame.events;

import com.example.coursegame.gameplay.Trip;

public abstract class Event {
    protected String description;

    public Event(String description) {
        this.description = description;
    }

    public abstract void execute(Trip trip, int userChoice);
}
