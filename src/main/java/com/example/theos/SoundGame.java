package com.example.theos;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// Der folgende Code wurde teilweise angepasst von [ChatGPT]
public class SoundGame {
    private static MediaPlayer mediaPlayer;

    public SoundGame(String filePath) { // Plays music
        Media media = new Media(getClass().getResource(filePath).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }

    public void stop() { // Stops music
        mediaPlayer.stop();
    }
}