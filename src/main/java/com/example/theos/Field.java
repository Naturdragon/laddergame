package com.example.theos;

public class Field {

    private fieldType type;
    private Coordinates coordinates;

    public Field(fieldType type, int xPercentage, int yPercentage) {
        this.type = type;
        coordinates.x = Application.sceneWidth * xPercentage / 100;
        coordinates.y = Application.sceneHeight * yPercentage / 100;
    }

    public fieldType getType() {
        return type;
    }

    public int getX() {
        return coordinates.x;
    }

    public int getY() {
        return coordinates.y;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    // Types of Fields
    public enum fieldType {
        NormalField,
        LadderField,
        SpecialChargeField,
        CrossoverField
    }

    class Coordinates { // nested class for coordinates of fields

        int x;
        int y;
    }
}
