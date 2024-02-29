package com.example.theos;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// The following code has been partially adapted from ChatGPT
public class SoundGame { // Manages music
    private static MediaPlayer mediaPlayer;

    public SoundGame(String filePath) { // Play music
        Media media = new Media(getClass().getResource(filePath).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void setVolume(double volume) { // Set volume
        mediaPlayer.setVolume(volume);
    }

    public void stop() { // Stop music
        mediaPlayer.stop();
    }
}