package com.example.theos;

import com.example.theos.BordGameGraph.BoardGraph;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SceneController {

    static Stage stage;

    public static void showTitleScreen() {
        Scene scene = TitleScreen.createTitleScreen();
        stage.setScene(scene);
        stage.show();
    }

    public static void showPlayerSelectSceen() {
        Scene scene = PlayerSelectionScreen.createPlayerSelectionScreen();
        stage.setScene(scene);
        stage.show();
    }

    /*
    Shows the gameboard screen, also:
    the instance of the gameboard which holds all data of the game (number of players, finished players, etc) is created here
     */
    public static void showGameBoardSceen(List<Player> playerList) {
        GameBoard gameBoard = new GameBoard();
        gameBoard.setPlayerList(playerList);
        gameBoard.fillGraphData();

        Scene scene = gameBoard.createGameBoardScreen();
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(event -> {
            if (TheOs.waitingForUserInput) {
                if (event.getCode() == KeyCode.UP) {
                    gameBoard.getDiceUI().selectNormalDie();
                }
                // Special die charges are checked here!
                if (event.getCode() == KeyCode.DOWN && gameBoard.getPlayerList().get(0).getSpecialDie().getCharge() > 0) {
                    gameBoard.getDiceUI().selectSpecialDie();
                }
                if (event.getCode() == KeyCode.SPACE) {
                    TheOs.waitingForUserInput = false;
                    gameBoard.playerTurn(gameBoard.getPlayerList().get(0));
                }
            }
        });
    }

    /*
    public static void showWinningSceen(List<Player> finishedPlayers) {
        Scene scene = WinningScreen.createWinningScreen(finishedPlayers);
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                showPlayerSelectSceen();
            }
        });
    }

     */

}