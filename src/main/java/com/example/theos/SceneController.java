package com.example.theos;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.List;

import static com.example.theos.PlayerSelectionScreen.MOUSE_DISPLAY;

public class SceneController {
    static Stage stage;

    public static void showTitleScreen() {
        Scene scene = TitleScreen.createTitleScreen();
        stage.setScene(scene);
        stage.show();
        manageMusicButtonAction(scene);
    }

    public static void showPlayerSelectScreen() {
        Scene scene = PlayerSelectionScreen.createPlayerSelectionScreen();
        stage.setScene(scene);
        stage.show();
        manageMusicButtonAction(scene);
        manageMouseAction(scene);
    }

    // Shows game board screen (is instance which holds all data of game: number of players, finished players, etc.)
    public static void showGameBoardScreen(List<Player> playerList) {
        TheOs.waitingForUserInput = false; // User input is unlocked after spawn-in animation is played (happens inside createGameBoardScreen, line 32)
        GameBoard gameBoard = new GameBoard(playerList);

        Scene scene = gameBoard.createGameBoardScreen();
        stage.setScene(scene);
        stage.show();
        manageGameBoardAction(scene, gameBoard);
    }

    public static void showWinningScreen(List<Player> finishedPlayers) {
        Scene scene = WinningScreen.createWinningScreen(finishedPlayers);
        stage.setScene(scene);
        stage.show();
        OptionButtons.instructionsOn = true;
        manageMusicButtonAction(scene);
    }

    public static void manageGameBoardAction(Scene scene, GameBoard gameBoard) { // Manage keys for game board
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
                    OptionButtons.instructionsOn = false;
                }
            }
            if (event.getCode() == KeyCode.M && !OptionButtons.musicTogglePressed) {
                event.consume();
                OptionButtons.toggleMusicStatePressed();
            }
            if (event.getCode() == KeyCode.ENTER) { // ! Only there to quickly access the winning screen, debugging purposes !
                showWinningScreen(gameBoard.getPlayerList());
                OptionButtons.instructionsOn = true;
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.M) {
                event.consume();
                OptionButtons.toggleMusicStateReleased();
            }
        });
    }

    public static void manageMusicButtonAction(Scene scene) { // Manage M key for music
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M && !OptionButtons.musicTogglePressed) {
                event.consume();
                OptionButtons.toggleMusicStatePressed();
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.M) {
                event.consume();
                OptionButtons.toggleMusicStateReleased();
            }
        });
    }

    public static void manageMouseAction(Scene scene) { // Manage clicking for mouse
        scene.setOnMousePressed(event -> {
            MOUSE_DISPLAY.setOpacity(0.5);
            MOUSE_DISPLAY.setTranslateY(MOUSE_DISPLAY.getY() + 1);
        });
        scene.setOnMouseReleased(event -> {
            MOUSE_DISPLAY.setOpacity(1.0);
            MOUSE_DISPLAY.setTranslateY(MOUSE_DISPLAY.getY() - 1);
        });
    }
}