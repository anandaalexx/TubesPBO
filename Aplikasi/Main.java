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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveToFile());

        BorderPane borderPane = new BorderPane();
        HBox buttonBar = new HBox(10, removeTaskButton,completeTaskButton,removeEventButton,saveButton);
        VBox vBox = new VBox(10,taskLab, tampilanListTask,eventLab, tampilanListEvent, buttonBar);
        borderPane.setCenter(vBox);
        borderPane.setPadding(new Insets(10));
        BorderPane.setAlignment(vBox, Pos.CENTER);

        Button previousButton = new Button("<<");
        previousButton.getStyleClass().add("navigation-button"); // Tambahkan kelas CSS
        previousButton.setOnAction(event -> {
            blnThnSekarang = blnThnSekarang.minusMonths(1);
            updateCalendar(calendarGridPane);
        });

        Button nextButton = new Button(">>");
        nextButton.getStyleClass().add("navigation-button"); // Tambahkan kelas CSS
        nextButton.setOnAction(event -> {
            blnThnSekarang = blnThnSekarang.plusMonths(1);
            updateCalendar(calendarGridPane);
        });

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

        updateCalendar(calendarGridPane);
        loadFromFile();    
        
	
	     // Menambahkan event handler untuk click pada item di ListView tampilanListTask
	     tampilanListTask.setOnMouseClicked(event -> {
	         int selectedIndex = tampilanListTask.getSelectionModel().getSelectedIndex();
	         if (selectedIndex != -1) {
	             Task selectedTask = calendar.getTasks().get(selectedIndex);
	             tampilDetailTask(selectedTask);
	         }
	     });
	
	     // Menambahkan event handler untuk click pada item di ListView tampilanListEvent
	     tampilanListEvent.setOnMouseClicked(event -> {
	         int selectedIndex = tampilanListEvent.getSelectionModel().getSelectedIndex();
	         if (selectedIndex != -1) {
	             Event selectedEvent = calendar.getEvents().get(selectedIndex);
	             tampilDetailEvent(selectedEvent);
	         }
	     });
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

    private void tampilDetail(Task task) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Details");
        alert.setHeaderText(null);
        alert.setContentText("Task for: " + task.getTaskDate() + "\n"+
        		"Title: " + task.getTitle() + "\n" +
                "Due Date: " + task.getDueDate() + "\n" +
                "Priority: "+ task.getPriority()+"\n" +
                "Status: " + (task.isStatusComplete() ? "Completed" : "Incomplete"));
                        
        alert.showAndWait();
    }

    private void tampilDetail(Event event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event Details");
        alert.setHeaderText(null);
        alert.setContentText("Event for: " + event.getEventDate() + "\n"+
        		"Title: " + event.getTitle() + "\n" +
                "Start Date: " + event.getStartDate() + "\n" +
                "End Date: " + event.getEndDate() + "\n" +
                "Priority: "+ event.getPriority()+"\n"+
                "Location: " + event.getLocation());
                
        alert.showAndWait();
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

    private void tampilOpsi() {
        Stage dialogStage2 = new Stage();
        dialogStage2.setTitle("Add Event/Task");

        Label pilLabel = new Label("			Tambah Event/Task");
        Button addTask = new Button("Add Task");
        Button addEvent = new Button("Add Event");

        addTask.setOnAction(e -> {tampilAddTask(); dialogStage2.close();});
        addEvent.setOnAction(e -> {tampilAddEvent(); dialogStage2.close();});

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, pilLabel);
        gridPane.addRow(1, addTask, addEvent);
        gridPane.setPadding(new Insets(10));

        // Set up the scene
        Scene scene = new Scene(gridPane);
        dialogStage2.setScene(scene);
        dialogStage2.show();
    }

    private void tampilAddTask() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Task");

        // Initialize GUI components
        Label titleLabel = new Label("Task For "+selectedDate+":");
        TextField titleTextField = new TextField();
        Label dueDateLabel = new Label("Due Date:");
        DatePicker dueDatePicker = new DatePicker();
        Label prioLabel = new Label ("Prioritas:");
        TextField prioTextField = new TextField();
        
        Button addButton = new Button("Add");
        
        addButton.setOnAction(e -> {
            String title = titleTextField.getText();
            String priority = prioTextField.getText();
            LocalDate taskDate = selectedDate;
            if (!title.isEmpty() && dueDatePicker.getValue() != null) {
                Date dueDate = java.sql.Date.valueOf(dueDatePicker.getValue());
                
				Task newTask = new Task(title, dueDate,taskDate,priority);
                calendar.addTask(newTask);
                updateTampilanListTask();
                dialogStage.close();
            } else {
                showAlert("Error", "Please enter a title and due date.");
            }
        });

        // Create the layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, titleLabel, titleTextField);
        gridPane.addRow(1, prioLabel,prioTextField);
        gridPane.addRow(2, dueDateLabel, dueDatePicker);
        gridPane.add(addButton, 1, 3);
        gridPane.setPadding(new Insets(10));

        // Set up the scene
        Scene scene = new Scene(gridPane);
        dialogStage.setScene(scene);
        dialogStage.setWidth(340);
        dialogStage.show();
    }
        
	private void tampilAddEvent() {
        Stage dialogStage1 = new Stage();
        dialogStage1.setHeight(260);
        dialogStage1.setWidth(250);
        dialogStage1.setTitle("Add Event");
        
        Label eventLabel = new Label("Event For "+selectedDate+":");
        TextField eventTextField = new TextField();
        Label startDateLabel = new Label("Start Date:");
        DatePicker startDatePicker = new DatePicker();
        Label endDateLabel = new Label("End Date:");
        DatePicker endDatePicker = new DatePicker();
        Label locLabel = new Label("Location:");
        TextField locTextField = new TextField();
        Label prioLabel = new Label ("Prioritas:");
        TextField prioTextField = new TextField();
        Button addButton = new Button("Add");
        

        // Set the action for Add button
        addButton.setOnAction(e -> {
            String title = eventTextField.getText();
            String location = locTextField.getText();
            String priority = prioTextField.getText();
            if (!title.isEmpty() && startDatePicker.getValue() != null && endDatePicker.getValue() != null && !location.isEmpty()) {
                Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
                Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());
                
                LocalDate eventDate = selectedDate;
                Event newEvent = new Event(title, startDate, endDate, location, eventDate,priority);
                calendar.addEvent(newEvent);
                
                updateTampilanListEvent();
                dialogStage1.close();
            }
        });

        // Create the layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.addRow(0, eventLabel, eventTextField);
        gridPane.addRow(1, startDateLabel, startDatePicker);
        gridPane.addRow(2, endDateLabel, endDatePicker);
        gridPane.addRow(3, prioLabel,prioTextField);
        gridPane.addRow(4, locLabel, locTextField);
        
        gridPane.add(addButton, 1,5 );
        gridPane.setPadding(new Insets(10));

        // Set up the scene
        Scene scene = new Scene(gridPane);
        dialogStage1.setScene(scene);
        dialogStage1.setWidth(345);
        dialogStage1.show();
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : calendar.getTasks()) {
                String taskString = "Task For: " + task.getTaskDate() + "\n"+
                		"Title: " + task.getTitle() + "\n" +
                        "Due Date: " + task.getDueDate() + "\n" +
                		"Prioritas: "+task.getPriority()+"\n"+
                        "Status: " + (task.isStatusComplete() ? "Completed" : "Incomplete") + "\n" +
                        "----------------------";
                writer.write(taskString);
                writer.newLine();
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to save tasks to file.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("events.txt"))) {
            for (Event event : calendar.getEvents()) {
                String eventString = "Event For: " +event.getEventDate()+ "\n" +
                		"Title: " + event.getTitle() + "\n" +
                        "Start Date: " + event.getStartDate() + "\n" +
                        "End Date: " + event.getEndDate() + "\n" +
                        "Prioritas: "+event.getPriority()+"\n"+
                        "Location: " + event.getLocation()+ "\n"+
                        "----------------------";
                writer.write(eventString);
                writer.newLine();

            }

        } catch (IOException e) {
            showAlert("Error", "Failed to save events to file.");
        }
        showAlert("Success", "Event and Task have been saved to events.txt and tasks.txt");
        
    }

    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	if (line.startsWith("Task For:")) {
            		String[] taskInfo = line.split(": ");
            	    if (taskInfo.length == 2) {
            	        LocalDate taskDate = LocalDate.parse(taskInfo[1].trim());
            	    
            		
            		line = reader.readLine();
	                String title = line.split(": ")[1];
	                line = reader.readLine();
	                String dueDateString = line.split(": ")[1];
	                line = reader.readLine();
	                String priority = line.split(": ")[1];
	                line = reader.readLine();
	                String statusString = line.split(": ")[1];
	                
	                
	
	                Date dueDate = java.sql.Date.valueOf(dueDateString);
	                boolean statusComplete = statusString.equals("Completed");
	
	                Task task = new Task(title, dueDate,taskDate,priority);
	                task.setEndTask(statusComplete);
	                calendar.addTask(task);
            	    }	
            	}
            }
            updateTampilanListTask();
        } catch (IOException e) {
            showAlert("Error", "Failed to load tasks from file.");
        }
    }

    private void loadFromFile(){
        loadTasksFromFile();
        loadEventsFromFile();
    }

    private void loadEventsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("events.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	if (line.startsWith("Event For:")) {
            		String[] eventInfo = line.split(": ");
            		if (eventInfo.length == 2) {
            			LocalDate eventDate = LocalDate.parse(eventInfo[1].trim());
            			
            		line = reader.readLine();
	                String title = line.split(": ")[1];
	                line = reader.readLine();
	                String startDateString = line.split(": ")[1];
	                line = reader.readLine();
	                String endDateString = line.split(": ")[1];
	                line = reader.readLine();
	                String priority = line.split(": ")[1];
	                line = reader.readLine();
	                String location = line.split(": ")[1];
	               
	
	                Date startDate = java.sql.Date.valueOf(startDateString);
	                Date endDate = java.sql.Date.valueOf(endDateString);
	
	                Event event = new Event(title, startDate, endDate, location, eventDate,priority);
	                calendar.addEvent(event);
            		}
            	}
            }
            updateTampilanListEvent();
        } catch (IOException e) {
            showAlert("Error", "Failed to load events from file.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}




