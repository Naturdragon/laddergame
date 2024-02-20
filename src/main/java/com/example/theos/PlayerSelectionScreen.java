package com.example.theos;

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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// The following code has been partially adapted from ChatGPT
public class PlayerSelectionScreen {

    /*constants for screen width, number of columns, character image size,
    padding values, minimum players and offsets  */
    private static final double SCREEN_WIDTH = 1422;
    private static final int NUM_COLUMNS = 3;
    private static final double CHARACTER_IMAGE_SIZE = SCREEN_WIDTH / (NUM_COLUMNS + 2);
    private static final int MIN_PLAYERS = 2;
    private static final double SELECTED_IMAGE_TRANSLATE_Y = 5; // Adjust this value

    /*GridPane for characters, BooleanProperties to track selection,
    arrays for characters and players characters, and playerCounter*/
    private static GridPane charactersGrid;
    private static Player[] players;
    private static int playerCounter = 1;
    public static final ImageView MOUSE_DISPLAY = new ImageView(new Image("images/player_select_screen/Player_Mouse.PNG"));

    /*creates and returns PlayerSelectionScreen including
    character selection grid, instructions and buttons */
    public static Scene createPlayerSelectionScreen() {
        playerCounter = 1;

        AnchorPane controlsBox = createControlsBox();
        charactersGrid = createCharactersGrid();
        AnchorPane optionButtons = OptionButtons.createOptionButtonsSet(null, true, true, true, false, true);
        Pane mainLayout = new Pane(controlsBox, charactersGrid, optionButtons);
        String backgroundIMG = "images/player_select_screen/Player_Selection_Screen.PNG";
        mainLayout.setStyle("-fx-background-image: url('" + backgroundIMG + "'); -fx-background-size: cover;");

        return new Scene(mainLayout, TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
    }

    //creates VBox and includes instruction text and images for controls
    private static AnchorPane createControlsBox() {
        Text controls = new Text("CONTROLS");
        controls.setFont(Font.font(TheOs.VARELA.getFamily(), 58));
        controls.setFill(TheOs.BROWN);

        Text selectText = new Text("Clicking on a Character" + "\nselects the Player");
        selectText.setFont(Font.font(TheOs.VARELA.getFamily(), 28));
        selectText.setFill(TheOs.BROWN);

        Text deselectText = new Text("Clicking on a Character" + "\nafter Selection deselects" + "\nthe Player");
        deselectText.setFont(Font.font(TheOs.VARELA.getFamily(), 28));
        deselectText.setFill(TheOs.BROWN);

        Text minPlayerText = new Text("You must select" + "\nat least two Characters!");
        minPlayerText.setFont(Font.font(TheOs.VARELA.getFamily(), 28));
        minPlayerText.setFill(TheOs.BROWN);

        Text ArrowText = new Text("Dice" + "\nSelect");
        ArrowText.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        ArrowText.setFill(TheOs.BROWN);

        Text MouseText = new Text("Player Select" + "\nOption Select");
        MouseText.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        MouseText.setFill(TheOs.BROWN);

        Text StartText = new Text("Start");
        StartText.setFont(Font.font(TheOs.VARELA.getFamily(), 30));
        StartText.setFill(TheOs.BROWN);

        Text SpaceText = new Text("SPACE");
        SpaceText.setFont(Font.font(TheOs.VARELA.getFamily(), 25));
        SpaceText.setFill(TheOs.BROWN);

        ImageView spaceButton = new ImageView(new Image("images/option_button_extras/Button_Space_Small.PNG"));
        spaceButton.setPreserveRatio(true);
        spaceButton.setFitWidth(165);

        MOUSE_DISPLAY.setPreserveRatio(true);
        MOUSE_DISPLAY.setFitWidth(30);

        Button startButton = new Button();
        startButton.setOpacity(0);

        AnchorPane controlsBox = new AnchorPane(controls, selectText, deselectText, minPlayerText, ArrowText, MouseText, StartText, spaceButton, SpaceText, startButton, MOUSE_DISPLAY);
        controlsBox.setTranslateX(70);
        controlsBox.setTranslateY(155);
        AnchorPane.setLeftAnchor(controls, -5.0);
        AnchorPane.setTopAnchor(selectText,30.0);
        AnchorPane.setTopAnchor(deselectText,115.0);
        AnchorPane.setTopAnchor(minPlayerText,235.0);
        AnchorPane.setLeftAnchor(ArrowText,220.0);
        AnchorPane.setTopAnchor(ArrowText,330.0);
        AnchorPane.setTopAnchor(MouseText,425.0);
        AnchorPane.setLeftAnchor(MOUSE_DISPLAY,250.0);
        AnchorPane.setTopAnchor(MOUSE_DISPLAY,440.0);
        AnchorPane.setLeftAnchor(StartText,220.0);
        AnchorPane.setTopAnchor(StartText,527.0);
        AnchorPane.setLeftAnchor(SpaceText,41.0);
        AnchorPane.setTopAnchor(SpaceText,531.0);
        AnchorPane.setTopAnchor(spaceButton,525.0);

        // Event handlers for pressing / releasing SPACE key
        startButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                SpaceText.setOpacity(0.5);
                spaceButton.setOpacity(0.5);
                SpaceText.setTranslateY(SpaceText.getY() + 1);
                spaceButton.setTranslateY(spaceButton.getY() + 1);
            }
        });
        startButton.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                SpaceText.setOpacity(1.0);
                spaceButton.setOpacity(1.0);
                SpaceText.setTranslateY(SpaceText.getY() - 1);
                spaceButton.setTranslateY(spaceButton.getY() - 1);

                if (allowPlayability()) { // Switch to game board in case of at least two players
                    SceneController.showGameBoardScreen(PlayerSelectionScreen.createPlayerList());
                } else { // Alert text
                    minPlayerText.setFill(TheOs.YOLANDA_RED);
                    OptionButtons.animateTextShake(minPlayerText);
                }
            }
        });

        return controlsBox;
    }

    /*GridPane contains character images, names and deselect buttons,
    allowing select or deselect characters*/
    private static GridPane createCharactersGrid() {
        charactersGrid = new GridPane();
        charactersGrid.setPadding(new Insets(75, 0, 0, 500));

        Character[] characters = new Character[NUM_COLUMNS * 2];
        players = new Player[NUM_COLUMNS * 2];

        String[] characterImagePaths = {"images/player_icons/Icon_1.PNG", "images/player_icons/Icon_2.PNG", "images/player_icons/Icon_3.PNG", "images/player_icons/Icon_4.PNG", "images/player_icons/Icon_5.PNG", "images/player_icons/Icon_6.PNG"};
        String[] characterNames = {"Diva O'Hara", "Y'Olanda", "Kidd'O", "Mint'O Lint", "Brooke O'Let", "O'Fitz"};

        BooleanProperty[] playerSelectedProperties = new BooleanProperty[characterImagePaths.length];

        int columnIndex = 0;
        int rowIndex = 0;
        for (int i = 0; i < characterImagePaths.length; i++) {

            ImageView characterImage = createCharacterImageView(characterImagePaths[i], i);

            characters[i] = new Character(characterNames[i]);
            players[i] = new Player(characters[i], null);

            Text playerNameText = new Text(characters[i].name());
            playerNameText.setFont(Font.font(TheOs.CAVEAT.getFamily(), 28));
            playerNameText.setFill(TheOs.BROWN);

            Text playerInfoText = new Text();
            playerInfoText.setFont(Font.font(TheOs.VARELA.getFamily(), 20));
            playerInfoText.setFill(TheOs.BROWN);
            playerInfoText.textProperty().bind(players[i].playerInfoProperty()); // Bind player info property

            VBox playerInfoBox = new VBox(characterImage, playerNameText, playerInfoText);
            playerInfoBox.setAlignment(Pos.CENTER);

            StackPane characterPane = new StackPane();
            characterPane.getChildren().addAll(playerInfoBox);
            charactersGrid.add(characterPane, columnIndex, rowIndex);

            columnIndex += 2;
            if (columnIndex >= NUM_COLUMNS * 2) {
                columnIndex = 0;
                rowIndex++;
            }
        }

        return charactersGrid;
    }

    /*Creates an ImageView for a given character image path and index,
     setting up event handling to toggle player selection when clicked*/
    private static ImageView createCharacterImageView(String imagePath, int playerIndex) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(CHARACTER_IMAGE_SIZE); // changed resizing to be done on the ImageView, not the Image itself (looks better / less pixelated)
        imageView.setPreserveRatio(true);

        imageView.setOnMousePressed(event -> {
            Player currentPlayer = players[playerIndex];
            if (currentPlayer.getPlayerNumber() == 0) {
                imageView.setOpacity(0.5);
            } else {
                imageView.setOpacity(0.25);
            }
            imageView.setTranslateY(imageView.getY() + 3);
        });

        imageView.setOnMouseReleased(event -> {
            togglePlayerSelection(playerIndex, imageView);
            imageView.setTranslateY(imageView.getY() - 3);
        });

        return imageView;
    }

    //toggles player selection status for a given player index
    private static void togglePlayerSelection(int playerIndex, ImageView characterImage) {
        Player currentPlayer = players[playerIndex];
        if (currentPlayer.getPlayerNumber() == 0) {
            selectPlayer(playerIndex, characterImage);
        } else {
            deselectPlayer(playerIndex, characterImage);
        }
    }
    /*selects a player by assigning a player number
    and adjusts the character image`s position and opacity*/
    private static void selectPlayer(int playerIndex, ImageView characterImage) {
        Player currentPlayer = players[playerIndex];
        currentPlayer.setPlayerNumber(playerCounter++);
        characterImage.setTranslateY(SELECTED_IMAGE_TRANSLATE_Y);

        characterImage.setOpacity(0.5);

        System.out.println("Selected: " + currentPlayer);
        System.out.println(allowPlayability());
        printAllPlayers();
    }

    //deselects a player by resetting the player number
    private static void deselectPlayer(int playerIndex, ImageView characterImage) {
        Player currentPlayer = players[playerIndex];
        int deselectedPlayerNumber = currentPlayer.getPlayerNumber();
        playerCounter--;

        currentPlayer.setPlayerNumber(0);
        characterImage.setOpacity(1.0);

        System.out.println("Deselected: " + currentPlayer);
        adjustPlayerNumbers(deselectedPlayerNumber);
        System.out.println(allowPlayability());
        printAllPlayers();
    }
    /*adjusts the player numbers of all players with numbers greater than
     the deselects player´s number */
    private static void adjustPlayerNumbers(int deselectedPlayerNumber) {
        for (Player player : players) {
            if (player.getPlayerNumber() > deselectedPlayerNumber) {
                player.setPlayerNumber(player.getPlayerNumber() - 1);
            }
        }
    }
    /*checks if the current selections allows to start the game
    by ensuring the minimum numbers of players is selected*/
    private static boolean allowPlayability() {
        return countSelectedPlayers() >= MIN_PLAYERS;
    }

    //counts and returns the number of currently selected players
    private static int countSelectedPlayers() {
        int count = 0;
        for (Player player : players) {
            if (player.getPlayerNumber() > 0) {
                count++;
            }
        }
        return count;
    }

    //prints information about players like their names and player numbers
    private static void printAllPlayers() {
        for (Player player : players) {
            System.out.println(player);
        }
        System.out.println();
    }

    /*
    Based on the information in  players[] array a (com.example.theos.)Player List is created and filled with the corresponding characters in the order they were selected
    This list is used to initialize the playerList of the gameBoard
    Returns a List<com.example.theos.Player>
     */
    public static List<com.example.theos.Player> createPlayerList() {
        List<com.example.theos.Player> selectedPlayers = new ArrayList<>();

        for (int i = 0; i < players.length; i++) { // null objects are added to the list so the players can later be added to it at the correct index
            selectedPlayers.add(null);
        }

        for (int i = 0; i < players.length; i++) {
            if (players[i].playerNumber > 0) { // if the playerNumber is > 0 that means the character was selected
                com.example.theos.Player player;
                Field spawn;

                switch (i) { // every character has a fixed index in players[], so based on which iteration of the for loop it is the correct com.example.theos.Player and their unique spawn field can be created
                    case 0 -> {
                        player = new com.example.theos.Player("Diva O'Hara", new int[]{-3, -3, 5, 6, 6, 7}, Paths.get("images/player_icons/Icon_1.PNG"), Paths.get("images/winning_screen/Win_1.PNG"), Paths.get("images/gameboard_screen/Game_O_1.PNG"), Paths.get("images/sprites/Sprites_1.PNG"), Paths.get("images/player_icons/PixelIcon_1.PNG"));
                        spawn = new Field(Field.fieldType.NormalField, 4.8, 60.1 - 1);
                    }
                    case 1 -> {
                        player = new com.example.theos.Player("Y'Olanda", new int[]{1, 1, 2, 4, 6, 6}, Paths.get("images/player_icons/Icon_2.PNG"), Paths.get("images/winning_screen/Win_2.PNG"), Paths.get("images/gameboard_screen/Game_O_2.PNG"), Paths.get("images/sprites/Sprites_2.PNG"), Paths.get("images/player_icons/PixelIcon_2.PNG"));
                        spawn = new Field(Field.fieldType.NormalField, 8.3, 56.6 - 1);
                    }
                    case 2 -> {
                        player = new com.example.theos.Player("Kidd'O", new int[]{-2, -1, 4, 5, 6, 6}, Paths.get("images/player_icons/Icon_3.PNG"), Paths.get("images/winning_screen/Win_3.PNG"), Paths.get("images/gameboard_screen/Game_O_3.PNG"), Paths.get("images/sprites/Sprites_3.PNG"), Paths.get("images/player_icons/PixelIcon_3.PNG"));
                        spawn = new Field(Field.fieldType.NormalField, 11.6, 53.1 - 1);
                    }
                    case 3 -> {
                        player = new com.example.theos.Player("Mint'O Lint", new int[]{1, 1, 2, 2, 3, 7}, Paths.get("images/player_icons/Icon_4.PNG"), Paths.get("images/winning_screen/Win_4.PNG"), Paths.get("images/gameboard_screen/Game_O_4.PNG"), Paths.get("images/sprites/Sprites_4.PNG"), Paths.get("images/player_icons/PixelIcon_4.PNG"));
                        spawn = new Field(Field.fieldType.NormalField, 8.3, 63.9 - 1);
                    }
                    case 4 -> {
                        player = new com.example.theos.Player("Brooke O'Let", new int[]{2, 2, 3, 4, 4, 5}, Paths.get("images/player_icons/Icon_5.PNG"), Paths.get("images/winning_screen/Win_5.PNG"), Paths.get("images/gameboard_screen/Game_O_5.PNG"), Paths.get("images/sprites/Sprites_5.PNG"), Paths.get("images/player_icons/PixelIcon_5.PNG"));
                        spawn = new Field(Field.fieldType.NormalField, 11.9, 60.3 - 1);
                    }
                    case 5 -> {
                        player = new com.example.theos.Player("O'Fitz", new int[]{-1, 0, 2, 3, 4, 7}, Paths.get("images/player_icons/Icon_6.PNG"), Paths.get("images/winning_screen/Win_6.PNG"), Paths.get("images/gameboard_screen/Game_O_6.PNG"), Paths.get("images/sprites/Sprites_6.PNG"), Paths.get("images/player_icons/PixelIcon_6.PNG"));
                        spawn = new Field(Field.fieldType.NormalField, 15.2, 56.9 - 1);
                    }
                    default -> {
                        continue; // Wenn i nicht einem der erwarteten Werte entspricht, überspringe den aktuellen Durchlauf
                    }
                }

                player.setCurrentField(spawn);
                selectedPlayers.set(players[i].playerNumber - 1, player); // the selected character of the player is added to the List based on the players[].playerNumber, this keeps the correct selection order
            }
        }

        selectedPlayers.removeIf(Objects::isNull); // all empty leftover spots are removed again

        return selectedPlayers;
    }

    private record Character(String name){} // Record of character (has: constructor, getter, equals, hashCode, toString)

    private static class Player {
        private static final List<Integer> playerNumbers = new ArrayList<>(); // List to store player numbers
        private final Character selectedCharacter;
        private int playerNumber;
        private final SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(false);
        private final StringProperty playerInfoProperty = new SimpleStringProperty();

        /*to create a player with a selected character
        and an optional player number*/
        public Player(Character selectedCharacter, Integer playerNumber) {
            this.selectedCharacter = selectedCharacter;
            this.playerNumber = (playerNumber != null) ? playerNumber : 0;
            playerNumbers.add(playerNumber); // Add player number to the list
            updatePlayerInfo();
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

        @Override
        public String toString() {
            return "P" + playerNumber + " (" + selectedCharacter.name() + ")";
        }
    }
}