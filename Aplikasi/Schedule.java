package Aplikasi;

import java.util.Date;

public class Schedule {
    private String title;
    private String priority;
    private Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority(){
        return priority;
    }

    public void setPriority(String priority){
        this.priority = priority;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }
}
