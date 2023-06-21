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

import java.time.LocalDate;
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

        Button removeEventButton = new Button("Remove Event");
        removeEventButton.setOnAction(e -> {
            int selectedIndex = tampilanListEvent.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                calendar.removeEvent(calendar.getEvents().get(selectedIndex));
                updateTampilanListEvent();
            }
        });

        BorderPane borderPane = new BorderPane();
        HBox buttonBar = new HBox(10, removeTaskButton,completeTaskButton,removeEventButton,saveButton);
        VBox vBox = new VBox(10,taskLab, tampilanListTask,eventLab, tampilanListEvent, buttonBar);
        borderPane.setCenter(vBox);
        borderPane.setPadding(new Insets(10));
        BorderPane.setAlignment(vBox, Pos.CENTER);

        GridPane calendarGridPane = new GridPane();
        calendarGridPane.setPrefSize(800, 400);
        calendarGridPane.setAlignment(Pos.TOP_LEFT);
        calendarGridPane.getStyleClass().add("calendar-grid"); 

        calendarGridPane.add(previousButton, 0, 0);
        calendarGridPane.add(title, 1, 0, 5, 1);
        calendarGridPane.add(nextButton, 3, 0);
        
        for (int i = 0; i < 7; i++) {
            Label dayOfWeek = new Label();
            GridPane.setFillWidth(dayOfWeek, true);
            GridPane.setHgrow(dayOfWeek, javafx.scene.layout.Priority.ALWAYS);
            dayOfWeek.setAlignment(Pos.CENTER);
            dayOfWeek.getStyleClass().add("day-of-week-label"); // Tambahkan kelas CSS
            calendarGridPane.add(dayOfWeek, i, 1);
            updateCalendar(calendarGridPane);
        }
    }

    private void updateCalendar(GridPane calendarGridPane) {
        calendarGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 1);

        LocalDate firstDayOfMonth = blnThnSekarang.atDay(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        Label monthLabel = (Label) calendarGridPane.getChildren().get(1);
        monthLabel.setText("   " + blnThnSekarang.getMonth().toString() + " " + blnThnSekarang.getYear());

        int row = 2;
        int column = dayOfWeek % 7;

     
        
        for (int day = 1; day <= blnThnSekarang.lengthOfMonth(); day++) {
            Button dayButton = new Button(String.valueOf(day));
            dayButton.setPrefWidth(200);
            dayButton.setPrefHeight(100);
            dayButton.getStyleClass().add("day-button"); // Tambahkan kelas CSS

            VBox dateBox = new VBox();
            dateBox.setAlignment(Pos.CENTER);
            dateBox.getChildren().add(dayButton);

            LocalDate currentDate = LocalDate.of(blnThnSekarang.getYear(), blnThnSekarang.getMonth(), day);

            // Tambahkan tampilanListTask hanya jika ada task pada tanggal ini
            for (Task task : calendar.getTasks()) {
                LocalDate taskDate = task.getTaskDate();
                if (taskDate.equals(currentDate)) {
                	dateBox.getChildren().add(new ListView<>(FXCollections.observableArrayList(FXCollections.singletonObservableList("Task: " + task.getTitle()))));
                }
            }

            // Tambahkan tampilanListEvent hanya jika ada event pada tanggal ini
            for (Event event : calendar.getEvents()) {
                LocalDate eventDate = event.getEventDate();
                if (eventDate.equals(currentDate)) {
                	dateBox.getChildren().add(new ListView<>(FXCollections.observableArrayList(FXCollections.singletonObservableList("Event: " + event.getTitle()))));
                }
            }
            
            column++;
            if (column == 7) {
                column = 0;
                row++;
            }
            
            dayButton.setOnAction(e -> {
            	selectedDate = currentDate;
                tampilOpsi();
            });
            calendarGridPane.add(dateBox, column, row);
        }
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




