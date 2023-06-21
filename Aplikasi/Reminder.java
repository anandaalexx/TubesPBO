package Aplikasi;

import java.util.Date;

public class Reminder {
    private Timer timer;

    public Reminder() {
        timer = new Timer(); 
    }

    private static void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void tampilEventMendatang(Calendar calendar) {
        ObservableList<Event> upcomingEvents = FXCollections.observableArrayList();

        for (Event event : calendar.getEvents()) {
            Date eventStartDate = event.getStartDate();
            Date currentDate = new Date();

            long timeDifference = eventStartDate.getTime() - currentDate.getTime();
            long hoursDifference = timeDifference / (60 * 60 * 1000);

            if (hoursDifference < 24) {
                upcomingEvents.add(event);
            }
        }

        if (upcomingEvents.isEmpty()) {
            showAlert("No Upcoming Events", "There are no upcoming events within the next 24 hours.");
        } else {
            StringBuilder eventStringBuilder = new StringBuilder();

            for (Event event : upcomingEvents) {
                eventStringBuilder.append("Event: ").append(event.getTitle()).append("\n")
                        .append("Start Date: ").append(event.getStartDate()).append("\n")
                        .append("End Date: ").append(event.getEndDate()).append("\n")
                        .append("Priority: ").append(event.getPriority()).append("\n")
                        .append("Location: ").append(event.getLocation()).append("\n")
                        .append("===================").append("\n");
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Events");
            alert.setHeaderText(null);
            alert.setContentText(eventStringBuilder.toString());
            alert.showAndWait();
        }
    }


=======
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


public class Reminder {
	
	private static void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	public static void tampilEventMendatang(Calendar calendar) {
        ObservableList<Event> upcomingEvents = FXCollections.observableArrayList();

        for (Event event : calendar.getEvents()) {
            Date eventStartDate = event.getStartDate();
            Date currentDate = new Date();

            long timeDifference = eventStartDate.getTime() - currentDate.getTime();
            long hoursDifference = timeDifference / (60 * 60 * 1000);

            if (hoursDifference < 24) {
                upcomingEvents.add(event);
            }
        }

        if (upcomingEvents.isEmpty()) {
            showAlert("No Upcoming Events", "There are no upcoming events within the next 24 hours.");
        } else {
            StringBuilder eventStringBuilder = new StringBuilder();

            for (Event event : upcomingEvents) {
                eventStringBuilder.append("Event: ").append(event.getTitle()).append("\n")
                        .append("Start Date: ").append(event.getStartDate()).append("\n")
                        .append("End Date: ").append(event.getEndDate()).append("\n")
                        .append("Priority: ").append(event.getPriority()).append("\n")
                        .append("Location: ").append(event.getLocation()).append("\n")
                        .append("===================").append("\n");
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Events");
            alert.setHeaderText(null);
            alert.setContentText(eventStringBuilder.toString());
            alert.showAndWait();
        }
    }

	
}