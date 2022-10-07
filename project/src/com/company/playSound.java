package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class playSound {
    public playSound() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File url = new File("mixkit-retro-game-notification-212.wav");
        Clip clip = AudioSystem.getClip();

        AudioInputStream ais = AudioSystem.getAudioInputStream( url );
        clip.open(ais);
        clip.start();
    }
}
