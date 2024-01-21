package com.example.theos;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class SoundGame {

    @Override
    public void start(Stage stage) {
        String musicPath = "music.mp3";
        double initialVolume = 0.5;

        MediaPlayer mediaPlayer = createMediaPlayer(musicPath, initialVolume);
        mediaPlayer.play();

        stage.setTitle("Background Music Example");
        stage.show();
    }

    private MediaPlayer createMediaPlayer(String musicPath, double initialVolume) {
        Media media = new Media(Paths.get(musicPath).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setVolume(initialVolume);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        return mediaPlayer;
    }

}

