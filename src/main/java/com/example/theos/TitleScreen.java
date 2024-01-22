package com.example.theos;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

// Der folgende Code wurde teilweise angepasst von [ChatGPT]

/*
The TitleScreen class displays the TitleScreen.
 */
public class TitleScreen {
    static final Font VARELA = Font.loadFont(Application.class.getClassLoader().getResourceAsStream("fonts/VarelaRound-Regular.ttf"), -1);
    static final ImageView SPACE_BUTTON = new ImageView(new Image("images/option_button_extras/Button_Space_Big.PNG"));
    static final Text START_GAME = new Text("Start Game");
    static final Text SPACE_TEXT = new Text("SPACE");

    public static Scene createTitleScreen() {

        // Creating main layout
        VBox instructionsBox = createTitleBox();
        HBox mainLayout = new HBox(instructionsBox);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setFillHeight(false);

        // Setting background image
        String backgroundImage = "url('images/title_screen/Title_BG.PNG')";
        mainLayout.setStyle("-fx-background-image: " + backgroundImage + "; " +
                "-fx-background-size: cover;");

        // Create return-to-main-menu button
        HBox closeAppButton = OptionButtons.createCloseAppButton();
        closeAppButton.setTranslateX(-815);
        closeAppButton.setTranslateY(-358);

        // Create music button
        HBox musicButton = OptionButtons.createMusicButton();
        musicButton.setTranslateX(465);
        musicButton.setTranslateY(-360);

        // Add buttons to main layout
        mainLayout.getChildren().add(closeAppButton);
        mainLayout.getChildren().add(musicButton);
        return new Scene(mainLayout, TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
    }

    private static VBox createTitleBox() {
        // Text shown on screen above SPACE button
        START_GAME.setFont(Font.font(VARELA.getFamily(), 30));
        START_GAME.setFill(TheOs.BROWN);

        // SPACE Text on button
        SPACE_TEXT.setFont(Font.font(VARELA.getFamily(), 32));
        SPACE_TEXT.setFill(TheOs.BROWN);

        // Button image
        SPACE_BUTTON.setPreserveRatio(true);
        SPACE_BUTTON.setFitWidth(348);

        // Button to start game
        Button startButton = new Button("Start game");

        // Event handler for pressing SPACE key / using mouse
        EventHandler<Event> pressHandler = event -> {
            // Toggle opacity for SPACE_TEXT & SPACE_BUTTON
            double currentOpacity = SPACE_TEXT.getOpacity();
            double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.5, but not below 0.5
            SPACE_TEXT.setOpacity(newOpacity);
            SPACE_BUTTON.setOpacity(newOpacity);
        };

        // Event handler for releasing SPACE key / mouse
        EventHandler<Event> releaseHandler = event -> {
            // Reset opacity for SPACE_TEXT & SPACE_BUTTON to normal
            SPACE_TEXT.setOpacity(1.0);
            SPACE_BUTTON.setOpacity(1.0);
            // switching to PlayerSelectionScreen
            SceneController.showPlayerSelectScreen();
        };

        // Set event handlers for both SPACE key & mouse if pressed
        startButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                pressHandler.handle(null); // Call the press handler
            }
        });

        // Set event handlers for both SPACE key & mouse if released
        startButton.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                releaseHandler.handle(null); // Call the release handler
            }
        });

        startButton.setOnMousePressed(pressHandler); // Call the press handler when the mouse is pressed
        startButton.setOnMouseReleased(releaseHandler); // Call the release handler when the mouse is released

        // Format & size
        startButton.setFont(Font.font(VARELA.getFamily(), 30));
        startButton.setOpacity(0);
        startButton.setPrefWidth(346);
        startButton.setPrefSize(346, 70);
        startButton.setTranslateY(95);

        // Positioning & animation
        SPACE_TEXT.setTranslateY(250);
        SPACE_BUTTON.setTranslateY(545);
        START_GAME.setTranslateY(328);
        animateButtons(startButton, SPACE_BUTTON, SPACE_TEXT, START_GAME);

        // VBOX for Layout
        VBox frontPage = new VBox(startButton,START_GAME, SPACE_TEXT, SPACE_BUTTON);
        frontPage.setAlignment(Pos.CENTER);
        frontPage.setSpacing(100);

        // Changing position for z-coordinate
        SPACE_TEXT.toFront();
        startButton.toFront();
        SPACE_BUTTON.toBack();

        // Creating main layout
        HBox mainLayout = new HBox(frontPage);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(100);

        return frontPage;
    }

    /*
    Animates the specified JavaFX buttons with a bouncing effect.
     */
    public static void animateButtons(Node... buttons) {
        for (Node button : buttons) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(500), button);
            transition.setByY(-5); // Movement
            transition.setCycleCount(Animation.INDEFINITE); // Repetition
            transition.setAutoReverse(true); // Bouncing effect
            transition.play();
        }
    }
}