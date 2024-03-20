package com.example.coursegame.gameplay;

import com.example.coursegame.events.DriverHitTrampolineEvent;
import com.example.coursegame.events.Event;
import com.example.coursegame.events.WheelFellOffEvent;
import com.example.coursegame.gameInterface.GameInterface;
import com.example.coursegame.observer.Observable;
import com.example.coursegame.observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trip implements Observable {
    private final double distance;
    private int expectedTime;
    private int actualTime;
    private List<Observer> observers = new ArrayList<>();
    private List<Event> events;

    public Trip(int expectedTime) {
        this.distance = generateRandomDistance(5, 20);
        this.expectedTime = expectedTime;
        this.actualTime = 0; // Начинаем с нуля
        this.events = new ArrayList<>();
        initializeEvents();
    }

    private void initializeEvents() {
        events.add(new WheelFellOffEvent());
        events.add(new DriverHitTrampolineEvent());
    }

    private double generateRandomDistance(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    public void start() {
        notifyObservers("Поїздка почалася", "Дистанція: " + distance + " км. Очікуваний час: " + expectedTime + " хв.");

        // Симуляция событий из списка
        Random random = new Random();
        int eventIndex = random.nextInt(events.size());
        Event event = events.get(eventIndex);
        event.execute(this,1 ); // Передача ссылки на Trip для взаимодействия с событием

        // Здесь должна быть реализация интерфейса для выбора действия игроком


        // Проверка на опоздание
        if (actualTime <= expectedTime) {
            notifyObservers("Поїздка завершена", "Ви успішно прибули в пункт призначення вчасно.");
        } else {
            notifyObservers("Поїздка завершена", "Ви опізнилися.");
        }
    }

    public void startGame() throws IOException {
        GameInterface gameInterface = new GameInterface();
        Trip trip = new Trip(30); // предположим, что expectedTime - это 30 минут

        notifyObservers("Поїздка почалася", "Дистанція: " + distance + " км. Очікуваний час: " + expectedTime + " хв.");
        trip.initializeEvents(); // Убедитесь, что события инициализированы

        // Симуляция событий из списка
        Random random = new Random();
        List<String> options = new ArrayList<>();
        options.add("Подати викрутку");
        options.add("Подати шланг");

        int eventIndex = random.nextInt(trip.getEvents().size());
        Event event = trip.getEvents().get(eventIndex);

        int userChoice = gameInterface.getUserChoice(event.getDescription(), options);
        event.execute(trip, userChoice); // Передаем выбор пользователя и объект поездки

        trip.checkIfArrivedOnTime();

        gameInterface.close();
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
