package com.example.theos;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

class SoundGame {
    private Clip clip;

    public SoundGame(String filePath) {
        try {
            // Create an AudioInputStream from the provided audio file path
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));

            // Get a Clip instance and open the audio stream
            clip = AudioSystem.getClip();
            clip.open(audioStream);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playLoop() {
        if (clip != null) {
            // Start playing the audio in a loop
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            // Stop the audio
            clip.stop();
        }
    }
}



