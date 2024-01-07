package com.example.theos.BordGameGraph;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
public class PlayerSelectionScreen {
    private static final double SCREEN_WIDTH = 1422;
    private static final double SCREEN_HEIGHT = 800;
    private static final int NUM_COLUMNS = 3;
    private static final double CHARACTER_IMAGE_SIZE = SCREEN_WIDTH / (NUM_COLUMNS + 1.5);
    private static final int PADDING_VALUE = 20;
    private static final int SPACING_VALUE = 10;
    private static final int MIN_PLAYERS = 2;
    private static final double SELECTED_IMAGE_TRANSLATE_Y = 5; // Adjust this value
    private static final double DESELECT_BUTTON_OFFSET_X = 50; // Adjust this value
    private static final double DESELECT_BUTTON_OFFSET_Y = 50;  // Adjust this value

    private String[] selectedCharacters;
    private GridPane charactersGrid;
    private BooleanProperty[] playerSelectedProperties;

    static final Font CAVEAT = Font.loadFont(Application.class.getClassLoader().getResourceAsStream("Caveat-SemiBold.ttf"), -1);

    static final Font VARELA = Font.loadFont(Application.class.getClassLoader().getResourceAsStream("VarelaRound-Regular.ttf"), -1);

    Color brown = Color.rgb(120, 98, 68);

    @Override
    public void start(Stage primaryStage) {
        VBox instructionsBox = createInstructionsBox();
        charactersGrid = createCharactersGrid();

        HBox mainLayout = new HBox(instructionsBox, charactersGrid);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(SPACING_VALUE);
        mainLayout.setFillHeight(false);

        String backgroundImage = "Player_Selection_Screen.png";
        mainLayout.setStyle("-fx-background-image: url('" + backgroundImage + "'); -fx-background-size: cover;");

        Scene scene = new Scene(mainLayout, SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryStage.setTitle("Player Selection Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createInstructionsBox() {

        Text controls = new Text("CONTROLS");
        controls.setFont(Font.font(VARELA.getFamily(), 58));
        controls.setFill(brown);

        Text row1 = new Text("Clicking on a character" + System.lineSeparator() +
                "adds a player" + System.lineSeparator());
        row1.setFont(Font.font(VARELA.getFamily(), 28));
        row1.setFill(brown);

        Text row2 = new Text("Clicking on the X" + System.lineSeparator() +
                "above the most recent" + System.lineSeparator() +
                "character removes" + System.lineSeparator() +
                "the player");
        row2.setFont(Font.font(VARELA.getFamily(), 28));
        row2.setFill(brown);

        Text row3 = new Text(" Dice" + System.lineSeparator() +
                "select");
        row3.setFont(Font.font(VARELA.getFamily(), 30));
        row3.setFill(brown);

        Text row4 = new Text("Player select" + System.lineSeparator() +
                "Option select");
        row4.setFont(Font.font(VARELA.getFamily(), 30));
        row4.setFill(brown);

        Text row5 = new Text("Start");
        row5.setFont(Font.font(VARELA.getFamily(), 30));
        row5.setFill(brown);

        Text row6 = new Text("SPACE");
        row6.setFont(Font.font(VARELA.getFamily(), 28));
        row6.setFill(brown);


        //Vbox (left side)
        VBox leftSide = new VBox(controls, row1, row2, row3, row4, row5, row6);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.setSpacing(100);

        controls.setTranslateX(6);
        controls.setTranslateY(260);

        row1.setTranslateX(-2);
        row1.setTranslateY(180);

        row2.setTranslateX(-2);
        row2.setTranslateY(100);

        row3.setTranslateX(100);
        row3.setTranslateY(40);

        row4.setTranslateX(30);
        row4.setTranslateY(-35);

        row5.setTranslateX(100);
        row5.setTranslateY(-105);

        row6.setTranslateX(-68);
        row6.setTranslateY(-242);

        return leftSide;
    }

    private GridPane createCharactersGrid() {
        charactersGrid = new GridPane();
        charactersGrid.setPadding(new Insets(PADDING_VALUE));
        charactersGrid.setHgap(-3);
        charactersGrid.setVgap(38);

        String[] characterImagePaths = {
                "Icon_1.PNG",
                "Icon_2.PNG",
                "Icon_3.PNG",
                "Icon_4.PNG",
                "Icon_5.PNG",
                "Icon_6.PNG"
        };

        selectedCharacters = new String[characterImagePaths.length];
        playerSelectedProperties = new BooleanProperty[characterImagePaths.length];

        int columnIndex = 0;
        int rowIndex = 0;

        for (int i = 0; i < characterImagePaths.length; i++) {
            ImageView characterImage = createCharacterImageView(characterImagePaths[i], i);
            Circle characterButton = createCharacterButton(i, characterImage);
            ImageView deselectButton = createDeselectButton(i, characterImage);
            if (i == 0) {
                Text playerNameText = new Text("Diva O'Hara ");
                playerNameText.setFont(Font.font(CAVEAT.getFamily(), 20));
                playerNameText.setFill(brown);
                VBox playerInfoBox = new VBox(characterImage, playerNameText);
                playerInfoBox.setAlignment(Pos.CENTER);
                StackPane characterPane = new StackPane();
                characterPane.getChildren().addAll(playerInfoBox, characterButton, deselectButton);
                StackPane.setAlignment(deselectButton, Pos.TOP_RIGHT); // Align deselectButton to top right
                StackPane.setMargin(deselectButton, new Insets(DESELECT_BUTTON_OFFSET_Y, DESELECT_BUTTON_OFFSET_X, 0, 0));
                charactersGrid.add(characterPane, columnIndex, rowIndex);

            }
            if (i == 1) {
                Text playerNameText = new Text("Y`Olanda ");
                playerNameText.setFont(Font.font(CAVEAT.getFamily(), 20));
                playerNameText.setFill(brown);
                VBox playerInfoBox = new VBox(characterImage, playerNameText);
                playerInfoBox.setAlignment(Pos.CENTER);
                StackPane characterPane = new StackPane();
                characterPane.getChildren().addAll(playerInfoBox, characterButton, deselectButton);
                StackPane.setAlignment(deselectButton, Pos.TOP_RIGHT); // Align deselectButton to top right
                StackPane.setMargin(deselectButton, new Insets(DESELECT_BUTTON_OFFSET_Y, DESELECT_BUTTON_OFFSET_X, 0, 0));
                charactersGrid.add(characterPane, columnIndex, rowIndex);

            }
            if (i == 2) {
                Text playerNameText = new Text("Kidd`O ");
                playerNameText.setFont(Font.font(CAVEAT.getFamily(), 20));
                playerNameText.setFill(brown);
                VBox playerInfoBox = new VBox(characterImage, playerNameText);
                playerInfoBox.setAlignment(Pos.CENTER);
                StackPane characterPane = new StackPane();
                characterPane.getChildren().addAll(playerInfoBox, characterButton, deselectButton);
                StackPane.setAlignment(deselectButton, Pos.TOP_RIGHT); // Align deselectButton to top right
                StackPane.setMargin(deselectButton, new Insets(DESELECT_BUTTON_OFFSET_Y, DESELECT_BUTTON_OFFSET_X, 0, 0));
                charactersGrid.add(characterPane, columnIndex, rowIndex);

            }

            if (i == 3) {
                Text playerNameText = new Text("Mint O`Lint ");
                playerNameText.setFont(Font.font(CAVEAT.getFamily(), 20));
                playerNameText.setFill(brown);
                VBox playerInfoBox = new VBox(characterImage, playerNameText);
                playerInfoBox.setAlignment(Pos.CENTER);
                StackPane characterPane = new StackPane();
                characterPane.getChildren().addAll(playerInfoBox, characterButton, deselectButton);
                StackPane.setAlignment(deselectButton, Pos.TOP_RIGHT); // Align deselectButton to top right
                StackPane.setMargin(deselectButton, new Insets(DESELECT_BUTTON_OFFSET_Y, DESELECT_BUTTON_OFFSET_X, 0, 0));
                charactersGrid.add(characterPane, columnIndex, rowIndex);

            }

            if (i == 4) {
                Text playerNameText = new Text("Brooke O`Let ");
                playerNameText.setFont(Font.font(CAVEAT.getFamily(), 20));
                playerNameText.setFill(brown);
                VBox playerInfoBox = new VBox(characterImage, playerNameText);
                playerInfoBox.setAlignment(Pos.CENTER);
                StackPane characterPane = new StackPane();
                characterPane.getChildren().addAll(playerInfoBox, characterButton, deselectButton);
                StackPane.setAlignment(deselectButton, Pos.TOP_RIGHT); // Align deselectButton to top right
                StackPane.setMargin(deselectButton, new Insets(DESELECT_BUTTON_OFFSET_Y, DESELECT_BUTTON_OFFSET_X, 0, 0));
                charactersGrid.add(characterPane, columnIndex, rowIndex);

            }
            if (i == 5) {
                Text playerNameText = new Text("O`Fitz ");
                playerNameText.setFont(Font.font(CAVEAT.getFamily(), 20));
                playerNameText.setFill(brown);
                VBox playerInfoBox = new VBox(characterImage, playerNameText);
                playerInfoBox.setAlignment(Pos.CENTER);
                StackPane characterPane = new StackPane();
                characterPane.getChildren().addAll(playerInfoBox, characterButton, deselectButton);
                StackPane.setAlignment(deselectButton, Pos.TOP_RIGHT); // Align deselectButton to top right
                StackPane.setMargin(deselectButton, new Insets(DESELECT_BUTTON_OFFSET_Y, DESELECT_BUTTON_OFFSET_X, 0, 0));
                charactersGrid.add(characterPane, columnIndex, rowIndex);

            }

            columnIndex += 2;
            if (columnIndex >= NUM_COLUMNS * 2) {
                columnIndex = 0;
                rowIndex++;
            }
        }

        return charactersGrid;
    }

    private Circle createCharacterButton(int playerIndex, ImageView characterImage) {
        Circle button = new Circle(CHARACTER_IMAGE_SIZE / 2);
        button.setFill(Color.TRANSPARENT);
        button.setStroke(Color.TRANSPARENT);

        button.setOnMousePressed(event -> {
            if (playerIndex < selectedCharacters.length) {
                togglePlayerSelection(playerIndex, characterImage);
            }
        });

        return button;
    }

    private ImageView createCharacterImageView(String imagePath, int playerIndex) {
        Image image = new Image(imagePath, CHARACTER_IMAGE_SIZE, CHARACTER_IMAGE_SIZE, true, true);
        ImageView imageView = new ImageView(image);

        imageView.setOnMouseClicked(event -> {
            togglePlayerSelection(playerIndex, imageView);
        });

        return imageView;
    }

    private ImageView createDeselectButton(int playerIndex, ImageView characterImage) {
        Image deselectImage = new Image("Player_Deselect.PNG", 35, 35, true, true);
        ImageView deselectImageView = new ImageView(deselectImage);

        playerSelectedProperties[playerIndex] = new SimpleBooleanProperty(false);

        deselectImageView.visibleProperty().bind(playerSelectedProperties[playerIndex]);

        deselectImageView.setOnMouseClicked(event -> {
            deselectPlayer(playerIndex, characterImage);
        });

        return deselectImageView;
    }

    private void togglePlayerSelection(int playerIndex, ImageView characterImage) {
        if (selectedCharacters[playerIndex] == null) {
            selectPlayer(playerIndex, characterImage);
        } else {
            deselectPlayer(playerIndex, characterImage);
        }
    }

    private void selectPlayer(int playerIndex, ImageView characterImage) {
        selectedCharacters[playerIndex] = "Player " + (playerIndex + 1);
        playerSelectedProperties[playerIndex].set(true);
        characterImage.setTranslateY(SELECTED_IMAGE_TRANSLATE_Y);
        characterImage.setOpacity(0.5);
        System.out.println("Selected: " + selectedCharacters[playerIndex] + " (Index " + playerIndex + ")");
        System.out.println("Selected Players Array: " + Arrays.toString(selectedCharacters));
        updateGameStatus();
    }

    private void deselectPlayer(int playerIndex, ImageView characterImage) {
        selectedCharacters[playerIndex] = null;
        playerSelectedProperties[playerIndex].set(false);
        characterImage.setTranslateY(0);
        characterImage.setOpacity(1.0);
        System.out.println("Deselected: Player " + (playerIndex + 1));
        System.out.println("Selected Players Array: " + Arrays.toString(selectedCharacters));
        updateGameStatus();
    }

    private void updateGameStatus() {
        if (countSelectedPlayers() >= MIN_PLAYERS) {
            System.out.println("Minimum players selected. You can start the game!");
        }
    }

    private int countSelectedPlayers() {
        int count = 0;
        for (String character : selectedCharacters) {
            if (character != null) {
                count++;
            }
        }
        return count;
    }



}
