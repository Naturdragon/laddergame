package com.example.theos;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
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
import javafx.stage.Stage;
import javafx.util.Duration;


public class TitleScreen {

    static final Font VARELA = Font.loadFont(Application.class.getClassLoader().getResourceAsStream("fonts/VarelaRound-Regular.ttf"), -1);
    static final ImageView SPACE_BUTTON = new ImageView(new Image("images/option_button_extras/Button_Space_Big.PNG"));
    static final Text START_GAME = new Text("Start Game");
    static final Text SPACE_TEXT = new Text("SPACE");

    public static Scene createTitleScreen() {
        VBox instructionsBox = createTitleBox();

        HBox mainLayout = new HBox(instructionsBox);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setFillHeight(false);

        String backgroundImage = "url('images/title_screen/Title_BG.PNG')";
        mainLayout.setStyle("-fx-background-image: " + backgroundImage + "; " +
                "-fx-background-size: cover;");

        return new Scene(mainLayout, TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
    }

    private static VBox createTitleBox() {

        //Text shown on Screen above the SPACE Button
        START_GAME.setFont(Font.font(VARELA.getFamily(), 30));
        START_GAME.setFill(TheOs.BROWN);

        SPACE_TEXT.setFont(Font.font(VARELA.getFamily(), 32));
        SPACE_TEXT.setFill(TheOs.BROWN);

        SPACE_BUTTON.setPreserveRatio(true);
        SPACE_BUTTON.setFitWidth(348);

        //Button to start game
        Button startButton = new Button("Start game");

        // Event handler for pressing SPACE key
        startButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                System.out.println("Start game");
                // Implement logic to start game

                // Toggle opacity for spaceText and menuButton
                double currentOpacity = SPACE_TEXT.getOpacity();
                double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.2, but not below 0.2
                SPACE_TEXT.setOpacity(newOpacity);
                SPACE_BUTTON.setOpacity(newOpacity);
            }
        });

        // Event handler for releasing SPACE key
        startButton.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                // Reset opacity to normal when the button is released
                SPACE_TEXT.setOpacity(1.0);
                SPACE_BUTTON.setOpacity(1.0);
                SceneController.showPlayerSelectSceen();
            }
        });

        startButton.setFont(Font.font(VARELA.getFamily(), 30));
        startButton.setOpacity(0);

        SPACE_TEXT.setTranslateY(90);
        SPACE_BUTTON.setTranslateY(545);
        START_GAME.setTranslateY(328);
        animateButtons(SPACE_BUTTON, SPACE_TEXT, START_GAME);

        //Vbox
        VBox frontPage = new VBox(START_GAME, SPACE_TEXT, startButton, SPACE_BUTTON);
        frontPage.setAlignment(Pos.CENTER);
        frontPage.setSpacing(100);

        SPACE_TEXT.toFront();
        SPACE_BUTTON.toBack();

        // Creating the main layout
        HBox mainLayout = new HBox(frontPage);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(100);

        return frontPage;
    }

    public static void animateButtons(Node... buttons) {
        for (Node button : buttons) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(500), button);
            transition.setByY(-5);
            transition.setCycleCount(Animation.INDEFINITE);
            transition.setAutoReverse(true);
            transition.play();
        }
    }
}
