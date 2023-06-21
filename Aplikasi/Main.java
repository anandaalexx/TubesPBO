package Aplikasi;

import java.util.TimeZone;

import javax.swing.text.html.ListView;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Date;

public class Main extends Application {
    private Calendar calendar;
    private ListView<String> tampilanListTask;
    private ListView<String> tampilanListEvent;
    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Sistem Informasi Penjadwalan dan Task Manajemen");
        Date currentDate = new Date();
        TimeZone timeZone = TimeZone.getDefault();
        calendar = new Calendar(currentDate, timeZone);

        tampilanListTask = new ListView<>();
        tampilanListTask.setPrefSize(400, 100);

        tampilanListEvent = new ListView<>();
        tampilanListEvent.setPrefSize(400, 100);

        Label title = new Label();
        GridPane.setColumnSpan(title, 7);
        title.setAlignment(Pos.CENTER);
        title.getStyleClass().add("title-label");

        Label taskLab = new Label("Task: ");
        Label eventLab = new Label("Event: ");
        
        Button removeTaskButton = new Button("Remove Task");
        removeTaskButton.setOnAction(e -> {
            int selectedIndex = tampilanListTask.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                calendar.removeTask(calendar.getTasks().get(selectedIndex));
                updateTampilanListTask();
            }
        });
                
        Button completeTaskButton = new Button("Complete Task");
        completeTaskButton.setOnAction(e -> {
            int selectedIndex = tampilanListTask.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                Task selectedTask = calendar.getTasks().get(selectedIndex);
                selectedTask.setEndTask(true);
                updateTampilanListTask();
            }
        });
    }
    
    private void updateTampilanListEvent() {
    	tampilanListEvent.getItems().clear();
        for (Event event : calendar.getEvents()) {
            String eventString = "Event: " + event.getTitle() + "\n";
            tampilanListEvent.getItems().add(eventString);
        }
    }
    
    private void updateTampilanListTask() {
    	tampilanListTask.getItems().clear();
        for (Task task : calendar.getTasks()) {
            String taskString = "Task: " + task.getTitle() + "\n";
            tampilanListTask.getItems().add(taskString);

        }
        
    }
}




