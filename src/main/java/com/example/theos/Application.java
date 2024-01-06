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

    // accessing the custom font fix: https://stackoverflow.com/questions/30245085/javafx-embed-custom-font-not-working
    static final Font customFont = Font.loadFont(Application.class.getClassLoader().getResourceAsStream("fonts/VarelaRound-Regular.ttf"), 22);

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

        Group group = new Group(gameBoard.getRolledNumberDisplay(), player1.getImageView());

        Pane root = new Pane(group);

        root.setBackground(gameBoard.getBackground()); // setBackground method needs Background object, not BackgroundImage

        // Creating the dice UI with an AnchorPane, which is added to the root pane
        AnchorPane diceUI = createDiceUI();
        diceUI.setTranslateX(40);
        diceUI.setTranslateY(800-215);

        root.getChildren().add(diceUI);

        Scene scene = new Scene(root, sceneWidth, sceneHeight); // ich hab die Größe erstmal auf 1422x800 eingestellt da 1920x1080 für meinen Laptop-Bildschirm zu groß war

        /* Test:
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() { // kann automatisch durch Lambda ersetzt werden, ist dann kürzer
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) { // passiert nur wenn Space gedrückt wird

                    Random randomizer = new Random();

                    int fieldsToMove = randomizer.nextInt(4) + 1;

                    System.out.println(playerList.get(playerWhoseTurnIs).toString() + " rolled " + fieldsToMove); // Ausgabe des Zuges auf der Konsole

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
                gameBoard.playerTurn(player1, playerTestView);
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

    /*
    Creates the diceUI and with the inital values (first player to turn)
    Returns an AnchorPane
     */
    public static AnchorPane createDiceUI() {
        // Colors used for the texts
        Color brown = Color.rgb(120, 98, 68);
        Color mintGreen = Color.rgb(63, 139, 88);

        // Creating all the ImageViews based on the assets and sizing them accordingly
        ImageView background = new ImageView(new Image("images/gameboard_screen/Game_BG_Player.png"));
        background.setFitWidth(550);
        background.setPreserveRatio(true);

        ImageView selectionArrow = new ImageView(new Image("images/gameboard_screen/Game_Die_Select.png"));
        selectionArrow.setFitWidth(30);
        selectionArrow.setPreserveRatio(true);
        selectionArrow.setX(496);
        selectionArrow.setY(73);

        ImageView spaceBar = new ImageView(new Image("images/option_button_extras/Button_Space_Small.PNG"));
        spaceBar.setFitWidth(152);
        spaceBar.setPreserveRatio(true);

        ImageView playerPortrait = new ImageView(new Image("images/player_icons/Icon_4.png"));
        playerPortrait.setFitWidth(160);
        playerPortrait.setPreserveRatio(true);

        ImageView nextPlayer1 = new ImageView(new Image("images/gameboard_screen/Game_O_2.png"));
        nextPlayer1.setFitWidth(44);
        nextPlayer1.setPreserveRatio(true);

        ImageView nextPlayer2 = new ImageView(new Image("images/gameboard_screen/Game_O_3.png"));
        nextPlayer2.setFitWidth(44);
        nextPlayer2.setPreserveRatio(true);

        ImageView nextPlayer3 = new ImageView(new Image("images/gameboard_screen/Game_O_4.png"));
        nextPlayer3.setFitWidth(44);
        nextPlayer3.setPreserveRatio(true);

        ImageView nextPlayer4 = new ImageView(new Image("images/gameboard_screen/Game_O_5.png"));
        nextPlayer4.setFitWidth(44);
        nextPlayer4.setPreserveRatio(true);

        ImageView nextPlayer5 = new ImageView(new Image("images/gameboard_screen/Game_O_6.png"));
        nextPlayer5.setFitWidth(44);
        nextPlayer5.setPreserveRatio(true);

        ImageView normalDieBG = new ImageView(new Image("images/gameboard_screen/Game_Die_0.png"));
        normalDieBG.setFitWidth(224);
        normalDieBG.setPreserveRatio(true);

        ImageView specialDieBG = new ImageView(new Image("images/gameboard_screen/Game_Die_4.png"));
        specialDieBG.setFitWidth(224);
        specialDieBG.setPreserveRatio(true);

        // Creating all the text nodes and setting their font
        Text normalDie0 = new Text("1");
        Text normalDie1 = new Text("2");
        Text normalDie2 = new Text("3");
        Text normalDie3 = new Text("4");
        Text normalDie4 = new Text("5");
        Text normalDie5 = new Text("6");

        normalDie0.setFont(customFont);
        normalDie0.setFill(brown);
        normalDie1.setFont(customFont);
        normalDie1.setFill(brown);
        normalDie2.setFont(customFont);
        normalDie2.setFill(brown);
        normalDie3.setFont(customFont);
        normalDie3.setFill(brown);
        normalDie4.setFont(customFont);
        normalDie4.setFill(brown);
        normalDie5.setFont(customFont);
        normalDie5.setFill(brown);

        Text specialDie0 = new Text("1");
        Text specialDie1 = new Text("1");
        Text specialDie2 = new Text("2");
        Text specialDie3 = new Text("2");
        Text specialDie4 = new Text("2");
        Text specialDie5 = new Text("7");

        specialDie0.setFont(customFont);
        specialDie0.setFill(mintGreen);
        specialDie1.setFont(customFont);
        specialDie1.setFill(mintGreen);
        specialDie2.setFont(customFont);
        specialDie2.setFill(mintGreen);
        specialDie3.setFont(customFont);
        specialDie3.setFill(mintGreen);
        specialDie4.setFont(customFont);
        specialDie4.setFill(mintGreen);
        specialDie5.setFont(customFont);
        specialDie5.setFill(mintGreen);

        Text playerName = new Text("Mint O’Lint");
        playerName.setFont(customFont);
        playerName.setFill(brown);

        Text normalDieUsages = new Text("∞");
        normalDieUsages.setFont(customFont);
        normalDieUsages.setFill(brown);

        Text specialDieUsages = new Text("3×");
        specialDieUsages.setFont(customFont);
        specialDieUsages.setFill(brown);

        Text spaceButtonLabel = new Text("SPACE");
        spaceButtonLabel.setFont(customFont);
        spaceButtonLabel.setFill(brown);

        Text pressToRollText = new Text("Press to roll");
        pressToRollText.setFont(customFont);
        pressToRollText.setFill(brown);

        // Layout for the left side (charactericon and name)
        VBox leftSide = new VBox(1, playerPortrait, playerName);
        leftSide.setAlignment(Pos.CENTER);

        // Layout for the right side (nextPlayerIcons, dice + text, spacebar-button, selector)
        HBox nextPlayerIcons = new HBox(15);
        nextPlayerIcons.getChildren().addAll(nextPlayer1, nextPlayer2, nextPlayer3, nextPlayer4, nextPlayer5);

        HBox normalDieTexts = new HBox(23, normalDieUsages, normalDie0, normalDie1, normalDie2,
                normalDie3, normalDie4, normalDie5);
        HBox.setMargin(normalDie0, new Insets(0, 0, 0, 6.5));

        HBox specialDieTexts = new HBox(23, specialDieUsages, specialDie0, specialDie1, specialDie2,
                specialDie3, specialDie4, specialDie5);
        HBox.setMargin(specialDie0, new Insets(0, 0, 0, 1.5));

        StackPane spaceButton = new StackPane(spaceBar, spaceButtonLabel);

        VBox rightSide = new VBox(5, nextPlayerIcons, normalDieTexts, specialDieTexts, spaceButton);
        VBox.setMargin(normalDieTexts, new Insets(25, 0, 0, 3));
        VBox.setMargin(specialDieTexts, new Insets(15, 0, 10, 3));
        VBox.setMargin(spaceButton, new Insets(0, 0, 0, -130));

        AnchorPane mainLayout = new AnchorPane();

        mainLayout.getChildren().addAll(background, normalDieBG, specialDieBG, leftSide, rightSide, selectionArrow, pressToRollText);
        // set anchor for the right side of the layout
        AnchorPane.setTopAnchor(rightSide, 0.0);
        AnchorPane.setLeftAnchor(rightSide, 222.0);
        // set anchor for the left side of the layout
        AnchorPane.setTopAnchor(leftSide, 18.0);
        AnchorPane.setLeftAnchor(leftSide, 23.0);
        // set anchor for the text label "Press to roll"
        AnchorPane.setRightAnchor(pressToRollText, 43.0);
        AnchorPane.setBottomAnchor(pressToRollText, 17.0);
        // set anchor for the background of the normaldie and specialdie images
        AnchorPane.setRightAnchor(normalDieBG, 63.0);
        AnchorPane.setBottomAnchor(normalDieBG, 104.0);
        AnchorPane.setRightAnchor(specialDieBG, 63.0);
        AnchorPane.setBottomAnchor(specialDieBG, 57.0);

        mainLayout.setPrefSize(200, 200);

        return mainLayout;
    }

    public static void animateDiceUI(AnchorPane anchorPane) {
        TranslateTransition translateTransition = new TranslateTransition();
    }

    public static void main(String[] args) {
        launch();
    }
}