package com.example.coursegame.observer;

public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String event, String message);
}
