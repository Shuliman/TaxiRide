package com.example.coursegame.gameInterface;

import com.example.coursegame.observer.Observer;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
@Component
public class GameInterface implements Observer {
    private final BufferedReader reader;

    public GameInterface() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    public void showGameStartInfo(double distance, int money) {
        String infoMessage = String.format(
                "Початок поїздки:\n" +
                        "Дистанція поїздки: %.2f км.\n" +
                        "Ваш баланс: %d грн.\n",
                distance, money);
        showMessageBox(infoMessage);
    }
    public String askForTaxiChoice(double economyCost, double comfortCost, double premiumCost) throws IOException {
        List<String> options = List.of(
                String.format("Economy - %.2f грн.", economyCost),
                String.format("Comfort - %.2f грн.", comfortCost),
                String.format("Premium - %.2f грн.", premiumCost)
        );
        int choiceIndex = getUserChoice("Оберіть тип таксі:", options);
        return options.get(choiceIndex).split(" - ")[0]; //return only taxi type
    }
    public int getUserChoice(String question, List<String> options) throws IOException {
        System.out.println(question);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        int choice = 0;
        while (choice < 1 || choice > options.size()) {
            try {
                System.out.print("Введіть ваш вибір: ");
                choice = Integer.parseInt(reader.readLine());
                if (choice < 1 || choice > options.size()) {
                    System.out.println("Невірний вибір, повторіть спробу.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть число.");
            }
        }
        return choice - 1; // Adjusting because array index starts at 0
    }
    @Override
    public void update(String event, String message) {
        showMessageBox(event + message);
    }
    public void showMessageBox(String message) {
        System.out.println(message);
    }
}