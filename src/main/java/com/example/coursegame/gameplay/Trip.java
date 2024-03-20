package com.example.coursegame.gameplay;

import com.example.coursegame.decorator.ComfortTaxiDecorator;
import com.example.coursegame.decorator.EconomyTaxiDecorator;
import com.example.coursegame.decorator.PremiumTaxiDecorator;
import com.example.coursegame.decorator.Taxi;
import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.enitiy.Player;
import com.example.coursegame.events.DriverHitTrampolineEvent;
import com.example.coursegame.events.Event;
import com.example.coursegame.events.WheelFellOffEvent;
import com.example.coursegame.gameInterface.GameInterface;
import com.example.coursegame.observer.Observable;
import com.example.coursegame.observer.Observer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trip implements Observable {
    private final double distance;
    private int expectedTime;
    private  Player player;
    private int actualTime;
    private List<Observer> observers = new ArrayList<>();
    private List<Event> events;
    private final int MIN_SPEED = 7;
    @Autowired
    private EventManager eventManager;

    public Trip(int expectedTime, Player player) {
        this.player = player;
        this.distance = generateRandomDistance(5, 20);
        this.expectedTime = expectedTime;
        this.actualTime = 0;
        this.events = new ArrayList<>();
    }
    private int calculateMinimalTime() {
        return (int) Math.ceil(this.distance / MIN_SPEED);
    }

    private void initializeEvents() {
        // Предполагаем, что одно событие может происходить каждые 5 минут поездки
        int eventsCount = actualTime / 5;
        events.clear(); // Очищаем список, чтобы добавить новые события
        Random random = new Random();
        for (int i = 0; i < eventsCount; i++) {
            // Генерируем случайное событие
            switch (random.nextInt(2)) { // Допустим, у нас есть 2 типа событий
                case 0:
                    events.add(new WheelFellOffEvent());
                    break;
                case 1:
                    events.add(new DriverHitTrampolineEvent());
                    break;
                // Здесь можно добавить больше случаев для разных событий
            }
        }
    }

    private double generateRandomDistance(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    public void start(GameInterface gameInterface) throws IOException {
        BaseTaxi chosenTaxi = null;
        while (chosenTaxi == null) {
            String taxiChoice = gameInterface.askForTaxiChoice();
            chosenTaxi = switch (taxiChoice) {
                case "Economy" -> new EconomyTaxiDecorator();
                case "Comfort" -> new ComfortTaxiDecorator();
                case "Premium" -> new PremiumTaxiDecorator();
                default -> new EconomyTaxiDecorator();
            };
            if (!player.canAffordTaxi(chosenTaxi, this.distance)) {
                gameInterface.showMessageBox("Недостатньо коштів. Виберіть інше таксі.");
                chosenTaxi = null;
            }
        }
        player.setSelectedTaxi(chosenTaxi);
        this.actualTime = calculateExpectedTime(player); // Теперь это начальное значение фактического времени
        int minimalTime = calculateMinimalTime(); // Минимальное время для сравнения в конце

        // Инициализация и генерация событий на основе ожидаемого времени поездки
        initializeEvents();

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

    public int calculateExpectedTime(Player player) {
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
