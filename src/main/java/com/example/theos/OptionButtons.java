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
    static boolean instructionsOn = true;
    static boolean musicOn = true;

    public static HBox createCloseAppButton() {
        ImageView closeAppIMG = new ImageView(new Image("images/option_button_extras/Button_Exit.PNG"));
        closeAppIMG.setFitWidth(50);
        closeAppIMG.setPreserveRatio(true);

        // Create an invisible hitbox (rectangle)
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image / hitbox & creates HBox
        StackPane closeAppButtonPane = new StackPane(closeAppIMG, hitbox);
        HBox closeAppButtonBox = new HBox(closeAppButtonPane);

        // Opacity & translation effects
        closeAppButtonBox.setOnMousePressed(event -> {
            double currentOpacity = closeAppButtonPane.getOpacity();
            double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.5, but not below 0.5
            closeAppButtonPane.setOpacity(newOpacity);
            closeAppButtonPane.setTranslateY(newOpacity > 0.5 ? closeAppButtonPane.getTranslateY() - 5 : closeAppButtonPane.getTranslateY() + 5);
        });

        // Close application
        closeAppButtonBox.setOnMouseReleased(event -> Platform.exit());

        return closeAppButtonBox;
    }

    public HBox createCloseInstructionsButton() {
        ImageView closeInstructionsIMG = new ImageView(new Image("images/option_button_extras/Button_Exit.PNG"));
        closeInstructionsIMG.setFitWidth(35);
        closeInstructionsIMG.setPreserveRatio(true);

        // Create an invisible hitbox (rectangle)
        Rectangle hitbox = new Rectangle(35, 35);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image / hitbox & creates HBox
        StackPane closeInstructionsButtonPane = new StackPane(closeInstructionsIMG, hitbox);
        HBox closeInstructionsButtonBox = new HBox(closeInstructionsButtonPane);

        // Opacity & translation effects
        closeInstructionsButtonBox.setOnMousePressed(event -> {
            double currentOpacity = closeInstructionsButtonPane.getOpacity();
            double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.5, but not below 0.5
            closeInstructionsButtonPane.setOpacity(newOpacity);
            closeInstructionsButtonPane.setTranslateY(newOpacity > 0.5 ? closeInstructionsButtonPane.getTranslateY() - 5 : closeInstructionsButtonPane.getTranslateY() + 5);
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

    public static HBox createReturnToMainMenuButton() {
        ImageView returnIMG = new ImageView(new Image("images/option_button_extras/Button_Main.PNG"));
        returnIMG.setFitWidth(50);
        returnIMG.setPreserveRatio(true);

        // Create an invisible hitbox (rectangle)
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image / hitbox & creates HBox
        StackPane returnButtonPane = new StackPane(returnIMG, hitbox);
        HBox returnButtonBox = new HBox(returnButtonPane);

        // Opacity & translation effects
        returnButtonBox.setOnMousePressed(event -> {
            double currentOpacity = returnButtonPane.getOpacity();
            double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.5, but not below 0.5
            returnButtonPane.setOpacity(newOpacity);
            returnButtonPane.setTranslateY(newOpacity > 0.5 ? returnButtonPane.getTranslateY() - 5 : returnButtonPane.getTranslateY() + 5);
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

        // Create an invisible hitbox (rectangle)
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image / hitbox & creates HBox
        StackPane instructionsButtonPane = new StackPane(instructionsIMG, hitbox);
        HBox instructionsButtonBox = new HBox(instructionsButtonPane);

        // Opacity & translation effects
        instructionsButtonBox.setOnMousePressed(event -> {
            double newOpacity = 0.5;
            instructionsButtonPane.setOpacity(newOpacity);
            instructionsButtonPane.setTranslateY(newOpacity > 0.5 ? -5 : 5);
        });

        instructionsButtonBox.setOnMouseReleased(event -> {
            // Opacity & translation effects
            instructionsButtonPane.setOpacity(1.0);
            instructionsButtonPane.setTranslateY(0);

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

    public static HBox createMusicButton() {
        ImageView musicIMG = new ImageView(new Image("images/option_button_extras/Button_Sound.PNG"));
        musicIMG.setFitWidth(50);
        musicIMG.setPreserveRatio(true);

        // Create an invisible hitbox (rectangle)
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image / hitbox & creates HBox
        StackPane musicButtonPane = new StackPane(musicIMG, hitbox);
        HBox musicButtonBox = new HBox(musicButtonPane);

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
