package com.example.theos;

import com.example.theos.BordGameGraph.BoardGraph;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    static boolean negativeInputForTesting = false;
    static boolean waitingForUserInput = true;

    @Override
    public void start(Stage stage) throws IOException {
        SceneController.stage = stage;
        stage.setTitle("The O’s");
        stage.setResizable(false); // daweil mal ohne resizable
        SceneController.showPlayerSelectSceen();
    }

    public static void main(String[] args) {
        launch();
    }
}