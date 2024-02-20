package com.example.theos;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.example.theos.SceneController.fullscreenOn;

// The following code has been partially adapted from ChatGPT
public class OptionButtons { // Creates option buttons, toggles states & manages button animation
    static boolean instructionsOn = true;
    private static boolean musicOn = true;
    static boolean musicTogglePressed = false;
    private static boolean animationCooldown = false;
    private static StackPane musicButtonPane;
    private final GameBoard gameBoard; // Add reference to GameBoard for createCloseInstructionsButton()
    public OptionButtons(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    // Create entire option buttons set
    public static AnchorPane createOptionButtonsSet(GameBoard gameBoard, boolean includeCloseAppButton, boolean includeScreenSizeButton, boolean includeReturnToTitleButton, boolean includeInstructionsButton, boolean includeMusicButton) {
        AnchorPane optionButtons = new AnchorPane();
        optionButtons.setTranslateX(20);
        optionButtons.setTranslateY(18);

        // Create buttons according to boolean
        HBox closeAppButton = includeCloseAppButton ? createCloseAppButton() : null;
        HBox screenSizeButton = includeScreenSizeButton ? createScreenSizeButton() : null;
        HBox returnToTitleButton = includeReturnToTitleButton ? createReturnToTitleButton() : null;
        HBox instructionsButton = includeInstructionsButton ? createInstructionsButton(gameBoard) : null;
        HBox musicButton = includeMusicButton ? createMusicButton() : null;

        // Add existing buttons to AnchorPane
        if (includeCloseAppButton) {
            optionButtons.getChildren().add(closeAppButton);
        }
        if (includeScreenSizeButton) {
            AnchorPane.setLeftAnchor(screenSizeButton, 60.0);
            optionButtons.getChildren().add(screenSizeButton);
        }
        if (includeReturnToTitleButton) {
            AnchorPane.setLeftAnchor(returnToTitleButton, 120.0);
            optionButtons.getChildren().add(returnToTitleButton);
        }
        if (includeInstructionsButton) {
            AnchorPane.setLeftAnchor(instructionsButton, 1273.0);
            optionButtons.getChildren().add(instructionsButton);
        }
        if (includeMusicButton) {
            AnchorPane.setLeftAnchor(musicButton, 1333.0);
            optionButtons.getChildren().add(musicButton);
        }

        return optionButtons;
    }

    private static HBox createCloseAppButton() {
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
            closeAppButtonPane.setTranslateY(closeAppButtonPane.getTranslateY() + 3);
        });

        // Close application
        closeAppButtonBox.setOnMouseReleased(event -> Platform.exit());

        return closeAppButtonBox;
    }

    private static HBox createScreenSizeButton() {
        ImageView screenSizeIMG = new ImageView(new Image("images/option_button_extras/Button_Instructions.PNG"));
        screenSizeIMG.setFitHeight(50);
        screenSizeIMG.setPreserveRatio(true);

        // Create invisible hitbox
        Rectangle hitbox = new Rectangle(50, 50);
        hitbox.setFill(Color.TRANSPARENT);

        // StackPane overlays image/hitbox & creates HBox
        StackPane screenSizeButtonPane = new StackPane(screenSizeIMG, hitbox);
        HBox screenSizeButtonBox = new HBox(screenSizeButtonPane);

        // Opacity & translation effects
        screenSizeButtonBox.setOnMousePressed(event -> {
            screenSizeButtonPane.setOpacity(0.5);
            screenSizeButtonPane.setTranslateY(screenSizeButtonPane.getTranslateY() + 3);
        });

        screenSizeButtonBox.setOnMouseReleased(event -> {
            // Opacity & translation effects
            screenSizeButtonPane.setOpacity(1.0);
            screenSizeButtonPane.setTranslateY(screenSizeButtonPane.getTranslateY() - 3);
            changeScreenSize();
        });

        return screenSizeButtonBox;
    }

    public static void changeScreenSize() {
        Stage stage = SceneController.stage;
        if (fullscreenOn) {
            stage.setFullScreen(false);
        } else {
            stage.setFullScreen(true);
        }
        fullscreenOn = !fullscreenOn; // Changes value
    }

    private static HBox createReturnToTitleButton() {
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
            returnButtonPane.setTranslateY(returnButtonPane.getTranslateY() + 3);
            instructionsOn = true;
        });

        // Return to title screen
        returnButtonBox.setOnMouseReleased(event -> SceneController.showTitleScreen());

        return returnButtonBox;
    }

    private static HBox createInstructionsButton(GameBoard gameBoard) {
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
            instructionsButtonPane.setTranslateY(instructionsButtonPane.getTranslateY() + 3);
        });

        instructionsButtonBox.setOnMouseReleased(event -> {
            // Opacity & translation effects
            instructionsButtonPane.setOpacity(1.0);
            instructionsButtonPane.setTranslateY(instructionsButtonPane.getTranslateY() - 3);

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
            closeInstructionsButtonPane.setTranslateY(closeInstructionsButtonPane.getTranslateY() + 3);
        });

        // Reset opacity & translation + close instructions window
        closeInstructionsButtonBox.setOnMouseReleased(event -> {
            closeInstructionsButtonPane.setOpacity(1.0);
            closeInstructionsButtonPane.setTranslateY(closeInstructionsButtonPane.getTranslateY() - 3);
            gameBoard.hideInstructions();
            instructionsOn = false;
        });

        return closeInstructionsButtonBox;
    }

    private static HBox createMusicButton() {
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

        // Change positioning once pressed / released
        musicButtonBox.setOnMousePressed(event -> toggleMusicStatePressed());
        musicButtonBox.setOnMouseReleased(event -> toggleMusicStateReleased());

        return musicButtonBox;
    }

    // Helper method to toggle music state when pressed
    public static void toggleMusicStatePressed() {
        if (musicOn) {
            musicButtonPane.setOpacity(0.5);
        } else {
            musicButtonPane.setOpacity(0.25);
        }
        musicButtonPane.setTranslateY(musicButtonPane.getTranslateY() + 3);
        musicTogglePressed = true;
    }

    // Helper method to toggle music state when released
    public static void toggleMusicStateReleased() {
        if (musicOn) {
            musicButtonPane.setOpacity(0.5);
            musicOn = false;
            SoundGame.setVolume(0);
        } else {
            musicButtonPane.setOpacity(1.0);
            musicOn = true;
            SoundGame.setVolume(0.1);
        }
        musicButtonPane.setTranslateY(musicButtonPane.getTranslateY() - 3);
        musicTogglePressed = false;
    }

    // Generic space button format
    public static void spaceButtonFormat(Text descriptionText, Text spaceText, ImageView spaceButton, Button button, Runnable releaseAction) {

        descriptionText.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        descriptionText.setFill(TheOs.BROWN);
        AnchorPane.setTopAnchor(descriptionText, -40.0);

        spaceText.setFont(Font.font(TheOs.VARELA.getFamily(), 32));
        spaceText.setFill(TheOs.BROWN);
        AnchorPane.setLeftAnchor(spaceText, 115.0);
        AnchorPane.setTopAnchor(spaceText, 18.0);

        spaceButton.setPreserveRatio(true);
        spaceButton.setFitWidth(348);

        button.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        button.setOpacity(0);
        button.setPrefSize(348, 77);

        // Event handlers for pressing & releasing
        EventHandler<Event> pressHandler = event -> {
            spaceText.setOpacity(0.5);
            spaceButton.setOpacity(0.5);
        };
        EventHandler<Event> releaseHandler = event -> {
            spaceText.setOpacity(1.0);
            spaceButton.setOpacity(1.0);
            releaseAction.run(); // Run specified action
        };

        // Call press & release handlers for mouse
        button.setOnMousePressed(pressHandler);
        button.setOnMouseReleased(releaseHandler);

        // Call press & release handlers for SPACE key
        button.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                pressHandler.handle(null);
            }
        });
        button.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                releaseHandler.handle(null);
            }
        });
    }

    // Up-and-down bouncing animation
    public static void animateButtonBounce(Node... buttons) {
        for (Node button : buttons) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(500), button);
            transition.setByY(-5); // Movement
            transition.setCycleCount(Animation.INDEFINITE); // Repetition
            transition.setAutoReverse(true); // Bouncing effect
            transition.play();
        }
    }

    // Left-and-right shake animation
    public static void animateTextShake(Node text) {
        if (!animationCooldown) {
            animationCooldown = true;

            TranslateTransition transition = new TranslateTransition(Duration.millis(100), text);
            transition.setByX(5); // Movement
            transition.setCycleCount(4); // Repetition
            transition.setAutoReverse(true); // Shake effect

            // Set up a pause after the animation finishes to escape an animation collide
            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
            pause.setOnFinished(event -> animationCooldown = false);

            // Play the animation and then the pause
            transition.setOnFinished(event -> pause.play());
            transition.play();
        }
    }
}