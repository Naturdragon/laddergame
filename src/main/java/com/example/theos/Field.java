package com.example.theos;

public class Field {

    private static int idCounter = 0; // used to assign ongoing id to every new Field
    private int id;
    private fieldType type;
    private Coordinates coordinates;

    public Field(fieldType type, double xPercentage, double yPercentage) { // field coordinates are calculated based on percentage, idea is to make the layout more flexible
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
        LadderField, // includes snakes (are seen as the same gameplay wise)
        SpecialChargeField,
        CrossoverField
    }

    class Coordinates { // nested class for coordinates of fields;

        private int x;
        private int y;
    }
}
