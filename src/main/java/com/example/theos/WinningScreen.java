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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

// The following code has been partially adapted from ChatGPT
public class WinningScreen { // Displays winning screen with leaderboard & options to return to title screen
    static final Text DESCRIPTION_TEXT = new Text("Return to Main Menu"); // Text shown on screen above SPACE button
    static final Text SPACE_TEXT = new Text("SPACE"); // SPACE Text on button
    static final ImageView SPACE_BUTTON = new ImageView(new Image("images/option_button_extras/Button_Space_Big.PNG")); // Button image
    static final Button RETURN_BUTTON = new Button("Return to Main Menu"); // Button to return to main menu

    public static Scene createWinningScreen(List<Player> finishedPlayers) {

        // Extract winner & initialize game
        Player winner = finishedPlayers.get(0);
        Player[] playersArray = finishedPlayers.toArray(new Player[0]);
        List<Player> players = Arrays.asList(playersArray);

        // Create main layout
        AnchorPane leftSide = createLeftSide(winner);
        GridPane leaderboardGrid = createLeaderboard(players);
        AnchorPane optionButtons = createOptionButtons();
        Pane mainLayout = new Pane(leftSide, leaderboardGrid, optionButtons);

        // Set background image on newly created main layout
        String backgroundImage = "url('images/winning_screen/Winning_Screen.png')";
        mainLayout.setStyle("-fx-background-image: " + backgroundImage + "; " +
                "-fx-background-size: cover;");

        // Create scene & return it to be set for the stage
        return new Scene(mainLayout, TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
    }

    // Create left side with winning character image & return button
    private static AnchorPane createLeftSide(Player winner) {
        ImageView winningCharacterImage = new ImageView(new Image(winner.getWinningImagePath()));
        winningCharacterImage.setPreserveRatio(true);
        winningCharacterImage.setFitWidth(262);

        // Format of return button
        DESCRIPTION_TEXT.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        DESCRIPTION_TEXT.setFill(TheOs.BROWN);
        SPACE_TEXT.setFont(Font.font(TheOs.VARELA.getFamily(), 32));
        SPACE_TEXT.setFill(TheOs.BROWN);
        SPACE_BUTTON.setPreserveRatio(true);
        SPACE_BUTTON.setFitWidth(348);
        RETURN_BUTTON.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        RETURN_BUTTON.setOpacity(0);
        RETURN_BUTTON.setPrefSize(348, 77);

        // Create left side & add to main layout
        AnchorPane leftSide = new AnchorPane(winningCharacterImage, SPACE_BUTTON, DESCRIPTION_TEXT, SPACE_TEXT, RETURN_BUTTON);
        leftSide.setTranslateX(250);
        leftSide.setTranslateY(690);

        // Position & animation of return button / winning player
        AnchorPane.setLeftAnchor(winningCharacterImage, 46.0);
        AnchorPane.setTopAnchor(winningCharacterImage, -499.0);
        AnchorPane.setLeftAnchor(DESCRIPTION_TEXT, 25.0);
        AnchorPane.setTopAnchor(DESCRIPTION_TEXT, -40.0);
        AnchorPane.setLeftAnchor(SPACE_TEXT, 115.0);
        AnchorPane.setTopAnchor(SPACE_TEXT, 18.0);
        OptionButtons.animateButtonBounce(RETURN_BUTTON, SPACE_TEXT, DESCRIPTION_TEXT, SPACE_BUTTON);

        // Event handlers for pressing / releasing
        EventHandler<MouseEvent> pressHandler = event -> {
            // Lower opacity
            SPACE_TEXT.setOpacity(0.5);
            SPACE_BUTTON.setOpacity(0.5);
        };
        EventHandler<MouseEvent> releaseHandler = event -> {
            // Reset opacity
            SPACE_TEXT.setOpacity(1.0);
            SPACE_BUTTON.setOpacity(1.0);

            // Return to Title Screen
            SceneController.showTitleScreen();
        };

        // Call press & release handlers for mouse
        RETURN_BUTTON.setOnMousePressed(pressHandler);
        RETURN_BUTTON.setOnMouseReleased(releaseHandler);

        // Call press & release handlers for SPACE key
        RETURN_BUTTON.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                pressHandler.handle(null);
            }
        });
        RETURN_BUTTON.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                releaseHandler.handle(null);
            }
        });

        return leftSide;
    }

    // Create the right side with leaderboard
    private static GridPane createLeaderboard(List<Player> players) {
        GridPane leaderboardGrid = new GridPane();
        leaderboardGrid.setAlignment(Pos.CENTER);
        leaderboardGrid.setHgap(5);
        leaderboardGrid.setVgap(35);

        // Add header
        Text rankingTitle = new Text("RANKING");
        rankingTitle.setFont(Font.font(TheOs.VARELA.getFamily(), 60)); // Increased font size
        rankingTitle.setFill(TheOs.BROWN);
        leaderboardGrid.add(rankingTitle, 0, 0, 4, 1); // Span 4 columns for the bigger header
        Text turnsTitle = new Text("  Turns");
        turnsTitle.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        turnsTitle.setFill(TheOs.BROWN);
        leaderboardGrid.add(turnsTitle, 4, 0);

        // Add player data to the leaderboard
        for (int i = 0; i < players.size(); i++) {
            ImageView characterImageView = new ImageView(new Image(players.get(i).getImage()));
            characterImageView.setFitWidth(50);
            characterImageView.setFitHeight(50);

            Text rankingText = new Text(String.valueOf(i + 1)); // Ranking starts from 1
            rankingText.setFont(Font.font(TheOs.VARELA.getFamily(), 36));
            rankingText.setFill(TheOs.BROWN);
            Text playerNameText = new Text(players.get(i).getName());
            playerNameText.setFont(Font.font(TheOs.VARELA.getFamily(), 36));
            playerNameText.setFill(TheOs.BROWN);
            Text turnsText = new Text("     " + players.get(i).getTurnCount());
            turnsText.setFont(Font.font(TheOs.VARELA.getFamily(), 36)); // Adjust the font size
            turnsText.setFill(TheOs.BROWN);

            leaderboardGrid.add(rankingText, 0, i + 1);
            leaderboardGrid.add(characterImageView, 1, i + 1);
            leaderboardGrid.add(playerNameText, 2, i + 1);
            leaderboardGrid.add(turnsText, 4, i + 1);
        }

        // Set padding for right side
        leaderboardGrid.setPadding(new Insets(125, 0, 0, 925)); // Adjust values accordingly

        return leaderboardGrid;
    }

    // Create option buttons
    private static AnchorPane createOptionButtons() {
        HBox closeAppButton = OptionButtons.createCloseAppButton();
        HBox returnToTitleButton = OptionButtons.createReturnToTitleButton();
        HBox musicButton = OptionButtons.createMusicButton();
        AnchorPane buttonLayout = new AnchorPane(closeAppButton, returnToTitleButton, musicButton);
        buttonLayout.setTranslateX(20);
        buttonLayout.setTranslateY(18);
        AnchorPane.setLeftAnchor(returnToTitleButton, 60.0);
        AnchorPane.setLeftAnchor(musicButton, 1333.0);

        return buttonLayout;
    }
}