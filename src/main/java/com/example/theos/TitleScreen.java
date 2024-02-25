package com.example.theos;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

// The following code has been partially adapted from ChatGPT
public class TitleScreen { // Displays title screen
    private static final Image BACKGROUND_IMG = new Image("images/title_screen/Title_BG.PNG");
    private static final Text DESCRIPTION_TEXT = new Text("Start Game"); // Text shown on screen above SPACE button
    private static final Text SPACE_TEXT = new Text("SPACE"); // SPACE text on button
    private static final ImageView SPACE_BUTTON = new ImageView(new Image("images/option_button_extras/Button_Space_Big.PNG")); // Button image
    private static final Button START_BUTTON = new Button("Start game");

    public static Pane createTitleScreen() { // Create scene for title screen
        Pane mainLayout = new Pane();
        SceneController.createBackgroundRegion(BACKGROUND_IMG, mainLayout);
        AnchorPane startButton = createStartButton();
        AnchorPane optionButtons = OptionButtons.createOptionButtonsSet(null, true, true, false, false, true);
        mainLayout.getChildren().add(startButton);
        mainLayout.getChildren().add(optionButtons);

        return mainLayout;
    }

    private static AnchorPane createStartButton() { // Create start button
        AnchorPane startButton = new AnchorPane(SPACE_BUTTON, DESCRIPTION_TEXT, SPACE_TEXT, START_BUTTON);
        startButton.setTranslateX(537);
        startButton.setTranslateY(690);

        // Format, position & animation
        OptionButtons.spaceButtonFormat(DESCRIPTION_TEXT, SPACE_TEXT, SPACE_BUTTON, START_BUTTON, SceneController::showPlayerSelectScreen);
        AnchorPane.setLeftAnchor(DESCRIPTION_TEXT, 90.0);
        OptionButtons.animateButtonBounce(START_BUTTON, SPACE_TEXT, DESCRIPTION_TEXT, SPACE_BUTTON);

        return startButton;
    }
}