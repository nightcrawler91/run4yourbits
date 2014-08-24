package Run4YourBits;

import java.io.*;

/**
 * This class creates a second thread in the application that runs
 * simultaneously with other running threads, using the defined classes in the
 * main method.
 */
public class MultithreadSystem implements Runnable {

    Background soundsEffect;
    Thread t1;
    String musicSoundFX;
    SimpleSoundPlayer sound;
    InputStream stream;
    static float Soundlevel;
    static float Musiclevel;
    float configSave[];

    public MultithreadSystem() {
        try {
            try (ObjectInputStream entradaObjs = new ObjectInputStream(
                            new FileInputStream("Saves" + File.separator + "config.dat"))) {
                configSave = (float[]) entradaObjs.readObject();
                Musiclevel = configSave[0];
                Soundlevel = configSave[1];
            }
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a new, second thread
     *
     * @param mus Filename of the sound file
     */
    public void newThread(String mus) {
        // Create a new, second thread
        if (mus.contains("snd")) {
            t1 = new Thread(soundsEffect, "Sound_player.wav");
            musicSoundFX = mus;
            t1.start(); // Start the thread
        }
        if (mus.contains("msc")) {
            t1 = new Thread(soundsEffect, "Sound_player.wav");
            musicSoundFX = mus;
            t1.start();
        }
        if (mus.contains("thm")) {
            t1 = new Thread(soundsEffect, "Sound_player.wav");
            musicSoundFX = mus;
            t1.start();
        }
        if (mus.endsWith(".mp3")) {
            t1 = new Thread(soundsEffect, "Sound_player.mp3");
            musicSoundFX = mus;
            t1.start();
        }
    }

    /**
     * Starts the sound reproduction
     */
    public void run() {

        if (musicSoundFX.contains("snd")) {
            SimpleSoundPlayer.soundKey(musicSoundFX, Soundlevel);
        }
        if (musicSoundFX.contains("msc")) {
            SimpleSoundPlayer.musicKey(musicSoundFX, Musiclevel);
        }
        if (musicSoundFX.contains("thm")) {
            SimpleSoundPlayer.themeKey(musicSoundFX, Musiclevel);
        }
        if (musicSoundFX.contains("mp3")) {
            SimpleSoundPlayer.playMusic(musicSoundFX);
        }
    }

    /**
     * Loads and plays the sound effect
     *
     * @param soundEffect Filename of the sound effect file
     */
    public void soundKey(String soundEffect) {//new one 3:18pm for wav
        sound = new SimpleSoundPlayer(soundEffect);
        // create the stream to play
        stream = new ByteArrayInputStream(sound.getSamples());
        // play the sound
        sound.play(stream);
    }
}
