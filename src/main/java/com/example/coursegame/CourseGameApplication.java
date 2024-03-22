package com.example.coursegame;

import com.example.coursegame.enitiy.Player;
import com.example.coursegame.gameInterface.GameInterface;
import com.example.coursegame.gameplay.Trip;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class CourseGameApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = SpringApplication.run(CourseGameApplication.class, args);
        GameInterface gameInterface = new GameInterface();
        Player player = new Player();

        Trip trip = context.getBean(Trip.class, 20, player);
        trip.start(gameInterface);
    }

}
