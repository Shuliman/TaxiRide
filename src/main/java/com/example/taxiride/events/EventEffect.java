package com.example.taxiride.events;

import com.example.taxiride.gameplay.Trip;

public interface EventEffect {
    void applyEffect(Trip trip);
}
