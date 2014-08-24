package Run4YourBits;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Main menu used to access all sections of the project
 */
public class Menu extends JFrame implements ActionListener {

    Fondo fondo;
    private JButton nuevo;
    private JButton salir;
    private JButton highscoreButton;
    private JButton configButton;
    private Config config;
    private LevelSelect levelSelect;
    private Highscores highscore;
    private ImageIcon icon;
    static MultithreadSystem sounds;
    static Thread musicPlayer, soundsPlayer;
/**
 * Creates and displays the menu
 */
    public Menu() {

        this.setSize(800, 300);
        fondo=new Fondo("fondoMenu.png");
        fondo.setBorder(BorderFactory.createMatteBorder(-1, -1, -1, -1, icon));
        this.add(fondo);        
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images" + File.separator + "logo.png"));

        fondo.setLayout(new GridLayout(2, 2, 200, 200));

        nuevo = CustomButton.createButton("New Game", this.getGraphicsConfiguration(), 18);
        nuevo.addActionListener(this);
        salir = CustomButton.createButton("Exit", this.getGraphicsConfiguration(), 18);
        salir.addActionListener(this);
        highscoreButton = CustomButton.createButton("Highscores", this.getGraphicsConfiguration(), 18);
        highscoreButton.addActionListener(this);
        configButton = CustomButton.createButton("Config", this.getGraphicsConfiguration(), 18);
        configButton.addActionListener(this);

        fondo.add(nuevo);
        fondo.add(highscoreButton);
        fondo.add(configButton);
        fondo.add(salir);

        try {
            this.getContentPane().setCursor(
                    Toolkit.getDefaultToolkit().createCustomCursor(
                    CompatibleImage.toCompatibleImage(
                    ImageIO.read(new File("Images" + File.separator + "cursor.png"))), new Point(0, 0), "cursor"));
            icon = new ImageIcon("Images/brick.png");
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        //crear e iniciar reproducción de sonido
        sounds = new MultithreadSystem();
        musicPlayer = new Thread(sounds);

        sounds.newThread("Music/msc menu.wav");
        musicPlayer.start();

        this.setVisible(true);
    }
/**
 * Define and runs the actions for each button
 * @param ae Required ActionEvent
 */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == nuevo) {
            this.setVisible(false);
          
            sounds = new MultithreadSystem();
            sounds.newThread("SFX/snd button.wav");
            soundsPlayer = new Thread(sounds);
            soundsPlayer.start();

            levelSelect = new LevelSelect(this);            
            this.dispose();
        } else if (ae.getSource() == highscoreButton) {
            this.setVisible(false);

            sounds = new MultithreadSystem();
            sounds.newThread("SFX/snd button.wav");
            soundsPlayer = new Thread(sounds);
            soundsPlayer.start();
            
            highscore = new Highscores(this);
            this.dispose();
        } else if (ae.getSource() == configButton) {

            sounds = new MultithreadSystem();
            sounds.newThread("SFX/snd button.wav");
            soundsPlayer = new Thread(sounds);
            soundsPlayer.start();

            config = new Config(this);
            this.dispose();
        } else if (ae.getSource() == salir) {
            System.exit(0);
        }
    }
}
