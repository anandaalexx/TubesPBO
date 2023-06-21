package Aplikasi;

import java.util.TimeZone;

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
    }
}
