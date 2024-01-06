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

public class Application extends javafx.application.Application {

    /* scene width and height are used for scene size and field class
       (–> x and y parameters of field constructor are in percent, useful for resizing)
        */
    static final int sceneWidth = 1422;
    static final int sceneHeight = 800;

    // colors for texts (in diceUI)
    final static Color BROWN = Color.rgb(120, 98, 68);
    final static Color MINT_GREEN = Color.rgb(63, 139, 88);

    // accessing the custom font fix: https://stackoverflow.com/questions/30245085/javafx-embed-custom-font-not-working
    static final Font CUSTOM_FONT_VARELA = Font.loadFont(Application.class.getClassLoader().getResourceAsStream("fonts/VarelaRound-Regular.ttf"), 22);
    static final Font CUSTOM_FONT_CAVEAT = Font.loadFont(Application.class.getClassLoader().getResourceAsStream("fonts/Caveat-SemiBold.ttf"), 25);

    static boolean waitingForUserInput = true;

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml")); // bis jetzt war noch kein FXML notwendig

        GameBoard gameBoard = new GameBoard();

        // Initialize gameboard Graph
        List<Field> fieldList = new ArrayList<>();

        Field field1 = new Field(Field.fieldType.NormalField, 17.3, 50.1); // Field 1
        Field field2 = new Field(Field.fieldType.NormalField, 19.2, 48.4); // Field 2
        Field field3 = new Field(Field.fieldType.NormalField, 21.4, 47.9); // Field 3
        Field field4 = new Field(Field.fieldType.NormalField, 23.7, 48.5); // Field 4
        Field field5 = new Field(Field.fieldType.NormalField, 25.1, 51.3); // Field 5
        Field field6 = new Field(Field.fieldType.NormalField, 25.1, 55.5); // Field 6
        Field field7 = new Field(Field.fieldType.LadderField, 25.1, 60.1); // Field 7
        Field field8 = new Field(Field.fieldType.NormalField, 26.1, 63.6); // Field 8
        Field field9 = new Field(Field.fieldType.NormalField, 28, 66); // Field 9
        Field field10 = new Field(Field.fieldType.NormalField, 30.4, 67.4); // Field 10
        Field field11 = new Field(Field.fieldType.NormalField, 32.9, 67.6); // Field 11
        Field field12 = new Field(Field.fieldType.NormalField, 35.4, 67); // Field 12
        Field field13 = new Field(Field.fieldType.NormalField, 37.7, 65.3); // Field 13
        Field field14 = new Field(Field.fieldType.NormalField, 39.6, 62.7); // Field 14
        Field field15 = new Field(Field.fieldType.NormalField, 40.8, 58.7); // Field 15
        Field field16 = new Field(Field.fieldType.NormalField, 40.9, 54.1); // Field 16
        Field field17 = new Field(Field.fieldType.LadderField, 39.8, 50.2); // Field 17
        Field field18 = new Field(Field.fieldType.NormalField, 38.4, 47); // Field 18
        Field field19 = new Field(Field.fieldType.NormalField, 36.3, 44.8);
        Field field20 = new Field(Field.fieldType.NormalField, 34, 42.9);


        fieldList.add(field1);
        fieldList.add(field2);
        fieldList.add(field3);
        fieldList.add(field4);
        fieldList.add(field5);
        fieldList.add(field6);
        fieldList.add(field7);
        fieldList.add(field8);
        fieldList.add(field9);
        fieldList.add(field10);
        fieldList.add(field11);
        fieldList.add(field12);
        fieldList.add(field13);
        fieldList.add(field14);
        fieldList.add(field15);
        fieldList.add(field16);
        fieldList.add(field17);
        fieldList.add(field18);
        fieldList.add(field19);
        fieldList.add(field20);

        for (int i = 0; i < fieldList.size(); i++) {
            gameBoard.getBoardGraph().addVertex(fieldList.get(i));
        }

        for (int i = 0; i < fieldList.size() - 1; i++) {
            gameBoard.getBoardGraph().addOneDirectionalEdge(fieldList.get(i), fieldList.get(i + 1), 500, BoardGraph.edgeType.NormalEdge);
        }

        gameBoard.getBoardGraph().addOneDirectionalEdge(field7, field17, 2500, BoardGraph.edgeType.LadderEdge);
        // initializing gameBoard Graph done; should we move this into a method?

        Circle playerTestView = new Circle(5); // just used for testing; better visualizes the exact path of player
        playerTestView.setCenterX(field1.getX());
        playerTestView.setCenterY(field1.getY());

        int[] mintOLintDie = {1, 1, 2, 2, 2, 7};
        Player player1 = new Player("Mint’O Lint", mintOLintDie, Paths.get("images"));

        Image mintOLintSprite = new Image("images/mintolint_sprite.png", 64 * 22, 64 * 3, true, false);
        player1.setImageView(new ImageView(mintOLintSprite));
        player1.setCurrentField(field1);
        player1.getImageView().setX(field1.getX() - 32);
        player1.getImageView().setY(field1.getY() - 32 - 15);

        player1.playIdle();
        //player1.playWalk();
        //player1.playSlip();

        /* Überlegung Group und Pane als Parent Element für Scene:
        Group setzt kein fixes Layout für Elemente vor, daher kommen da die sich bewegenden Elemente wie Spieler rein

        Pane dient als Root Element fürs Layout, darin kommt die Group mit den Spielern (dz. Kreise)
        Pane ermöglicht, im Gegensatz zu Group, dass ein Hintergrundbild gesetzt wird. Und vllt ist es später auch praktisch (bzw. ein Subtyp von Pane)
        für zusätliche Layout-Elemente wie Spieler- und Würfelanzeige.
         */

        Group group = new Group(player1.getImageView());

        Pane root = new Pane(group);

        root.setBackground(gameBoard.getBackground()); // setBackground method needs Background object, not BackgroundImage

        // Placing the DiceUI of the GameBoard on the screen
        gameBoard.getDiceUI().setTranslateX(40);
        gameBoard.getDiceUI().setTranslateY(800 - 215);

        root.getChildren().addAll(gameBoard.getDiceUI());

        Scene scene = new Scene(root, sceneWidth, sceneHeight); // ich hab die Größe erstmal auf 1422x800 eingestellt da 1920x1080 für meinen Laptop-Bildschirm zu groß war

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.A) {
                player1.playIdle();
            }
            if (event.getCode() == KeyCode.S) {
                player1.playSlip();
            }
            if (waitingForUserInput) {
                if (event.getCode() == KeyCode.UP) {
                    gameBoard.getDiceUI().selectNormalDie();
                }
                // Special die charges are checked here!
                if (event.getCode() == KeyCode.DOWN && player1.getSpecialDie().getCharge() > 0) {
                    gameBoard.getDiceUI().selectSpecialDie();
                }
                if (event.getCode() == KeyCode.SPACE) {
                    waitingForUserInput = false;
                    gameBoard.playerTurn(player1);
                }
            }
        });

        stage.setTitle("The O’s");
        stage.setScene(scene);
        stage.setResizable(false); // daweil mal ohne resizable
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}