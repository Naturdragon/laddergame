package com.example.theos;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.util.List;

import static com.example.theos.PlayerSelectionScreen.MOUSE_DISPLAY;

public class SceneController {

    public static void showTitleScreen() {
        Pane pane = TitleScreen.createTitleScreen();
        TheOs.mainStage.getScene().setRoot(pane);
    }

    public static void showPlayerSelectScreen() {
        Pane pane = PlayerSelectionScreen.createPlayerSelectionScreen();
        TheOs.mainStage.getScene().setRoot(pane);
        manageMouseAction(TheOs.mainStage.getScene());
    }

    // Shows game board screen (is instance which holds all data of game: number of players, finished players, etc.)
    public static void showGameBoardScreen(List<Player> playerList) {
        TheOs.waitingForUserInput = false; // User input is unlocked after spawn-in animation is played (happens inside createGameBoardScreen, line 32)
        GameBoard gameBoard = new GameBoard(playerList);

        Pane pane = gameBoard.createGameBoardScreen();
        TheOs.mainStage.getScene().setRoot(pane);
        manageGameBoardAction(TheOs.mainStage.getScene(), gameBoard);
    }

    public static void showWinningScreen(List<Player> finishedPlayers) {
        Pane pane = WinningScreen.createWinningScreen(finishedPlayers);
        TheOs.mainStage.getScene().setRoot(pane);
        OptionButtons.instructionsOn = true;
    }

    public static void createBackgroundRegion(Image image, Pane mainLayout) {
        BackgroundImage backgroundImg = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT, false, false, false, false));
        Region backgroundRegion = new Region();
        backgroundRegion.setBackground(new Background(backgroundImg));
        backgroundRegion.setPrefSize(TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
        mainLayout.getChildren().add(backgroundRegion);

        mainLayout.setClip(new javafx.scene.shape.Rectangle(TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT)); // Limits scene size to 1422x800 in all window sizes
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
            if (event.getCode() == KeyCode.F && !OptionButtons.fullscreenCooldown) {
                event.consume();
                OptionButtons.toggleFullscreenPressed();
            }
            if (event.getCode() == KeyCode.M && !OptionButtons.musicCooldown) {
                event.consume();
                OptionButtons.toggleMusicStatePressed();
            }
            if (event.getCode() == KeyCode.ENTER) { // ! Only there to quickly access the winning screen, debugging purposes !
                showWinningScreen(gameBoard.getPlayerList());
                OptionButtons.instructionsOn = true;
            }
            TheOs.mainStage.fullScreenProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue) { // If fullscreen is exited
                    OptionButtons.fullscreenButtonPane.setOpacity(1.0);
                    OptionButtons.fullscreenOn = false;
                }
            });
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.F && OptionButtons.fullscreenCooldown) {
                event.consume();
                OptionButtons.toggleFullscreenReleased();
            }
            if (event.getCode() == KeyCode.M && OptionButtons.musicCooldown) {
                event.consume();
                OptionButtons.toggleMusicStateReleased();
            }
        });
    }

    public static void manageStateButtonAction(Scene scene) { // Manage keys for screen size & music
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F && !OptionButtons.fullscreenCooldown) {
                event.consume();
                OptionButtons.toggleFullscreenPressed();
            }
            if (event.getCode() == KeyCode.M && !OptionButtons.musicCooldown) {
                event.consume();
                OptionButtons.toggleMusicStatePressed();
            }
            TheOs.mainStage.fullScreenProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue) { // If fullscreen is exited
                    OptionButtons.fullscreenButtonPane.setOpacity(1.0);
                    OptionButtons.fullscreenOn = false;
                }
            });
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.F && OptionButtons.fullscreenCooldown) {
                event.consume();
                OptionButtons.toggleFullscreenReleased();
            }
            if (event.getCode() == KeyCode.M && OptionButtons.musicCooldown) {
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