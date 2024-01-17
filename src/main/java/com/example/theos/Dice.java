package com.example.theos;

import java.util.Random;

public class Dice {
    // Random numbers use static to use the same random object continuously
    static Random rnd = new Random();
    private int charge;
    private int[] dice;
    private dieType type;

    // Constructor for a normal die
    public Dice()
    {
        charge = 0;
        dice = new int[]{1, 2, 3, 4, 5, 6};
        type = dieType.NormalDie;
    }

    // Constructor for a special die
    public Dice(int[] specDie)
    {
        this.charge = 3;
        this.dice = specDie;
        type = dieType.SpecialDie;
    }

    public int getCharge()
    {
        return charge;
    }

    public int[] getDice() {
        return dice;
    }

    // Method for increasing charge by 1
    public void addCharge()
    {
        charge++;
    }

    public dieType getDieType()
    {
        return type;
    }

    // Method for rolling a die
    public int rollDie()
    {
        if (type == dieType.SpecialDie) { // In case of a special die, decrease the charge by 1
            charge--;
        }

        return dice[rnd.nextInt(dice.length)];

    }

    // Types of die
    public enum dieType {
        NormalDie,
        SpecialDie
    }
}
