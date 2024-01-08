package com.example.theos;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class OptionButtons {

    public static HBox createCloseAppButton() {
        ImageView closeImage = new ImageView(new Image("images/option_button_extras/Button_Exit.PNG"));
        closeImage.setFitWidth(50);
        closeImage.setFitHeight(50);

        // Create an invisible hitbox (rectangle)
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // Set action on click for both the image and the hitbox
        closeImage.setOnMouseClicked(event -> Platform.exit());
        hitbox.setOnMouseClicked(event -> Platform.exit());

        // Use a StackPane to overlay the image and hitbox
        StackPane closeButtonPane = new StackPane(closeImage, hitbox);

        HBox closeButtonBox = new HBox(closeButtonPane);
        closeButtonBox.getStyleClass().add("app-button");
        closeButtonBox.setOnMouseEntered(event -> closeButtonBox.getStyleClass().add("hovered"));
        closeButtonBox.setOnMouseExited(event -> closeButtonBox.getStyleClass().remove("hovered"));

        return closeButtonBox;
    }

    public static HBox createReturnToMainMenuButton() {
        ImageView returnImage = new ImageView(new Image("images/option_button_extras/Button_Main.PNG"));
        returnImage.setFitWidth(50);
        returnImage.setFitHeight(50);

        // Create an invisible hitbox (rectangle)
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // Set action on click for both the image and the hitbox
        returnImage.setOnMouseClicked(event -> SceneController.showTitleScreen());
        hitbox.setOnMouseClicked(event -> SceneController.showTitleScreen());

        // Use a StackPane to overlay the image and hitbox
        StackPane returnButtonPane = new StackPane(returnImage, hitbox);

        HBox returnButtonBox = new HBox(returnButtonPane);
        returnButtonBox.getStyleClass().add("app-button");
        returnButtonBox.setOnMouseEntered(event -> returnButtonBox.getStyleClass().add("hovered"));
        returnButtonBox.setOnMouseExited(event -> returnButtonBox.getStyleClass().remove("hovered"));

        return returnButtonBox;
    }
}
