package com.example.theos;

import Animation.SpriteAnimation;
import javafx.animation.Animation;
import javafx.scene.image.ImageView;

import java.nio.file.Path;

public class Player {
    private String name;
    private static int nextID = 0; // Used for automatic ID distribution
    private int iD;
    private Dice basicDie;
    private Dice specialDie;
    private Path imagePath;
    private Path winningImagePath;
    private Field currentField; // Current Field where the player is
    private SpriteAnimation currentAnimation;
    private ImageView imageView; //used to represent the character of the player on screen
    private int turnCount;

    public Player(String characterName, int[] specialDiceArray, Path path) {
        name = characterName;
        basicDie = new Dice();
        specialDie = new Dice(specialDiceArray);
        imagePath = path;

        iD = nextID;
        nextID++;
        turnCount = 0;
    }

    public Player(String characterName, int[] specialDiceArray, Path path, Path winningImagePath, Field currentCharacterField) {
        this(characterName, specialDiceArray, path);
        this.winningImagePath = winningImagePath;
        currentField = currentCharacterField;
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
    The following three methods create the respective animations based on the sprite sheet and
    load it into the currentAnimation variable of character. Any previous animation is stopped to prevent animation-bugs.
    Return nothing
     */
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

    public void playSwim() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        currentAnimation = new SpriteAnimation(imageView, 20, 20, 0, 148, 74, 74);
        currentAnimation.setCycleCount(Animation.INDEFINITE);
        currentAnimation.play();
    }

    public void playFallTransparent() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        currentAnimation = new SpriteAnimation(imageView, 6, 6, 0, 222, 74, 74);
        currentAnimation.setCycleCount(1);
        currentAnimation.play();
    }

    public void playFallOpaque() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        currentAnimation = new SpriteAnimation(imageView, 6, 6, 0, 296, 74, 74);
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

    public void playJump() {
        if (currentAnimation != null) {
            currentAnimation.stop();
        }
        currentAnimation = new SpriteAnimation(imageView, 6, 6, 740, 296, 74, 74);
        currentAnimation.setCycleCount(1);
        currentAnimation.play();
    }

    /*
        Rolls the dice which are saved in the Player class
        Used to choose the die to roll
        returns the die result
        !! DIE SPECIAL DIE CHARGES DO NOT GET CHECKED HERE. THIS NEEDS TO BE DONE IN THE APPLICATION CLASS !!
        */
    public int rollDie(Dice.dieType dieType) {

        switch (dieType) {
            /*
            // Is it needed? The IDE complains that this statement and the default statement are the same. Should we ignor it or should we remove the statement?
            case NormalDie -> {
                return basicDie.rollDie();
            }
            break;
             */
            case SpecialDie -> {
                return specialDie.rollDie();
            }
            default -> {
                return basicDie.rollDie();
            }
        }
    }
}
