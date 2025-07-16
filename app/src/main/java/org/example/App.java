package org.example;

import java.net.HttpURLConnection;
import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    public String getGreeting(){
        return "Hello!";
    }
    @Override
    public void start(Stage primaryStage) {
        // Create your custom button(s)
        ImageButton coffeeButton = new ImageButton("/image.png");
        coffeeButton.setOnAction(event -> {
            coffeeButton.isOn = !coffeeButton.isOn;
            if(coffeeButton.isOn == true){
                coffeeButton.setStyle("-fx-background-color: #00d100 ;"); // green for true / isOn
            }else{
                coffeeButton.setStyle("-fx-background-color: red;");
            }
        });
        
        // VBox aligned vertically w/ spacing between buttons
        VBox vbox = new VBox(20); // 20px spacing
        vbox.setAlignment(Pos.CENTER);   // Vertically center alignment
        vbox.getChildren().addAll(coffeeButton);
        vbox.setPrefWidth(150);
        vbox.setStyle("-fx-background-color: #fdffcf; -fx-border-width: 0 0 0 2; -fx-border-color: black;");

        try{
            String lat = "";
            String lon = "";
            String email = "";
            URL url = new URL("https://api.weather.gov/points/" + lat + "," + lon);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Required by NWS API
            con.setRequestProperty("User-Agent", "MyJavaApp/1.0 " + email);

            int status = con.getResponseCode(); //GET calls the API
            if(status == 200){//checks if GET was successful (hence code 200)
                
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        // Put VBox on the right side of the screen using a BorderPane
        BorderPane root = new BorderPane();
        root.setRight(vbox); // VBox appears on the right

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Home SmartHub");
        primaryStage.getIcons().add(new Image(getClass().getResource("/icon.png").toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
