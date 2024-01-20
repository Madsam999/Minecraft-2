package GameEngine.utils;

import GameEngine.io.Input;
import org.example.Main;
import org.lwjglx.Sys;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MusicPlayer {
    public static void playMusic(String path) {
        try {
            File music = new File(Main.class.getResource(path).toURI());
            System.out.println(music.exists());
            if(music.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else {
                System.out.println("File does not exist.");
            }
        }
        catch (Exception e) {
            System.out.println("Error with playing sound.");
        }
    }
}
