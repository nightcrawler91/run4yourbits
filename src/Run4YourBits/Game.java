package Run4YourBits;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFrame;

/**
 * This class is the main method for the frame and creates the different levels
 */
public class Game extends JFrame {

    final int screenHeight = 800;
    final int screenWidth = 300;
    int borderWidth = 24;
    Background level;
    BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    Cursor blankCursor;

   /**
    * Constructs a level with custom values
    * @param level the level to add to the game
    */
    public Game(Background level) {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Images" + File.separator + "logo.png"));
        frame.getContentPane().add(level);
        frame.setTitle("Run 4 Your Bits");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenHeight, (screenWidth + borderWidth));
        frame.getContentPane().setCursor(blankCursor);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Constructs the level with default values for each level
     */
    public Game(int option) {
        
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Images" + File.separator + "logo.png"));
        switch (option) {
            case 1:
                level = new Background(5200, 215, 150,
                        "animacionMario.png", "animacionMarioHit.png", "marioGameOver.png", 6, 1, 75,
                        "fondoMario.jpg", "marco.png",
                        "plant.png", "bill.png", 600,238,
                        "msc marioLevel.wav", "snd marioJump.wav", "snd marioHit.wav", "snd marioDash.wav", 30, 
                        Color.BLACK,true);
                break;
            case 2:
                level = new Background(7200, 228, 150,
                        "animacionKirby.png", "animacionKirby.png", "animacionKirby.png", 12, 1, 75,
                        "fondoKirby.jpg", "marco.png",
                        "spiky.png", "kingDedede.png", 500,240,
                        "msc kirbyLevel.wav", "snd kirbyJump.wav", "snd kirbyHit.wav", "snd kirbyDash.wav", 17,
                        Color.BLACK,true);
                break;
            case 3:
                level = new Background(9200, 228, 150,
                        "animacionShadow.png", "animacionShadow.png", "shadowGameOver.png", 10, 1, 75,
                        "fondoShadow.jpg", "marco.png",
                        "mole.png", "rocket.png", 400,250,
                        "msc shadowLevel.wav", "snd shadowJump.wav", "snd shadowHit.wav", "snd shadowDash.wav", 30,
                        Color.WHITE,true);
                break;
            case 4:
                level = new Background(11200, 218, 150,
                        "animacionZero.png", "animacionZero.png", "zeroGameOver.png", 16, 1, 75,
                        "fondoZero.jpg", "marco.png",
                        "metaur.png", "chopper.png", 300,230,
                        "msc zeroLevel.wav", "snd zeroJump.wav", "snd zeroHit.wav", "snd zeroDash.wav", 30,
                        Color.WHITE,true);
                break;
        }

        frame.getContentPane().add(level);
        frame.setTitle("Run 4 Your Bits!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenHeight, (screenWidth + borderWidth));
        frame.getContentPane().setCursor(blankCursor);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
/**
 * Accesor for the actual level
 * @return level
 */
    public Background getLevel() {
        return level;
    }
/**
 * Mutator for the actual level
 * @param level 
 */
    public void setLevel(Background level) {
        this.level = level;
    }
}