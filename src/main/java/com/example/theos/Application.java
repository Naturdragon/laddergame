package com.example.theos;

import Animation.SpriteAnimation;
import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Application extends javafx.application.Application {

    /* scene width and height are used for scene size and field class
       –> x and y parameters of field constructor are in percent, useful for resizing
        */
    static final int sceneWidth = 1422;
    static final int sceneHeight = 800;

    /* 14.12. Mateo:
        Moves player from current field to desired field.
        Method accesses the list of fields and based on the indexes of all fields from start to end builds the path the character will move along.
        Loads created path and character into PathTransitions and plays the animation.
        Returns nothing
        */
    public static void movePlayerTest(List<TestField> testFieldList, Player player, TestField currentTestFieldOfPlayer, int fieldsToMove) {

        Path path = new Path();
        path.getElements().add(new MoveTo(currentTestFieldOfPlayer.animationPointX, currentTestFieldOfPlayer.animationPointY)); // start point of path is current field of player

        int duration = 0;

        // the coordinates of every field from current to end are added to the path as straight lines
        for (int i = 0; i < fieldsToMove; i++) {
            path.getElements().add(new LineTo(testFieldList.get(i + 1 + currentTestFieldOfPlayer.fieldID).animationPointX, testFieldList.get(i + 1 + currentTestFieldOfPlayer.fieldID).animationPointY));

            duration += 500;
        }

        //player.currentField += fieldsToMove;
        player.playWalk();

        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.millis(duration));
        transition.setPath(path);
        transition.setNode(player.getImageView());
        transition.play();
        transition.setOnFinished(event -> player.playIdle());
    }

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml")); // 14.12. Mateo: bis jetzt war noch kein FXML notwendig

        /* Test: Creating all the players and adding them to the list
        TestPlayer player1 = new TestPlayer(15, Color.HOTPINK);
        TestPlayer player2 = new TestPlayer(15, Color.BLUE);
        TestPlayer player3 = new TestPlayer(15, Color.RED);
        TestPlayer player4 = new TestPlayer(15, Color.DARKOLIVEGREEN);
        */

        /* Test: so players are shown at the correct starting position
        player1.setCenterX(165);
        player1.setCenterY(423);
        player2.setCenterY(423);
        player2.setCenterX(165);
        player3.setCenterY(423);
        player3.setCenterX(165);
        player4.setCenterY(423);
        player4.setCenterX(165);

        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        */

        // Test: Creating all the fields and adding them to the list
        TestField testField0 = new TestField(165, 423-20);
        TestField testField1 = new TestField(246, 400-20);
        TestField testField2 = new TestField(274, 387-20);
        TestField testField3 = new TestField(303, 381-20);
        TestField testField4 = new TestField(336, 390-20);
        TestField testField5 = new TestField(357, 410-20);
        TestField testField6 = new TestField(355, 442-20);
        TestField testField7 = new TestField(357, 479-20);
        TestField testField8 = new TestField(371, 509-20);
        TestField testField9 = new TestField(398, 526-20);
        TestField testField10 = new TestField(431, 537-20);
        TestField testField11 = new TestField(1153, 337-20);

        List<TestField> testFieldList = new ArrayList<>();

        testFieldList.add(testField0);
        testFieldList.add(testField1);
        testFieldList.add(testField2);
        testFieldList.add(testField3);
        testFieldList.add(testField4);
        testFieldList.add(testField5);
        testFieldList.add(testField6);
        testFieldList.add(testField7);
        testFieldList.add(testField8);
        testFieldList.add(testField9);
        testFieldList.add(testField10);
        testFieldList.add(testField11);


        GameBoard gameBoard = new GameBoard();

        int[] mintOLintDie = {1, 1, 2, 2, 2, 7};
        Player player1 = new Player("Mint’O Lint", mintOLintDie, Paths.get("images"));
        Image mintOLintSprite = new Image("images/mintolint_sprite.png", 64 * 22, 64 * 3, true, false);
        player1.setImageView(new ImageView(mintOLintSprite));
        player1.getImageView().setX(165);
        player1.getImageView().setY(423-20);

        player1.playIdle();
        //player1.playWalk();
        //player1.playSlip();

        /* Überlegung Group und Pane als Parent Element für Scene:
        Group setzt kein fixes Layout für Elemente vor, daher kommen da die sich bewegenden Elemente wie Spieler rein

        Pane dient als Root Element fürs Layout, darin kommt die Group mit den Spielern (dz. Kreise)
        Pane ermöglicht, im Gegensatz zu Group, dass ein Hintergrundbild gesetzt wird. Und vllt ist es später auch praktisch (bzw. ein Subtyp von Pane)
        für zusätliche Layout-Elemente wie Spieler- und Würfelanzeige.
         */

        Group group = new Group(gameBoard.getRolledNumberDisplay(), player1.getImageView());

        Pane root = new Pane(group);

        root.setBackground(gameBoard.getBackground()); // setBackground method needs Background object, not BackgroundImage

        Scene scene = new Scene(root, sceneWidth, sceneHeight); // ich hab die Größe erstmal auf 1422x800 eingestellt da 1920x1080 für meinen Laptop-Bildschirm zu groß war

        /* Test:
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() { // kann automatisch durch Lambda ersetzt werden, ist dann kürzer
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) { // passiert nur wenn Space gedrückt wird

                    Random randomizer = new Random();

                    int fieldsToMove = randomizer.nextInt(4) + 1;

                    System.out.println(playerList.get(playerWhoseTurnIs).toString() + " rolled " + fieldsToMove); // 14.12. Mateo: Ausgabe des Zuges auf der Konsole

                    movePlayerTest(testFieldList, playerList.get(playerWhoseTurnIs), testFieldList.get(playerList.get(playerWhoseTurnIs).currentField), fieldsToMove);

                    playerWhoseTurnIs++;

                    if (playerWhoseTurnIs == playerList.size())
                        playerWhoseTurnIs = 0; // wenn der letzte Spieler dran war startet die Liste (playerWhosTurnIs) wieder vom Anfang
                }
            }
        });
         */

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gameBoard.playerTurn(player1);
                movePlayerTest(testFieldList, player1, testFieldList.get(0), 7);
                //player1.playWalk();
            }

            if (event.getCode() == KeyCode.A) {
                player1.playIdle();
            }

            if (event.getCode() == KeyCode.S) {
                player1.playSlip();
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