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

import static com.example.theos.TheOs.BROWN;

// Der folgende Code wurde teilweise angepasst von [ChatGPT]
public class PlayerSelectionScreen {
    private static final double SCREEN_WIDTH = 1422;
    private static final int NUM_COLUMNS = 3;
    private static final double CHARACTER_IMAGE_SIZE = SCREEN_WIDTH / (NUM_COLUMNS + 2);
    private static final int PADDING_VALUE = 20;
    private static final int SPACING_VALUE = 10;
    private static final int MIN_PLAYERS = 2;
    private static final double SELECTED_IMAGE_TRANSLATE_Y = 5; // Adjust this value
    private static final double DESELECT_BUTTON_OFFSET_X = 50; // Adjust this value
    private static final double DESELECT_BUTTON_OFFSET_Y = 50;  // Adjust this value

    private static GridPane charactersGrid;
    static private BooleanProperty[] playerSelectedProperties;
    private static Character[] characters;
    private static Player[] players;
    private static int playerCounter = 1;

    static final Font CAVEAT = Font.loadFont(PlayerSelectionScreen.class.getClassLoader().getResourceAsStream("fonts/Caveat-SemiBold.ttf"), -1);
    static final Font VARELA = Font.loadFont(PlayerSelectionScreen.class.getClassLoader().getResourceAsStream("fonts/VarelaRound-Regular.ttf"), -1);

    /*creates and returns PlayerSelectionScreen including
    character selection grid, instructions and buttons */
    public static Scene createPlayerSelectionScreen() {
        playerCounter = 1;

        VBox instructionsBox = createInstructionsBox();
        charactersGrid = createCharactersGrid();

        HBox mainLayout = new HBox(instructionsBox, charactersGrid);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(SPACING_VALUE);
        mainLayout.setFillHeight(false);

        String backgroundImage = "images/player_select_screen/Player_Selection_Screen.PNG";
        mainLayout.setStyle("-fx-background-image: url('" + backgroundImage + "'); -fx-background-size: cover;");

        // Create close and return-to-main-menu buttons
        HBox closeButton = OptionButtons.createCloseAppButton();
        HBox mainMenuButton = OptionButtons.createReturnToMainMenuButton();
        HBox musicButton = OptionButtons.createMusicButton();
        closeButton.setTranslateX(-388);
        closeButton.setTranslateY(-306);
        mainMenuButton.setTranslateX(-329);
        mainMenuButton.setTranslateY(-356);
        musicButton.setTranslateX(943);
        musicButton.setTranslateY(-408);

        // Add buttons to the layout
        VBox buttonLayout = new VBox(closeButton, mainMenuButton, musicButton);
        mainLayout.getChildren().add(buttonLayout);
        charactersGrid.toFront();

        return new Scene(mainLayout, TheOs.SCENE_WIDTH, TheOs.SCENE_HEIGHT);
    }
    //creates VBox and includes instruction text and images for controls
    private static VBox createInstructionsBox() {

        Text controls = new Text("CONTROLS");
        controls.setFont(Font.font(VARELA.getFamily(), 58));
        controls.setFill(BROWN);

        Text row1 = new Text("Clicking on a Character" + System.lineSeparator() +
                "selects the Player" + System.lineSeparator());
        row1.setFont(Font.font(VARELA.getFamily(), 28));
        row1.setFill(BROWN);

        Text row2 = new Text("Clicking on a Character" + System.lineSeparator() +
                "after Selection deselects" + System.lineSeparator() +
                "the Player" + System.lineSeparator());
        row2.setFont(Font.font(VARELA.getFamily(), 28));
        row2.setFill(BROWN);

        Text row3 = new Text("You must select" + System.lineSeparator() +
                "at least two Characters!" + System.lineSeparator());
        row3.setFont(Font.font(VARELA.getFamily(), 28));
        row3.setFill(BROWN);

        Text row4 = new Text("Dice" + System.lineSeparator() +
                "Select");
        row4.setFont(Font.font(VARELA.getFamily(), 30));
        row4.setFill(BROWN);

        Text row5 = new Text("Player Select" + System.lineSeparator() +
                "Option Select");
        row5.setFont(Font.font(VARELA.getFamily(), 30));
        row5.setFill(BROWN);

        Text row6 = new Text("Start");
        row6.setFont(Font.font(VARELA.getFamily(), 30));
        row6.setFill(BROWN);

        Text row7 = new Text("SPACE");
        row7.setFont(Font.font(VARELA.getFamily(), 28));
        row7.setFill(BROWN);

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
                row6.setTranslateY(-480);
                spaceButton.setTranslateY(1075);
            }
        });

        // Event handler for releasing SPACE key
        startButton.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                // Reset opacity to normal when the button is released
                row6.setOpacity(1.0);
                spaceButton.setOpacity(1.0);
                row6.setTranslateY(-480);
                spaceButton.setTranslateY(1075);

                // The scene is switched to the GameBoard with the current list of selected characters if at least 2 characters are selected
                if (allowPlayability()) SceneController.showGameBoardScreen(PlayerSelectionScreen.createPlayerList());
            }
        });

        startButton.setOpacity(0);

        //Vbox (left side)
        VBox leftSide = new VBox(controls, row1, row2, row3, row4, row6,row7, spaceButton, row5, startButton);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.setSpacing(100);

        controls.setTranslateX(-8);
        controls.setTranslateY(355);

        row1.setTranslateX(-16);
        row1.setTranslateY(268);

        row2.setTranslateX(-6);
        row2.setTranslateY(150);

        row3.setTranslateX(-11);
        row3.setTranslateY(33);

        row4.setTranslateX(87);
        row4.setTranslateY(-75);

        row5.setTranslateX(-65);
        row5.setTranslateY(-283);

        row6.setTranslateX(93);
        row6.setTranslateY(-480);

        row7.setTranslateX(-77);
        row7.setTranslateY(-45);

        spaceButton.setTranslateX(-79);
        spaceButton.setTranslateY(1075);

        spaceButton.toBack();
        row6.toFront();

        return leftSide;
    }

    /*Gridpane contains character images, names and deselect buttons,
    allowing select or deselect characters*/
    private static GridPane createCharactersGrid() {
        charactersGrid = new GridPane();
        charactersGrid.setPadding(new Insets(PADDING_VALUE));
        charactersGrid.setHgap(-3);
        charactersGrid.setVgap(-10);

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

        playerSelectedProperties = new BooleanProperty[characterImagePaths.length];

        int columnIndex = 0;
        int rowIndex = 0;

        for (int i = 0; i < characterImagePaths.length; i++) {
            if (i >= NUM_COLUMNS * 2) {
                break;
            }

            ImageView characterImage = createCharacterImageView(characterImagePaths[i], i);

            characters[i] = new Character(characterNames[i]);
            players[i] = new Player(characters[i], null);

            Text playerNameText = new Text(characters[i].getName());
            playerNameText.setFont(Font.font(CAVEAT.getFamily(), 28));
            playerNameText.setFill(BROWN);

            Text playerInfoText = new Text();
            playerInfoText.setFont(Font.font(VARELA.getFamily(), 20));
            playerInfoText.setFill(BROWN);
            playerInfoText.textProperty().bind(players[i].playerInfoProperty()); // Bind the player info property

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

        imageView.setOnMouseClicked(event -> {
            togglePlayerSelection(playerIndex, imageView);
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
        characterImage.setTranslateY(0);
        characterImage.setOpacity(1.0);

        System.out.println("Deselected: " + currentPlayer);
        adjustPlayerNumbers(deselectedPlayerNumber);
        System.out.println(allowPlayability());
        printAllPlayers();
    }
    /*adjusts the player numbers of all players with numbers greater than
     the deselects player´s number */
    private static void adjustPlayerNumbers(int deselectedPlayerNumber) {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            if (player.getPlayerNumber() > deselectedPlayerNumber) {
                player.setPlayerNumber(player.getPlayerNumber() - 1);
            }
        }
    }
    /*checks if the current selections allows to start the game
    by ensuring the minimum numbers of players is selected*/
    private static boolean allowPlayability() {
        boolean allowPlayability = false;
        if (countSelectedPlayers() >= MIN_PLAYERS) {
            allowPlayability = true;
        }
        return allowPlayability;
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
                    case 0:
                        player = new com.example.theos.Player("Diva O'Hara", new int[]{-3, -3, 5, 6, 6, 7}, Paths.get("images/player_icons/Icon_1.PNG"), Paths.get("images/winning_screen/Win_1.PNG"), Paths.get("images/gameboard_screen/Game_O_1.PNG"), Paths.get("images/sprites/Sprites_1.png"));
                        spawn = new Field(Field.fieldType.NormalField, 4.8, 60.1 - 1);
                        break;
                    case 1:
                        player = new com.example.theos.Player("Y'Olanda", new int[]{1, 1, 2, 4, 6, 6}, Paths.get("images/player_icons/Icon_2.PNG"), Paths.get("images/winning_screen/Win_2.PNG"), Paths.get("images/gameboard_screen/Game_O_2.PNG"), Paths.get("images/sprites/Sprites_2.png"));
                        spawn = new Field(Field.fieldType.NormalField, 8.3, 56.6 - 1);
                        break;
                    case 2:
                        player = new com.example.theos.Player("Kidd'O", new int[]{-2, -1, 4, 5, 6, 6}, Paths.get("images/player_icons/Icon_3.PNG"), Paths.get("images/winning_screen/Win_3.PNG"), Paths.get("images/gameboard_screen/Game_O_3.PNG"), Paths.get("images/sprites/Sprites_3.png"));
                        spawn = new Field(Field.fieldType.NormalField, 11.6, 53.1 - 1);
                        break;
                    case 3:
                        player = new com.example.theos.Player("Mint'O Lint", new int[]{1, 1, 2, 2, 3, 7}, Paths.get("images/player_icons/Icon_4.PNG"), Paths.get("images/winning_screen/Win_4.PNG"), Paths.get("images/gameboard_screen/Game_O_4.PNG"), Paths.get("images/sprites/Sprites_4.png"));
                        spawn = new Field(Field.fieldType.NormalField, 8.3, 63.9 - 1);
                        break;
                    case 4:
                        player = new com.example.theos.Player("Brooke O'Let", new int[]{2, 2, 3, 4, 4, 5}, Paths.get("images/player_icons/Icon_5.PNG"), Paths.get("images/winning_screen/Win_5.PNG"), Paths.get("images/gameboard_screen/Game_O_5.PNG"), Paths.get("images/sprites/Sprites_5.png"));
                        spawn = new Field(Field.fieldType.NormalField, 11.9, 60.3 - 1);
                        break;
                    case 5:
                        player = new com.example.theos.Player("O'Fitz", new int[]{-1, 0, 2, 3, 4, 7}, Paths.get("images/player_icons/Icon_6.PNG"), Paths.get("images/winning_screen/Win_6.PNG"), Paths.get("images/gameboard_screen/Game_O_6.PNG"), Paths.get("images/sprites/Sprites_6.png"));
                        spawn = new Field(Field.fieldType.NormalField, 15.2, 56.9 - 1);
                        break;
                    default:
                        continue; // Wenn i nicht einem der erwarteten Werte entspricht, überspringe den aktuellen Durchlauf
                }

                player.setCurrentField(spawn);
                selectedPlayers.set(players[i].playerNumber - 1, player); // the selected character of the player is added to the List based on the players[].playerNumber, this keeps the correct selection order
            }
        }

        selectedPlayers.removeIf(Objects::isNull); // all empty leftover spots are removed again

        return selectedPlayers;
    }

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
            return "P" + playerNumber + " (" + selectedCharacter.getName() + ")";
        }
    }
}