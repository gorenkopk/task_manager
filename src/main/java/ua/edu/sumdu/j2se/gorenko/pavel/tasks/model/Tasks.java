package ua.edu.sumdu.j2se.gorenko.pavel.tasks.model;

import java.util.*;

public class Tasks {

    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) {

        ArrayTaskList incomingList = new ArrayTaskList();

        for (Task t :tasks) {
            if (t.isActive() && t.nextTimeAfter(start) != null
                    && t.nextTimeAfter(start).compareTo(end) <= 0) {
                incomingList.add(t);
            }
        }
        return incomingList;

    }
    public static SortedMap <Date, Set<Task>> calendar (Iterable<Task> tasks, Date start, Date end){
        SortedMap<Date, Set<Task>> kalendar = new TreeMap<Date, Set<Task>>() ;
        for(Task task: tasks){
            Date temp = task.nextTimeAfter(start);
            while ( temp !=null && temp.compareTo(end)<=0 ){
                if(kalendar.containsKey(temp)){
                    kalendar.get(temp).add(task);
                }
                else{
                    Set <Task> first = new HashSet<Task>();
                    first.add(task);
                    kalendar.put(temp, first);
                }
                temp = task.nextTimeAfter(temp);
            }
        }
        return kalendar;
    }
}