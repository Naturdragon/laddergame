package com.example.theos;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;

public class TheOs extends javafx.application.Application {

    /* scene width and height are used for scene size and field class
       (–> x and y parameters of field constructor are in percent, useful for resizing)
        */
    public static final int SCENE_WIDTH = 1422;
    public static final int SCENE_HEIGHT = 800;

    // colors for texts
    public static final Color BROWN = Color.rgb(120, 98, 68);
    public static final Color DIVA_PINK = Color.rgb(198, 50, 116);
    public static final Color OLANDA_RED = Color.rgb(185, 58, 50);
    public static final Color KIDD_YELLOW = Color.rgb(193, 155, 54);
    public static final Color MINT_GREEN = Color.rgb(63, 139, 88);
    public static final Color BROOKE_BLUE = Color.rgb(106, 162, 194);
    public static final Color FITZ_PURPLE = Color.rgb(58, 75, 156);
    public static boolean waitingForUserInput;

    @Override
    public void start(Stage stage){
        SceneController.stage = stage;
        stage.setTitle("The O’s");
        stage.setResizable(false); // daweil mal ohne resizable
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