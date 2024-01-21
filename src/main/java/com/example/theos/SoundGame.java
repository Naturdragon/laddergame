package com.example.theos;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundGame {
    private static MediaPlayer mediaPlayer;

    public SoundGame(String filePath) { // plays music
        Media media = new Media(getClass().getResource(filePath).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }

    public void stop() { // stops music
        mediaPlayer.stop();
    }
}