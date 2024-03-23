package com.example.taxiride.gameplay;

import com.example.taxiride.enitiy.BaseTaxi;
import com.example.taxiride.enitiy.Player;
import com.example.taxiride.events.Event;
import com.example.taxiride.gameInterface.GameInterface;
import com.example.taxiride.observer.Observable;
import com.example.taxiride.observer.Observer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.taxiride.service.EventSimulator.simulate;
import static com.example.taxiride.service.TaxiChooser.chooseTaxi;
import static com.example.taxiride.service.TimeCalculator.calculateInitialTime;
import static com.example.taxiride.service.TimeCalculator.calculateMinimalTime;

@Component
public class Trip implements Observable {
    private final ApplicationContext context;
    private final double distance;
    private final Player player;
    private int travelTime;
    private final List<Observer> observers = new ArrayList<>();
    private List<Event> events;
    private final EventManager eventManager;

    public Trip(Player player, EventManager eventManager, ApplicationContext context, GameInterface gameInterface) {
        this.player = player;
        this.distance = generateRandomDistance(100, 300);
        this.travelTime = 0;
        this.events = new ArrayList<>();
        this.eventManager = eventManager;
        this.context = context;
        addObserver(gameInterface);
    }
    private double generateRandomDistance(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }
    public void start(GameInterface gameInterface) throws IOException {
        gameInterface.showGameStartInfo(distance, player.getMoney());
        BaseTaxi chosenTaxi = chooseTaxi(gameInterface, context, player, this.distance);
        player.setSelectedTaxi(chosenTaxi);
        this.travelTime = calculateInitialTime(this.distance, player.getSelectedTaxi().getSpeed());
        int minimalTime = calculateMinimalTime(this.distance, 7);

        this.events = eventManager.generateEvents(travelTime);

        notifyObservers("Поїздка почалася!\n", "Дистанція: " + Math.ceil(distance) +
                " км. Очікуваний час: " + travelTime + " хв" + " Щоб прибути вчасно терба впоратись за: " + minimalTime
                + " хв.");

        simulate(gameInterface, this.events, this);

        checkIfArrivedOnTime(minimalTime);
    }
    private void checkIfArrivedOnTime(int minimalTime) {
        if (travelTime <= minimalTime) {
            notifyObservers("Поїздка завершена \n", "Ви успішно прибули в пункт призначення вчасно.");
        } else {
            notifyObservers("Поїздка завершена \n", "Ви запізнилися.");
        }
    }
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String event, String message) {
        for (Observer observer : observers) {
            observer.update(event, message);
        }
    }

    public void addTime(int additionalTime) {
        this.travelTime += additionalTime;
        notifyObservers("Поїздка тепер триватиме: ", this.travelTime +" хв.");
    }

    public void reduceTime(int reducedTime) {
        this.travelTime -= reducedTime;
        notifyObservers("Поїздка тепер триватиме: ", this.travelTime +" хв.");
    }
}
