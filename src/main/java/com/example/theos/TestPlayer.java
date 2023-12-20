package com.example.theos;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class TestPlayer extends Circle { // 14.12. Mateo: für erste Testzwecke werden die Spieler/Charaktere erstmal abstahiert als Kreise dargestellt

    int currentField; // 14.12. Mateo: speichert den Index passend zur Fieldliste, wo sich der Spieler gerade befindet

    private int playerID;

    private static int playerCount = 0; // 14.12. Mateo: damit beim erstellen automatisch durchnummeriert wird

    public TestPlayer(double radius, Paint fill) {
        super(radius, fill);
        playerCount++;
        currentField = 0;
        playerID = playerCount;
    }

    @Override // 14.12. Mateo: für Ausgabe auf Konsole, welcher Spieler was gewürfelt hat
    public String toString() {
        return getClass().getSimpleName() + " " + playerID;
    }
}
