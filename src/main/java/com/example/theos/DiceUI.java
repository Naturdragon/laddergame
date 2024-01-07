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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceUI extends AnchorPane {

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
    static final Text PRESS_TO_ROLL_TEXT = new Text("Press to roll");

    private ImageView nextPlayer1;
    private ImageView nextPlayer2;
    private ImageView nextPlayer3;
    private ImageView nextPlayer4;
    private ImageView nextPlayer5;
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

    private state uiState;

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

        SPACE_BUTTON_LABEL.setFont(Application.CUSTOM_FONT_VARELA);
        SPACE_BUTTON_LABEL.setFill(Application.BROWN);

        PRESS_TO_ROLL_TEXT.setFont(Application.CUSTOM_FONT_VARELA);
        PRESS_TO_ROLL_TEXT.setFill(Application.BROWN);

        NORMAL_DIE_BG.setFitWidth(224);
        NORMAL_DIE_BG.setPreserveRatio(true);

        NORMAL_DIE_USAGES.setFont(Application.CUSTOM_FONT_VARELA);
        NORMAL_DIE_USAGES.setFill(Application.BROWN);

        NORMAL_DIE_0.setFont(Application.CUSTOM_FONT_VARELA);
        NORMAL_DIE_0.setFill(Application.BROWN);
        NORMAL_DIE_1.setFont(Application.CUSTOM_FONT_VARELA);
        NORMAL_DIE_1.setFill(Application.BROWN);
        NORMAL_DIE_2.setFont(Application.CUSTOM_FONT_VARELA);
        NORMAL_DIE_2.setFill(Application.BROWN);
        NORMAL_DIE_3.setFont(Application.CUSTOM_FONT_VARELA);
        NORMAL_DIE_3.setFill(Application.BROWN);
        NORMAL_DIE_4.setFont(Application.CUSTOM_FONT_VARELA);
        NORMAL_DIE_4.setFill(Application.BROWN);
        NORMAL_DIE_5.setFont(Application.CUSTOM_FONT_VARELA);
        NORMAL_DIE_5.setFill(Application.BROWN);

        nextPlayer1 = new ImageView(new Image("images/gameboard_screen/Game_O_2.png"));
        nextPlayer1.setFitWidth(44);
        nextPlayer1.setPreserveRatio(true);
        nextPlayer2 = new ImageView(new Image("images/gameboard_screen/Game_O_3.png"));
        nextPlayer2.setFitWidth(44);
        nextPlayer2.setPreserveRatio(true);
        nextPlayer3 = new ImageView(new Image("images/gameboard_screen/Game_O_4.png"));
        nextPlayer3.setFitWidth(44);
        nextPlayer3.setPreserveRatio(true);
        nextPlayer4 = new ImageView(new Image("images/gameboard_screen/Game_O_5.png"));
        nextPlayer4.setFitWidth(44);
        nextPlayer4.setPreserveRatio(true);
        nextPlayer5 = new ImageView(new Image("images/gameboard_screen/Game_O_6.png"));
        nextPlayer5.setFitWidth(44);
        nextPlayer5.setPreserveRatio(true);

        specialDieBG = new ImageView(new Image("images/gameboard_screen/Game_Die_4.png"));
        specialDieBG.setFitWidth(224);
        specialDieBG.setPreserveRatio(true);

        playerPortrait = new ImageView(new Image("images/player_icons/Icon_4.png"));
        playerPortrait.setFitWidth(160);
        playerPortrait.setPreserveRatio(true);

        playerName = new Text("Mint O’Lint");
        playerName.setFont(Application.CUSTOM_FONT_CAVEAT);
        playerName.setFill(Application.BROWN);

        specialDieUsages = new Text("3×");
        specialDieUsages.setFont(Application.CUSTOM_FONT_VARELA);
        specialDieUsages.setFill(Application.BROWN);

        specialDie0 = new Text("1");
        specialDie1 = new Text("1");
        specialDie2 = new Text("2");
        specialDie3 = new Text("2");
        specialDie4 = new Text("2");
        specialDie5 = new Text("7");

        specialDie0.setFont(Application.CUSTOM_FONT_VARELA);
        specialDie0.setFill(Application.MINT_GREEN);
        specialDie1.setFont(Application.CUSTOM_FONT_VARELA);
        specialDie1.setFill(Application.MINT_GREEN);
        specialDie2.setFont(Application.CUSTOM_FONT_VARELA);
        specialDie2.setFill(Application.MINT_GREEN);
        specialDie3.setFont(Application.CUSTOM_FONT_VARELA);
        specialDie3.setFill(Application.MINT_GREEN);
        specialDie4.setFont(Application.CUSTOM_FONT_VARELA);
        specialDie4.setFill(Application.MINT_GREEN);
        specialDie5.setFont(Application.CUSTOM_FONT_VARELA);
        specialDie5.setFill(Application.MINT_GREEN);

        // Creating the layout:

        // Layout for the left side (charactericon and name)
        VBox leftSide = new VBox(0, playerPortrait, playerName);
        leftSide.setAlignment(Pos.CENTER);

        // Layout for the right side (nextPlayerIcons, dice + text, spacebar-button, selector)
        HBox nextPlayerIcons = new HBox(15);
        nextPlayerIcons.getChildren().addAll(nextPlayer1, nextPlayer2, nextPlayer3, nextPlayer4, nextPlayer5);

        HBox normalDieTexts = new HBox(23, NORMAL_DIE_USAGES, NORMAL_DIE_0, NORMAL_DIE_1, NORMAL_DIE_2,
                NORMAL_DIE_3, NORMAL_DIE_4, NORMAL_DIE_5);
        HBox.setMargin(NORMAL_DIE_0, new Insets(0, 0, 0, 6.5));

        HBox specialDieTexts = new HBox(23, specialDieUsages, specialDie0, specialDie1, specialDie2,
                specialDie3, specialDie4, specialDie5);
        HBox.setMargin(specialDie0, new Insets(0, 0, 0, 1.5));

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
    public void updateUI(Player nextPlayer) {
        playerName.setText(nextPlayer.getName());

        specialDieUsages.setText(nextPlayer.getSpecialDie().getCharge() + "×");

        specialDie0.setText(String.valueOf(nextPlayer.getSpecialDie().getDice()[0]));
        specialDie1.setText(String.valueOf(nextPlayer.getSpecialDie().getDice()[1]));
        specialDie2.setText(String.valueOf(nextPlayer.getSpecialDie().getDice()[2]));
        specialDie3.setText(String.valueOf(nextPlayer.getSpecialDie().getDice()[3]));
        specialDie4.setText(String.valueOf(nextPlayer.getSpecialDie().getDice()[4]));
        specialDie5.setText(String.valueOf(nextPlayer.getSpecialDie().getDice()[5]));

        specialDie0.setFill(Application.MINT_GREEN);
        specialDie1.setFill(Application.MINT_GREEN);
        specialDie2.setFill(Application.MINT_GREEN);
        specialDie3.setFill(Application.MINT_GREEN);
        specialDie4.setFill(Application.MINT_GREEN);
        specialDie5.setFill(Application.MINT_GREEN);

        NORMAL_DIE_0.setFill(Application.BROWN);
        NORMAL_DIE_1.setFill(Application.BROWN);
        NORMAL_DIE_2.setFill(Application.BROWN);
        NORMAL_DIE_3.setFill(Application.BROWN);
        NORMAL_DIE_4.setFill(Application.BROWN);
        NORMAL_DIE_5.setFill(Application.BROWN);
    }

    /*
    Animates the DiceUI moving down and back up and updates all the data shown using updateUI()
    Returns nothing
     */
    public void animatePlayerSwitch(Player nextPlayer) {
        TranslateTransition translateDown = new TranslateTransition(Duration.millis(1000), this);
        translateDown.setByY(this.getHeight());

        TranslateTransition translateUp = new TranslateTransition(Duration.millis(1000), this);
        translateUp.setByY(-this.getHeight());

        translateDown.play();
        translateDown.setOnFinished(event -> {
            updateUI(nextPlayer);
            selectNormalDie();
            translateUp.play();
            translateUp.setOnFinished(event1 -> Application.waitingForUserInput = true);
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

        ScaleTransition scale = new ScaleTransition(Duration.millis(200));
        scale.setToX(3);
        scale.setToY(3);

        FadeTransition fade = new FadeTransition(Duration.millis(400));
        fade.setToValue(0.0);

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
