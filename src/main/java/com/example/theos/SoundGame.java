package com.example.theos;


import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

class SoundGame {
    private Clip clip;

    public SoundGame(String filePath) {
        try {
            // Verwende den ClassLoader, um die Sounddatei als Ressource zu laden
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));

                // Erstelle eine Clip-Instanz und öffne den Audio-Stream
                clip = AudioSystem.getClip();
                clip.open(audioStream);
            } else {
                System.out.println("Datei nicht gefunden: " + filePath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playLoop() {
        if (clip != null) {
            // Starte das Audio im Loop
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            // Stoppe das Audio
            clip.stop();
        }
    }
}

