package Run4YourBits;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * Menu for setting new highscores
 */
public class HighscoreDialog extends JFrame implements ActionListener {

    private JButton submitButton;
    Fondo fondo;
    Background back;
    Menu menu;
    JTextField text;
    Score score;
    ArrayList highscores;
    EndMenu end;
    //MultithreadSystem sounds;
    //Thread soundPlayer;
    //String soundToPlay;

    /**
     * Constructs a new menu
     *
     * @param back The level that displays the menu
     * @param sounds MultithreadSystem used for sound playback
     * @param soundPlayer Thread used for sound playback
     * @param soundToPlay String used to locate the correct sound fie
     */
    public HighscoreDialog(Background back, MultithreadSystem sounds, Thread soundPlayer, String soundToPlay) {

        this.back = back;
//        this.sounds=sounds;
//        this.soundPlayer=soundPlayer;
//        this.soundToPlay=soundToPlay;

        this.setUndecorated(true);
        this.setTitle("Submit");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(180, 350);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images" + File.separator + "logo.png"));

        this.add(fondo = new Fondo("fondoPausa.png"));
        fondo.setLayout(new BorderLayout());

        submitButton = CustomButton.createButton("Go", this.getGraphicsConfiguration(), 16);
        submitButton.addActionListener(this);

        text = new JTextField("Name");
        text.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                text.setText("");

            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        try {
            text.setFont((Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts" + File.separator + "high.ttf")))).deriveFont(Font.BOLD, 14));
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Highscores.class.getName()).log(Level.SEVERE, null, ex);
        }

        fondo.add(text, BorderLayout.CENTER);
        fondo.add(submitButton, BorderLayout.SOUTH);


        try {
            this.getContentPane().setCursor(
                    Toolkit.getDefaultToolkit().createCustomCursor(
                    CompatibleImage.toCompatibleImage(
                    ImageIO.read(new File("Images" + File.separator + "cursor.png"))), new Point(0, 0), "cursor"));
            try (ObjectInputStream entradaObjs = new ObjectInputStream(new FileInputStream("Saves" + File.separator + "highscores.dat"))) {
                highscores = (ArrayList) entradaObjs.readObject();
            }
        } catch (ClassNotFoundException | IOException ex) {
            highscores = new ArrayList(10);
        }

        this.pack();

        this.setLocationRelativeTo(null);

        back.pause = true;

        this.setVisible(true);

        this.requestFocus();

        sounds = new MultithreadSystem();
        sounds.newThread(soundToPlay);//Effecto de sonido cuando se gana el nivel.
        soundPlayer = new Thread(sounds);
        soundPlayer.start();

    }

    /**
     * Define the actions for each button and runs them accordingly
     *
     * @param e Required ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            if (!text.getText().equals("Name") && text.getText().length() >= 4) {
                score = new Score(text.getText(), back.character.score);
                highscores.add(score);
                Collections.sort(highscores);
                
                try {
                    try (ObjectOutputStream salidaObjs = new ObjectOutputStream(
                                    new FileOutputStream("Saves" + File.separator + "highscores.dat"))) {
                        salidaObjs.writeObject(highscores);
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                
                //sounds.sound.sfx.stop();
                end = new EndMenu(back);
                this.dispose();
            }
        }
    }
}