package Run4YourBits;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Menu displayed when pausing the game
 */
public class PauseMenu extends JFrame implements ActionListener {

    private JButton continueButton, retry, retryCheck, /*
             * config,
             */ returnMenu/*
             * , exit
             */;
    Fondo fondo;
    Background back;
    Menu menu;

    /**
     * Creates the menu
     *
     * @param back Menu instance used for music control
     */
    public PauseMenu(Background back) {

        this.back = back;

        this.setUndecorated(true);
        this.setTitle("Pause");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(180, 350);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images" + File.separator + "logo.png"));

        this.add(fondo = new Fondo("fondoPausa.png"));
        fondo.setLayout(new GridLayout(2, 2));

        continueButton = CustomButton.createButton("Continue", this.getGraphicsConfiguration(), 16);
        continueButton.addActionListener(this);

        retry = CustomButton.createButton("Retry", this.getGraphicsConfiguration(), 16);
        retry.addActionListener(this);

        retryCheck = CustomButton.createButton("Retry from Checkpoint", this.getGraphicsConfiguration(), 16);
        retryCheck.addActionListener(this);

        //config = CustomButton.createButton("Config", this.getGraphicsConfiguration(), 16);
        //config.addActionListener(this);

        returnMenu = CustomButton.createButton("Return to Menu", this.getGraphicsConfiguration(), 16);
        returnMenu.addActionListener(this);

        //exit = CustomButton.createButton("Exit", this.getGraphicsConfiguration(), 16);
        //exit.addActionListener(this);

        //creo y agrego key listener
        AL keyListener = new AL();
        keyListener.setMenu(this);
        addKeyListener(keyListener);


        fondo.add(continueButton);
        fondo.add(returnMenu);
        fondo.add(retry);
        fondo.add(retryCheck);
        //fondo.add(config);
        //fondo.add(exit);

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
     * Defines and runs the actions for each button
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == continueButton) {
            this.transferFocusBackward();
            back.pause = false;
            this.dispose();
        }
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
        if (e.getSource() == retryCheck) {
            //retry from checkpoint
            Background newLevel = new Background(back.getLevelLength(), back.getStandardHeight(),
                    back.getMaxHeightValue(), back.getCharacterImage(),
                    back.getCharacterHitImage(), back.getCharacterGameOverImage(),
                    back.getCharacterSteps(), back.getSpeed(), back.character.getRespawnPoint(), back.getBackgroundImage(),
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
//        if (e.getSource() == config) {
//            //config goes here
//        }
        if (e.getSource() == returnMenu) {
            JFrame frame = (JFrame) back.getTopLevelAncestor();
            frame.setVisible(false);
            back.sounds.sound.sfx.stop();                               ////////////////////Detiene musica.
            menu = new Menu();
            frame.dispose();
            this.dispose();
        }
//        if (e.getSource() == exit) {
//            System.exit(0);
//        }
    }

    /**
     * Listens for keyboard events and acts accordingly
     */
    private class AL extends KeyAdapter {

        PauseMenu menu;

        /**
         * This sets the menu that will interact with the keyboard
         *
         * @param menu
         */
        public void setMenu(PauseMenu menu) {
            this.menu = menu;
        }

        /**
         * Methods when keys are pressed
         *
         * @param e Key Event
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_P || key == KeyEvent.VK_ESCAPE) {
                menu.transferFocusBackward();
                back.pause = false;
                menu.dispose();
            }
        }
    }
}
