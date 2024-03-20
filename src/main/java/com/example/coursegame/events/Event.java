package com.example.coursegame.events;

import com.example.coursegame.gameplay.Trip;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Event {
    protected String description;

    public Event(String description) {
        this.description = description;
    }

    public abstract void execute(Trip trip, int userChoice);

    public abstract List<String> getOptions();
}
