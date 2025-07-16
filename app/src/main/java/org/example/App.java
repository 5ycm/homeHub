package org.example;

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
        ImageButton button1 = new ImageButton("/image.png");
        button1.setOnAction(event -> {
            button1.isOn = !button1.isOn;
            if(button1.isOn == true){
                button1.setStyle("-fx-background-color: #00d100 ;"); // green for true / isOn
            }else{
                button1.setStyle("-fx-background-color: red;");
            }
        });
        
        // VBox aligned vertically, spacing between buttons
        VBox vbox = new VBox(20); // 20px spacing
        vbox.setAlignment(Pos.CENTER);   // Vertical center alignment
        vbox.getChildren().addAll(button1);
        vbox.setPrefWidth(150);
        vbox.setStyle("-fx-background-color: #fdffcf; -fx-border-width: 0 0 0 2; -fx-border-color: black;");



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
