package com.example.theos;

import com.example.theos.BordGameGraph.BoardGraph;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private BoardGraph boardGraph;
    private List<Player> playerList;
    private Background background;
    private DiceUI diceUI;

    public GameBoard() {

        boardGraph = new BoardGraph();

        playerList = new ArrayList<>();

        BackgroundImage backgroundImg = new BackgroundImage(
                new Image("images/gameboard_screen/Game_BG.PNG"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(Application.sceneWidth, Application.sceneHeight, false, false, true, true));

        background = new Background(backgroundImg);

        diceUI = new DiceUI();
    }

    public GameBoard(BoardGraph boardGraph, List<Player> playerList, Background background) {
        this.boardGraph = boardGraph;
        this.playerList = playerList;
        this.background = background;
    }

    public BoardGraph getBoardGraph() {
        return boardGraph;
    }

    public DiceUI getDiceUI() {
        return diceUI;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Background getBackground()
    {
        return background;
    }

    /*
    Rolls the appropriate die of the player depending on which one is selected at the diceUI
    Calls animateRolledNumber method of diceUI and the movePlayer method
    Returns nothing
     */
    public void playerTurn(Player player) {

        int rolled = 0;

        if (diceUI.getUiState() == DiceUI.state.NormalDieSelected) {
            rolled = player.rollDie(Dice.dieType.NormalDie);
        }
        if (diceUI.getUiState() == DiceUI.state.SpecialDieSelected) {
            rolled = player.rollDie(Dice.dieType.SpecialDie);
        }

        diceUI.animateRolledNumber(player, rolled);

        movePlayer(player, rolled);
    }

    /*
    Moves player from current field to desired field.
    Method accesses the list of fields and based on the indexes of all fields from start to end builds the path the character will move along.
    Loads created path and character into PathTransitions and plays the animation.
    Returns nothing
    */
    public void movePlayer(Player player, int fieldsToMove)
    {
        int animationOffsetX = 0;
        int animationOffsetY = 15;

        SequentialTransition sequentialTransition = boardGraph.getAnimationPathFromGraph(player.getCurrentField(),fieldsToMove, animationOffsetX, animationOffsetY, player.getImageView());
        sequentialTransition.play();
        sequentialTransition.setOnFinished(event ->player.playIdle());

        player.setCurrentField(boardGraph.hopCountTraversal(player.getCurrentField(),fieldsToMove));




        /*
        Previous Code

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
        transition.setOnFinished(event -> {
            diceUI.animatePlayerSwitch(player);
            player.playIdle();
        });
    }

}
