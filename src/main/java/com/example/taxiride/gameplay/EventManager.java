package com.example.taxiride.gameplay;

import com.example.taxiride.events.AccidentEvent;
import com.example.taxiride.events.DriverHitTrampolineEvent;
import com.example.taxiride.events.Event;
import com.example.taxiride.events.WheelFellOffEvent;
import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

@Component
public class EventManager {
    private final Random random = new Random();

    public List<Event> generateEvents(int expectedTime) {
        List<Event> events = new ArrayList<>();
        int eventsCount = expectedTime / 3;

        for (int i = 0; i < eventsCount; i++) {
            switch (random.nextInt(3)) {
                case 0:
                    events.add(new WheelFellOffEvent());
                    break;
                case 1:
                    events.add(new DriverHitTrampolineEvent());
                    break;
                case 2:
                    events.add(new AccidentEvent());
                    break;

            }
        }
        return events;
    }
}
