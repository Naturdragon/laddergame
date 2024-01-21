package com.example.theos;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundGame {
    private MediaPlayer mediaPlayer;

    public SoundGame(String filePath) { // plays  music
        Media media = new Media(getClass().getResource(filePath).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
    }

    public void stop() { // stops music
        mediaPlayer.stop();
    }
}