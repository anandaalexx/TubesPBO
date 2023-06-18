package Aplikasi;

import java.util.Date;

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

}
