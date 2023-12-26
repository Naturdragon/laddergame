package com.example.theos;

import Graph.Graph;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    Graph boardGraph;
    List<Player> playerList;
    Background background;
    Text rolledNumberDisplay;

    public GameBoard() {
        boardGraph = new Graph();
        playerList = new ArrayList<>();

        BackgroundImage backgroundImg = new BackgroundImage(
                new Image("images/gameboard_downsized.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));

        background = new Background(backgroundImg);

        rolledNumberDisplay = new Text();

        rolledNumberDisplay.setX(30);
        rolledNumberDisplay.setY(750);
        rolledNumberDisplay.setFont(new Font(30));

    }

    public GameBoard(Graph boardGraph, List<Player> playerList, Background background, Text rolledNumberDisplay) {
        this.boardGraph = boardGraph;
        this.playerList = playerList;
        this.background = background;
        this.rolledNumberDisplay = rolledNumberDisplay;
    }

    public Graph getBoardGraph() {
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
    public void playerTurn(Player player) {

        int rolled = player.rollDie(Dice.dieType.NormalDie);

        rolledNumberDisplay.setText(String.valueOf(rolled));
    }

    /*
    Moves player from current field to desired field.
    Method accesses the list of fields and based on the indexes of all fields from start to end builds the path the character will move along.
    Loads created path and character into PathTransitions and plays the animation.
    Returns nothing
    */
    public void movePlayer (Player player, int fieldsToMove){



    }

}
