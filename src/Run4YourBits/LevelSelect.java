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
import javax.swing.JButton;
import javax.swing.JFrame;
/**
 * Menu that allows you tou select each fo the different levels
 */
public class LevelSelect extends JFrame implements ActionListener {

    private JButton mario;
    private JButton kirby;
    private JButton shadow;
    private JButton zero;
    private JButton returnMenu;
    private JButton exit;
    Fondo fondo;
    Menu menu, pastMenu;
    Game game;
/**
 * Creates the menu
 * @param pastMenu Menu instance used to control its music
 */
    public LevelSelect(Menu pastMenu) {

        this.setSize(800, 300);
        this.add(fondo = new Fondo("fondoNivel.png"));
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images" + File.separator + "logo.png"));

        fondo.setLayout(new GridLayout(3, 2, 100, 50));

        this.pastMenu = pastMenu;

        mario = CustomButton.createButton("Level 1", this.getGraphicsConfiguration(),18);
        mario.addActionListener(this);

        kirby = CustomButton.createButton("Level 2", this.getGraphicsConfiguration(),18);
        kirby.addActionListener(this);

        shadow = CustomButton.createButton("Level 3", this.getGraphicsConfiguration(),18);
        shadow.addActionListener(this);

        zero = CustomButton.createButton("Level 4", this.getGraphicsConfiguration(),18);
        zero.addActionListener(this);

        returnMenu = CustomButton.createButton("Return to Menu", this.getGraphicsConfiguration(),18);
        returnMenu.addActionListener(this);

        exit = CustomButton.createButton("Exit", this.getGraphicsConfiguration(),18);
        exit.addActionListener(this);

        fondo.add(mario);
        fondo.add(kirby);
        fondo.add(shadow);
        fondo.add(zero);
        fondo.add(returnMenu);
        fondo.add(exit);



        try {
            this.getContentPane().setCursor(
                    Toolkit.getDefaultToolkit().createCustomCursor(
                    CompatibleImage.toCompatibleImage(
                    ImageIO.read(new File("Images" + File.separator + "cursor.png"))), new Point(0, 0), "cursor"));
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.setVisible(true);
    }
/**
 * Defines and runs each button actions
 * @param ae Required ActionEvent
 */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == mario) {
            pastMenu.sounds.sound.sfx.stop();
            pastMenu.musicPlayer.stop();
            pastMenu.dispose();
            game = new Game(1);
            this.setVisible(false);
            this.dispose();
        } else if (ae.getSource() == kirby) {
            pastMenu.sounds.sound.sfx.stop();
            pastMenu.musicPlayer.stop();
            pastMenu.dispose();
            game = new Game(2);
            this.dispose();
        } else if (ae.getSource() == shadow) {
            pastMenu.sounds.sound.sfx.stop();
            pastMenu.musicPlayer.stop();
            pastMenu.dispose();
            game = new Game(3);
            this.dispose();
        } else if (ae.getSource() == zero) {
            pastMenu.sounds.sound.sfx.stop();
            pastMenu.musicPlayer.stop();
            pastMenu.dispose();
            game = new Game(4);
            this.dispose();
        } else if (ae.getSource() == returnMenu) {
            pastMenu.sounds.sound.sfx.stop();
            pastMenu.musicPlayer.stop();
            pastMenu.dispose();
            this.setVisible(false);
            menu = new Menu();
            this.dispose();
        } else if (ae.getSource() == exit) {
            System.exit(0);
        }
    }
}