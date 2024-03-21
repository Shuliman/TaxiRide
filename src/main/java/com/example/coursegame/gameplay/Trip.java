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
    @Autowired
    private ApplicationContext context;
    private final double distance;
    private  Player player;
    private int actualTime;
    private List<Observer> observers = new ArrayList<>();
    private List<Event> events;
    private final EventManager eventManager;

    public Trip(Player player, EventManager eventManager) {
        this.player = player;
        this.distance = generateRandomDistance(5, 20);
        this.actualTime = 0;
        this.events = new ArrayList<>();
        this.eventManager = eventManager;
    }
    private int calculateMinimalTime() {
        int MIN_SPEED = 7;
        return (int) Math.ceil(this.distance / MIN_SPEED);
    }

    private double generateRandomDistance(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    public void start(GameInterface gameInterface) throws IOException {
        BaseTaxi chosenTaxi = null;
        while (chosenTaxi == null) {
            String taxiChoice = gameInterface.askForTaxiChoice();
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
        this.actualTime = calculateInitialTime(player); // Теперь это начальное значение фактического времени
        int minimalTime = calculateMinimalTime(); // Минимальное время для сравнения в конце

        // Инициализация и генерация событий на основе ожидаемого времени поездки
        this.events = eventManager.generateEvents(actualTime);

        // Уведомление об начале поездки
        notifyObservers("Поїздка почалася", "Дистанція: " + distance + " км. Очікуваний час: " + actualTime + " хв.");

        // Симуляция событий
        simulateEvents(gameInterface);

        // Проверка, прибыл ли игрок вовремя
        checkIfArrivedOnTime(minimalTime);
    }

    private void checkIfArrivedOnTime(int minimalTime) {
        if (actualTime <= minimalTime) {
            notifyObservers("Поїздка завершена", "Ви успішно прибули в пункт призначення вчасно.");
        } else {
            notifyObservers("Поїздка завершена", "Ви запізнилися.");
        }
    }

    public int calculateInitialTime(Player player) {
        int speed = player.getSelectedTaxi().getSpeed(); // Предполагается, что у Taxi есть метод getSpeed()
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
        this.actualTime += additionalTime;
    }

    public void reduceTime(int reducedTime) {
        this.actualTime -= reducedTime;
    }
}
