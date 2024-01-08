package com.example.theos;

import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TestWinningScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Simulate finished players
        Player player1 = new Player("Diva O'Hara", new int[]{-3,-3,6,6,7}, Paths.get("images/player_icons/Icon_1.PNG"), Paths.get("images/winning_screen/Win_1.PNG"), new Field(Field.fieldType.NormalField, 100,100));
        Player player2 = new Player("Y'Olanda", new int[]{1,1,2,4,6,6}, Paths.get("images/player_icons/Icon_2.PNG"), Paths.get("images/winning_screen/Win_2.PNG"), new Field(Field.fieldType.NormalField, 100,100));
        Player player3 = new Player("Kidd'O", new int[]{-2,-1,4,5,6,6}, Paths.get("images/player_icons/Icon_3.PNG"), Paths.get("images/winning_screen/Win_3.PNG"), new Field(Field.fieldType.NormalField, 100,100));
        Player player4 = new Player("Mint'O Lint", new int[]{1,1,2,2,2,7}, Paths.get("images/player_icons/Icon_4.PNG"), Paths.get("images/winning_screen/Win_4.PNG"), new Field(Field.fieldType.NormalField, 100,100));
        Player player5 = new Player("Brooke O'Let", new int[]{2,2,3,4,4,5}, Paths.get("images/player_icons/Icon_5.PNG"), Paths.get("images/winning_screen/Win_5.PNG"), new Field(Field.fieldType.NormalField, 100,100));
        Player player6 = new Player("O'Fitz", new int[]{-1,0,2,3,4,7}, Paths.get("images/player_icons/Icon_6.PNG"), Paths.get("images/winning_screen/Win_6.PNG"), new Field(Field.fieldType.NormalField, 100,100));


        // Mark players as finished
        player1.increaseTurns();
        player2.increaseTurns();
        player3.increaseTurns();
        player4.increaseTurns();
        player5.increaseTurns();
        player6.increaseTurns();

        List<Player> finishedPlayers = Arrays.asList(player5, player2, player3, player1, player6, player4);

        // Create an instance of WinningScreen and call showWinningScreen
        //WinningScreen winningScreen = new WinningScreen();
        //winningScreen.createWinningScreen(finishedPlayers);
    }
}