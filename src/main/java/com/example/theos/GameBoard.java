package com.example.theos;

import Graph.Graph;
import com.example.theos.BordGameGraph.BoardGraph;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private BoardGraph boardGraph;
    private List<Player> playerList;
    private Background background;
    private Text rolledNumberDisplay;

    public GameBoard() {

        boardGraph = new BoardGraph();

        playerList = new ArrayList<>();

        BackgroundImage backgroundImg = new BackgroundImage(
                new Image("images/gameboard_screen/Game_BG.PNG"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(Application.sceneWidth, Application.sceneHeight, false, false, true, true));

        background = new Background(backgroundImg);

        rolledNumberDisplay = new Text();

        rolledNumberDisplay.setX(30);
        rolledNumberDisplay.setY(750);
        rolledNumberDisplay.setFont(new Font(30));

    }

    public GameBoard(BoardGraph boardGraph, List<Player> playerList, Background background, Text rolledNumberDisplay) {
        this.boardGraph = boardGraph;
        this.playerList = playerList;
        this.background = background;
        this.rolledNumberDisplay = rolledNumberDisplay;
    }

    public BoardGraph getBoardGraph() {
        return boardGraph;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Background getBackground() {
        return background;
    }

    public Text getRolledNumberDisplay() {
        return rolledNumberDisplay;
    }

    /*
    Lets a player make a move meaning:
    Player can select which die to use, die is rolled
    calls movePlayer method to play the animation of the player moving
    Returns nothing
     */
    public void playerTurn(Player player, Circle testView) {

        int rolled = player.rollDie(Dice.dieType.NormalDie);

        rolledNumberDisplay.setText(String.valueOf(rolled));

        movePlayer(player, rolled, testView);
    }

    /*
    Moves player from current field to desired field.
    Method accesses the list of fields and based on the indexes of all fields from start to end builds the path the character will move along.
    Loads created path and character into PathTransitions and plays the animation.
    Returns nothing
    */
    public void movePlayer(Player player, int fieldsToMove, Circle testView) {

        int animationOffsetX = 0;
        int animationOffsetY = 15;

        Path standardPath = new Path();
        standardPath.getElements().add(new MoveTo(player.getCurrentField().getX() - animationOffsetX, player.getCurrentField().getY() - animationOffsetY));

        Path ladderPath = new Path();

        for (int i = 0; i < fieldsToMove; i++) {
            Field nextField = boardGraph.hopCountTraversal(player.getCurrentField(), 1);
            standardPath.getElements().add(new LineTo(nextField.getX() - animationOffsetX, nextField.getY() - animationOffsetY));

            player.setCurrentField(nextField);
        }

        //if (player.getCurrentField().getType() == Field.fieldType.LadderField) {}

        int duration = 500 * fieldsToMove; // Duration should be stored in the weight of the Edges that are traversed; how can that information be accessed by this method?

        player.playWalk();

        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.millis(duration));
        transition.setPath(standardPath);
        transition.setNode(player.getImageView());
        transition.play();
        transition.setOnFinished(event -> player.playIdle());
    }

}
