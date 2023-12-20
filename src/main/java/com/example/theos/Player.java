package com.example.theos;

import java.nio.file.Path;

public class Player {
    private String name;
    private static int nextID = 0; // Used for automatic ID distribution
    private int iD;
    private Dice basicDie;
    private Dice specialDie;
    private Path imagePath;
    private Field currentField; // Current Field where the player is

    public Player(String characterName, int[] specialDiceArray, Path path)
    {
        name = characterName;
        basicDie = new Dice();
        specialDie = new Dice(specialDiceArray);
        imagePath = path;

        iD = nextID;
        nextID++;
    }

    public Player(String characterName, int[] specialDiceArray, Path path, Field currentCharacterField)
    {
        this(characterName, specialDiceArray, path);
        currentField = currentCharacterField;
    }

    public String getName()
    {
        return name;
    }

    public int getID()
    {
        return iD;
    }

    public Path getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(Path path)
    {
        imagePath = path;
    }

    public Field getCurrentField()
    {
        return currentField;
    }

    public void setCurrentField(Field currentField)
    {
        this.currentField = currentField;
    }

    /*
    Rolls the dice which are saved in the Player class
    Used to choose the die to roll
    returns the die result
    !! DIE SPECIAL DIE CHARGES DO NOT GET CHECKED HERE. THIS NEEDS TO BE DONE IN THE APPLICATION CLASS !!
    */
    public int rollDie(Dice.dieType dieType)
    {

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
