package com.example.theos;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OptionButtons {

    private GameBoard gameBoard;  // Add a reference to GameBoard for createCloseInstructionsButton()
    public OptionButtons(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
    private static boolean musicOn = true;

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

    public HBox createCloseInstructionsButton() {
        ImageView closeImage = new ImageView(new Image("images/option_button_extras/Button_Exit.PNG"));
        closeImage.setFitWidth(35);
        closeImage.setFitHeight(35);

        // Create an invisible hitbox (rectangle)
        Rectangle hitbox = new Rectangle(35, 35);
        hitbox.setFill(Color.TRANSPARENT);

        // Use a StackPane to overlay the image and hitbox
        StackPane closeButtonPane = new StackPane(closeImage, hitbox);

        HBox closeButtonBox = new HBox(closeButtonPane);
        closeButtonBox.getStyleClass().add("app-button");
        closeButtonBox.setOnMouseEntered(event -> closeButtonBox.getStyleClass().add("hovered"));
        closeButtonBox.setOnMouseExited(event -> closeButtonBox.getStyleClass().remove("hovered"));

        // Opacity and translation effect on press
        closeButtonBox.setOnMousePressed(event -> {
            double currentOpacity = closeButtonPane.getOpacity();
            double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.5, but not below 0.5
            closeButtonPane.setOpacity(newOpacity);
            closeButtonPane.setTranslateY(newOpacity > 0.5 ? closeButtonPane.getTranslateY() - 5 : closeButtonPane.getTranslateY() + 5);
        });

        // Reset opacity and y-coordinate & close instructions window
        closeButtonBox.setOnMouseReleased(event -> {
            closeButtonPane.setOpacity(1.0);
            closeButtonPane.setTranslateY(closeButtonPane.getTranslateY() - 5);
            gameBoard.hideInstructions();
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
        returnImage.setOnMouseReleased(event -> SceneController.showTitleScreen());
        hitbox.setOnMouseReleased(event -> SceneController.showTitleScreen());

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

        return returnButtonBox;
    }

    public static HBox createInstructionsButton(GameBoard gameBoard) {
        ImageView instructionsImage = new ImageView(new Image("images/option_button_extras/Button_Instructions.PNG"));
        instructionsImage.setFitHeight(50);
        instructionsImage.setPreserveRatio(true);

        // Create an invisible hitbox (rectangle)
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        instructionsImage.setOnMouseReleased(event -> gameBoard.showInstructions());
        hitbox.setOnMouseReleased(event -> gameBoard.showInstructions());

        // Use a StackPane to overlay the image and hitbox
        StackPane instructionsButtonPane = new StackPane(instructionsImage, hitbox);

        HBox instructionsButtonBox = new HBox(instructionsButtonPane);
        instructionsButtonBox.setOnMouseEntered(event -> instructionsButtonBox.getStyleClass().add("hovered"));
        instructionsButtonBox.setOnMouseExited(event -> instructionsButtonBox.getStyleClass().remove("hovered"));

        // Set action on mouse press for both the image and the hitbox
        instructionsButtonBox.setOnMousePressed(event -> {
            // Opacity and translation effect on press
            double newOpacity = 0.5;
            instructionsButtonPane.setOpacity(newOpacity);
            instructionsButtonPane.setTranslateY(newOpacity > 0.5 ? -5 : 5);
        });

        instructionsButtonBox.setOnMouseReleased(event -> {
            // Opacity and translation effect on release
            instructionsButtonPane.setOpacity(1.0);
            instructionsButtonPane.setTranslateY(0);
        });

        return instructionsButtonBox;
    }

    public static HBox createMusicButton() {
        System.out.println("Creating music button");  // Debugging output

        ImageView musicImage = new ImageView(new Image("images/option_button_extras/Button_Sound.PNG"));
        musicImage.setFitWidth(50);
        musicImage.setFitHeight(50);

        // Create an invisible hitbox (rectangle)
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // Use a StackPane to overlay the image and hitbox
        StackPane musicButtonPane = new StackPane(musicImage, hitbox);

        HBox musicButtonBox = new HBox(musicButtonPane);
        musicButtonBox.getStyleClass().add("app-button");
        musicButtonBox.setOnMouseEntered(event -> musicButtonBox.getStyleClass().add("hovered"));
        musicButtonBox.setOnMouseExited(event -> musicButtonBox.getStyleClass().remove("hovered"));

        // Make sure right state is being displayed when loaded in (music on/off)
        if (musicOn) {
            double newOpacity = 1.0;
            musicButtonPane.setOpacity(newOpacity);
            musicButtonPane.setTranslateY(0);
        }
        else {
            double currentOpacity = musicButtonPane.getOpacity();
            double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.5, but not below 0.5
            musicButtonPane.setOpacity(newOpacity);
            musicButtonPane.setTranslateY(newOpacity > 0.5 ? musicButtonPane.getTranslateY() - 5 : musicButtonPane.getTranslateY() + 5);
        }

        // Change state once clicked
        musicButtonBox.setOnMouseClicked(event -> {
            if (musicOn) {
                double currentOpacity = musicButtonPane.getOpacity();
                double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.5, but not below 0.5
                musicButtonPane.setOpacity(newOpacity);
                musicButtonPane.setTranslateY(newOpacity > 0.5 ? musicButtonPane.getTranslateY() - 5 : musicButtonPane.getTranslateY() + 5);
                musicOn = false;
            }
            else {
                double newOpacity = 1.0;
                musicButtonPane.setOpacity(newOpacity);
                musicButtonPane.setTranslateY(0);
                musicOn = true;
                }
        });

        return musicButtonBox;
    }
}
