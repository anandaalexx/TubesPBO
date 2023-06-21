package Aplikasi;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

public class Reminder {
    private Timer timer;

    public Reminder() {
        timer = new Timer();
    }

    public void setReminder(Event event) {
        Date startDate = event.getStartDate();
        LocalDateTime startDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime reminderDateTime = startDateTime.minusDays(1);

        TimerTask reminderTask = new TimerTask() {
            @Override
            public void run() {
                showReminder(event);
            }
        };

        timer.schedule(reminderTask, Date.from(reminderDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }

    private void showReminder(Event event) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Reminder");
            alert.setHeaderText(null);
            alert.setContentText("Event '" + event.getTitle() + "' is tomorrow!");

            // Customize the alert dialog
            Label label = new Label("Reminder");
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
            VBox vbox = new VBox(label, new Label("Event: " + event.getTitle()));
            vbox.setAlignment(Pos.CENTER_LEFT);
            vbox.setSpacing(10);
            alert.getDialogPane().setContent(vbox);

            alert.showAndWait();
        });
    }


}