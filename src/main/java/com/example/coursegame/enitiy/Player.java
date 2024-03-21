package com.example.coursegame.enitiy;

import com.example.coursegame.decorator.Taxi;
import com.example.coursegame.observer.Observer;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
@Getter
public class Player implements Observer {
    private int money;
    @Setter
    private Taxi selectedTaxi;
    @Value("${game.player.minMoney}")
    private int minMoney;
    @Value("${game.player.maxMoney}")
    private int maxMoney;

    @PostConstruct
    public void init() {
        this.money = new Random().nextInt(maxMoney - minMoney + 1) + minMoney;
    }
    @Override
    public void update(String event, String message) {
        System.out.println("Повідомлення для гравця: " + event + " - " + message);
    }

    public boolean canAffordTaxi(BaseTaxi taxi, double distance) {
        return this.money >= taxi.getRideCost(distance);
    }
}
