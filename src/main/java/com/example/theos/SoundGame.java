package com.example.theos;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class SoundGame {
    private static MediaPlayer mediaPlayer;

    public static void playBackgroundMusic() {
        String musicFile = "/sound/sound.mp3";
        Media media = new Media(Objects.requireNonNull(SoundGame.class.getResource(musicFile).toExternalForm())); // Create Media object from the music file
        mediaPlayer = new MediaPlayer(media); // Create MediaPlayer using the Media object

        mediaPlayer.setVolume(0.5);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void stopBackgroundMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop the music
        }
    }
}