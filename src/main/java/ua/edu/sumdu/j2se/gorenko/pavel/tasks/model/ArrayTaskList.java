package ua.edu.sumdu.j2se.gorenko.pavel.tasks.model;

import java.util.*;

public class ArrayTaskList extends TaskList {
    private Task [] arrayTask;
    private final int MAX_ELEMENTS = 10;

    public ArrayTaskList() {
        super();
        this.arrayTask = new Task[MAX_ELEMENTS];
        this.size = 0;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            System.out.println("You try to add \"null\" to ArrayTaskList. Task can't be null");
        } else {
            if (arrayTask.length == size) {
                arrayTask = Arrays.copyOf(arrayTask, size + (((size * 30 / 100) > MAX_ELEMENTS) ? MAX_ELEMENTS
                        : (size * 30 / 100)));
            }
            arrayTask[size++] = task;
            notifyObservers("refresh");
        }
    }

    @Override
    public boolean remove(Task task) {
        for (int i = 0; i < size; i++) {
            if (task.equals(arrayTask[i])) {
                arrayTask[i] = null;
                for (int k = i; k < size - 1; k++) {
                    arrayTask[k] = arrayTask[k + 1];
                }
                size--;
                arrayTask[size]= null;
                notifyObservers("refresh");
                return true;
            }
        }
        return false;
    }

    @Override
    public Task getTask(int index) {
        try {
            return arrayTask[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Task with index - " + e.getMessage() + " doesn't exist");
            return null;
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Iterator<Task> iterator() {
        return new ArrayListIterator(this);
    }

    public class ArrayListIterator implements Iterator<Task> {
        private ArrayTaskList arrayTaskList;
        private int index = 0;
        private boolean wasCalledNext;

        public ArrayListIterator(ArrayTaskList arrayTaskList) {
            this.arrayTaskList = arrayTaskList;
            this.wasCalledNext = false;
        }

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Task next() {
            if (!hasNext()) {
                throw new NoSuchElementException("1.Next element doesn't exist");
            }
            Task item = arrayTaskList.getTask(index);
            index++;
            wasCalledNext = true;
            return item;
        }

        @Override
        public void remove() {
            if (!wasCalledNext) {
                throw new IllegalStateException();
            }
            arrayTaskList.remove(arrayTaskList.getTask(--index));
            wasCalledNext = false;
        }
    }
}