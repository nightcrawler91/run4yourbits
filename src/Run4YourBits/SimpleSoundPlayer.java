package Run4YourBits;

import java.io.*;
import javax.sound.sampled.*;
import javazoom.jl.player.Player;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * The SimpleSoundPlayer class encapsulates a sound that can be opened from the
 * file system and later played.
 */
public class SimpleSoundPlayer {

    private AudioFormat format;
    private byte[] samples;
    //Config nivelVolmn;
    static float Soundlevel;                   /////////////////////////////NIVEL DE VOLUMEN!!!!!!!
    static float Musiclevel;
    static Clip sfx;
    static float[] configSave;

    /**
     * Player construction
     *
     * @param format file extension
     * @param samples array where the file is stores
     */
    public SimpleSoundPlayer(AudioFormat format, byte[] samples) {
        this.format = format;
        this.samples = samples;
//        try {
//            try (ObjectInputStream entradaObjs = new ObjectInputStream(
//                            new FileInputStream("Saves" + File.separator + "config.dat"))) {
//                configSave = (float[]) entradaObjs.readObject();
//                Musiclevel = configSave[0];
//                Soundlevel = configSave[1];
//                System.out.println("Volumen music: " + Musiclevel);
//                System.out.println("Volumen sound: " + Soundlevel);
//            }
//        } catch (ClassNotFoundException | IOException e) {
//            System.out.println(e.getMessage());
//        }
    }

    /**
     * Opens a sound from a file.
     */
    public SimpleSoundPlayer(String filename) {
        try {
//            try {
//                try (ObjectInputStream entradaObjs = new ObjectInputStream(
//                                new FileInputStream("Saves" + File.separator + "config.dat"))) {
//                    configSave = (float[]) entradaObjs.readObject();
//                    Musiclevel = configSave[0];
//                    Soundlevel = configSave[1];
//                    System.out.println("Volumen music: " + Musiclevel);
//                    System.out.println("Volumen sound: " + Soundlevel);
//                }
//            } catch (ClassNotFoundException | IOException e) {
//                System.out.println(e.getMessage());
//            }
            // open the audio input stream
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            format = stream.getFormat();

            // get the audio samples
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException ex) {
            ex.getMessage();
        }
    }

    /**
     * Gets the samples of this sound as a byte array.
     */
    public byte[] getSamples() {
        return samples;
    }

    /**
     * Gets the samples from an AudioInputStream as an array of bytes.
     */
    private byte[] getSamples(AudioInputStream audioStream) {
        // get the number of bytes to read
        int length = (int) (audioStream.getFrameLength() * format.getFrameSize());

        // read the entire stream
        byte[] samples = new byte[length];
        DataInputStream is = new DataInputStream(audioStream);
        try {
            is.readFully(samples);
        } catch (IOException ex) {
            ex.getMessage();
        }

        // return the samples
        return samples;
    }

    /**
     * Plays a stream. This method blocks (doesn't return) until the sound is
     * finished playing.
     */
    public void play(InputStream source) {

        // use a short, 100ms (1/10th sec) buffer for real-time
        // change to the sound stream
        int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
        byte[] buffer = new byte[bufferSize];

        // create a line to play to
        SourceDataLine line;
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, bufferSize);
        } catch (LineUnavailableException ex) {
            ex.getMessage();
            return;
        }

        // start the line
        line.start();

        // copy data to the line
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1) {
                    line.write(buffer, 0, numBytesRead);
                }
            }
        } catch (IOException ex) {
            ex.getMessage();
        }

        // wait until all data is played, then close the line
        line.drain();
        line.close();

    }

    /**
     * MP3 player
     *
     * @param musicName filename of the music file
     */
    public static void playMusic(String musicName) {  ///MP3 PLAYER          /////////////////////////NIVEL DE VOLUMEN!!!!!!!
//        musicVolume = Soundlevel;
        try {
            FileInputStream file = new FileInputStream(musicName);
            Player playmp3 = new Player(file);


            playmp3.play();                                                         /////////////POSIBLE LOOP!!!!

            while (true) {
                if (playmp3.isComplete()) {
                    playmp3.close();
                    playmp3.play();
                }
            }

        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Plays a sound effect
     *
     * @param soundEffect filename of the sound effect
     */
//    public static void soundKey(String soundEffect) {//new one 3:18pm for wav
//        SimpleSoundPlayer sound = new SimpleSoundPlayer(soundEffect);
//        // create the stream to play
//        InputStream stream = new ByteArrayInputStream(sound.getSamples());
//        // play the sound
//        sound.play(stream);
//    }
    public static void soundKey(String soundEffect, float volumen) {                //////////////// Nuevo metodo que carga el SFX pero con volumen modificable :D  11.53pm 24 Marzo de 2012.

        try {
            try {
                try (ObjectInputStream entradaObjs = new ObjectInputStream(
                                new FileInputStream("Saves" + File.separator + "config.dat"))) {
                    configSave = (float[]) entradaObjs.readObject();
                    Musiclevel = configSave[1];
                    Soundlevel = configSave[0];
                    //System.out.println("Volumen music: " + Musiclevel);
                    //System.out.println("Volumen sound: " + Soundlevel);
                }
            } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.getMessage());
            }
            //volumen = Soundlevel;
            Clip sfx = AudioSystem.getClip();
            // Se carga con un fichero wav
            sfx.open(AudioSystem.getAudioInputStream(new File(soundEffect)));
            FloatControl gainControl = (FloatControl) sfx.getControl(FloatControl.Type.MASTER_GAIN);  // En esta seccion se cambia el volumen de la musica 11:45pm 24 Marzo 2012
            //System.out.println("soundLevel: " + Soundlevel);
            gainControl.setValue(Soundlevel);
            // Comienza la reproducción
            sfx.start();
            //System.out.println("Sound Volumen at:" + Soundlevel);
        } catch (Exception e) {
            System.out.println("SoundFX problem detected!!!" + e);
        }
    }

    public static void musicKey(String Music, float volumen) {                //////////////// Nuevo metodo que carga la Musica pero con volumen modificable :D  11.53pm 24 Marzo de 2012.

        try {
            try {
                try (ObjectInputStream entradaObjs = new ObjectInputStream(
                                new FileInputStream("Saves" + File.separator + "config.dat"))) {
                    configSave = (float[]) entradaObjs.readObject();
                    Musiclevel = configSave[1];
                    Soundlevel = configSave[0];
                    //System.out.println("Volumen music: " + Musiclevel);
                    //System.out.println("Volumen sound: " + Soundlevel);
                }
            } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.getMessage());
            }
            //volumen = Musiclevel;
            sfx = AudioSystem.getClip();
            // Se carga con un fichero wav

            sfx.open(AudioSystem.getAudioInputStream(new File(Music)));
            FloatControl gainControl = (FloatControl) sfx.getControl(FloatControl.Type.MASTER_GAIN);  // En esta seccion se cambia el volumen de la musica 11:45pm 24 Marzo 2012
            gainControl.setValue(Musiclevel);
            // Comienza la reproducción

//            sfx.start();
            sfx.loop(-1);                                    ////Loop para la musica.
            //System.out.println("Music Volumen at:" + Musiclevel);

        } catch (Exception e) {
            System.out.println("Music problem detected!!!" + e);
        }
    }

    public static void themeKey(String Music, float volumen) {                //////////////// Nuevo metodo que carga el theme pero con volumen modificable :D  11.53pm 24 Marzo de 2012.

        try {
            try {
                try (ObjectInputStream entradaObjs = new ObjectInputStream(
                                new FileInputStream("Saves" + File.separator + "config.dat"))) {
                    configSave = (float[]) entradaObjs.readObject();
                    Musiclevel = configSave[1];
                    Soundlevel = configSave[0];
                    //System.out.println("Volumen music: " + Musiclevel);
                    //System.out.println("Volumen sound: " + Soundlevel);
                }
            } catch (ClassNotFoundException | IOException e) {
                System.out.println(e.getMessage());
            }
            //volumen = Musiclevel;
            sfx = AudioSystem.getClip();
            // Se carga con un fichero wav

            sfx.open(AudioSystem.getAudioInputStream(new File(Music)));
            FloatControl gainControl = (FloatControl) sfx.getControl(FloatControl.Type.MASTER_GAIN);  // En esta seccion se cambia el volumen de la musica 11:45pm 24 Marzo 2012
            gainControl.setValue(Musiclevel);
            // Comienza la reproducción

            sfx.start();

            //System.out.println("Music Volumen at:" + Musiclevel);

        } catch (Exception e) {
            System.out.println("Music problem detected!!!" + e);
        }
    }

    public static void stop() {
        sfx.stop();
    }

    /**
     * Creates and manages the audio player
     *
     * @param soundEffect filename of the sound effect
     */
    public static void soundEffect(String soundEffect) {
        AudioPlayer MGP = AudioPlayer.player;
        AudioStream BGM;
        //AudioData MD;
//            ContinuousAudioDataStream loop= null;
        try {

            BGM = new AudioStream(new FileInputStream(soundEffect));
            //MD = BGM.getData();
//            loop = new ContinuousAudioDataStream(MD);
        } catch (IOException ex) {
            ex.getMessage();
        }

        MGP.start();
    }
}