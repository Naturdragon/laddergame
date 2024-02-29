package com.example.theos;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TheOs extends javafx.application.Application {

    /* scene width and height are used for scene size and field class
       (–> x and y parameters of field constructor are in percent, useful for resizing)
        */ 
    public static final double SCENE_WIDTH = 1422;
    public static final double SCENE_HEIGHT = 800;

    public static Stage mainStage;
    public static boolean waitingForUserInput;

    // Fonts for text
    public static final Font CAVEAT = Font.loadFont(PlayerSelectionScreen.class.getClassLoader().getResourceAsStream("fonts/Caveat-SemiBold.ttf"), 25);
    public static final Font VARELA = Font.loadFont(PlayerSelectionScreen.class.getClassLoader().getResourceAsStream("fonts/VarelaRound-Regular.ttf"), 22);

    // Colors for text
    public static final Color BROWN = Color.rgb(120, 98, 68);
    public static final Color DIVA_PINK = Color.rgb(198, 50, 116);
    public static final Color YOLANDA_RED = Color.rgb(185, 58, 50);
    public static final Color KIDDO_YELLOW = Color.rgb(193, 155, 54);
    public static final Color MINTO_GREEN = Color.rgb(63, 139, 88);
    public static final Color BROOKE_BLUE = Color.rgb(106, 162, 194);
    public static final Color OFITZ_PURPLE = Color.rgb(58, 75, 156);

    @Override
    public void start(Stage stage){
        mainStage = stage;
        Scene scene = new Scene(TitleScreen.createTitleScreen(), SCENE_WIDTH, SCENE_HEIGHT);
        scene.setFill(Color.BLACK);
        mainStage.setScene(scene);
        stage.setTitle("The O’s");
        mainStage.setResizable(false); // For now non-resizable
        SceneController.manageStateButtonAction(TheOs.mainStage.getScene());
        mainStage.show();

        SoundGame soundGame = new SoundGame("/sound/sound.wav"); // Play theme song
        SoundGame.setVolume(0.1);
        stage.setOnCloseRequest(event -> soundGame.stop()); // Stop theme song in case of closed application

        // Set application icon
        Image icon = new Image("/images/application_icon/Application_Icon.PNG");
        stage.getIcons().add(icon);
    }

    public static void main(String[] args) {
        launch();
    }
}