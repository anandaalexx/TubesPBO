package Aplikasi;

import java.time.LocalDate;
import java.util.Date;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

public class Task extends Schedule {
	private LocalDate taskDate;
    private Date dueDate;
    private boolean statusComplete;

    public Task(String title,Date dueDate, LocalDate taskDate,String priority) {
        setTitle(title);
        setPriority(priority);
        this.taskDate = taskDate;
        this.dueDate = dueDate;
        this.statusComplete = false;
    }
    public LocalDate getTaskDate() {
		return taskDate;
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

