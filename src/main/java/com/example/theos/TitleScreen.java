package com.example.theos;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// The following code has been partially adapted from ChatGPT
public class TitleScreen { // Displays title screen
    static final Text DESCRIPTION_TEXT = new Text("Start Game"); // Text shown on screen above SPACE button
    static final Text SPACE_TEXT = new Text("SPACE"); // SPACE Text on button
    static final ImageView SPACE_BUTTON = new ImageView(new Image("images/option_button_extras/Button_Space_Big.PNG")); // Button image
    static final Button START_BUTTON = new Button("Start game");

    public static Scene createTitleScreen() { // Creates scene for title screen

        // Create main layout
        AnchorPane startButton = createStartButton();
        AnchorPane optionButtons = createOptionButtons();
        Pane mainLayout = new Pane(startButton, optionButtons);

        // Set background image
        String backgroundImage = "url('images/title_screen/Title_BG.PNG')";
        mainLayout.setStyle("-fx-background-image: " + backgroundImage + "; " +
                "-fx-background-size: cover;");

        return new Scene(mainLayout, TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
    }

    private static AnchorPane createStartButton() { // Creates start button

        // Format of start button
        DESCRIPTION_TEXT.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        DESCRIPTION_TEXT.setFill(TheOs.BROWN);
        SPACE_TEXT.setFont(Font.font(TheOs.VARELA.getFamily(), 32));
        SPACE_TEXT.setFill(TheOs.BROWN);
        SPACE_BUTTON.setPreserveRatio(true);
        SPACE_BUTTON.setFitWidth(348);
        START_BUTTON.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        START_BUTTON.setOpacity(0);
        START_BUTTON.setPrefSize(348, 77);

        // Create AnchorPane for start button
        AnchorPane startButton = new AnchorPane(SPACE_BUTTON, DESCRIPTION_TEXT, SPACE_TEXT, START_BUTTON);
        startButton.setTranslateX(537);
        startButton.setTranslateY(690);

        // Position & animation of start button
        AnchorPane.setLeftAnchor(DESCRIPTION_TEXT, 90.0);
        AnchorPane.setTopAnchor(DESCRIPTION_TEXT, -40.0);
        AnchorPane.setLeftAnchor(SPACE_TEXT, 115.0);
        AnchorPane.setTopAnchor(SPACE_TEXT, 18.0);
        OptionButtons.animateButtonBounce(START_BUTTON, SPACE_TEXT, DESCRIPTION_TEXT, SPACE_BUTTON);

        // Event handlers for pressing & releasing
        EventHandler<Event> pressHandler = event -> {
            // Lower opacity
            SPACE_TEXT.setOpacity(0.5);
            SPACE_BUTTON.setOpacity(0.5);
        };
        EventHandler<Event> releaseHandler = event -> {
            // Reset opacity
            SPACE_TEXT.setOpacity(1.0);
            SPACE_BUTTON.setOpacity(1.0);

            // Switch to Player Selection Screen
            SceneController.showPlayerSelectScreen();
        };

        // Call press & release handlers for mouse
        START_BUTTON.setOnMousePressed(pressHandler);
        START_BUTTON.setOnMouseReleased(releaseHandler);

        // Call press & release handlers for SPACE key
        START_BUTTON.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                pressHandler.handle(null);
            }
        });
        START_BUTTON.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                releaseHandler.handle(null);
            }
        });

        return startButton;
    }

    // Create option buttons
    private static AnchorPane createOptionButtons() {
        HBox closeAppButton = OptionButtons.createCloseAppButton();
        HBox musicButton = OptionButtons.createMusicButton();
        AnchorPane buttonLayout = new AnchorPane(closeAppButton, musicButton);
        buttonLayout.setTranslateX(20);
        buttonLayout.setTranslateY(18);
        AnchorPane.setLeftAnchor(musicButton, 1333.0);

        return buttonLayout;
    }
}