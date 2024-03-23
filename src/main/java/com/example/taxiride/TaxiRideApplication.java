package com.example.taxiride;

import com.example.taxiride.enitiy.Player;
import com.example.taxiride.gameInterface.GameInterface;
import com.example.taxiride.gameplay.Trip;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class TaxiRideApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = SpringApplication.run(TaxiRideApplication.class, args);
        GameInterface gameInterface = new GameInterface();
        Player player = new Player();

        Trip trip = context.getBean(Trip.class, 20, player);
        trip.start(gameInterface);
    }

}
