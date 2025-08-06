package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private ScheduledExecutorService ses;
    private WeatherFetcher wF;
    private information info;
    private WeatherData weather;
    
    public void start(Stage primaryStage) {
        info = new information();
        wF = new WeatherFetcher(info.lati, info.lon, info.email);
        weather = updateWeather(); // updates weather var
        ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(() -> {
            updateWeather();
        }, 0, info.interval, TimeUnit.MINUTES);

        ImageButton coffeeButton = new ImageButton("/image.png");
        coffeeButton.setOnAction(event -> {
            coffeeButton.isOn = !coffeeButton.isOn;
            if(coffeeButton.isOn) {
                coffeeButton.setStyle("-fx-background-color: #7ce604 ;"); 
            } else {
                coffeeButton.setStyle("-fx-background-color: #db380e;");
            }
        });

        ImageButton testt = new ImageButton("/icon.png");
        VBox Tvbox = new VBox(10); 
        Tvbox.setAlignment(Pos.CENTER);
        //Tvbox.getChildren().add(testt); added below

        VBox Bvbox = new VBox(10); 
        Bvbox.setAlignment(Pos.CENTER);
        Bvbox.getChildren().add(coffeeButton);

        VBox separatorBox = new VBox();
        separatorBox.setPrefWidth(150);
        separatorBox.setStyle("-fx-background-color: #fdffcf; -fx-border-width: 0 0 0 2; -fx-border-color: black;");

        Tvbox.prefHeightProperty().bind(separatorBox.heightProperty().multiply(0.6));
        Bvbox.prefHeightProperty().bind(separatorBox.heightProperty().multiply(0.4));


        separatorBox.getChildren().addAll(Tvbox, Bvbox);
        Label tempLabel;
        if(weather!=null){
            tempLabel = new Label(weather.temperatureF + " F");
        }
        else{
            tempLabel = new Label("Loading...");
        }

        Tvbox.getChildren().addAll(tempLabel, testt);


        BorderPane root = new BorderPane();
        root.setRight(separatorBox);

        
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Home SmartHub");
        primaryStage.getIcons().add(new Image(getClass().getResource("/icon.png").toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private WeatherData updateWeather() {
        try {
            WeatherData temp = wF.fetchWeather();

            Platform.runLater(() -> {
                System.out.println("Temperature: " + temp.temperatureF + " °F");
                // update the UI elements here with new weather data
            });
            return temp;
        } catch (Exception e) {
            System.out.println("Failed to fetch weather, NWS has not updated temp yet.");
            return weather;
        }
    }

    public void stop() throws Exception {
        if (ses != null && !ses.isShutdown()) {
            ses.shutdown();
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
