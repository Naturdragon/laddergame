package com.example.theos;

import com.example.theos.BordGameGraph.BoardGraph;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private static final Image BACKGROUND_IMG = new Image("images/gameboard_screen/Game_BG.PNG");
    private BoardGraph boardGraph;
    private List<Player> playerList;
    private Background backgroundTop;
    private DiceUI diceUI;
    private List<Player> finishedPlayers;
    private List<Field> winningFields;
    private List<Field> lastFields;
    private List<Field> waterfallFields;
    private Pane mainLayout;
    private final AnchorPane INSTRUCTIONS_WINDOW = createInstructionsWindow();
    private Field crossoverField1;
    private Field crossoverField2;

    private int animationOffsetX = 0;
    private int animationOffsetY = 15;

    // Default constructor: just initializes the most important variables with their default constructor
    public GameBoard() {

        boardGraph = new BoardGraph();
        playerList = new ArrayList<>();
        winningFields = new ArrayList<>();
        finishedPlayers = new ArrayList<>();
        diceUI = new DiceUI();
        mainLayout = new Pane();
    }

    /*
    Constructor which takes in a list of players (created by the PlayerSelectScreen) and calls fillGraphData
    (fillGraphData logically has to happen after the correct player list is assigned)
     */
    public GameBoard(List<Player> playerList) {
        this.playerList = playerList;

        boardGraph = new BoardGraph();
        winningFields = new ArrayList<>();
        lastFields = new ArrayList<>();
        waterfallFields = new ArrayList<>();
        finishedPlayers = new ArrayList<>();
        diceUI = new DiceUI();
        mainLayout = new Pane();

        fillGraphData();
    }

    public BoardGraph getBoardGraph() {
        return boardGraph;
    }

    public DiceUI getDiceUI() {
        return diceUI;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Player> getFinishedPlayers() {
        return finishedPlayers;
    }

    public List<Field> getWinningFields() {
        return winningFields;
    }

    public List<Field> getWaterFallFields() {
        return waterfallFields;
    }

    /*
        Plays the animation (as a ParallelTransition) of all characters spawning in
        Unlocks the user to be able to input something (roll or select die)
        Returns nothing
         */
    public void playGameStartAnimation() {
        ParallelTransition transition = new ParallelTransition();
        transition.setCycleCount(1);

        for (Player player : playerList) {
            transition.getChildren().add(player.playSpawnAnimation());
        }

        transition.play();
        transition.setOnFinished(event -> {
            for (Player player : playerList) {
                player.playIdle();
            }
            TheOs.waitingForUserInput = true;
        });
    }

    /*
    Creates the scene/screen view of the game board
    Backgrounds are set, DiceUI and instructionsWindow are placed on screen
    All characters are placed on screen and playGameStartAnimation() is called, afterwards userinput is unlocked
    Returns a scene object, which can be used for the stage object in TheOs start() method
     */
    public Pane createGameBoardScreen() {
        SceneController.createBackgroundRegion(BACKGROUND_IMG, mainLayout);

        // Placing the diceUI on the screen
        diceUI.updateNextPlayer(playerList);
        diceUI.setTranslateX(40);
        diceUI.setTranslateY(800 - 215);
        mainLayout.getChildren().add(diceUI);

        // the ImageViews of all players in playerList are placed on screen at the coordinates of their spawn field
        for (Player player : playerList) {
            mainLayout.getChildren().add(player.getImageView());
            player.getImageView().setX(player.getCurrentField().getX() - 27);
            player.getImageView().setY(player.getCurrentField().getY() - 27 - 15);
        }

        // Placing the top background (waterfall section)
        BackgroundImage backgroundImgTop = new BackgroundImage(
                new Image("images/gameboard_screen/Game_BG_Top.PNG"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT, false, false, false, false));
        backgroundTop = new Background(backgroundImgTop);

        Region backgroundTopRegion = new Region();
        backgroundTopRegion.setBackground(backgroundTop);
        backgroundTopRegion.setPrefSize(TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
        mainLayout.getChildren().add(backgroundTopRegion);

        AnchorPane optionButtons = OptionButtons.createOptionButtonsSet(this, true, true, true, true, true);
        mainLayout.getChildren().add(optionButtons);

        this.playGameStartAnimation(); // plays the spawn in animation of the characters

        this.showInstructions(); // when the scene is created the instructionsWindow should be shown on screen for new players

        //selectPathEvent(crossoverField2, 0); // TODO delete later, only for simulating a crossover

        return mainLayout;
    }

    /*
    Creates the layout of the InstructionsWindow
    This method is used to fill the instructionsWindow variable of the GameBoard
    The window can in later use be added to the screen or hidden from the screen
    Returns an AnchorPane
     */
    private AnchorPane createInstructionsWindow() {
        VBox instructionsText = new VBox();

        Text text1 = new Text("Landing on a        - Field makes your Character" + System.lineSeparator() + "take a shortcut");
        text1.setFont(TheOs.VARELA);
        text1.setFill(TheOs.BROWN);
        Text text2 = new Text("Landing on a        - Field makes your Character " + System.lineSeparator() + "be set backwards, try to avoid them!");
        text2.setFont(TheOs.VARELA);
        text2.setFill(TheOs.BROWN);
        Text text3 = new Text("Every Character has a Normal Die" + System.lineSeparator() + "and a Special Die");
        text3.setFont(TheOs.VARELA);
        text3.setFill(TheOs.BROWN);
        Text text4 = new Text("The Numbers on the Special Die are different" + System.lineSeparator() + "for each Character, choose carefully!");
        text4.setFont(TheOs.VARELA);
        text4.setFill(TheOs.BROWN);
        Text text5 = new Text("You can use your Special Die only 3 Times at" + System.lineSeparator() + "the Start");
        text5.setFont(TheOs.VARELA);
        text5.setFill(TheOs.BROWN);
        Text text6 = new Text("Landing on a        - Field increases your Charges");
        text6.setFont(TheOs.VARELA);
        text6.setFill(TheOs.BROWN);
        Text text7 = new Text("Select which Die to use with");
        text7.setFont(TheOs.VARELA);
        text7.setFill(TheOs.BROWN);

        instructionsText.getChildren().addAll(text1, text2, text3, text4, text5, text6, text7);
        instructionsText.setSpacing(50);
        VBox.setMargin(text2, new Insets(-35, 0, 0, 0));
        VBox.setMargin(text4, new Insets(-35, 0, 0, 0));
        VBox.setMargin(text6, new Insets(-35, 0, 0, 0));

        ImageView instructionsBG = new ImageView("images/gameboard_screen/Instructions_BG.PNG");
        instructionsBG.setOpacity(0.85);

        ImageView normalDieIMG = new ImageView("images/gameboard_screen/Game_Die_0.PNG");
        normalDieIMG.setFitWidth(140);
        normalDieIMG.setPreserveRatio(true);

        ImageView specialDieIMG = new ImageView("images/gameboard_screen/Game_Die_All.PNG");
        specialDieIMG.setFitWidth(140);
        specialDieIMG.setPreserveRatio(true);

        ImageView ladderFieldIMG = new ImageView("images/gameboard_screen/Tutorial_Up.PNG");
        ladderFieldIMG.setFitWidth(35);
        ladderFieldIMG.setPreserveRatio(true);
        ImageView snakeFieldIMG = new ImageView("images/gameboard_screen/Tutorial_Down.PNG");
        snakeFieldIMG.setFitWidth(35);
        snakeFieldIMG.setPreserveRatio(true);
        ImageView specialFieldIMG = new ImageView("images/gameboard_screen/Tutorial_Special.PNG");
        specialFieldIMG.setFitWidth(35);
        specialFieldIMG.setPreserveRatio(true);

        ImageView playerArrowsIMG = new ImageView("images/player_select_screen/Player_Arrows.PNG");
        playerArrowsIMG.setFitWidth(135);
        playerArrowsIMG.setPreserveRatio(true);

        OptionButtons optionButtons = new OptionButtons(this);
        HBox closeButton = optionButtons.createCloseInstructionsButton();

        AnchorPane instructionsWindow = new AnchorPane(instructionsBG, normalDieIMG, specialDieIMG, ladderFieldIMG, snakeFieldIMG, specialFieldIMG, playerArrowsIMG, instructionsText, closeButton);
        instructionsWindow.setTranslateX(700);
        instructionsWindow.setTranslateY(70);
        AnchorPane.setTopAnchor(instructionsText, 85.0);
        AnchorPane.setLeftAnchor(instructionsText, 40.0);
        AnchorPane.setRightAnchor(normalDieIMG, 40.0);
        AnchorPane.setTopAnchor(normalDieIMG, 255.0);
        AnchorPane.setRightAnchor(specialDieIMG, 210.0);
        AnchorPane.setTopAnchor(specialDieIMG, 283.0);
        AnchorPane.setTopAnchor(closeButton, 40.0);
        AnchorPane.setLeftAnchor(closeButton, 40.0);
        AnchorPane.setTopAnchor(ladderFieldIMG, 78.0);
        AnchorPane.setLeftAnchor(ladderFieldIMG, 183.0);
        AnchorPane.setTopAnchor(snakeFieldIMG, 145.0);
        AnchorPane.setLeftAnchor(snakeFieldIMG, 183.0);
        AnchorPane.setTopAnchor(specialFieldIMG, 488.0);
        AnchorPane.setLeftAnchor(specialFieldIMG, 182.0);
        AnchorPane.setTopAnchor(playerArrowsIMG, 560.0);
        AnchorPane.setLeftAnchor(playerArrowsIMG, 350.0);

        return instructionsWindow;
    }

    /*
    Adds the instructions window to the rootLayout
    Plays an animation of the instructions popping up
    Returns nothing
     */
    public void showInstructions() {
        if (!mainLayout.getChildren().contains(INSTRUCTIONS_WINDOW)) {
            mainLayout.getChildren().add(INSTRUCTIONS_WINDOW);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(350), INSTRUCTIONS_WINDOW);
            scaleTransition.setFromX(0);
            scaleTransition.setFromY(0);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(350), INSTRUCTIONS_WINDOW);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1);

            ParallelTransition loadAnimation = new ParallelTransition(scaleTransition, fadeTransition);
            loadAnimation.play();

            /* the idleAnimation was a bit distracting so its commented out
            TranslateTransition idleAnimation = new TranslateTransition(Duration.millis(500), INSTRUCTIONS_WINDOW);
            idleAnimation.setByY(-2);
            idleAnimation.setCycleCount(Animation.INDEFINITE);
            idleAnimation.setAutoReverse(true);

            loadAnimation.setOnFinished(actionEvent -> idleAnimation.play());
             */
        }
    }

    /*
    Removes the instructionsWindow from the rootLayout
    Plays an animation of the window closing
    Returns nothing
     */
    public void hideInstructions() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(350), INSTRUCTIONS_WINDOW);
        scaleTransition.setToX(0.0);
        scaleTransition.setToY(0.0);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(350), INSTRUCTIONS_WINDOW);
        fadeTransition.setToValue(0.0);

        ParallelTransition closeAnimation = new ParallelTransition(scaleTransition, fadeTransition);
        closeAnimation.play();
        closeAnimation.setOnFinished(actionEvent -> mainLayout.getChildren().remove(INSTRUCTIONS_WINDOW));
    }

    /*
    Creates backend part of the gameboard, meaning: all logical fields are created,
    the BoardGraph is filled with the fields (as vertexes) and ladders/snakes (as edges),
    special fields (waterfall, winning fields) are loaded into the respective variables of GameBoard
     */
    public void fillGraphData() {
        List<Field> fieldListUpperPath = new ArrayList<>();

        // Fields on main path
        Field field101 = new Field(Field.fieldType.NormalField, 17.3, 50.1);
        Field field102 = new Field(Field.fieldType.NormalField, 19.2, 48.4);
        Field field103 = new Field(Field.fieldType.NormalField, 21.4, 47.9);
        Field field104 = new Field(Field.fieldType.NormalField, 23.7, 48.5);
        Field field105 = new Field(Field.fieldType.NormalField, 25.1, 51.3);
        Field field106 = new Field(Field.fieldType.NormalField, 25.1, 55.5);
        Field field107 = new Field(Field.fieldType.LadderField, 25.1, 60.1);
        Field field108 = new Field(Field.fieldType.NormalField, 26.1, 63.6);
        Field field109 = new Field(Field.fieldType.NormalField, 28, 66);
        Field field110 = new Field(Field.fieldType.NormalField, 30.4, 67.4);

        Field field111 = new Field(Field.fieldType.NormalField, 32.9, 67.6);
        Field field112 = new Field(Field.fieldType.NormalField, 35.4, 67);
        Field field113 = new Field(Field.fieldType.NormalField, 37.7, 65.3);
        Field field114 = new Field(Field.fieldType.NormalField, 39.6, 62.7);
        Field field115 = new Field(Field.fieldType.NormalField, 40.8, 58.7);
        Field field116 = new Field(Field.fieldType.NormalField, 40.9, 54.1);
        Field field117 = new Field(Field.fieldType.NormalField, 39.8, 50.2);
        Field field118 = new Field(Field.fieldType.NormalField, 38.4, 47);
        Field field119 = new Field(Field.fieldType.NormalField, 36.3, 44.8);
        Field field120 = new Field(Field.fieldType.NormalField, 34, 42.9);

        Field field121 = new Field(Field.fieldType.LadderField, 31.4, 41.1);
        Field field122 = new Field(Field.fieldType.NormalField, 28.7, 39.4);
        Field field123 = new Field(Field.fieldType.NormalField, 26.2, 37.9);
        Field field124 = new Field(Field.fieldType.NormalField, 23.7, 36.4);
        Field field125 = new Field(Field.fieldType.LadderField, 21.2, 34.7);
        Field field126 = new Field(Field.fieldType.NormalField, 18.7, 33.1);
        Field field127 = new Field(Field.fieldType.LadderField, 16.6, 30.9);
        Field field128 = new Field(Field.fieldType.NormalField, 15.1, 28.4);
        Field field129 = new Field(Field.fieldType.NormalField, 14.1, 25.3);
        Field field130 = new Field(Field.fieldType.NormalField, 13.6, 21.3);

        Field field131 = new Field(Field.fieldType.NormalField, 14.3, 17.1);
        Field field132 = new Field(Field.fieldType.NormalField, 15.7, 14);
        Field field133 = new Field(Field.fieldType.NormalField, 17.7, 12);
        Field field134 = new Field(Field.fieldType.NormalField, 19.9, 10.9);
        Field field135 = new Field(Field.fieldType.NormalField, 21.9, 11.3);
        Field field136 = new Field(Field.fieldType.NormalField, 23.6, 12.4);
        Field field137 = new Field(Field.fieldType.NormalField, 25.1, 14.4);
        Field field138 = new Field(Field.fieldType.NormalField, 26.3, 17);
        Field field139 = new Field(Field.fieldType.NormalField, 27.2, 19.9);
        Field field140 = new Field(Field.fieldType.NormalField, 28.1, 23.2);

        Field field141 = new Field(Field.fieldType.NormalField, 29, 26.3);
        Field field142 = new Field(Field.fieldType.NormalField, 30.2, 29.2);
        Field field143 = new Field(Field.fieldType.LadderField, 31.7, 31.8);
        Field field144 = new Field(Field.fieldType.NormalField, 33.5, 34.1);
        Field field145 = new Field(Field.fieldType.NormalField, 35.7, 35.5);
        Field field146 = new Field(Field.fieldType.NormalField, 38.2, 36.6);
        Field field147 = new Field(Field.fieldType.NormalField, 40.8, 36.9);
        Field field148 = new Field(Field.fieldType.NormalField, 43.6, 36.4);
        Field field149 = new Field(Field.fieldType.CrossoverField, 45.9, 34.5);
        crossoverField1 = field149;
        Field field150 = new Field(Field.fieldType.NormalField, 43.8, 32.7);

        Field field151 = new Field(Field.fieldType.NormalField, 42, 30.7);
        Field field152 = new Field(Field.fieldType.NormalField, 40.1, 28.9);
        Field field153 = new Field(Field.fieldType.NormalField, 38.4, 27.1);
        Field field154 = new Field(Field.fieldType.LadderField, 36.6, 25.1);
        Field field155 = new Field(Field.fieldType.NormalField, 34.9, 23.3);
        Field field156 = new Field(Field.fieldType.NormalField, 33.5, 21.1);
        Field field157 = new Field(Field.fieldType.NormalField, 32.5, 18.2);
        Field field158 = new Field(Field.fieldType.NormalField, 32.2, 14.6);
        Field field159 = new Field(Field.fieldType.NormalField, 32.8, 11.3);
        Field field160 = new Field(Field.fieldType.SpecialChargeField, 34.1, 8.4);

        Field field161 = new Field(Field.fieldType.NormalField, 35.9, 6.8);
        Field field162 = new Field(Field.fieldType.NormalField, 37.8, 6.1);
        Field field163 = new Field(Field.fieldType.NormalField, 39.6, 6.4);
        Field field164 = new Field(Field.fieldType.NormalField, 41.4, 7.6);
        Field field165 = new Field(Field.fieldType.NormalField, 43, 9.1);
        Field field166 = new Field(Field.fieldType.NormalField, 44.7, 10.8);
        Field field167 = new Field(Field.fieldType.NormalField, 46.6, 12.8);
        Field field168 = new Field(Field.fieldType.NormalField, 48.5, 14.8);
        Field field169 = new Field(Field.fieldType.NormalField, 50.3, 16.6);
        Field field170 = new Field(Field.fieldType.LadderField, 52.1, 18.3); // Waterfall

        Field field171 = new Field(Field.fieldType.LadderField, 53.8, 20); // Waterfall
        Field field172 = new Field(Field.fieldType.NormalField, 55.5, 21.9);
        Field field173 = new Field(Field.fieldType.NormalField, 57.1, 23.6);
        Field field174 = new Field(Field.fieldType.NormalField, 59.2, 21.6);
        Field field175 = new Field(Field.fieldType.NormalField, 61.5, 19.3);
        Field field176 = new Field(Field.fieldType.NormalField, 63.6, 17);
        Field field177 = new Field(Field.fieldType.LadderField, 66.1, 14.9);
        Field field178 = new Field(Field.fieldType.NormalField, 69, 14.5);
        Field field179 = new Field(Field.fieldType.NormalField, 71.1, 15.8);
        Field field180 = new Field(Field.fieldType.NormalField, 72.8, 18.6);

        Field field181 = new Field(Field.fieldType.NormalField, 73.6, 22.2);
        Field field182 = new Field(Field.fieldType.NormalField, 73.4, 26);
        Field field183 = new Field(Field.fieldType.NormalField, 72.4, 29.7);
        Field field184 = new Field(Field.fieldType.LadderField, 70.6, 32.5);
        Field field185 = new Field(Field.fieldType.NormalField, 68.4, 34.9);
        Field field186 = new Field(Field.fieldType.NormalField, 66.2, 37.1);
        Field field187 = new Field(Field.fieldType.NormalField, 64.2, 39.1);
        Field field188 = new Field(Field.fieldType.NormalField, 66.2, 41.2);
        Field field189 = new Field(Field.fieldType.NormalField, 68.3, 43.4);
        Field field190 = new Field(Field.fieldType.NormalField, 70.5, 45.5);

        Field field191 = new Field(Field.fieldType.NormalField, 72.8, 46.8);
        Field field192 = new Field(Field.fieldType.NormalField, 75.1, 47);
        Field field193 = new Field(Field.fieldType.NormalField, 77.3, 46.4);
        Field field194 = new Field(Field.fieldType.LadderField, 79.5, 44.7);
        Field field195 = new Field(Field.fieldType.NormalField, 81.1, 42.3);

        waterfallFields.add(field170);
        waterfallFields.add(field171);

        fieldListUpperPath.add(field101);
        fieldListUpperPath.add(field102);
        fieldListUpperPath.add(field103);
        fieldListUpperPath.add(field104);
        fieldListUpperPath.add(field105);
        fieldListUpperPath.add(field106);
        fieldListUpperPath.add(field107);
        fieldListUpperPath.add(field108);
        fieldListUpperPath.add(field109);
        fieldListUpperPath.add(field110);

        fieldListUpperPath.add(field111);
        fieldListUpperPath.add(field112);
        fieldListUpperPath.add(field113);
        fieldListUpperPath.add(field114);
        fieldListUpperPath.add(field115);
        fieldListUpperPath.add(field116);
        fieldListUpperPath.add(field117);
        fieldListUpperPath.add(field118);
        fieldListUpperPath.add(field119);
        fieldListUpperPath.add(field120);

        fieldListUpperPath.add(field121);
        fieldListUpperPath.add(field122);
        fieldListUpperPath.add(field123);
        fieldListUpperPath.add(field124);
        fieldListUpperPath.add(field125);
        fieldListUpperPath.add(field126);
        fieldListUpperPath.add(field127);
        fieldListUpperPath.add(field128);
        fieldListUpperPath.add(field129);
        fieldListUpperPath.add(field130);

        fieldListUpperPath.add(field131);
        fieldListUpperPath.add(field132);
        fieldListUpperPath.add(field133);
        fieldListUpperPath.add(field134);
        fieldListUpperPath.add(field135);
        fieldListUpperPath.add(field136);
        fieldListUpperPath.add(field137);
        fieldListUpperPath.add(field138);
        fieldListUpperPath.add(field139);
        fieldListUpperPath.add(field140);

        fieldListUpperPath.add(field141);
        fieldListUpperPath.add(field142);
        fieldListUpperPath.add(field143);
        fieldListUpperPath.add(field144);
        fieldListUpperPath.add(field145);
        fieldListUpperPath.add(field146);
        fieldListUpperPath.add(field147);
        fieldListUpperPath.add(field148);
        fieldListUpperPath.add(field149);
        fieldListUpperPath.add(field150);

        fieldListUpperPath.add(field151);
        fieldListUpperPath.add(field152);
        fieldListUpperPath.add(field153);
        fieldListUpperPath.add(field154);
        fieldListUpperPath.add(field155);
        fieldListUpperPath.add(field156);
        fieldListUpperPath.add(field157);
        fieldListUpperPath.add(field158);
        fieldListUpperPath.add(field159);
        fieldListUpperPath.add(field160);

        fieldListUpperPath.add(field161);
        fieldListUpperPath.add(field162);
        fieldListUpperPath.add(field163);
        fieldListUpperPath.add(field164);
        fieldListUpperPath.add(field165);
        fieldListUpperPath.add(field166);
        fieldListUpperPath.add(field167);
        fieldListUpperPath.add(field168);
        fieldListUpperPath.add(field169);
        fieldListUpperPath.add(field170);

        fieldListUpperPath.add(field171);
        fieldListUpperPath.add(field172);
        fieldListUpperPath.add(field173);
        fieldListUpperPath.add(field174);
        fieldListUpperPath.add(field175);
        fieldListUpperPath.add(field176);
        fieldListUpperPath.add(field177);
        fieldListUpperPath.add(field178);
        fieldListUpperPath.add(field179);
        fieldListUpperPath.add(field180);

        fieldListUpperPath.add(field181);
        fieldListUpperPath.add(field182);
        fieldListUpperPath.add(field183);
        fieldListUpperPath.add(field184);
        fieldListUpperPath.add(field185);
        fieldListUpperPath.add(field186);
        fieldListUpperPath.add(field187);
        fieldListUpperPath.add(field188);
        fieldListUpperPath.add(field189);
        fieldListUpperPath.add(field190);

        fieldListUpperPath.add(field191);
        fieldListUpperPath.add(field192);
        fieldListUpperPath.add(field193);
        fieldListUpperPath.add(field194);
        fieldListUpperPath.add(field195);

        lastFields.add(field195);

        // Fields on shortest path
        List<Field> fieldListMiddlePath = new ArrayList<>();

        Field field201 = new Field(Field.fieldType.NormalField, 48.2, 37.2);
        Field field202 = new Field(Field.fieldType.NormalField, 50.7, 39.6);
        Field field203 = new Field(Field.fieldType.NormalField, 53, 42);
        Field field204 = new Field(Field.fieldType.NormalField, 51.1, 44.7);
        Field field205 = new Field(Field.fieldType.SpecialChargeField, 49.9, 48.5);
        Field field206 = new Field(Field.fieldType.NormalField, 49.5, 53.3);
        Field field207 = new Field(Field.fieldType.NormalField, 50.3, 57.5);
        Field field208 = new Field(Field.fieldType.NormalField, 51.8, 60.7);
        Field field209 = new Field(Field.fieldType.CrossoverField, 53.8, 62.5);
        crossoverField2 = field209;
        Field field210 = new Field(Field.fieldType.LadderField, 55.9, 63.1);

        Field field211 = new Field(Field.fieldType.SpecialChargeField, 58.4, 62.2);
        Field field212 = new Field(Field.fieldType.LadderField, 60.4, 59.5);
        Field field213 = new Field(Field.fieldType.LadderField, 61.7, 55.7);
        Field field214 = new Field(Field.fieldType.SpecialChargeField, 62, 51.4);
        Field field215 = new Field(Field.fieldType.LadderField, 61.4, 47.2);
        Field field216 = new Field(Field.fieldType.NormalField, 59.9, 43.7);
        Field field217 = new Field(Field.fieldType.NormalField, 57.8, 41.1);
        Field field218 = new Field(Field.fieldType.NormalField, 55.9, 38.3);
        Field field219 = new Field(Field.fieldType.NormalField, 56.1, 34.3);
        Field field220 = new Field(Field.fieldType.NormalField, 58.3, 33.4);

        Field field221 = new Field(Field.fieldType.NormalField, 60.2, 35.1);
        Field field222 = new Field(Field.fieldType.NormalField, 62.2, 37.1);

        fieldListMiddlePath.add(field201);
        fieldListMiddlePath.add(field202);
        fieldListMiddlePath.add(field203);
        fieldListMiddlePath.add(field204);
        fieldListMiddlePath.add(field205);
        fieldListMiddlePath.add(field206);
        fieldListMiddlePath.add(field207);
        fieldListMiddlePath.add(field208);
        fieldListMiddlePath.add(field209);
        fieldListMiddlePath.add(field210);

        fieldListMiddlePath.add(field211);
        fieldListMiddlePath.add(field212);
        fieldListMiddlePath.add(field213);
        fieldListMiddlePath.add(field214);
        fieldListMiddlePath.add(field215);
        fieldListMiddlePath.add(field216);
        fieldListMiddlePath.add(field217);
        fieldListMiddlePath.add(field218);
        fieldListMiddlePath.add(field219);
        fieldListMiddlePath.add(field220);

        fieldListMiddlePath.add(field221);
        fieldListMiddlePath.add(field222);

        // Fields on longest path
        List<Field> fieldListLowerPath = new ArrayList<>();

        Field field301 = new Field(Field.fieldType.NormalField, 52.5, 67.1);
        Field field302 = new Field(Field.fieldType.NormalField, 51.1, 71.7);
        Field field303 = new Field(Field.fieldType.NormalField, 50.3, 76.3);
        Field field304 = new Field(Field.fieldType.NormalField, 50, 80.7);
        Field field305 = new Field(Field.fieldType.NormalField, 50.5, 85.6);
        Field field306 = new Field(Field.fieldType.NormalField, 51.9, 89.6);
        Field field307 = new Field(Field.fieldType.SpecialChargeField, 54.2, 92.2);
        Field field308 = new Field(Field.fieldType.NormalField, 57, 93.4);
        Field field309 = new Field(Field.fieldType.NormalField, 59.6, 93.3);
        Field field310 = new Field(Field.fieldType.NormalField, 62.3, 92.1);

        Field field311 = new Field(Field.fieldType.NormalField, 64.6, 90.3);
        Field field312 = new Field(Field.fieldType.LadderField, 66.4, 87.1);
        Field field313 = new Field(Field.fieldType.NormalField, 67.2, 82.9);
        Field field314 = new Field(Field.fieldType.LadderField, 67.2, 78.4);
        Field field315 = new Field(Field.fieldType.NormalField, 66.6, 73.9);
        Field field316 = new Field(Field.fieldType.LadderField, 65.8, 69.6);
        Field field317 = new Field(Field.fieldType.NormalField, 65.7, 65.3);
        Field field318 = new Field(Field.fieldType.NormalField, 66.3, 61.4);
        Field field319 = new Field(Field.fieldType.NormalField, 67.6, 58);
        Field field320 = new Field(Field.fieldType.NormalField, 69.6, 55.8);

        Field field321 = new Field(Field.fieldType.LadderField, 71.9, 54.9);
        Field field322 = new Field(Field.fieldType.NormalField, 74.4, 55.3);
        Field field323 = new Field(Field.fieldType.NormalField, 76.7, 57.1);
        Field field324 = new Field(Field.fieldType.LadderField, 78.6, 60.5);
        Field field325 = new Field(Field.fieldType.NormalField, 79.6, 64.8);
        Field field326 = new Field(Field.fieldType.NormalField, 79.8, 69.1);
        Field field327 = new Field(Field.fieldType.LadderField, 79.7, 73.7);
        Field field328 = new Field(Field.fieldType.NormalField, 79.7, 78.6);
        Field field329 = new Field(Field.fieldType.NormalField, 80.3, 83);
        Field field330 = new Field(Field.fieldType.NormalField, 81.5, 87.2);

        Field field331 = new Field(Field.fieldType.NormalField, 83.5, 90.4);
        Field field332 = new Field(Field.fieldType.LadderField, 86.7, 91.8);
        Field field333 = new Field(Field.fieldType.NormalField, 89.8, 90.8);
        Field field334 = new Field(Field.fieldType.NormalField, 92.1, 88.5);
        Field field335 = new Field(Field.fieldType.NormalField, 93.7, 85.1);
        Field field336 = new Field(Field.fieldType.NormalField, 94.6, 81.3);
        Field field337 = new Field(Field.fieldType.NormalField, 94.9, 77.4);
        Field field338 = new Field(Field.fieldType.NormalField, 94.3, 73.7);
        Field field339 = new Field(Field.fieldType.NormalField, 93.3, 70.3);
        Field field340 = new Field(Field.fieldType.LadderField, 91.9, 67.6);

        Field field341 = new Field(Field.fieldType.NormalField, 90.1, 65.4);
        Field field342 = new Field(Field.fieldType.NormalField, 88.2, 63.5);
        Field field343 = new Field(Field.fieldType.NormalField, 86.4, 61.6);
        Field field344 = new Field(Field.fieldType.NormalField, 84.3, 59.4);
        Field field345 = new Field(Field.fieldType.NormalField, 82.5, 57.1);
        Field field346 = new Field(Field.fieldType.LadderField, 82, 53.2);
        Field field347 = new Field(Field.fieldType.NormalField, 82.7, 49.7);

        fieldListLowerPath.add(field301);
        fieldListLowerPath.add(field302);
        fieldListLowerPath.add(field303);
        fieldListLowerPath.add(field304);
        fieldListLowerPath.add(field305);
        fieldListLowerPath.add(field306);
        fieldListLowerPath.add(field307);
        fieldListLowerPath.add(field308);
        fieldListLowerPath.add(field309);
        fieldListLowerPath.add(field310);

        fieldListLowerPath.add(field311);
        fieldListLowerPath.add(field312);
        fieldListLowerPath.add(field313);
        fieldListLowerPath.add(field314);
        fieldListLowerPath.add(field315);
        fieldListLowerPath.add(field316);
        fieldListLowerPath.add(field317);
        fieldListLowerPath.add(field318);
        fieldListLowerPath.add(field319);
        fieldListLowerPath.add(field320);

        fieldListLowerPath.add(field321);
        fieldListLowerPath.add(field322);
        fieldListLowerPath.add(field323);
        fieldListLowerPath.add(field324);
        fieldListLowerPath.add(field325);
        fieldListLowerPath.add(field326);
        fieldListLowerPath.add(field327);
        fieldListLowerPath.add(field328);
        fieldListLowerPath.add(field329);
        fieldListLowerPath.add(field330);

        fieldListLowerPath.add(field331);
        fieldListLowerPath.add(field332);
        fieldListLowerPath.add(field333);
        fieldListLowerPath.add(field334);
        fieldListLowerPath.add(field335);
        fieldListLowerPath.add(field336);
        fieldListLowerPath.add(field337);
        fieldListLowerPath.add(field338);
        fieldListLowerPath.add(field339);
        fieldListLowerPath.add(field340);

        fieldListLowerPath.add(field341);
        fieldListLowerPath.add(field342);
        fieldListLowerPath.add(field343);
        fieldListLowerPath.add(field344);
        fieldListLowerPath.add(field345);
        fieldListLowerPath.add(field346);
        fieldListLowerPath.add(field347);

        lastFields.add(field347);

        Field win1 = new Field(Field.fieldType.NormalField, 82.4, 29.1);
        Field win2 = new Field(Field.fieldType.NormalField, 84.5, 31.3);
        Field win3 = new Field(Field.fieldType.NormalField, 85.1, 26.1);
        Field win4 = new Field(Field.fieldType.NormalField, 86.4, 28.7);
        Field win5 = new Field(Field.fieldType.NormalField, 88.4, 26.5);
        Field win6 = new Field(Field.fieldType.NormalField, 88.9, 31.1);

        winningFields.add(win1);
        winningFields.add(win2);
        winningFields.add(win3);
        winningFields.add(win4);
        winningFields.add(win5);
        winningFields.add(win6);

        // Adding Fields to the Beginning Path && Upper Path && Upper End Path (101 - 149 & 150 - 187 & 188 - 195)
        for (int i = 0; i < fieldListUpperPath.size(); i++) {
            getBoardGraph().addVertex(fieldListUpperPath.get(i));
        }

        for (int i = 0; i < fieldListUpperPath.size() - 1; i++) {
            if (field149 == fieldListUpperPath.get(i)) {
                getBoardGraph().addEdge(fieldListUpperPath.get(i), fieldListUpperPath.get(i + 1), 500, BoardGraph.edgeType.CrossoverPathOne);
                continue;
            }
            getBoardGraph().addEdge(fieldListUpperPath.get(i), fieldListUpperPath.get(i + 1), 500, BoardGraph.edgeType.NormalEdge);
        }

        // Adding Fields for the Middle Path (201 - 222)
        for (int i = 0; i < fieldListMiddlePath.size(); i++) {
            getBoardGraph().addVertex(fieldListMiddlePath.get(i));
        }

        for (int i = 0; i < fieldListMiddlePath.size() - 1; i++) {
            if (field209 == fieldListMiddlePath.get(i)) {
                getBoardGraph().addEdge(fieldListMiddlePath.get(i), fieldListMiddlePath.get(i + 1), 500, BoardGraph.edgeType.CrossoverPathOne);
                continue;
            }
            getBoardGraph().addEdge(fieldListMiddlePath.get(i), fieldListMiddlePath.get(i + 1), 500, BoardGraph.edgeType.NormalEdge);
        }

        // Adding Fields to the Lower Path (301 - 347)
        for (int i = 0; i < fieldListLowerPath.size(); i++) {
            getBoardGraph().addVertex(fieldListLowerPath.get(i));
        }

        for (int i = 0; i < fieldListLowerPath.size() - 1; i++) {
            getBoardGraph().addEdge(fieldListLowerPath.get(i), fieldListLowerPath.get(i + 1), 500, BoardGraph.edgeType.NormalEdge);
        }

        // Add edges between three main paths

        boardGraph.addOneDirectionalEdgeForward(field149, field201, 500, BoardGraph.edgeType.CrossoverPathTwo);
        boardGraph.addOneDirectionalEdgeBackward(field201, field149, 500, BoardGraph.edgeType.NormalEdge);

        boardGraph.addOneDirectionalEdgeForward(field209, field301, 500, BoardGraph.edgeType.CrossoverPathTwo);
        boardGraph.addOneDirectionalEdgeBackward(field301, field209, 500, BoardGraph.edgeType.NormalEdge);

        boardGraph.addEdge(field222, field187, 500, BoardGraph.edgeType.NormalEdge);

        // Add normal edges from spawn area to first field
        for (Player player : playerList) {
            boardGraph.addVertex(player.getCurrentField());
            switch (player.getName()) {
                case "Diva O'Hara" -> boardGraph.addOneDirectionalEdgeForward(player.getCurrentField(), field101, 2100, BoardGraph.edgeType.NormalEdge);
                case "Y'Olanda" -> boardGraph.addOneDirectionalEdgeForward(player.getCurrentField(), field101, 1500, BoardGraph.edgeType.NormalEdge);
                case "Kidd'O" -> boardGraph.addOneDirectionalEdgeForward(player.getCurrentField(), field101, 1000, BoardGraph.edgeType.NormalEdge);
                case "Mint'O Lint" -> boardGraph.addOneDirectionalEdgeForward(player.getCurrentField(), field101, 2000, BoardGraph.edgeType.NormalEdge);
                case "Brooke O'Let" -> boardGraph.addOneDirectionalEdgeForward(player.getCurrentField(), field101, 1400, BoardGraph.edgeType.NormalEdge);
                case "O'Fitz" -> boardGraph.addOneDirectionalEdgeForward(player.getCurrentField(), field101, 700, BoardGraph.edgeType.NormalEdge);
                default -> boardGraph.addOneDirectionalEdgeForward(player.getCurrentField(), field101, 500, BoardGraph.edgeType.NormalEdge);
            }
        }

        // Add ladder edges between the necessary fields (going from the main path)
        getBoardGraph().addOneDirectionalEdgeForward(field107, field117, 3000, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field121, field104, 1700, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field125, field140, 1700, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field127, field135, 2300, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field143, field124, 1250, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field154, field165, 2150, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field170, field202, 700, BoardGraph.edgeType.LadderEdge);    // water fall
        getBoardGraph().addOneDirectionalEdgeForward(field171, field203, 700, BoardGraph.edgeType.LadderEdge);    // water fall
        getBoardGraph().addOneDirectionalEdgeForward(field177, field182, 1800, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field184, field174, 2500, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field194, field322, 1500, BoardGraph.edgeType.LadderEdge);
        // Add ladder edges between the necessary fields (going from the shortest path)
        getBoardGraph().addOneDirectionalEdgeForward(field210, field203, 2400, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field212, field204, 2400, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field213, field204, 2400, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field215, field206, 2400, BoardGraph.edgeType.LadderEdge);
        // Add ladder edges between the necessary fields (going from the longest path)
        getBoardGraph().addOneDirectionalEdgeForward(field312, field304, 3100, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field314, field330, 2900, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field316, field301, 2600, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field321, field189, 1400, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field324, field318, 2400, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field327, field344, 1800, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field332, field337, 2500, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field340, field328, 2600, BoardGraph.edgeType.LadderEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field346, field322, 1500, BoardGraph.edgeType.LadderEdge);


        getBoardGraph().addVertex(win1);
        getBoardGraph().addVertex(win2);
        getBoardGraph().addVertex(win3);
        getBoardGraph().addVertex(win4);
        getBoardGraph().addVertex(win5);
        getBoardGraph().addVertex(win6);

        getBoardGraph().addOneDirectionalEdgeForward(field195, win1, 500, BoardGraph.edgeType.NormalEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field195, win2, 500, BoardGraph.edgeType.NormalEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field195, win3, 500, BoardGraph.edgeType.NormalEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field195, win4, 500, BoardGraph.edgeType.NormalEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field195, win5, 500, BoardGraph.edgeType.NormalEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field195, win6, 500, BoardGraph.edgeType.NormalEdge);

       // Add normal edges from last fields to winning area

        getBoardGraph().addOneDirectionalEdgeForward(field347, win1, 500, BoardGraph.edgeType.NormalEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field347, win2, 500, BoardGraph.edgeType.NormalEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field347, win3, 500, BoardGraph.edgeType.NormalEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field347, win4, 500, BoardGraph.edgeType.NormalEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field347, win5, 500, BoardGraph.edgeType.NormalEdge);
        getBoardGraph().addOneDirectionalEdgeForward(field347, win6, 500, BoardGraph.edgeType.NormalEdge);
      
        // TODO Testing
        /*
        for (Player player : playerList) {
            player.setCurrentField(field208);
        }
         */
    }

    /*
    This method is meant to be called when a player lands at a crossover
    The arrows for the user to select a path are created and placed on screen
    Normal user input (UP / DOWN / SPACE) is locked during this event
    After an arrow was clicked with the mouse they disappear, and the method to further move the player along the chosen path is called
     */
    public void selectPathEvent(int hopsLeft, Player player) {

        // Creating the arrows with the image and setting their size and opacity
        ImageView pathOneArrow = new ImageView("images/gameboard_screen/Game_Crossover_Arrow.PNG");
        ImageView pathTwoArrow = new ImageView("images/gameboard_screen/Game_Crossover_Arrow.PNG");
        ImageView dieBG = new ImageView("images/gameboard_screen/Game_Die_BG.PNG");

        pathOneArrow.setFitWidth(55);
        pathOneArrow.setPreserveRatio(true);
        pathOneArrow.setOpacity(0.85);

        pathTwoArrow.setFitWidth(55);
        pathTwoArrow.setPreserveRatio(true);
        pathTwoArrow.setOpacity(0.85);

        dieBG.setFitWidth(50);
        dieBG.setPreserveRatio(true);
        dieBG.setOpacity(0.85);

        String turns = String.valueOf(hopsLeft);
        Text remainingTurns = new Text(turns);
        remainingTurns.setFont(Font.font(TheOs.VARELA.getFamily(), 35));
        remainingTurns.setFill(TheOs.BROWN);

        remainingTurns.setTranslateX(player.getCurrentField().getX() - 11);
        remainingTurns.setTranslateY(player.getCurrentField().getY() - 63);
        dieBG.setTranslateX(player.getCurrentField().getX() - 25);
        dieBG.setTranslateY(player.getCurrentField().getY() - 100);

        // Positioning the arrows
        if (player.getCurrentField() == crossoverField1) { // position the arrows at the first crossover
            // first path option: left
            pathOneArrow.setRotate(-55);
            pathOneArrow.setTranslateX(562);
            pathOneArrow.setTranslateY(187);

            // second path option: right
            pathTwoArrow.setRotate(123);
            pathTwoArrow.setTranslateX(695);
            pathTwoArrow.setTranslateY(265);
        } else if (player.getCurrentField() == crossoverField2) { // position the arrows at the second crossover
            // first path option: right
            pathOneArrow.setRotate(70);
            pathOneArrow.setTranslateX(815);
            pathOneArrow.setTranslateY(445);

            // second path option: down
            pathTwoArrow.setRotate(195);
            pathTwoArrow.setTranslateX(705);
            pathTwoArrow.setTranslateY(530);
        } else { // this cant really happen as selectPathEvent will only be called when a player is on a crossing field
            System.out.println("Invalid Location");
        }

        // Creating the idle animation of the arrows
        TranslateTransition idleArrowOne = new TranslateTransition(Duration.millis(600), pathOneArrow);
        idleArrowOne.setByY(-5);
        idleArrowOne.setCycleCount(Animation.INDEFINITE);
        idleArrowOne.setAutoReverse(true);
        idleArrowOne.play();

        FadeTransition fadeArrowOne = new FadeTransition(Duration.millis(200), pathOneArrow);
        fadeArrowOne.setFromValue(0);
        fadeArrowOne.setToValue(1);

        TranslateTransition idleArrowTwo = new TranslateTransition(Duration.millis(600), pathTwoArrow);
        idleArrowTwo.setByY(-5);
        idleArrowTwo.setCycleCount(Animation.INDEFINITE);
        idleArrowTwo.setAutoReverse(true);
        idleArrowTwo.play();

        mainLayout.getChildren().addAll(pathOneArrow, pathTwoArrow, dieBG, remainingTurns);

        // on mouse press the target arrow gets highlighted and on release both are removed form the layout and the rest of the move is done via crossingManager()
        pathOneArrow.setOnMousePressed(event -> {
            pathOneArrow.setOpacity(0.5);
            //pathOneArrow.setScaleX(1.4);
            //pathOneArrow.setScaleY(1.4);


        });
        pathOneArrow.setOnMouseReleased(event -> {
            pathOneArrow.setOpacity(1);
            mainLayout.getChildren().removeAll(pathOneArrow, pathTwoArrow, dieBG, remainingTurns);
            movePlayer(player, hopsLeft, BoardGraph.edgeType.CrossoverPathOne);
        });

        pathTwoArrow.setOnMousePressed(event -> {
            pathTwoArrow.setOpacity(0.5);
            //pathTwoArrow.setScaleX(1.4);
            //pathTwoArrow.setScaleY(1.4);
        });
        pathTwoArrow.setOnMouseReleased(event -> {
            pathTwoArrow.setOpacity(1);
            mainLayout.getChildren().removeAll(pathOneArrow, pathTwoArrow, dieBG, remainingTurns);
            movePlayer(player, hopsLeft, BoardGraph.edgeType.CrossoverPathTwo);
        });
    }

    /*
    Rolls the appropriate die of the player depending on which one is selected at the diceUI
    Calls animateRolledNumber method of diceUI and the movePlayer method
    Updates the special die charges, so it can be seen when the player used the special die
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

        diceUI.updateSpecialCharges(player);
        diceUI.animateRolledNumber(player, rolled);

        movePlayer(player, rolled);
    }

    /*
    Moves player from current field to desired field.
    Method accesses the list of fields and based on the indexes of all fields from start to end builds the path the character will move along.
    Loads created path and character into PathTransitions and plays the animation.
    Returns nothing
    */
    public void movePlayer(Player player, int fieldsToMove){
        movePlayer(player,fieldsToMove,null);
    }
    public void movePlayer(Player player, int fieldsToMove, BoardGraph.edgeType typeOfEdge) {
        Field startingField = player.getCurrentField(); // The Field where the player starts from when the method gets called.

        player.playWalk();  // Begins Player Animation

        // Gets SequentialPath from the Graph and plays it
        SequentialTransition sequentialTransition = boardGraph.getSequentialAnimationAndMakeMove(player, fieldsToMove, animationOffsetX, animationOffsetY, this, typeOfEdge);
        sequentialTransition.setNode(player.getImageView());
        sequentialTransition.play();

        sequentialTransition.setOnFinished(event -> {
            // Check if the new field of the player is a specialChargeField
            if (player.getCurrentField().getType() == Field.fieldType.SpecialChargeField) {
                player.getSpecialDie().addCharge();
                diceUI.updateSpecialCharges(player);
                this.playChargeAddedAnimation();
            }

            // TODO: Improve?
            if (player.getCurrentField().getType() != Field.fieldType.CrossoverField && typeOfEdge == null || typeOfEdge != null) {        // Checks if the methode has the first return of the crossing.
                player.increaseTurns();
                diceUI.switchPlayerTurn(this);

                // the current player is moved to the end of the list
                playerList.add(playerList.get(0));
                playerList.remove(0);
            }

            player.playIdle();
            if (winningFields.contains(player.getCurrentField())) {
                player.playSwim();
            }
        });
    }

    /*
    Meant to play when someone lands on a special field
    Shows "Special Die Charges +1" animated on the screen
    Returns nothing
     */
    public void playChargeAddedAnimation() {
        Text text = new Text("Special Die Charges +1");
        text.setFill(TheOs.BROWN);
        text.setFont(Font.font(TheOs.VARELA.getFamily(), 65));
        /* Adding a white stroke to the text makes it look better and more legible,
        but the animation becomes really laggy for some reason (maybe only on my pc):

        text.setStroke(Color.WHITE);
        text.setStrokeWidth(5);
        text.setStrokeType(StrokeType.OUTSIDE);
         */

        text.setX(370);
        text.setY(350);
        mainLayout.getChildren().add(text);

        TranslateTransition translate = new TranslateTransition(Duration.millis(1000), text);
        translate.setByY(-60);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), text);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1);

        PauseTransition pause = new PauseTransition(Duration.millis(500)); // brief pause so the text is easier to read

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), text);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0.0);

        SequentialTransition fadeInAndOut = new SequentialTransition(fadeIn, pause, fadeOut);

        ParallelTransition parallelTransition = new ParallelTransition(translate, fadeInAndOut);
        parallelTransition.play();
        parallelTransition.setOnFinished(actionEvent -> mainLayout.getChildren().remove(text));
    }

    /*
    Method that checks, whether a player has finished the game and are then added to a list for the ranking
    also removes the edge from the last field to a WinningField, if the winningField is already in use
     */
    public boolean addFinishedPlayer(Player player) {
        Field lastField = boardGraph.hopCountTraversal(player.getCurrentField(), Integer.MAX_VALUE, null);

            for (int i = 0; i < this.getWinningFields().size(); i++) {
                if (player.getCurrentField().getX()  == getWinningFields().get(i).getX()
                        && player.getCurrentField().getY() == getWinningFields().get(i).getY()) {
                        boardGraph.removeEdge(lastFields.get(0), getWinningFields().get(i));
                        boardGraph.removeEdge(lastFields.get(1), getWinningFields().get(i));
                        getWinningFields().remove(i);
                }
            }

        if (lastField != null && lastField.getType() != Field.fieldType.LadderField) {
                // Player has reached or passed the last field
                finishedPlayers.add(player);
                return true;
        }
        return false;
    }

    /*
    Special Methode that gets called by the Click on the Arrows at a crossing.
     */
    /*
    public void crossingManager(int fieldsToMove, Player player, BoardGraph.edgeType edgeType) {
        player.playWalk();
        SequentialTransition sequentialTransition =boardGraph.getSequentialAnimationAndMakeMove(player,fieldsToMove,animationOffsetX,animationOffsetY,this,edgeType);
        sequentialTransition.setNode(player.getImageView());
        sequentialTransition.play();

        sequentialTransition.setOnFinished(event -> {
            // Check if the new field of the player is a specialChargeField
            if (player.getCurrentField().getType() == Field.fieldType.SpecialChargeField) {
                player.getSpecialDie().addCharge();
                diceUI.updateSpecialCharges(player);
                this.playChargeAddedAnimation();
            }
            player.increaseTurns();
            diceUI.switchPlayerTurn(this);
            player.playIdle();

            // the current player is moved to the end of the list (This gets done when the first transition finishes)
            playerList.add(playerList.get(0));
            playerList.remove(0);
        });

    }
    */
}