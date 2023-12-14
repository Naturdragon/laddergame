package com.example.theos;

import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application extends javafx.application.Application {

    static int playerWhoseTurnIs = 0;
    static List<Player> playerList = new ArrayList<>();
    static List<Field> fieldList = new ArrayList<>();

    /* 14.12. Mateo:
    Moves player from current field to desired field.
    Method accesses the list of fields and based on the indexes of all fields from start to end builds the path the character will move along.
    Loads created path and character into PathTransitions and plays the animation.
    Returns nothing
     */
    public static void movePlayer(List<Field> fieldList, Player player, Field currentFieldOfPlayer, int fieldsToMove) {

        Path path = new Path();
        path.getElements().add(new MoveTo(currentFieldOfPlayer.animationPointX, currentFieldOfPlayer.animationPointY)); // start point of path is current field of player

        int duration = 0;

        // the coordinates of every field from current to end are added to the path as straight lines
        for (int i = 0; i < fieldsToMove; i++) {
            path.getElements().add(new LineTo(fieldList.get(i + 1 + currentFieldOfPlayer.fieldID).animationPointX, fieldList.get(i + 1 + currentFieldOfPlayer.fieldID).animationPointY));

            duration += 500;
        }

        player.currentField += fieldsToMove;

        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.millis(duration));
        transition.setPath(path);
        transition.setNode(player);
        transition.play();
    }

    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml")); // 14.12. Mateo: bis jetzt war noch kein FXML notwendig

        // 14.12. Mateo: Creating all the players and adding them to the list
        Player player1 = new Player(15, Color.HOTPINK);
        Player player2 = new Player(15, Color.BLUE);
        Player player3 = new Player(15, Color.RED);
        Player player4 = new Player(15, Color.DARKOLIVEGREEN);

        // 14.12. Mateo: so players are shown at the correct starting position
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

        // 14.12. Mateo: Creating all the fields and adding them to the list
        Field field0 = new Field(165, 423);
        Field field1 = new Field(246, 400);
        Field field2 = new Field(274, 387);
        Field field3 = new Field(303, 381);
        Field field4 = new Field(336, 390);
        Field field5 = new Field(357, 410);
        Field field6 = new Field(355, 442);
        Field field7 = new Field(357, 479);
        Field field8 = new Field(371, 509);
        Field field9 = new Field(398, 526);
        Field field10 = new Field(431, 537);
        Field field11 = new Field(1153, 337);

        fieldList.add(field0); // 14.12. Mateo: adding all the fields and players to their lists should probably happen in a loop (problem for later)
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

        Group group = new Group(player1, player2, player3, player4); // 14.12. Mateo: Group setzt kein fixes Layout für Elemente vor, daher kommen da die sich bewegenden Elemente wie Spieler rein

        /* 14.12. Mateo:
        Pane dient als Root Element fürs Layout, darin kommt die Group mit den Spielern (dz. Kreise)
        Pane ermöglicht, im Gegensatz zu Group, dass ein Hintergrundbild gesetzt wird. Und vllt ist es später auch praktisch (bzw. ein Subtyp von Pane)
        für zusätliche Layout-Elemente wie Spieler- und Würfelanzeige.
         */
        Pane root = new Pane(group);

        // 14.12. Mateo: Image of board is loaded into a BackgroundImage object, along with other parameters; https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BackgroundImage.html
        BackgroundImage backgroundImg = new BackgroundImage(new Image("images/gameboard_downsized.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));

        root.setBackground(new Background(backgroundImg)); // 14.12. Mateo: setBackground method needs Background object, not BackgroundImage

        Scene scene = new Scene(root, 1422, 800); // 14.12. Mateo: ich hab die Größe erstmal auf 1422x800 eingestellt da 1920x1080 für meinen Laptop-Bildschirm zu groß war

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() { // 14.12. Mateo: kann automatisch durch Lambda ersetzt werden, ist dann kürzer
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) { // 14.12. Mateo: passiert nur wenn Space gedrückt wird

                    Random randomizer = new Random();

                    int fieldsToMove = randomizer.nextInt(4) + 1;

                    System.out.println(playerList.get(playerWhoseTurnIs).toString() + " rolled " + fieldsToMove); // 14.12. Mateo: Ausgabe des Zuges auf der Konsole

                    movePlayer(fieldList, playerList.get(playerWhoseTurnIs), fieldList.get(playerList.get(playerWhoseTurnIs).currentField), fieldsToMove);

                    playerWhoseTurnIs++;

                    if (playerWhoseTurnIs == playerList.size()) playerWhoseTurnIs = 0; // 14.12. Mateo: wenn der letzte Spieler dran war startet die Liste (playerWhosTurnIs) wieder vom Anfang
                }
            }
        });

        stage.setTitle("The O’s");
        stage.setScene(scene);
        stage.setResizable(false); // 14.12. Mateo: daweil mal ohne resizable
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}