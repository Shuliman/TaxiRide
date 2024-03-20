package com.example.coursegame.gameInterface;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameInterface {
    private Screen screen;
    private final MultiWindowTextGUI gui;

    public GameInterface() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
    }

    public String askForTaxiChoice() throws IOException {
        List<String> options = Arrays.asList("Economy", "Comfort", "Premium");
        int choiceIndex = getUserChoice("Оберіть тип таксі:", options);
        return options.get(choiceIndex);
    }

    public int getUserChoice(String question, List<String> options) throws IOException {
        // Создание диалогового окна для выбора действия игроком
        final AtomicInteger selectedOption = new AtomicInteger(-1);
        BasicWindow window = new BasicWindow();
        Panel panel = new Panel(new GridLayout(1));

        panel.addComponent(new Label(question));
        ActionListBox actionListBox = new ActionListBox();
        for (int i = 0; i < options.size(); i++) {
            int idx = i;
            actionListBox.addItem(options.get(i), () -> {
                selectedOption.set(idx);
                window.close();
            });
        }
        panel.addComponent(actionListBox);
        window.setComponent(panel);

        gui.addWindowAndWait(window);

        return selectedOption.get();
    }
    public void showMessageBox(String message) throws IOException {
        // Создание окна сообщения
        BasicWindow messageWindow = new BasicWindow();
        Panel panel = new Panel(new GridLayout(1)); // Используем GridLayout для упорядоченного расположения компонентов

        // Добавляем метку с переданным сообщением
        panel.addComponent(new Label(message));

        // Добавляем кнопку для закрытия окна
        Button closeButton = new Button("OK", messageWindow::close);
        panel.addComponent(closeButton);

        // Установка компонента панели в окно
        messageWindow.setComponent(panel);

        // Отображение окна сообщения
        gui.addWindowAndWait(messageWindow);
    }


    public void close() throws IOException {
        screen.stopScreen();
    }
}
