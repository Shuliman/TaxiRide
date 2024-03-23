package com.example.coursegame.gameplay;

import com.example.coursegame.decorator.ComfortTaxiDecorator;
import com.example.coursegame.decorator.EconomyTaxiDecorator;
import com.example.coursegame.decorator.PremiumTaxiDecorator;
import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.enitiy.Player;
import com.example.coursegame.events.Event;
import com.example.coursegame.gameInterface.GameInterface;
import com.example.coursegame.observer.Observable;
import com.example.coursegame.observer.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private int calculateMinimalTime() {
        int MIN_SPEED = 7;
        return (int) Math.ceil(this.distance / MIN_SPEED);
    }
    private double generateRandomDistance(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }
    public void start(GameInterface gameInterface) throws IOException {
        gameInterface.showGameStartInfo(distance, player.getMoney());
        BaseTaxi chosenTaxi = null;
        while (chosenTaxi == null) {
            String taxiChoice = gameInterface.askForTaxiChoice(
                    context.getBean(EconomyTaxiDecorator.class).getRideCost(distance),
                    context.getBean(ComfortTaxiDecorator.class).getRideCost(distance),
                    context.getBean(PremiumTaxiDecorator.class).getRideCost(distance)
            );
            chosenTaxi = switch (taxiChoice) {
                case "Economy" -> context.getBean(EconomyTaxiDecorator.class);
                case "Comfort" -> context.getBean(ComfortTaxiDecorator.class);
                case "Premium" -> context.getBean(PremiumTaxiDecorator.class);
                default -> context.getBean(EconomyTaxiDecorator.class);
            };
            if (!player.canAffordTaxi(chosenTaxi, this.distance)) {
                gameInterface.showMessageBox("Недостатньо коштів. Виберіть інше таксі.");
                chosenTaxi = null;
            }
        }
        player.setSelectedTaxi(chosenTaxi);
        this.travelTime = calculateInitialTime(player);
        int minimalTime = calculateMinimalTime();

        this.events = eventManager.generateEvents(travelTime);

        notifyObservers("Поїздка почалася!\n", "Дистанція: " + Math.ceil(distance) +
                " км. Очікуваний час: " + travelTime + " хв" + " Щоб прибути вчасно терба впоратись за: " + minimalTime
                + " хв.");

        simulateEvents(gameInterface);

        checkIfArrivedOnTime(minimalTime);
    }
    private void checkIfArrivedOnTime(int minimalTime) {
        if (travelTime <= minimalTime) {
            notifyObservers("Поїздка завершена \n", "Ви успішно прибули в пункт призначення вчасно.");
        } else {
            notifyObservers("Поїздка завершена \n", "Ви запізнилися.");
        }
    }
    public int calculateInitialTime(Player player) {
        int speed = player.getSelectedTaxi().getSpeed();
        return (int) Math.ceil(this.distance / speed);
    }

    private void simulateEvents(GameInterface gameInterface) throws IOException {
        Random random = new Random();
        for (Event event : events) {
            List<String> options = event.getOptions();
            if (!options.isEmpty()) {
                int userChoice = gameInterface.getUserChoice(event.getDescription(), options);
                event.execute(this, userChoice);
            }
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
        notifyObservers("Поїздка тепер триватиме: ", String.valueOf(this.travelTime) +" хв.");
    }

    public void reduceTime(int reducedTime) {
        this.travelTime -= reducedTime;
        notifyObservers("Поїздка тепер триватиме: ", String.valueOf(this.travelTime) +" хв.");
    }
}
