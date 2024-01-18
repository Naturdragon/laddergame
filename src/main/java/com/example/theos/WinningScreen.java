package com.example.theos;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

import static com.example.theos.OptionButtons.createCloseAppButton;
import static com.example.theos.TitleScreen.animateButtons;
import static javafx.application.Application.launch;

public class WinningScreen {

    static final Font VARELA = Font.loadFont(Application.class.getClassLoader().getResourceAsStream("fonts/VarelaRound-Regular.ttf"), -1);

    /* Displays the winning screen with the leaderboard and options to return to the main menu.
     * primaryStage    The primary stage of the JavaFX application.
     * finishedPlayers List of players who have completed the game.
     */
    public static Scene createWinningScreen(List<Player> finishedPlayers) {
        // Extract the winner
        Player winner = finishedPlayers.get(0);

        // Initialize game
        Player[] playersArray = finishedPlayers.toArray(new Player[0]);
        List<Player> players = Arrays.asList(playersArray);

        // Creating the left side with the winning character image and return button
        ImageView winningCharacterImage = new ImageView(new Image(winner.getWinningImagePath()));
        winningCharacterImage.setFitWidth(262);
        winningCharacterImage.setFitHeight(185);

        //Text shown on Screen above the SPACE Button
        Text returnToMenu = new Text("Return to Main Menu");
        returnToMenu.setFont(Font.font(VARELA.getFamily(), 30));
        returnToMenu.setFill(TheOs.BROWN);

        Text spaceText = new Text("SPACE");
        spaceText.setFont(Font.font(VARELA.getFamily(), 32));
        spaceText.setFill(TheOs.BROWN);

        ImageView menuButton = new ImageView(new Image("images/option_button_extras/Button_Space_Big.PNG"));
        menuButton.setPreserveRatio(true);
        menuButton.setFitWidth(348);

        // Button to return to MAIN MENU (not finished)
        Button returnButton = new Button("Return to Main Menu");

        // Event handler for pressing SPACE key or pressing the mouse
        EventHandler<MouseEvent> pressHandler = event -> {
            // Toggle opacity for spaceText and menuButton
            double currentOpacity = spaceText.getOpacity();
            double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.5, but not below 0.5
            spaceText.setOpacity(newOpacity);
            menuButton.setOpacity(newOpacity);
        };

        // Event handler for releasing SPACE key or releasing the mouse
        EventHandler<MouseEvent> releaseHandler = event -> {
            // Reset opacity to normal
            spaceText.setOpacity(1.0);
            menuButton.setOpacity(1.0);

            SceneController.showTitleScreen();
        };

        // Set event handlers for both SPACE key and mouse
        returnButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                pressHandler.handle(null); // Call the press handler
            }
        });

        returnButton.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                releaseHandler.handle(null); // Call the release handler
            }
        });

        returnButton.setOnMousePressed(pressHandler); // Call the press handler when the mouse is pressed
        returnButton.setOnMouseReleased(releaseHandler); // Call the release handler when the mouse is released


        returnButton.setFont(Font.font(VARELA.getFamily(), 30));
        returnButton.setOpacity(0);

        // Create the return to main menu button using the winning screen scene
        HBox closeAppButton = OptionButtons.createCloseAppButton();

        // Adjust the position of closeAppButton
        closeAppButton.setAlignment(Pos.TOP_LEFT);
        closeAppButton.setPadding(new Insets(20, 0, 0, 20)); // Adjust the values accordingly
        closeAppButton.setTranslateX(51);
        closeAppButton.setTranslateY(-93);

        //Vbox (left side) with the added close button
        VBox leftSide = new VBox(closeAppButton, winningCharacterImage, returnButton, returnToMenu, spaceText, menuButton);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.setSpacing(100);

        // Adjust the position of winningCharacterImage
        winningCharacterImage.setTranslateX(120); // Translate pixels to the right
        winningCharacterImage.setTranslateY(-71); // Translate pixels down (for up use -)

        //Adjust the position of the Return to Main Menu Text
        returnToMenu.setTranslateX(122);
        returnToMenu.setTranslateY(105);

        // Adjust the position of returnButton
        returnButton.setTranslateX(118);
        returnButton.setTranslateY(-123);

        //Adjust the position of the SPACE Text
        spaceText.setTranslateX(117);
        spaceText.setTranslateY(26);

        //Adjust the position of the Button IMAGE
        menuButton.setTranslateX(120);
        menuButton.setTranslateY(777);

        spaceText.toFront(); // Bring spaceText to the front
        returnButton.toFront();
        menuButton.toBack(); // Send menuButton to the back

        animateButtons(returnButton, returnToMenu, spaceText, menuButton);

        // Creating the right side with the leaderboard
        GridPane leaderboardGrid = new GridPane();
        leaderboardGrid.setAlignment(Pos.CENTER);
        leaderboardGrid.setHgap(5);
        leaderboardGrid.setVgap(35);

        // Add header
        Text rankingTitle = new Text("RANKING");
        rankingTitle.setFont(Font.font(VARELA.getFamily(), 60)); // Increased font size
        rankingTitle.setFill(TheOs.BROWN);
        leaderboardGrid.add(rankingTitle, 0, 0, 4, 1); // Span 4 columns for the bigger header
        Text turnsTitle = new Text("    Turns");
        turnsTitle.setFont(Font.font(VARELA.getFamily(), 30));
        turnsTitle.setFill(TheOs.BROWN);
        leaderboardGrid.add(turnsTitle, 4, 0);

        // Add player data to the leaderboard
        for (int i = 0; i < players.size(); i++) {
            ImageView characterImageView = new ImageView(new Image(players.get(i).getImage()));
            characterImageView.setFitWidth(50);
            characterImageView.setFitHeight(50);

            Text rankingText = new Text(String.valueOf(i + 1)); // Ranking starts from 1
            rankingText.setFont(Font.font(VARELA.getFamily(), 36));
            rankingText.setFill(TheOs.BROWN);
            Text playerNameText = new Text(players.get(i).getName());
            playerNameText.setFont(Font.font(VARELA.getFamily(), 36));
            playerNameText.setFill(TheOs.BROWN);
            Text turnsText = new Text("     " + players.get(i).getTurnCount());
            turnsText.setFont(Font.font(VARELA.getFamily(), 36)); // Adjust the font size
            turnsText.setFill(TheOs.BROWN);

            leaderboardGrid.add(rankingText, 0, i + 1);
            leaderboardGrid.add(characterImageView, 1, i + 1);
            leaderboardGrid.add(playerNameText, 2, i + 1);
            leaderboardGrid.add(turnsText, 4, i + 1);
        }

        // Set padding for the right side
        leaderboardGrid.setPadding(new Insets(20, 0, 0, 0)); // Adjust the values accordingly

        // Set size for VBox (left side) and GridPane (right side)
        leftSide.setMinWidth(711); // Adjust the size accordingly
        leaderboardGrid.setMinWidth(711); // Adjust the size accordingly

        // Creating the main layout
        HBox mainLayout = new HBox(leftSide, leaderboardGrid);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(100);

        // Set background image for the whole scene using CSS
        String backgroundImage = "url('images/winning_screen/Winning_Screen.png')";
        mainLayout.setStyle("-fx-background-image: " + backgroundImage + "; " +
                "-fx-background-size: cover;");

        // Creating the scene and returning it to be set for the stage
        return new Scene(mainLayout, TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
    }
}