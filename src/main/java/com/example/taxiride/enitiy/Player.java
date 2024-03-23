package com.example.taxiride.enitiy;

import com.example.taxiride.decorator.Taxi;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
@Getter
public class Player {
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

    public boolean canAffordTaxi(BaseTaxi taxi, double distance) {
        return this.money >= taxi.getRideCost(distance);
    }
}
