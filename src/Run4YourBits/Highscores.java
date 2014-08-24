package Run4YourBits;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Menu to display the top ten highscores
 */
public class Highscores extends JFrame implements ActionListener {

    private JButton returns;
    private Fondo fondo;
    ArrayList highscores = null;
    private JTextArea text;
    Menu menu, backMenu;
    String score;
    ImageIcon icon, icon2;
    static MultithreadSystem sounds;
    static Thread musicPlayer, soundsPlayer;

    /**
     * Constructs the menu
     *
     * @param backMenu Main menu instance used to control its music
     */
    public Highscores(Menu backMenu) {

        this.setSize(800, 300);
        this.add(fondo = new Fondo("fondoNivel.png"));
        this.backMenu = backMenu;

        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images" + File.separator + "logo.png"));

        try {
            try (ObjectInputStream entradaObjs = new ObjectInputStream(new FileInputStream("Saves" + File.separator + "highscores.dat"))) {
                highscores = (ArrayList) entradaObjs.readObject();
                icon = new ImageIcon("Images/brick.png");
                icon2 = new ImageIcon("Images/brick2.png");
            }
        } catch (ClassNotFoundException | IOException| NullPointerException e) {
            System.out.println(e.getMessage());
            highscores = new ArrayList(10);
        }

        if (highscores.size() < 10) {
            for (int i = 0; i < highscores.size(); i++) {
                Score actual = (Score) highscores.get(i);
                if (i == 9) {
                    score += "X.- " + actual.getName() + "\t" + String.format("%05d", actual.getScore()) + "\n";

                } else {
                    score += (i + 1) + ".- " + actual.getName() + "\t" + String.format("%05d", actual.getScore()) + "\n";
                }
            }
        } else {
            for (int i = 0; i < 10; i++) {
                Score actual = (Score) highscores.get(i);
                if (i == 9) {
                    score += "X.- " + actual.getName() + "\t" + String.format("%05d", actual.getScore()) + "\n";

                } else {
                    score += (i + 1) + ".- " + actual.getName() + "\t" + String.format("%05d", actual.getScore()) + "\n";
                }            }
        }
        score = "\n" + score.substring(4);

        text = new JTextArea();
        text.setColumns(1);
        text.setRows(10);
        text.setEditable(false);

        try {
            text.setFont((Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts" + File.separator + "score.ttf")))).deriveFont(Font.BOLD, 18));
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Highscores.class.getName()).log(Level.SEVERE, null, ex);
        }
        text.setForeground(Color.BLACK);
        text.setBackground(new Color(255, 255, 255, 170));

        text.setBorder(BorderFactory.createMatteBorder(-1, -1, -1, -1, icon2));
        text.setText(score);

        fondo.setLayout(new BorderLayout());

        returns = CustomButton.createButton("Go Back", this.getGraphicsConfiguration(), 18);
        returns.addActionListener(this);

        fondo.add(text, BorderLayout.CENTER);
        fondo.add(returns, BorderLayout.SOUTH);

        try {
            this.getContentPane().setCursor(
                    Toolkit.getDefaultToolkit().createCustomCursor(
                    CompatibleImage.toCompatibleImage(
                    ImageIO.read(new File("Images" + File.separator + "cursor.png"))), new Point(0, 0), "cursor"));
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.pack();
        this.setLocationRelativeTo(null);
        backMenu.sounds.sound.sfx.stop();

        sounds = new MultithreadSystem();
        musicPlayer = new Thread(sounds);

        sounds.newThread("Music/msc highscore.wav");
        musicPlayer.start();

        this.setVisible(true);
    }

    /**
     * Defines and runs the actions for each button
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returns) {
            this.setVisible(false);
            backMenu.dispose();
            sounds.sound.sfx.stop();
            backMenu.musicPlayer.stop();
            menu = new Menu();
            this.dispose();
        }
    }
}