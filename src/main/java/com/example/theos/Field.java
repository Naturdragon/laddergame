package com.example.theos;

public class Field {

    static int idCounter = 0;
    private int id;
    private fieldType type;
    private Coordinates coordinates;

    public Field(fieldType type, double xPercentage, double yPercentage) {
        id = idCounter;
        idCounter++;
        this.type = type;
        coordinates = new Coordinates();
        coordinates.x = (int) (TheOs.SCENE_WIDTH * xPercentage / 100);
        coordinates.y = (int) (TheOs.SCENE_HEIGHT * yPercentage / 100);
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
    public int getId(){ return id; }

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

    class Coordinates { // nested class for coordinates of fields; TODO: place getX and getY methods in coordinate class? like so: field.getCoordinates.getX()

        int x;
        int y;
    }
}
