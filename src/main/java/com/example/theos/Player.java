package com.example.theos;

import Animation.SpriteAnimation;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.util.Duration;

import java.nio.file.Path;

public class Player {
    private String name;
    private static int nextID = 0; // Used for automatic ID distribution
    private int iD;
    private Dice basicDie;
    private Dice specialDie;
    private Path imagePath;
    private Path winningImagePath;
    private Path nextPlayerImagePath;
    private Path spriteImagePath;
    private Path pixelImagePath;
    private Field currentField; // Current Field where the player is
    private Transition currentAnimation; // The current animation of the Player. aka. Walking, standing, etc.
    private ImageView imageView; //used to represent the character of the player on screen
    private int turnCount;

    public Player(String characterName, int[] specialDiceArray, Path path) {
        name = characterName;
        basicDie = new Dice();
        specialDie = new Dice(specialDiceArray);
        imagePath = path;

        // Increase the ID by one
        iD = nextID;
        nextID++;
        turnCount = 0;
    }

    public Player(String characterName, int[] specialDiceArray, Path path, Path winningImagePath, Path nextPlayerImagePath, Path spriteImagePath, Path pixelImagePath) {
        this(characterName, specialDiceArray, path);
        this.winningImagePath = winningImagePath;
        this.nextPlayerImagePath = nextPlayerImagePath;
        this.spriteImagePath = spriteImagePath;
        this.pixelImagePath = pixelImagePath;

        imageView = new ImageView(new Image(getSpriteImagePath()));

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(54);
    }

    public String getNextPlayerImagePath() {
        return nextPlayerImagePath.toString();
    }

    public String getSpriteImagePath() {
        return spriteImagePath.toString();
    }

    public String getPixelImagePath() {
        return pixelImagePath.toString();
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return iD;
    }

    public Dice getSpecialDie() {
        return specialDie;
    }

    public Path getImagePath() {
        return imagePath;
    }

    public void setImagePath(Path path) {
        imagePath = path;
    }

    public Field getCurrentField() {
        return currentField;
    }

    public void setCurrentField(Field currentField) {
        this.currentField = currentField;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getWinningImagePath() {
        return (winningImagePath != null) ? winningImagePath.toString() : "";
    }

    public String getImage() {
        return imagePath.toString();
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void increaseTurns() {
        turnCount++;
    }

    /*
    Creates the SpawnAnimation of a character. It's a ParallelTransition consisting of a Translate and a Sprite Animation
    Unlike other character animations this is not loaded into the currentAnimation variable, reason being:
    the spawn animation of all characters are later played together in another ParallelTransition, this helps for input handling
    Returns a Transition (ParallelTransition)
     */
    public Transition playSpawnAnimation() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        // Create SpriteAnimation based on the spritesheet
        SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, 6, 6, 0, 222, 74, 74);
        imageView.setY(currentField.getY() - 150);

        // Create animation for movement
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), imageView);
        transition.setByY(107);

        // Combine the two animations
        ParallelTransition currentAnimation = new ParallelTransition(spriteAnimation, transition);
        currentAnimation.setCycleCount(1);

        return currentAnimation;
    }

    public void playIdle() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        currentAnimation = new SpriteAnimation(imageView, 20, 20, 0, 0, 74, 74);
        currentAnimation.setCycleCount(Animation.INDEFINITE);
        currentAnimation.play();
    }

    public void playWalk() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        currentAnimation = new SpriteAnimation(imageView, 20, 20, 0, 74, 74, 74);
        currentAnimation.setCycleCount(Animation.INDEFINITE);
        currentAnimation.play();
    }

    public void playFallWaterfall() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        currentAnimation = new SpriteAnimation(imageView, 10, 10, 0, 296, 74, 74);
        currentAnimation.setCycleCount(1);
        currentAnimation.play();
    }

    public void playJump() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        currentAnimation = new SpriteAnimation(imageView, 6, 6, 740, 296, 74, 74);
        currentAnimation.setCycleCount(1);
        currentAnimation.play();
    }

    public void playFallCereal() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        currentAnimation = new SpriteAnimation(imageView, 6, 6, 740, 222, 74, 74);
        currentAnimation.setCycleCount(1);
        currentAnimation.play();
    }

    public void playSwim() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        currentAnimation = new SpriteAnimation(imageView, 20, 20, 0, 148, 74, 74);
        currentAnimation.setCycleCount(Animation.INDEFINITE);
        currentAnimation.play();
    }

    /*
    Plays the ending animation when a player reaches a winning field: the character jumps up from the last field (195 or 347) and falls down to the winning fields, swimming animation starts then
    The player has already logically reached the winning field, so currentField == one of the winning fields
    to correctly play the animation the input parameter specifies from which last field the player reached the end
    returns nothing (plays the animation)
     */
    public Transition[] getEndAnimation(Field winningField) {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }

        // First part: jump up from last field
        TranslateTransition translateUp = new TranslateTransition(Duration.millis(600), imageView);
        translateUp.setByY(-200);

        SpriteAnimation jumpSprite = new SpriteAnimation(imageView, 6, 6, 740, 296, 74, 74); // jump animation based on spritesheet

        ParallelTransition jumpParallel = new ParallelTransition(translateUp, jumpSprite);
        /*
        jumpParallel.setOnFinished(event -> {
            this.imageView.setX(currentField.getX()-82);
            this.imageView.setY(currentField.getY()-200+95);
            // -82 and +95 because the characters weren’t displayed at the correct coordinates of the fields (maybe something to do with how setX/Y works)
        });
        */
        jumpParallel.setOnFinished(actionEvent -> imageView.setOpacity(0));

        // Second part: fall down to the winning field
        TranslateTransition translateDown = new TranslateTransition(Duration.millis(600), imageView);
        translateDown.setByY(200);

        SpriteAnimation fallCerealSprite = new SpriteAnimation(imageView, 6, 6, 740, 222, 74, 74); // fallCereal animation based on spritesheet

        ParallelTransition fallParallel = new ParallelTransition(translateDown, fallCerealSprite);

        PathTransition pseudoPath = new PathTransition();
        pseudoPath.setNode(imageView);
        pseudoPath.setDuration(Duration.millis(200));

        javafx.scene.shape.Path path = new javafx.scene.shape.Path();
        path.getElements().add(new MoveTo(200, 200));
        path.getElements().add(new LineTo(winningField.getX(), winningField.getY()-200));

        //pseudoPath.setPath(new Line(200, 200, currentField.getX(), currentField.getY()-200));
        pseudoPath.setPath(path);
        pseudoPath.setOnFinished(actionEvent -> imageView.setOpacity(1));

        SequentialTransition sequence = new SequentialTransition(pseudoPath, fallParallel);

        return new Transition[]{jumpParallel, sequence};
    }

    /*
        Rolls the dice which are saved in the Player class
        Used to choose the die to roll
        returns the die result
        !! DIE SPECIAL DIE CHARGES DO NOT GET CHECKED HERE. THIS NEEDS TO BE DONE IN THE SceneController CLASS !!
        */
    public int rollDie(Dice.dieType dieType) {

        switch (dieType) {
            case SpecialDie -> {
                return specialDie.rollDie();
            }
            default -> {
                return basicDie.rollDie();
            }
        }
    }
}