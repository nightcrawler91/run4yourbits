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
 * Menu displayed at the end each game
 */
public class EndMenu extends JFrame implements ActionListener {

    private JButton retry, returnMenu;
    Fondo fondo;
    Background back;
    Menu menu;

    /**
     * Constructs the EndMenu
     *
     * @param back returns the menu
     */
    public EndMenu(Background back) {

        this.back = back;

        this.setUndecorated(true);
        this.setTitle("Pause");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(180, 350);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images" + File.separator + "logo.png"));

        this.add(fondo = new Fondo("fondoPausa.png"));
        fondo.setLayout(new GridLayout(2, 2));

        retry = CustomButton.createButton("Retry", this.getGraphicsConfiguration(), 16);
        retry.addActionListener(this);

        returnMenu = CustomButton.createButton("Return to Menu", this.getGraphicsConfiguration(), 16);
        returnMenu.addActionListener(this);

        fondo.add(returnMenu);
        fondo.add(retry);

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


        this.setVisible(true);

        this.requestFocus();
    }

    /**
     * Defines and runs the action for each button
     *
     * @param e Required ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == retry) {

            Background newLevel = new Background(back.getLevelLength(), back.getStandardHeight(),
                    back.getMaxHeightValue(), back.getCharacterImage(),
                    back.getCharacterHitImage(), back.getCharacterGameOverImage(),
                    back.getCharacterSteps(), back.getSpeed(), 75, back.getBackgroundImage(),
                    back.getFrameImage(), back.getLowObstacleImage(),
                    back.getHighObstacleImage(), back.getObstacleDistance(), back.getLowObstacleHeight(),
                    back.getMusicFile(), back.getJumpSound(), back.getHitSound(),
                    back.getDashSound(), back.getDelta(), back.getStringColor(), false);

            JFrame frame = (JFrame) back.getTopLevelAncestor();
            frame.setVisible(false);
            frame.remove(back);
            frame.add(newLevel);
            frame.setVisible(true);
            this.transferFocusBackward();
            this.dispose();
        }

        if (e.getSource() == returnMenu) {
            JFrame frame = (JFrame) back.getTopLevelAncestor();
            frame.setVisible(false);
            menu = new Menu();
            frame.dispose();
            this.dispose();
        }
    }
}
