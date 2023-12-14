package com.example.theos;

public class Field {

    public int animationPointX;
    public int animationPointY;
    public int fieldID; // 14.12. Mateo: wird in movePlayer Methode verwendet

    private static int fieldCount = 0; // 14.12. Mateo: um Fieldobjekte beim erstellen mit fortlaufender fieldID zu versehen

    public Field(int animationPointX, int animationPointY) {
        this.animationPointX = animationPointX;
        this.animationPointY = animationPointY;

        this.fieldID = fieldCount;
        fieldCount++;
    }
}
