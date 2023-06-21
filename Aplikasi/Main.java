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
    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Sistem Informasi Penjadwalan dan Task Manajemen");
        Date currentDate = new Date();
        TimeZone timeZone = TimeZone.getDefault();
        calendar = new Calendar(currentDate, timeZone);
    }
}
