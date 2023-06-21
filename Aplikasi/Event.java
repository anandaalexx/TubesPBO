package Aplikasi;

import java.time.LocalDate;
import java.util.Date;

public class Event extends Schedule {
	
    private String location;
    private Date startDate;
    private Date endDate;
    private LocalDate eventDate;

    public Event(String title, Date startDate, Date endDate, String location, LocalDate eventDate,String priority) {
        setTitle(title);
        setPriority(priority);
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.eventDate = eventDate;
    }
    public LocalDate getEventDate() {
		return eventDate;
	}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;

    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
	
}