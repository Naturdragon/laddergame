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
    private Field currentField; // Current Field where the player is

    private ImageView imageView; //used to represent the character of the player on screen

    public Player(String characterName, int[] specialDiceArray, Path path) {
        name = characterName;
        basicDie = new Dice();
        specialDie = new Dice(specialDiceArray);
        imagePath = path;

        iD = nextID;
        nextID++;
    }

    public Player(String characterName, int[] specialDiceArray, Path path, Field currentCharacterField) {
        this(characterName, specialDiceArray, path);
        currentField = currentCharacterField;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return iD;
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

    /*
    The following three methods create the respective animations based on the sprite sheet loaded into imageview
    and allow later use of the animation e.g: .play() or .stop()
    Return a SpriteAnimation instance
     */
    public SpriteAnimation playIdle() {
        SpriteAnimation animation = new SpriteAnimation(imageView, 20, 20, 0, 0, 64, 64);
        animation.setCycleCount(Animation.INDEFINITE);

        return animation;
    }

    public SpriteAnimation playWalk() {
        SpriteAnimation animation = new SpriteAnimation(imageView, 19, 19, 0, 64, 64, 64);
        animation.setCycleCount(Animation.INDEFINITE);

        return animation;
    }

    public SpriteAnimation playSlip() {
        SpriteAnimation animation = new SpriteAnimation(imageView, 22, 22, 0, 128, 64, 64);
        animation.setCycleCount(Animation.INDEFINITE);

        return animation;

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
