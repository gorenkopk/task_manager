package ua.edu.sumdu.j2se.gorenko.pavel.tasks.model;

public interface Observable {
    void registerObserver(Observer o);
    void notifyObservers(String message);
}