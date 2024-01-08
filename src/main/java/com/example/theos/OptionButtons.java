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

        // Use a StackPane to overlay the image and hitbox
        StackPane closeButtonPane = new StackPane(closeImage, hitbox);

        HBox closeButtonBox = new HBox(closeButtonPane);
        closeButtonBox.getStyleClass().add("app-button");
        closeButtonBox.setOnMouseEntered(event -> closeButtonBox.getStyleClass().add("hovered"));
        closeButtonBox.setOnMouseExited(event -> closeButtonBox.getStyleClass().remove("hovered"));

        // Set action on mouse press for both the image and the hitbox
        closeButtonBox.setOnMousePressed(event -> {
            // Opacity and translation effect on press
            double currentOpacity = closeButtonPane.getOpacity();
            double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.5, but not below 0.5
            closeButtonPane.setOpacity(newOpacity);
            closeButtonPane.setTranslateY(newOpacity > 0.5 ? closeButtonPane.getTranslateY() - 5 : closeButtonPane.getTranslateY() + 5);
        });

        // Set action on mouse release for both the image and the hitbox
        closeButtonBox.setOnMouseReleased(event -> {
            // Perform exit action
            Platform.exit();
        });

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

        // Set action on mouse press for both the image and the hitbox
        returnButtonBox.setOnMousePressed(event -> {
            // Opacity and translation effect on press
            double currentOpacity = returnButtonPane.getOpacity();
            double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.5, but not below 0.5
            returnButtonPane.setOpacity(newOpacity);
            returnButtonPane.setTranslateY(newOpacity > 0.5 ? returnButtonPane.getTranslateY() - 5 : returnButtonPane.getTranslateY() + 5);
        });

        // Set action on mouse release for both the image and the hitbox
        returnButtonBox.setOnMouseReleased(event -> {
            // Perform exit action
            Platform.exit();
        });

        return returnButtonBox;
    }
}
