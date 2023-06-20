package Aplikasi;

import java.util.Date;
<<<<<<< HEAD
=======

public class Task extends Schedule{
    private Date dueDate;
    private boolean statusComplete;

    public Task(String title, Date dueDate) {
        setTitle(title);
        this.dueDate = dueDate;
        this.statusComplete = false;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isStatusComplete() {
        return statusComplete;
    }

    public void setEndTask(boolean completed) {
        this.statusComplete = completed;
    }

    public void status() {
        System.out.println("Task: " + getTitle());
        System.out.println("Due Date: " + dueDate);
        System.out.println("Status: " + (statusComplete ? "Completed" : "Incomplete"));
    }
>>>>>>> ef749162a317afb3fb398f61cfa56302d3d848ae

public class Task extends Schedule {
    private Date dueDate;
    private boolean statusComplete;

    public Task(String title, Date dueDate) {
        setTitle(title);
        this.dueDate = dueDate;
        this.statusComplete = false;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isStatusComplete() {
        return statusComplete;
    }

    public void setEndTask(boolean completed) {
        this.statusComplete = completed;
    }

    public void status() {
        System.out.println("Task: " + getTitle());
        System.out.println("Due Date: " + dueDate);
        System.out.println("Status: " + (statusComplete ? "Completed" : "Incomplete"));
    }
}
