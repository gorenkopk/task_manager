package ua.edu.sumdu.j2se.gorenko.pavel.tasks.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class TaskList implements Iterable<Task>, Cloneable, Serializable, Observable {

    int size;
    List<Observer> observers;

    public TaskList() {
        observers = new LinkedList<>();
    }

    public int size() {
        return size;
    }

    public abstract Task getTask(int index);

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    @Override
    public int hashCode() {
        int hash = 0;
        for (Task task : this) {
            hash = hash + task.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskList that = (TaskList) o;
        if (that == o) {
            Iterator first = this.iterator();
            Iterator second = that.iterator();
            while (first.hasNext()) {
                if (first.next().equals(second.next()));
                return true;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TaskList clone() throws CloneNotSupportedException {
        try {
            return (TaskList) super.clone();
        }
        catch (CloneNotSupportedException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public String toString() {
        String t = "";
        Iterator forToString = this.iterator();
        while (forToString.hasNext()) {
            t = t + forToString.next().toString() + "\n";
        }
        return t;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(String message) {
        for(Observer observer: observers) {
            observer.notification(message);
        }
    }
}



