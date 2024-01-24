package com.example.theos;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// Der folgende Code wurde teilweise angepasst von [ChatGPT]
public class OptionButtons {
    static boolean instructionsOn = true;
    private static boolean musicOn = true;
    static boolean musicTogglePressed = false;
    private static StackPane musicButtonPane;
    private GameBoard gameBoard; // Add reference to GameBoard for createCloseInstructionsButton()
    public OptionButtons(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public static HBox createCloseAppButton() {
        ImageView closeAppIMG = new ImageView(new Image("images/option_button_extras/Button_Exit.PNG"));
        closeAppIMG.setFitWidth(50);
        closeAppIMG.setPreserveRatio(true);

        // Create invisible hitbox
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image/hitbox & creates HBox
        StackPane closeAppButtonPane = new StackPane(closeAppIMG, hitbox);
        HBox closeAppButtonBox = new HBox(closeAppButtonPane);

        // Opacity & translation effects
        closeAppButtonBox.setOnMousePressed(event -> {
            closeAppButtonPane.setOpacity(0.5);
            closeAppButtonPane.setTranslateY(closeAppButtonPane.getTranslateY() + 5);
        });

        // Close application
        closeAppButtonBox.setOnMouseReleased(event -> Platform.exit());

        return closeAppButtonBox;
    }

    public static HBox createReturnToTitleButton() {
        ImageView returnIMG = new ImageView(new Image("images/option_button_extras/Button_Main.PNG"));
        returnIMG.setFitWidth(50);
        returnIMG.setPreserveRatio(true);

        // Create invisible hitbox
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image/hitbox & creates HBox
        StackPane returnButtonPane = new StackPane(returnIMG, hitbox);
        HBox returnButtonBox = new HBox(returnButtonPane);

        // Opacity & translation effects
        returnButtonBox.setOnMousePressed(event -> {
            returnButtonPane.setOpacity(0.5);
            returnButtonPane.setTranslateY(returnButtonPane.getTranslateY() + 5);
            instructionsOn = true;
        });

        // Return to title screen
        returnButtonBox.setOnMouseReleased(event -> SceneController.showTitleScreen());

        return returnButtonBox;
    }

    public static HBox createInstructionsButton(GameBoard gameBoard) {
        ImageView instructionsIMG = new ImageView(new Image("images/option_button_extras/Button_Instructions.PNG"));
        instructionsIMG.setFitHeight(50);
        instructionsIMG.setPreserveRatio(true);

        // Create invisible hitbox
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image/hitbox & creates HBox
        StackPane instructionsButtonPane = new StackPane(instructionsIMG, hitbox);
        HBox instructionsButtonBox = new HBox(instructionsButtonPane);

        // Opacity & translation effects
        instructionsButtonBox.setOnMousePressed(event -> {
            instructionsButtonPane.setOpacity(0.5);
            instructionsButtonPane.setTranslateY(instructionsButtonPane.getTranslateY() + 5);
        });

        instructionsButtonBox.setOnMouseReleased(event -> {
            // Opacity & translation effects
            instructionsButtonPane.setOpacity(1.0);
            instructionsButtonPane.setTranslateY(instructionsButtonPane.getTranslateY() - 5);

            // Show / hide instructions window
            if (instructionsOn) {
                gameBoard.hideInstructions();
                instructionsOn = false;
            } else {
                gameBoard.showInstructions();
                instructionsOn = true;
            }
        });

        return instructionsButtonBox;
    }

    public HBox createCloseInstructionsButton() {
        ImageView closeInstructionsIMG = new ImageView(new Image("images/option_button_extras/Button_Exit.PNG"));
        closeInstructionsIMG.setFitWidth(35);
        closeInstructionsIMG.setPreserveRatio(true);

        // Create invisible hitbox
        Rectangle hitbox = new Rectangle(35, 35);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image/hitbox & creates HBox
        StackPane closeInstructionsButtonPane = new StackPane(closeInstructionsIMG, hitbox);
        HBox closeInstructionsButtonBox = new HBox(closeInstructionsButtonPane);

        // Opacity & translation effects
        closeInstructionsButtonBox.setOnMousePressed(event -> {
            closeInstructionsButtonPane.setOpacity(0.5);
            closeInstructionsButtonPane.setTranslateY(closeInstructionsButtonPane.getTranslateY() + 5);
        });

        // Reset opacity & translation + close instructions window
        closeInstructionsButtonBox.setOnMouseReleased(event -> {
            closeInstructionsButtonPane.setOpacity(1.0);
            closeInstructionsButtonPane.setTranslateY(closeInstructionsButtonPane.getTranslateY() - 5);
            gameBoard.hideInstructions();
            instructionsOn = false;
        });

        return closeInstructionsButtonBox;
    }

    public static HBox createMusicButton() {
        ImageView musicIMG = new ImageView(new Image("images/option_button_extras/Button_Sound.PNG"));
        musicIMG.setFitWidth(50);
        musicIMG.setPreserveRatio(true);

        // Create invisible hitbox
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image/hitbox & creates HBox
        musicButtonPane = new StackPane(musicIMG, hitbox);
        HBox musicButtonBox = new HBox(musicButtonPane);

        // Set right state when loaded in (music on/off)
        if (musicOn) {
            musicButtonPane.setOpacity(1.0);
        } else {
            musicButtonPane.setOpacity(0.5);
        }

        // Change positioning once pressed
        musicButtonBox.setOnMousePressed(event -> {
            toggleMusicStatePressed();
        });

        // Change state once released
        musicButtonBox.setOnMouseReleased(event -> {
            toggleMusicStateReleased();
        });

        return musicButtonBox;
    }

    // Helper method to toggle music state when pressed
    public static void toggleMusicStatePressed() {
        if (musicOn) {
            musicButtonPane.setOpacity(0.5);
            musicButtonPane.setTranslateY(musicButtonPane.getTranslateY() + 5);
        } else {
            musicButtonPane.setOpacity(0.25);
            musicButtonPane.setTranslateY(musicButtonPane.getTranslateY() + 5);
        }
        musicTogglePressed = true;
    }

    // Helper method to toggle music state when released
    public static void toggleMusicStateReleased() {
        if (musicOn) {
            musicButtonPane.setOpacity(0.5);
            musicButtonPane.setTranslateY(musicButtonPane.getTranslateY() - 5);
            musicOn = false;
            SoundGame.setVolume(0);
        } else {
            musicButtonPane.setOpacity(1.0);
            musicButtonPane.setTranslateY(musicButtonPane.getTranslateY() - 5);
            musicOn = true;
            SoundGame.setVolume(0.1);
        }
        musicTogglePressed = false;
    }
}