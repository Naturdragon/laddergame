package com.example.theos;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;

public class TheOs extends javafx.application.Application {

    /* scene width and height are used for scene size and field class
       (–> x and y parameters of field constructor are in percent, useful for resizing)
        */
    static final int SCENE_WIDTH = 1422;
    static final int SCENE_HEIGHT = 800;

    // colors for texts
    static final Color BROWN = Color.rgb(120, 98, 68);
    static final Color DIVA_PINK = Color.rgb(198, 50, 116);
    static final Color OLANDA_RED = Color.rgb(185, 58, 50);
    static final Color KIDD_YELLOW = Color.rgb(193, 155, 54);
    static final Color MINT_GREEN = Color.rgb(63, 139, 88);
    static final Color BROOKE_BLUE = Color.rgb(106, 162, 194);
    static final Color FITZ_PURPLE = Color.rgb(58, 75, 156);
    static boolean waitingForUserInput;

    @Override
    public void start(Stage stage){
        SceneController.stage = stage;
        stage.setTitle("The O’s");
        stage.setResizable(false); // for now not resizable
        SceneController.showTitleScreen();

        SoundGame soundGame = new SoundGame("/sound/sound.wav"); // plays music
        SoundGame.setVolume(0.1);
        stage.setOnCloseRequest(event -> soundGame.stop()); // stops music in case of closed application

        // Set application icon
        Image icon = new Image("/images/application_icon/Application_Icon.PNG");
        stage.getIcons().add(icon);
        }

    public static void main(String[] args) {
        launch();
    }
}