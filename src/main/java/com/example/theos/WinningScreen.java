package com.example.theos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

// The following code has been partially adapted from ChatGPT
public class WinningScreen { // Displays winning screen with leaderboard & options to return to title screen
    private static final Text DESCRIPTION_TEXT = new Text("Return to Main Menu"); // Text shown on screen above SPACE button
    private static final Text SPACE_TEXT = new Text("SPACE"); // SPACE text on button
    private static final ImageView SPACE_BUTTON = new ImageView(new Image("images/option_button_extras/Button_Space_Big.PNG")); // Button image
    private static final Button RETURN_BUTTON = new Button("Return to Main Menu"); // Button to return to title screen

    public static Scene createWinningScreen(List<Player> finishedPlayers) { // Create winning screen

        // Extract winner & initialize game
        Player winner = finishedPlayers.get(0);
        Player[] playersArray = finishedPlayers.toArray(new Player[0]);
        List<Player> players = Arrays.asList(playersArray);

        // Create main layout & set background
        AnchorPane leftSide = createLeftSide(winner);
        GridPane leaderboardGrid = createLeaderboard(players);
        AnchorPane optionButtons = OptionButtons.createOptionButtonsSet(null, true, true, true, false, true);
        Pane mainLayout = new Pane(leftSide, leaderboardGrid, optionButtons);
        String backgroundIMG = "images/winning_screen/Winning_Screen.PNG";
        mainLayout.setStyle("-fx-background-image: url('" + backgroundIMG + "'); -fx-background-size: cover;");

        return new Scene(mainLayout, TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
    }

    private static AnchorPane createLeftSide(Player winner) { // Create left side with winning character image & return button
        ImageView winningCharacterIMG = new ImageView(new Image(winner.getWinningImagePath()));
        winningCharacterIMG.setPreserveRatio(true);
        winningCharacterIMG.setFitWidth(262);

        // Create left side, add to main layout & position of winning player
        AnchorPane leftSide = new AnchorPane(winningCharacterIMG, SPACE_BUTTON, DESCRIPTION_TEXT, SPACE_TEXT, RETURN_BUTTON);
        leftSide.setTranslateX(250);
        leftSide.setTranslateY(690);
        AnchorPane.setLeftAnchor(winningCharacterIMG, 46.0);
        AnchorPane.setTopAnchor(winningCharacterIMG, -499.0);

        // Format, position & animation of return button
        OptionButtons.spaceButtonFormat(DESCRIPTION_TEXT, SPACE_TEXT, SPACE_BUTTON, RETURN_BUTTON, SceneController::showTitleScreen);
        AnchorPane.setLeftAnchor(DESCRIPTION_TEXT, 25.0);
        OptionButtons.animateButtonBounce(RETURN_BUTTON, SPACE_TEXT, DESCRIPTION_TEXT, SPACE_BUTTON);

        return leftSide;
    }

    private static GridPane createLeaderboard(List<Player> players) { // Create right side with leaderboard
        GridPane leaderboardGrid = new GridPane();
        leaderboardGrid.setAlignment(Pos.CENTER);
        leaderboardGrid.setVgap(35);

        // Add header
        Text rankingTitle = new Text("RANKING");
        rankingTitle.setFont(Font.font(TheOs.VARELA.getFamily(), 60));
        rankingTitle.setFill(TheOs.BROWN);
        leaderboardGrid.add(rankingTitle, 0, 0, 4, 1);

        Text turnsTitle = new Text("Turns");
        turnsTitle.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        turnsTitle.setFill(TheOs.BROWN);
        leaderboardGrid.add(turnsTitle, 3, 0);

        // Column size
        ColumnConstraints col0 = new ColumnConstraints(40); // Rank
        ColumnConstraints col1 = new ColumnConstraints(60); // Character icon
        ColumnConstraints col2 = new ColumnConstraints(234); // Character name
        ColumnConstraints col3 = new ColumnConstraints(110); // Turns
        leaderboardGrid.getColumnConstraints().addAll(col0, col1, col2, col3);

        // Add player data to leaderboard
        for (int i = 0; i < players.size(); i++) {
            ImageView pixelIconIMG = new ImageView(new Image(players.get(i).getPixelImagePath()));
            pixelIconIMG.setFitWidth(50);
            pixelIconIMG.setFitHeight(50);
            pixelIconIMG.setTranslateY(-8);

            Text rankingText = new Text(String.valueOf(i + 1));
            rankingText.setFont(Font.font(TheOs.VARELA.getFamily(), 36));
            rankingText.setFill(TheOs.BROWN);

            Text playerNameText = new Text(players.get(i).getName());
            playerNameText.setFont(Font.font(TheOs.VARELA.getFamily(), 36));
            playerNameText.setFill(TheOs.BROWN);

            Text turnsText = new Text("  " + players.get(i).getTurnCount());
            turnsText.setFont(Font.font(TheOs.VARELA.getFamily(), 36));
            turnsText.setFill(TheOs.BROWN);

            leaderboardGrid.add(rankingText, 0, i + 1);
            leaderboardGrid.add(pixelIconIMG, 1, i + 1);
            leaderboardGrid.add(playerNameText, 2, i + 1);
            leaderboardGrid.add(turnsText, 3, i + 1);
        }

        leaderboardGrid.setPadding(new Insets(125, 0, 0, 930));

        return leaderboardGrid;
    }
}