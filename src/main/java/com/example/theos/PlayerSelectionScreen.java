package com.example.theos;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelectionScreen extends Application {
    private static final double SCREEN_WIDTH = 1422;
    private static final double SCREEN_HEIGHT = 800;
    private static final int NUM_COLUMNS = 3;
    private static final double CHARACTER_IMAGE_SIZE = SCREEN_WIDTH / (NUM_COLUMNS + 2);
    private static final int PADDING_VALUE = 20;
    private static final int SPACING_VALUE = 10;
    private static final int MIN_PLAYERS = 2;
    private static final double SELECTED_IMAGE_TRANSLATE_Y = 5; // Adjust this value
    private static final double DESELECT_BUTTON_OFFSET_X = 50; // Adjust this value
    private static final double DESELECT_BUTTON_OFFSET_Y = 50;  // Adjust this value

    static private String[] selectedCharacters;
    private static GridPane charactersGrid;
    static private BooleanProperty[] playerSelectedProperties;
    private static Character[] characters;
    private static Player[] players;
    private static int playerCounter = 1;

    static final Font CAVEAT = Font.loadFont(PlayerSelectionScreen.class.getClassLoader().getResourceAsStream("fonts/Caveat-SemiBold.ttf"), -1);

    static final Font VARELA = Font.loadFont(PlayerSelectionScreen.class.getClassLoader().getResourceAsStream("fonts/VarelaRound-Regular.ttf"), -1);

    static Color brown = Color.rgb(120, 98, 68);

    @Override
    public void start(Stage primaryStage) {
        VBox instructionsBox = createInstructionsBox();
        charactersGrid = createCharactersGrid();

        HBox mainLayout = new HBox(instructionsBox, charactersGrid);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(SPACING_VALUE);
        mainLayout.setFillHeight(false);

        String backgroundImage = "images/player_select_screen/Player_Selection_Screen.png";
        mainLayout.setStyle("-fx-background-image: url('" + backgroundImage + "'); -fx-background-size: cover;");

        Scene scene = new Scene(mainLayout, SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryStage.setTitle("Player Selection Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*

    public static Scene createPlayerSelectionScreen() {
        VBox instructionsBox = createInstructionsBox();
        charactersGrid = createCharactersGrid();

        HBox mainLayout = new HBox(instructionsBox, charactersGrid);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(SPACING_VALUE);
        mainLayout.setFillHeight(false);

        String backgroundImage = "images/player_select_screen/Player_Selection_Screen.PNG";
        mainLayout.setStyle("-fx-background-image: url('" + backgroundImage + "'); -fx-background-size: cover;");

        return new Scene(mainLayout, TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
    }

     */

    private static VBox createInstructionsBox() {

        Text controls = new Text("CONTROLS");
        controls.setFont(Font.font(VARELA.getFamily(), 58));
        controls.setFill(brown);

        Text row1 = new Text("Clicking on a character" + System.lineSeparator() +
                "adds a player" + System.lineSeparator());
        row1.setFont(Font.font(VARELA.getFamily(), 28));
        row1.setFill(brown);

        Text row2 = new Text("Clicking on the X" + System.lineSeparator() +
                "above the character" + System.lineSeparator() +
                "removes the player" + System.lineSeparator());
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

        ImageView spaceButton = new ImageView(new Image("images/option_button_extras/Button_Space_Small.PNG"));
        spaceButton.setPreserveRatio(true);
        spaceButton.setFitWidth(165);

        Button startButton = new Button();

        // Event handler for pressing SPACE key
        startButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                // Toggle opacity for spaceText and menuButton
                double currentOpacity = row6.getOpacity();
                double newOpacity = (currentOpacity > 0.5) ? currentOpacity - 0.5 : 0.5; // Decrease opacity by 0.2, but not below 0.2
                row6.setOpacity(newOpacity);
                spaceButton.setOpacity(newOpacity);
            }
        });

        // Event handler for releasing SPACE key
        startButton.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                // Reset opacity to normal when the button is released
                row6.setOpacity(1.0);
                spaceButton.setOpacity(1.0);
            }
        });

        startButton.setOpacity(0);

        //Vbox (left side)
        VBox leftSide = new VBox(controls, row1, row2, row3, row4, row6, spaceButton, row5, startButton);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.setSpacing(100);

        controls.setTranslateX(-31);
        controls.setTranslateY(250);

        row1.setTranslateX(-31);
        row1.setTranslateY(190);

        row2.setTranslateX(-48);
        row2.setTranslateY(100);

        row3.setTranslateX(66);
        row3.setTranslateY(30);

        row4.setTranslateX(16);
        row4.setTranslateY(-50);

        row5.setTranslateX(71);
        row5.setTranslateY(-115);

        row6.setTranslateX(-107);
        row6.setTranslateY(-378);

        spaceButton.setTranslateX(-107);
        spaceButton.setTranslateY(975);

        spaceButton.toBack();
        row6.toFront();

        return leftSide;
    }

    private static GridPane createCharactersGrid() {
        charactersGrid = new GridPane();
        charactersGrid.setPadding(new Insets(PADDING_VALUE));
        charactersGrid.setHgap(-3);
        charactersGrid.setVgap(10);

        characters = new Character[NUM_COLUMNS * 2];
        players = new Player[NUM_COLUMNS * 2];

        String[] characterImagePaths = {
                "images/player_icons/Icon_1.PNG",
                "images/player_icons/Icon_2.PNG",
                "images/player_icons/Icon_3.PNG",
                "images/player_icons/Icon_4.PNG",
                "images/player_icons/Icon_5.PNG",
                "images/player_icons/Icon_6.PNG"
        };

        String[] characterNames = {
                "Diva O'Hara",
                "Y'Olanda",
                "Kidd'O",
                "Mint'O Lint",
                "Brooke O'Let",
                "O'Fitz"
        };

        selectedCharacters = new String[characterImagePaths.length];
        playerSelectedProperties = new BooleanProperty[characterImagePaths.length];

        int columnIndex = 0;
        int rowIndex = 0;

        for (int i = 0; i < characterImagePaths.length; i++) {
            if (i >= NUM_COLUMNS * 2) {
                break;
            }

            ImageView characterImage = createCharacterImageView(characterImagePaths[i], i);
            Circle characterButton = createCharacterButton(i, characterImage);
            ImageView deselectButton = createDeselectButton(i, characterImage);

            characters[i] = new Character(characterNames[i]);
            players[i] = new Player(characters[i], null);

            Text playerNameText = new Text(characters[i].getName());
            playerNameText.setFont(Font.font(CAVEAT.getFamily(), 28));
            playerNameText.setFill(brown);

            Text playerInfoText = new Text();
            playerInfoText.setFont(Font.font(VARELA.getFamily(), 20));
            playerInfoText.setFill(brown);
            playerInfoText.textProperty().bind(players[i].playerInfoProperty()); // Bind the player info property

            VBox playerInfoBox = new VBox(characterImage, playerNameText, playerInfoText);
            playerInfoBox.setAlignment(Pos.CENTER);

            StackPane characterPane = new StackPane();
            characterPane.getChildren().addAll(playerInfoBox, characterButton, deselectButton);
            StackPane.setAlignment(deselectButton, Pos.TOP_RIGHT);
            StackPane.setMargin(deselectButton, new Insets(DESELECT_BUTTON_OFFSET_Y, DESELECT_BUTTON_OFFSET_X, 0, 0));
            charactersGrid.add(characterPane, columnIndex, rowIndex);

            columnIndex += 2;
            if (columnIndex >= NUM_COLUMNS * 2) {
                columnIndex = 0;
                rowIndex++;
            }
        }

        return charactersGrid;
    }

    private static Circle createCharacterButton(int playerIndex, ImageView characterImage) {
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

    private static ImageView createCharacterImageView(String imagePath, int playerIndex) {
        Image image = new Image(imagePath, CHARACTER_IMAGE_SIZE, CHARACTER_IMAGE_SIZE, true, true);
        ImageView imageView = new ImageView(image);

        imageView.setOnMouseClicked(event -> {
            togglePlayerSelection(playerIndex, imageView);
        });

        return imageView;
    }

    private static ImageView createDeselectButton(int playerIndex, ImageView characterImage) {
        Image deselectImage = new Image("images/player_select_screen/Player_Deselect.PNG", 35, 35, true, true);
        ImageView deselectImageView = new ImageView(deselectImage);

        playerSelectedProperties[playerIndex] = new SimpleBooleanProperty(false);

        deselectImageView.visibleProperty().bind(playerSelectedProperties[playerIndex]);

        deselectImageView.setOnMouseClicked(event -> {
            deselectPlayer(playerIndex, characterImage);
        });

        return deselectImageView;
    }

    private static void togglePlayerSelection(int playerIndex, ImageView characterImage) {
        Player currentPlayer = players[playerIndex];
        if (currentPlayer.getPlayerNumber() == 0) {
            selectPlayer(playerIndex, characterImage);
        } else {
            deselectPlayer(playerIndex, characterImage);
        }
    }

    private static void selectPlayer(int playerIndex, ImageView characterImage) {
        Player currentPlayer = players[playerIndex];
        currentPlayer.setPlayerNumber(playerCounter++);
        characterImage.setTranslateY(SELECTED_IMAGE_TRANSLATE_Y);

        characterImage.setOpacity(0.5);

        System.out.println("Selected: " + currentPlayer);
        System.out.println(allowPlayability());
        printAllPlayers();
    }

    private static void deselectPlayer(int playerIndex, ImageView characterImage) {
        Player currentPlayer = players[playerIndex];
        int deselectedPlayerNumber = currentPlayer.getPlayerNumber();
        playerCounter--;

        currentPlayer.setPlayerNumber(0);
        characterImage.setTranslateY(0);
        characterImage.setOpacity(1.0);

        System.out.println("Deselected: " + currentPlayer);
        adjustPlayerNumbers(deselectedPlayerNumber);
        System.out.println(allowPlayability());
        printAllPlayers();
    }

    private static void adjustPlayerNumbers(int deselectedPlayerNumber) {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            if (player.getPlayerNumber() > deselectedPlayerNumber) {
                player.setPlayerNumber(player.getPlayerNumber() - 1);
            }
        }
    }

    private static boolean allowPlayability() {
        boolean allowPlayability = false;
        if (countSelectedPlayers() >= MIN_PLAYERS) {
            allowPlayability = true;
        }
        return allowPlayability;
    }

    private static int countSelectedPlayers() {
        int count = 0;
        for (String character : selectedCharacters) {
            if (character != null) {
                count++;
            }
        }
        return count;
    }

    private static void printAllPlayers() {
        for (Player player : players) {
            System.out.println(player);
        }
        System.out.println();
    }

    /*
    private static void createPlayerList() {
        List<com.example.theos.Player> selectedPlayers = new ArrayList<>();

        for (int i = 0; i < players.length; i++) {

            if (players[i].playerNumber > 0) {
                com.example.theos.Player newPlayer = new com.example.theos.Player(players[i].selectedCharacter.getName(), )

                selectedPlayers.add();
            }
        }
    }

     */

    private static class Character {
        private String name;

        public Character(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static class Player {
        private static List<Integer> playerNumbers = new ArrayList<>(); // List to store player numbers
        private Character selectedCharacter;
        private int playerNumber;
        private final SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(false);
        private final StringProperty playerInfoProperty = new SimpleStringProperty();

        public Player(Character selectedCharacter, Integer playerNumber) {
            this.selectedCharacter = selectedCharacter;
            this.playerNumber = (playerNumber != null) ? playerNumber : 0;
            playerNumbers.add(playerNumber); // Add player number to the list
            updatePlayerInfo();
        }

        public Character getSelectedCharacter() {
            return selectedCharacter;
        }

        public int getPlayerNumber() {
            return playerNumber;
        }

        public void setPlayerNumber(int playerNumber) {
            playerNumbers.remove(Integer.valueOf(this.playerNumber)); // Remove old player number
            this.playerNumber = playerNumber;
            playerNumbers.add(playerNumber); // Add new player number
            updatePlayerInfo();
            selectedProperty.set(playerNumber != 0);
        }

        public SimpleBooleanProperty selectedProperty() {
            return selectedProperty;
        }

        public StringProperty playerInfoProperty() {
            return playerInfoProperty;
        }

        private void updatePlayerInfo() {
            if (playerNumber != 0) {
                playerInfoProperty.set("P" + playerNumber);
            } else {
                playerInfoProperty.set("");
            }
        }

        public static List<Integer> getPlayerNumbers() {
            return playerNumbers;
        }

        @Override
        public String toString() {
            return "P" + playerNumber + " (" + selectedCharacter.getName() + ")";
        }
    }
}