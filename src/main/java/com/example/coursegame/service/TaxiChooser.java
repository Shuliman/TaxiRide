package com.example.coursegame.service;

import com.example.coursegame.decorator.ComfortTaxiDecorator;
import com.example.coursegame.decorator.EconomyTaxiDecorator;
import com.example.coursegame.decorator.PremiumTaxiDecorator;
import com.example.coursegame.enitiy.BaseTaxi;
import com.example.coursegame.enitiy.Player;
import com.example.coursegame.gameInterface.GameInterface;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class TaxiChooser {
    public static BaseTaxi chooseTaxi(GameInterface gameInterface, ApplicationContext context, Player player, double distance) throws IOException {
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
            if (!player.canAffordTaxi(chosenTaxi, distance)) {
                gameInterface.showMessageBox("Недостатньо коштів. Виберіть інше таксі.");
                chosenTaxi = null;
            }
        }
        return  chosenTaxi;
    }
}
