package com.example.theos;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.List;

public class SceneController {
    static Stage stage;

    public static void showTitleScreen() {
        Scene scene = TitleScreen.createTitleScreen();
        stage.setScene(scene);
        stage.show();
    }

    public static void showPlayerSelectScreen() {
        Scene scene = PlayerSelectionScreen.createPlayerSelectionScreen();
        stage.setScene(scene);
        stage.show();
    }

    /*
    Shows the gameboard screen, also:
    the instance of the gameboard which holds all data of the game (number of players, finished players, etc) is created here
     */
    public static void showGameBoardScreen(List<Player> playerList) {
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
                    gameBoard.hideInstructions();
                }
            }
            /* its possible to implement showing the instructions through a key press or option button in the future,
            however, right now there is a visual bug with the close button similar to the one with the dice select arrow
            if (event.getCode() == KeyCode.I) {
                gameBoard.showInstructions();
            }
             */

            if (event.getCode() == KeyCode.ENTER) { // TODO: delete later, only there to quickly access the winning screen
                showWinningScreen(gameBoard.getPlayerList());
                OptionButtons.instructionsOn = true;
            }
        });
    }

    public static void showWinningScreen(List<Player> finishedPlayers) {
        Scene scene = WinningScreen.createWinningScreen(finishedPlayers);
        stage.setScene(scene);
        stage.show();
        OptionButtons.instructionsOn = true;
    }
}