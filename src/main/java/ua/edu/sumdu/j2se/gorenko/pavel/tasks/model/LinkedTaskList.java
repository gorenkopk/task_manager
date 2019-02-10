package ua.edu.sumdu.j2se.gorenko.pavel.tasks.model;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Date;

public class LinkedTaskList extends TaskList {
    /**
     * inner class Node for Linked List
     */
    private class Node {
        Node next;
        Task data;
    }

    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (task == null) {
            System.out.println("You try to add \"null\" to LinkedTaskList. Task can't be null");
        } else {
            Node a = new Node();
            a.data = task;
            if (tail == null) {
                head = a;
                tail = a;
            } else {
                tail.next = a;
                tail = a;
            }
            size++;
        }
    }

    @Override
    public boolean remove(Task task) {
        if (head == null)
            return false;

        if (head == tail) {
            head = null;
            tail = null;
            size--;
            return true;
        }

        if (head.data == task) {
            head = head.next;
            size--;
            return true;
        }

        Node t = head;
        while (t.next != null) {
            if (t.next.data == task) {
                if(tail == t.next){
                    tail = t;
                }
                t.next = t.next.next;
                size--;
                return true;
            }
            t = t.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Task getTask(int index) {
        Node b = head;
        if ((index + 1) <= size) {
            for (int i = 0; i < index; i++) {
                b = b.next;
            }
            return b.data;
        }
        return null;
    }

    public void printList()
    {
        Node t = head;
        while (t != null)
        {
            System.out.println(t.data + " ");
            t = t.next;
        }
    }

    @Override
    public Iterator<Task> iterator() {
        return new LinkedListIterator(this);
    }

    private class LinkedListIterator implements Iterator<Task> {
        private LinkedTaskList linkedlist;
        private Node head;
        private Node next;

        public LinkedListIterator(LinkedTaskList linkedList) {
            this.linkedlist = linkedList;
            this.next = linkedlist.head;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Task next() {
            if(!hasNext()) {
                throw new NoSuchElementException("Next element doesn't exist");
            }
            Task item = next.data;
            head = next;
            next = next.next;
            return item;
        }

        public void remove() {
            if (head == null) {
                throw new IllegalStateException();
            }
            linkedlist.remove(head.data);
        }
    }

}
