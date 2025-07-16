package org.example;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends Button {
    boolean isOn = false;
    public ImageButton(String imagePath) {
        if (imagePath == null) {
            throw new IllegalArgumentException("imagePath was null in ImageButton Constructor");
        }

        Image icon = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(75);
        imageView.setFitHeight(75);
        imageView.setPreserveRatio(true);

        this.setGraphic(imageView);
        this.setPrefSize(100, 100);
        this.getStyleClass().add("roundButton");
    }
}
