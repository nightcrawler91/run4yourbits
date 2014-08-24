package Run4YourBits;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * Menu for volume configurations for both sound and music
 * @author jrlpp
 */
public class Config extends JFrame implements ActionListener {

    private JButton returns;
    private Fondo fondo;
    float[] configSave;
    Menu menu, backMenu;
    String score;
    ImageIcon icon;
    static float VolumenMusic;                    //////////////////////////////////Alto 0.0f--------bajo -30.0f
    static float VolumenSound;                    //////////////////////////////////Alto 0.0f--------bajo -30.0f
    JSlider musicSlider, volumeSlider;
/**
 * Creates a new configuration method
 * @param backMenu Menu instance used to control its music
 */
    public Config(Menu backMenu) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            try (ObjectInputStream entradaObjs = new ObjectInputStream(
                            new FileInputStream("Saves" + File.separator + "config.dat"))) {
                configSave = (float[]) entradaObjs.readObject();
            }
        } catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }

        this.setSize(800, 300);
        this.add(fondo = new Fondo("fondoConfig.png"));
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images" + File.separator + "logo.png"));
        this.backMenu = backMenu;

        icon = new ImageIcon("Images/brick.png");

        fondo.setLayout(new BorderLayout());

        returns = CustomButton.createButton("Go Back", this.getGraphicsConfiguration(), 18);
        returns.addActionListener(this);

        musicSlider = new JSlider(JSlider.HORIZONTAL, -30, 0, (int) configSave[0]);
        musicSlider.setOpaque(false);
        musicSlider.setMajorTickSpacing(10);
        musicSlider.setMinorTickSpacing(2);
        musicSlider.setPaintTicks(true);

        volumeSlider = new JSlider(JSlider.HORIZONTAL, -30, 0, (int) configSave[1]);
        volumeSlider.setOpaque(false);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(2);
        volumeSlider.setPaintTicks(true);


        fondo.add(returns, BorderLayout.SOUTH);
        fondo.add(musicSlider, BorderLayout.NORTH);
        fondo.add(volumeSlider, BorderLayout.CENTER);


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
 * Defines each button's actions
 * @param e Required ActionEvent
 */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returns) {
            this.setVisible(false);
            backMenu.sounds.sound.sfx.stop();
            backMenu.dispose();
            configSave[0] = (float) musicSlider.getValue();
            configSave[1] = (float) volumeSlider.getValue();
            try {
                try (ObjectOutputStream salidaObjs = new ObjectOutputStream(
                                new FileOutputStream("Saves" + File.separator + "config.dat"))) {
                    salidaObjs.writeObject(configSave);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            menu = new Menu();
            this.dispose();
        }
    }
}