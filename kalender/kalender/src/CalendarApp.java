import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.TimeZone;

public class CalendarApp extends Application {

    private YearMonth currentYearMonth;
    private ListView<String> taskListView;
    private ListView<String> eventListView;
    private Calendar calendar;
    private LocalDate selectedDate;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        currentYearMonth = YearMonth.now();

        primaryStage.setTitle("Sistem Informasi Penjadwalan dan Task Manajemen");
        Date currentDate = new Date();
        TimeZone timeZone = TimeZone.getDefault();
        calendar = new Calendar(currentDate, timeZone);

        taskListView = new ListView<>();
        taskListView.setPrefSize(400, 100);

        eventListView = new ListView<>();
        eventListView.setPrefSize(400, 100);
       
        
        Label title = new Label();
        GridPane.setColumnSpan(title, 7);
        GridPane.setFillWidth(title, true);
        title.setAlignment(Pos.CENTER);
        title.getStyleClass().add("title-label"); // Tambahkan kelas CSS
        
        Button removeTaskButton = new Button("Remove Task");
        removeTaskButton.setOnAction(e -> {
            int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                calendar.removeTask(calendar.getTasks().get(selectedIndex));
                updateTaskListView();
            }
        });
        
        
        Button completeTaskButton = new Button("Complete Task");
        completeTaskButton.setOnAction(e -> {
            int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                Task selectedTask = calendar.getTasks().get(selectedIndex);
                selectedTask.setEndTask(true);
                updateTaskListView();
            }
        });
        
        Button removeEventButton = new Button("Remove Event");
        removeEventButton.setOnAction(e -> {
            int selectedIndex = eventListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                calendar.removeEvent(calendar.getEvents().get(selectedIndex));
                updateEventListView();
            }
        });
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveToFile());
       
        BorderPane borderPane = new BorderPane();
        HBox buttonBar = new HBox(10, removeTaskButton,completeTaskButton,removeEventButton,saveButton);
        VBox vBox = new VBox(10, taskListView, eventListView, buttonBar);
        borderPane.setCenter(vBox);
        borderPane.setPadding(new Insets(10));
        BorderPane.setAlignment(vBox, Pos.CENTER);

        GridPane calendarGridPane = new GridPane();
        calendarGridPane.setPrefSize(800, 400);
        calendarGridPane.setAlignment(Pos.TOP_LEFT);
        calendarGridPane.getStyleClass().add("calendar-grid"); // Tambahkan kelas CSS
        
        Button previousButton = new Button("<<");
        previousButton.getStyleClass().add("navigation-button"); // Tambahkan kelas CSS
        previousButton.setOnAction(event -> {
            currentYearMonth = currentYearMonth.minusMonths(1);
            updateCalendar(calendarGridPane);
        });

        Button nextButton = new Button(">>");
        nextButton.getStyleClass().add("navigation-button"); // Tambahkan kelas CSS
        nextButton.setOnAction(event -> {
            currentYearMonth = currentYearMonth.plusMonths(1);
            updateCalendar(calendarGridPane);
        });

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
        
	     // Di dalam method start()
	
	     // Menambahkan event handler untuk click pada item di ListView taskListView
	     taskListView.setOnMouseClicked(event -> {
	         int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
	         if (selectedIndex != -1) {
	             Task selectedTask = calendar.getTasks().get(selectedIndex);
	             showTaskDetails(selectedTask);
	         }
	     });
	
	     // Menambahkan event handler untuk click pada item di ListView eventListView
	     eventListView.setOnMouseClicked(event -> {
	         int selectedIndex = eventListView.getSelectionModel().getSelectedIndex();
	         if (selectedIndex != -1) {
	             Event selectedEvent = calendar.getEvents().get(selectedIndex);
	             showEventDetails(selectedEvent);
	         }
	     });
     
        Scene scene = new Scene(new VBox(calendarGridPane, borderPane));
        scene.getStylesheets().add(getClass().getResource("calendar.css").toExternalForm()); // Tambahkan file CSS eksternal
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void showTaskDetails(Task task) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Details");
        alert.setHeaderText(null);
        alert.setContentText("Task for: " + task.getTaskDate() + "\n"+
        		"Title: " + task.getTitle() + "\n" +
                "Due Date: " + task.getDueDate() + "\n" +
                "Status: " + (task.isStatusComplete() ? "Completed" : "Incomplete"));
        alert.showAndWait();
    }

    private void showEventDetails(Event event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event Details");
        alert.setHeaderText(null);
        alert.setContentText("Event for: " + event.getEventDate() + "\n"+
        		"Title: " + event.getTitle() + "\n" +
                "Start Date: " + event.getStartDate() + "\n" +
                "End Date: " + event.getEndDate() + "\n" +
                "Location: " + event.getLocation());
        alert.showAndWait();
    }

    private void updateCalendar(GridPane calendarGridPane) {
        calendarGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) > 1);

        LocalDate firstDayOfMonth = currentYearMonth.atDay(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        Label monthLabel = (Label) calendarGridPane.getChildren().get(1);
        monthLabel.setText("   " + currentYearMonth.getMonth().toString() + " " + currentYearMonth.getYear());

        int row = 2;
        int column = dayOfWeek % 7;

        for (int day = 1; day <= currentYearMonth.lengthOfMonth(); day++) {
            Button dayButton = new Button(String.valueOf(day));
            dayButton.setPrefWidth(200);
            dayButton.setPrefHeight(100);
            dayButton.getStyleClass().add("day-button"); // Tambahkan kelas CSS

            VBox dateBox = new VBox();
            dateBox.setAlignment(Pos.CENTER);
            dateBox.getChildren().add(dayButton);
            

            LocalDate currentDate = LocalDate.of(currentYearMonth.getYear(), currentYearMonth.getMonth(), day);

            // Tambahkan taskListView hanya jika ada task pada tanggal ini
            for (Task task : calendar.getTasks()) {
                LocalDate taskDate = task.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (taskDate.equals(currentDate)) {
                    taskListView.getItems().add(task.getTitle());
                    dateBox.getChildren().add(new ListView<>(taskListView.getItems()));
                    break;
                }
            }

            // Tambahkan eventListView hanya jika ada event pada tanggal ini
            for (Event event : calendar.getEvents()) {
                LocalDate eventStartDate = event.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (eventStartDate.equals(currentDate)) {
                    eventListView.getItems().add(event.getTitle());
                    dateBox.getChildren().add(new ListView<>(eventListView.getItems()));
                    break;
                }
            }
            

            calendarGridPane.add(dateBox, column, row);
            column++;
            if (column == 7) {
                column = 0;
                row++;
            }

            dayButton.setOnAction(e -> {
                selectedDate = currentDate;
                showOptionDialog();
            });
        }
    }



    private void showOptionDialog() {
        Stage dialogStage2 = new Stage();
        dialogStage2.setTitle("Add Event/Task");

        Label pilLabel = new Label("			Tambah Event/Task");
        Button addTask = new Button("Add Task");
        Button addEvent = new Button("Add Event");

        addTask.setOnAction(e -> showAddTaskDialog());
        addEvent.setOnAction(e -> showAddEventDialog());

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

    private void showAddTaskDialog() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Task");

        // Initialize GUI components
        Label titleLabel = new Label("Task For: "+ selectedDate +"\n"+
        "Title:");
        TextField titleTextField = new TextField();
        Label dueDateLabel = new Label("Due Date:");
        DatePicker dueDatePicker = new DatePicker();
        Button addButton = new Button("Add");
        
        addButton.setOnAction(e -> {
            String title = titleTextField.getText();
            LocalDate taskDate = selectedDate;
            if (!title.isEmpty() && dueDatePicker.getValue() != null) {
                Date dueDate = java.sql.Date.valueOf(dueDatePicker.getValue());
                Task newTask = new Task(title, dueDate,taskDate);
                calendar.addTask(newTask);
                updateTaskListView();
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
        gridPane.addRow(1, dueDateLabel, dueDatePicker);
        gridPane.add(addButton, 1, 2);
        gridPane.setPadding(new Insets(10));

        // Set up the scene
        Scene scene = new Scene(gridPane);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    

    private void updateTaskListView() {
        taskListView.getItems().clear();
        for (Task task : calendar.getTasks()) {
            String taskString = "Task: " + task.getTitle() + "\n";
            taskListView.getItems().add(taskString);

        }
        
    }
	private void showAddEventDialog() {
        Stage dialogStage1 = new Stage();
        dialogStage1.setTitle("Add Event");

        Label eventLabel = new Label("Event For: "+selectedDate+"\n"+
        "Title:");
        TextField eventTextField = new TextField();
        Label startDateLabel = new Label("Start Date:");
        DatePicker startDatePicker = new DatePicker();
        Label endDateLabel = new Label("End Date:");
        DatePicker endDatePicker = new DatePicker();
        Button addButton = new Button("Add");
        Label locLabel = new Label("Location:");
        TextField locTextField = new TextField();

        // Set the action for Add button
        addButton.setOnAction(e -> {
            String title = eventTextField.getText();
            String location = locTextField.getText();
            LocalDate eventDate = selectedDate;
            if (!title.isEmpty() && startDatePicker.getValue() != null && endDatePicker.getValue() != null && !location.isEmpty()) {
                Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
                Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());
                
                Event newEvent = new Event(title, startDate, endDate, location, eventDate);
                calendar.addEvent(newEvent);
                updateEventListView();
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
        gridPane.addRow(3, locLabel, locTextField);
        gridPane.add(addButton, 1, 5);
        gridPane.setPadding(new Insets(10));

        // Set up the scene
        Scene scene = new Scene(gridPane);
        dialogStage1.setScene(scene);
        dialogStage1.show();
    }
	
    private void updateEventListView() {
        eventListView.getItems().clear();
        for (Event event : calendar.getEvents()) {
            String eventString = "Event: " + event.getTitle() + "\n";
            eventListView.getItems().add(eventString);
        }
    }
    
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : calendar.getTasks()) {
                String taskString = "Task For: " + selectedDate + "\n"+
                		"Title: " + task.getTitle() + "\n" +
                        "Due Date: " + task.getDueDate() + "\n" +
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
                String eventString = "Event For: " +selectedDate+ "\n" +
                		"Title: " + event.getTitle() + "\n" +
                        "Start Date: " + event.getStartDate() + "\n" +
                        "End Date: " + event.getEndDate() + "\n" +
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
    
    private void loadFromFile() {
        loadTasksFromFile();
        loadEventsFromFile();
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
	                String statusString = line.split(": ")[1];
	
	                Date dueDate = java.sql.Date.valueOf(dueDateString);
	                boolean statusComplete = statusString.equals("Completed");
	
	                Task task = new Task(title, dueDate,taskDate);
	                task.setEndTask(statusComplete);
	                calendar.addTask(task);
            	    }
            	}
            }
            updateTaskListView();
        } catch (IOException e) {
            showAlert("Error", "Failed to load tasks from file.");
        }
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
	                String location = line.split(": ")[1];
	
	                Date startDate = java.sql.Date.valueOf(startDateString);
	                Date endDate = java.sql.Date.valueOf(endDateString);
	
	                Event event = new Event(title, startDate, endDate, location, eventDate);
	                calendar.addEvent(event);
            		}
            	}
            }
            updateEventListView();
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