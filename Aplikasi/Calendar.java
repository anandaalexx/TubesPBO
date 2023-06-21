package Aplikasi;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.Date;

public class Calendar {
    private List<Task> tasks;
    private List<Event> events;
    
    public Calendar(Date date, TimeZone timeZone) {
        this.tasks = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }
    
    
}