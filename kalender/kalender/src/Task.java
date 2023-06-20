import java.time.LocalDate;
import java.util.Date;

public class Task extends Schedule {
	private LocalDate taskDate;
    private Date dueDate;
    private boolean statusComplete;

    public Task(String title,Date dueDate, LocalDate taskDate) {
        setTitle(title);
        this.taskDate = taskDate;
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
    
    public LocalDate getTaskDate() {
		return taskDate;
	}
    
    public void status() {
        System.out.println("Task: " + getTitle());
        System.out.println("Due Date: " + dueDate);
        System.out.println("Status: " + (statusComplete ? "Completed" : "Incomplete"));
    }

	
}
