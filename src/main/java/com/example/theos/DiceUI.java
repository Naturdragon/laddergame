package com.example.theos;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceUI extends AnchorPane {

    // accessing the custom font fix: https://stackoverflow.com/questions/30245085/javafx-embed-custom-font-not-working
    static final Font CUSTOM_FONT_VARELA = Font.loadFont(TheOs.class.getClassLoader().getResourceAsStream("fonts/VarelaRound-Regular.ttf"), 22);
    static final Font CUSTOM_FONT_CAVEAT = Font.loadFont(TheOs.class.getClassLoader().getResourceAsStream("fonts/Caveat-SemiBold.ttf"), 25);
    static final ImageView BACKGROUND = new ImageView(new Image("images/gameboard_screen/Game_BG_Player.png"));
    static final ImageView SELECTION_ARROW = new ImageView(new Image("images/gameboard_screen/Game_Die_Select.png"));
    static final ImageView SPACE_BAR_BG = new ImageView(new Image("images/option_button_extras/Button_Space_Small.PNG"));
    static final ImageView NORMAL_DIE_BG = new ImageView(new Image("images/gameboard_screen/Game_Die_0.png"));
    static final Text NORMAL_DIE_USAGES = new Text("∞");
    static final Text NORMAL_DIE_0 = new Text("1");
    static final Text NORMAL_DIE_1 = new Text("2");
    static final Text NORMAL_DIE_2 = new Text("3");
    static final Text NORMAL_DIE_3 = new Text("4");
    static final Text NORMAL_DIE_4 = new Text("5");
    static final Text NORMAL_DIE_5 = new Text("6");
    static final Text SPACE_BUTTON_LABEL = new Text("SPACE");
    static final Text PRESS_TO_ROLL_TEXT = new Text("Press to Roll");

    private List<ImageView> nextPlayerIconsList = new ArrayList<>();
    private ImageView specialDieBG;
    private ImageView playerPortrait;
    private Text specialDieUsages;
    private Text playerName;
    private Text specialDie0;
    private Text specialDie1;
    private Text specialDie2;
    private Text specialDie3;
    private Text specialDie4;
    private Text specialDie5;

    private state uiState; // used to be able to indentify which die to use in playerTurn() method

    /*
    Default constructor creates the interface and layouts it
    the content is filled with Mint'O Lint as a character for demonstration purposes
     */
    public DiceUI() {
        BACKGROUND.setFitWidth(550);
        BACKGROUND.setPreserveRatio(true);

        SELECTION_ARROW.setFitWidth(30);
        SELECTION_ARROW.setPreserveRatio(true);
        SELECTION_ARROW.setX(496);
        SELECTION_ARROW.setY(74);
        animateSelectionArrow();

        SPACE_BAR_BG.setFitWidth(152);
        SPACE_BAR_BG.setPreserveRatio(true);

        SPACE_BUTTON_LABEL.setFont(CUSTOM_FONT_VARELA);
        SPACE_BUTTON_LABEL.setFill(TheOs.BROWN);

        PRESS_TO_ROLL_TEXT.setFont(CUSTOM_FONT_VARELA);
        PRESS_TO_ROLL_TEXT.setFill(TheOs.BROWN);

        NORMAL_DIE_BG.setFitWidth(224);
        NORMAL_DIE_BG.setPreserveRatio(true);

        NORMAL_DIE_USAGES.setFont(CUSTOM_FONT_VARELA);
        NORMAL_DIE_USAGES.setFill(TheOs.BROWN);

        NORMAL_DIE_0.setFont(CUSTOM_FONT_VARELA);
        NORMAL_DIE_0.setFill(TheOs.BROWN);
        NORMAL_DIE_1.setFont(CUSTOM_FONT_VARELA);
        NORMAL_DIE_1.setFill(TheOs.BROWN);
        NORMAL_DIE_2.setFont(CUSTOM_FONT_VARELA);
        NORMAL_DIE_2.setFill(TheOs.BROWN);
        NORMAL_DIE_3.setFont(CUSTOM_FONT_VARELA);
        NORMAL_DIE_3.setFill(TheOs.BROWN);
        NORMAL_DIE_4.setFont(CUSTOM_FONT_VARELA);
        NORMAL_DIE_4.setFill(TheOs.BROWN);
        NORMAL_DIE_5.setFont(CUSTOM_FONT_VARELA);
        NORMAL_DIE_5.setFill(TheOs.BROWN);

        for (int i = 0; i < 5; i++) {
            ImageView nextPlayer = new ImageView();
            nextPlayer.setFitWidth(44);
            nextPlayer.setPreserveRatio(true);

            nextPlayerIconsList.add(nextPlayer);
        }

        specialDieBG = new ImageView();
        specialDieBG.setFitWidth(224);
        specialDieBG.setPreserveRatio(true);

        playerPortrait = new ImageView();
        playerPortrait.setFitWidth(160);
        playerPortrait.setPreserveRatio(true);

        playerName = new Text();
        playerName.setFont(CUSTOM_FONT_CAVEAT);
        playerName.setFill(TheOs.BROWN);

        specialDieUsages = new Text();
        specialDieUsages.setFont(CUSTOM_FONT_VARELA);
        specialDieUsages.setFill(TheOs.BROWN);

        specialDie0 = new Text();
        specialDie1 = new Text();
        specialDie2 = new Text();
        specialDie3 = new Text();
        specialDie4 = new Text();
        specialDie5 = new Text();

        specialDie0.setFont(CUSTOM_FONT_VARELA);
        specialDie1.setFont(CUSTOM_FONT_VARELA);
        specialDie2.setFont(CUSTOM_FONT_VARELA);
        specialDie3.setFont(CUSTOM_FONT_VARELA);
        specialDie4.setFont(CUSTOM_FONT_VARELA);
        specialDie5.setFont(CUSTOM_FONT_VARELA);

        // Creating the layout:

        // Layout for the left side (charactericon and name)
        VBox leftSide = new VBox(0, playerPortrait, playerName);
        leftSide.setAlignment(Pos.CENTER);

        // Layout for the right side (nextPlayerIcons, dice + text, spacebar-button, selector)
        HBox nextPlayerIcons = new HBox(15);
        nextPlayerIcons.getChildren().addAll(nextPlayerIconsList.get(0), nextPlayerIconsList.get(1), nextPlayerIconsList.get(2), nextPlayerIconsList.get(3), nextPlayerIconsList.get(4));

        HBox normalDieTexts = new HBox(23, NORMAL_DIE_USAGES, NORMAL_DIE_0, NORMAL_DIE_1, NORMAL_DIE_2,
                NORMAL_DIE_3, NORMAL_DIE_4, NORMAL_DIE_5);
        HBox.setMargin(NORMAL_DIE_0, new Insets(0, 0, 0, 6.5));

        HBox specialDieTexts = new HBox(23, specialDieUsages, specialDie0, specialDie1, specialDie2,
                specialDie3, specialDie4, specialDie5);
        HBox.setMargin(specialDieUsages, new Insets(0, 1.5, 0, 0));

        StackPane spaceButton = new StackPane(SPACE_BAR_BG, SPACE_BUTTON_LABEL);

        VBox rightSide = new VBox(5, nextPlayerIcons, normalDieTexts, specialDieTexts, spaceButton);
        VBox.setMargin(normalDieTexts, new Insets(25, 0, 0, 3));
        VBox.setMargin(specialDieTexts, new Insets(15, 0, 10, 3));
        VBox.setMargin(spaceButton, new Insets(0, 0, 0, -130));

        this.getChildren().addAll(BACKGROUND, NORMAL_DIE_BG, specialDieBG, leftSide, rightSide, SELECTION_ARROW, PRESS_TO_ROLL_TEXT);
        // set anchor for the right side of the layout
        AnchorPane.setTopAnchor(rightSide, 0.0);
        AnchorPane.setLeftAnchor(rightSide, 222.0);
        // set anchor for the left side of the layout
        AnchorPane.setTopAnchor(leftSide, 18.0);
        AnchorPane.setLeftAnchor(leftSide, 23.0);
        // set anchor for the text label "Press to roll"
        AnchorPane.setRightAnchor(PRESS_TO_ROLL_TEXT, 43.0);
        AnchorPane.setBottomAnchor(PRESS_TO_ROLL_TEXT, 17.0);
        // set anchor for the background of the normaldie and specialdie images
        AnchorPane.setRightAnchor(NORMAL_DIE_BG, 63.0);
        AnchorPane.setBottomAnchor(NORMAL_DIE_BG, 104.0);
        AnchorPane.setRightAnchor(specialDieBG, 63.0);
        AnchorPane.setBottomAnchor(specialDieBG, 57.0);

        this.setPrefSize(200, 200);

        selectNormalDie();
    }

    public state getUiState() {
        return uiState;
    }

    /*
    Updates the data shown by the UI with the new player currently playing
    Returns nothing
     */
    public void updateUI(List<Player> playerList) {
        playerName.setText(playerList.get(0).getName());

        specialDieUsages.setText(playerList.get(0).getSpecialDie().getCharge() + "×");

        specialDie0.setText(String.valueOf(playerList.get(0).getSpecialDie().getDice()[0]));
        specialDie1.setText(String.valueOf(playerList.get(0).getSpecialDie().getDice()[1]));
        specialDie2.setText(String.valueOf(playerList.get(0).getSpecialDie().getDice()[2]));
        specialDie3.setText(String.valueOf(playerList.get(0).getSpecialDie().getDice()[3]));
        specialDie4.setText(String.valueOf(playerList.get(0).getSpecialDie().getDice()[4]));
        specialDie5.setText(String.valueOf(playerList.get(0).getSpecialDie().getDice()[5]));

        playerPortrait.setImage(new Image(playerList.get(0).getImage()));

        for (int i = 0; i < playerList.size() - 1; i++) {
            nextPlayerIconsList.get(i).setImage(new Image(playerList.get(i + 1).getNextPlayerImagePath()));
        }

        switch (playerList.get(0).getName()) {
            case "Diva O'Hara" -> {
                specialDieBG.setImage(new Image("images/gameboard_screen/Game_Die_1.PNG"));
                specialDie0.setFill(TheOs.DIVA_PINK);
                specialDie1.setFill(TheOs.DIVA_PINK);
                specialDie2.setFill(TheOs.DIVA_PINK);
                specialDie3.setFill(TheOs.DIVA_PINK);
                specialDie4.setFill(TheOs.DIVA_PINK);
                specialDie5.setFill(TheOs.DIVA_PINK);

                HBox.setMargin(specialDie0, new Insets(0, 0, 0, -3.5));
                HBox.setMargin(specialDie1, new Insets(0, 0, 0, -9));
                HBox.setMargin(specialDie2, new Insets(0, 0, 0, -4));
                HBox.setMargin(specialDie3, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie4, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie5, new Insets(0, 0, 0, 0));
            }
            case "Y'Olanda" -> {
                specialDieBG.setImage(new Image("images/gameboard_screen/Game_Die_2.PNG"));
                specialDie0.setFill(TheOs.OLANDA_RED);
                specialDie1.setFill(TheOs.OLANDA_RED);
                specialDie2.setFill(TheOs.OLANDA_RED);
                specialDie3.setFill(TheOs.OLANDA_RED);
                specialDie4.setFill(TheOs.OLANDA_RED);
                specialDie5.setFill(TheOs.OLANDA_RED);

                HBox.setMargin(specialDie0, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie1, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie2, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie3, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie4, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie5, new Insets(0, 0, 0, 0));
            }
            case "Kidd'O" -> {
                specialDieBG.setImage(new Image("images/gameboard_screen/Game_Die_3.PNG"));
                specialDie0.setFill(TheOs.KIDD_YELLOW);
                specialDie1.setFill(TheOs.KIDD_YELLOW);
                specialDie2.setFill(TheOs.KIDD_YELLOW);
                specialDie3.setFill(TheOs.KIDD_YELLOW);
                specialDie4.setFill(TheOs.KIDD_YELLOW);
                specialDie5.setFill(TheOs.KIDD_YELLOW);

                HBox.setMargin(specialDie0, new Insets(0, 0, 0, -3.5));
                HBox.setMargin(specialDie1, new Insets(0, 0, 0, -9));
                HBox.setMargin(specialDie2, new Insets(0, 0, 0, -5));
                HBox.setMargin(specialDie3, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie4, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie5, new Insets(0, 0, 0, 0));
            }
            case "Mint'O Lint" -> {
                specialDieBG.setImage(new Image("images/gameboard_screen/Game_Die_4.PNG"));
                specialDie0.setFill(TheOs.MINT_GREEN);
                specialDie1.setFill(TheOs.MINT_GREEN);
                specialDie2.setFill(TheOs.MINT_GREEN);
                specialDie3.setFill(TheOs.MINT_GREEN);
                specialDie4.setFill(TheOs.MINT_GREEN);
                specialDie5.setFill(TheOs.MINT_GREEN);

                HBox.setMargin(specialDie0, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie1, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie2, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie3, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie4, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie5, new Insets(0, 0, 0, 0));
            }
            case "Brooke O'Let" -> {
                specialDieBG.setImage(new Image("images/gameboard_screen/Game_Die_5.PNG"));
                specialDie0.setFill(TheOs.BROOKE_BLUE);
                specialDie1.setFill(TheOs.BROOKE_BLUE);
                specialDie2.setFill(TheOs.BROOKE_BLUE);
                specialDie3.setFill(TheOs.BROOKE_BLUE);
                specialDie4.setFill(TheOs.BROOKE_BLUE);
                specialDie5.setFill(TheOs.BROOKE_BLUE);

                HBox.setMargin(specialDie0, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie1, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie2, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie3, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie4, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie5, new Insets(0, 0, 0, 0));
            }
            case "O'Fitz" -> {
                specialDieBG.setImage(new Image("images/gameboard_screen/Game_Die_6.PNG"));
                specialDie0.setFill(TheOs.FITZ_PURPLE);
                specialDie1.setFill(TheOs.FITZ_PURPLE);
                specialDie2.setFill(TheOs.FITZ_PURPLE);
                specialDie3.setFill(TheOs.FITZ_PURPLE);
                specialDie4.setFill(TheOs.FITZ_PURPLE);
                specialDie5.setFill(TheOs.FITZ_PURPLE);

                HBox.setMargin(specialDie0, new Insets(0, 0, 0, -4));
                HBox.setMargin(specialDie1, new Insets(0, 0, 0, -5));
                HBox.setMargin(specialDie2, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie3, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie4, new Insets(0, 0, 0, 0));
                HBox.setMargin(specialDie5, new Insets(0, 0, 0, 0));
            }
            default -> System.out.println("Character not found"); // only the names in the above ifs should be possible
        }
    }

    /*
    Animates the DiceUI moving down and back up and updates all the data shown using updateUI()
    !! THIS METHOD ALSO CHECKS IF THE GAME IS OVER (ALL PLAYERS REACHED THE LAST FIELD) !!
    Returns nothing
     */
    public void switchPlayerTurn(GameBoard gameBoard) {
        TranslateTransition translateDown = new TranslateTransition(Duration.millis(800), this);
        translateDown.setByY(this.getHeight());

        TranslateTransition translateUp = new TranslateTransition(Duration.millis(800), this);
        translateUp.setByY(-this.getHeight());

        translateDown.play();
        translateDown.setOnFinished(event -> {

            // check if the current player reached the final field
            if (gameBoard.getPlayerList().get(gameBoard.getPlayerList().size() - 1).getCurrentField() == gameBoard.getWinningFields().get(0)) {
                gameBoard.addFinishedPlayer(gameBoard.getPlayerList().get(gameBoard.getPlayerList().size() - 1));
                gameBoard.getPlayerList().remove(gameBoard.getPlayerList().size() - 1);
            }

            // if gameboards playerList is empty then all players are finished => winning screen is started
            if (gameBoard.getPlayerList().isEmpty()) {
                SceneController.showWinningScreen(gameBoard.getFinishedPlayers());
                return;
            }

            System.out.println(gameBoard.getPlayerList().get(gameBoard.getPlayerList().size() - 1).getName()); // TODO only for debugging

            updateUI(gameBoard.getPlayerList());
            selectNormalDie();
            translateUp.play();
            translateUp.setOnFinished(event1 -> TheOs.waitingForUserInput = true);
        });
    }

    // updates the ui to show the normal die being selected, returns nothing
    public void selectNormalDie() {
        uiState = state.NormalDieSelected;
        SELECTION_ARROW.setY(74);
        NORMAL_DIE_BG.setOpacity(1);
        specialDieBG.setOpacity(0.5);
    }

    // updates the ui to show the special die being selected, returns nothing
    public void selectSpecialDie() {
        uiState = state.SpecialDieSelected;
        SELECTION_ARROW.setY(121);
        NORMAL_DIE_BG.setOpacity(0.5);
        specialDieBG.setOpacity(1);
    }

    // animates the selection arrow moving up and down (for indefinite duration)
    public void animateSelectionArrow() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), SELECTION_ARROW);
        transition.setByY(-3);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
    }

    /*
    Shows the user which number was rolled
    the rolled number is loaded into a ParallelTransition consisting of a Scale- and a FadeTransition
    Returns nothing (the animation is played from within the method)
     */
    public void animateRolledNumber(Player player, int numberRolled) {
        // first part of the animation is a scaletransition to 3x the original size
        ScaleTransition scale = new ScaleTransition(Duration.millis(300));
        scale.setToX(3);
        scale.setToY(3);
        // second part of the animation is a fadetransition to 0 % opacity
        FadeTransition fade = new FadeTransition(Duration.millis(500));
        fade.setToValue(0.0);
        // the two transitions are played simultaneously
        ParallelTransition parallel = new ParallelTransition(scale, fade);

        if (uiState == state.NormalDieSelected) {
            if (numberRolled == 1) {
                scale.setNode(NORMAL_DIE_0);
                fade.setNode(NORMAL_DIE_0);
            }
            if (numberRolled == 2) {
                scale.setNode(NORMAL_DIE_1);
                fade.setNode(NORMAL_DIE_1);
            }
            if (numberRolled == 3) {
                scale.setNode(NORMAL_DIE_2);
                fade.setNode(NORMAL_DIE_2);
            }
            if (numberRolled == 4) {
                scale.setNode(NORMAL_DIE_3);
                fade.setNode(NORMAL_DIE_3);
            }
            if (numberRolled == 5) {
                scale.setNode(NORMAL_DIE_4);
                fade.setNode(NORMAL_DIE_4);
            }
            if (numberRolled == 6) {
                scale.setNode(NORMAL_DIE_5);
                fade.setNode(NORMAL_DIE_5);
            }
        }

        // this part feels needlessly long but it works:
        if (uiState == state.SpecialDieSelected) {
            int[] dieUsed = player.getSpecialDie().getDice();

            List<Integer> equalsFound = new ArrayList<>();

            int indexOfRolledNumber;

            for (int i = 0; i < dieUsed.length; i++) {
                if (numberRolled == dieUsed[i]) {
                    equalsFound.add(i);
                }
            }

            if (equalsFound.size() > 1) {
                Random random = new Random();
                indexOfRolledNumber = equalsFound.get(random.nextInt(equalsFound.size()));
            } else {
                indexOfRolledNumber = equalsFound.get(0);
            }

            if (indexOfRolledNumber == 0) {
                scale.setNode(specialDie0);
                fade.setNode(specialDie0);
            }
            if (indexOfRolledNumber == 1) {
                scale.setNode(specialDie1);
                fade.setNode(specialDie1);
            }
            if (indexOfRolledNumber == 2) {
                scale.setNode(specialDie2);
                fade.setNode(specialDie2);
            }
            if (indexOfRolledNumber == 3) {
                scale.setNode(specialDie3);
                fade.setNode(specialDie3);
            }
            if (indexOfRolledNumber == 4) {
                scale.setNode(specialDie4);
                fade.setNode(specialDie4);
            }
            if (indexOfRolledNumber == 5) {
                scale.setNode(specialDie5);
                fade.setNode(specialDie5);
            }
        }

        parallel.play();
        parallel.setOnFinished(event -> {
            // again, this could probably be a lot more compact, but it works
            NORMAL_DIE_0.setOpacity(1);
            NORMAL_DIE_0.setScaleY(1);
            NORMAL_DIE_0.setScaleX(1);

            NORMAL_DIE_1.setOpacity(1);
            NORMAL_DIE_1.setScaleY(1);
            NORMAL_DIE_1.setScaleX(1);

            NORMAL_DIE_2.setOpacity(1);
            NORMAL_DIE_2.setScaleY(1);
            NORMAL_DIE_2.setScaleX(1);

            NORMAL_DIE_3.setOpacity(1);
            NORMAL_DIE_3.setScaleY(1);
            NORMAL_DIE_3.setScaleX(1);

            NORMAL_DIE_4.setOpacity(1);
            NORMAL_DIE_4.setScaleY(1);
            NORMAL_DIE_4.setScaleX(1);

            NORMAL_DIE_5.setOpacity(1);
            NORMAL_DIE_5.setScaleY(1);
            NORMAL_DIE_5.setScaleX(1);

            specialDie0.setOpacity(1);
            specialDie0.setScaleY(1);
            specialDie0.setScaleX(1);

            specialDie1.setOpacity(1);
            specialDie1.setScaleY(1);
            specialDie1.setScaleX(1);

            specialDie2.setOpacity(1);
            specialDie2.setScaleY(1);
            specialDie2.setScaleX(1);

            specialDie3.setOpacity(1);
            specialDie3.setScaleY(1);
            specialDie3.setScaleX(1);

            specialDie4.setOpacity(1);
            specialDie4.setScaleY(1);
            specialDie4.setScaleX(1);

            specialDie5.setOpacity(1);
            specialDie5.setScaleY(1);
            specialDie5.setScaleX(1);
        });
    }

    public enum state {
        NormalDieSelected,
        SpecialDieSelected
    }
}

