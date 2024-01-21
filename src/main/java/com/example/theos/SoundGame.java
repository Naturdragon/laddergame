package com.example.theos;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

class SoundGame {
    private Clip clip;

    public SoundGame(String filePath) {
        try {
            // Classloader to load the sound file as a resource
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));

                clip = AudioSystem.getClip();
                clip.open(audioStream);
            } else {
                System.out.println("Datei nicht gefunden: " + filePath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //plays the sound in a loop
    public void playLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    //stop the playback of a sound
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
