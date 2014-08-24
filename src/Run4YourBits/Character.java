package Run4YourBits;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * This class defines the behavior of the main avatar and its different
 * animations, it also includes the score,speed and the multiplier variables.
 */
public class Character extends Thread {

    int positionX, positionY, deltaY, nx, nx2, staticPosition, lives, steps, actualStep,
            score, multiplier, pixPerStep, flickerCount, invincibilityCount, respawnPoint;
    BufferedImage actual, normal, hit, gameOver;
    boolean powerUp;
    boolean isAlive;
    boolean isJumping;
    boolean keyAllowed; //esta variable indica cuando se prenden o apagan los listeners
    boolean hitAllowed;
    boolean dashPressed;// para saber si la tecla abajo esta siendo presionada
    boolean paintCharacter;
    boolean wasDashing;
    boolean invincibilityOn;
    MultithreadSystem sounds;
    Thread soundsPlayer;
    String jumpSound, dashSound;

    /**
     * Constructs a character based on input
     *
     * @param characterImage filename of the avatar that will be displayed
     * @param characterHitImage filename of the avatar image when hit
     * @param characterGameOverImage filename of the avatar image when dead
     * @param stepsPersonaje number of frames the character image has
     * @param speed speed at which the character moves
     * @param jumpSound filename of the jumping sound for that level
     * @param dashSound filename of the ducking sound for that level
     */
    public Character(String characterImage, String characterHitImage, String characterGameOverImage,
            int stepsPersonaje, int speed, String jumpSound, String dashSound, int respawnPoint) {

        isAlive = true;
        keyAllowed = true;
        dashPressed = false;
        isJumping = false;
        hitAllowed = true;
        paintCharacter = true;
        invincibilityOn = false;

        try {
            normal = CompatibleImage.toCompatibleImage(ImageIO.read(new File("Images" + File.separator + "Character" + File.separator + characterImage)));
            hit = CompatibleImage.toCompatibleImage(ImageIO.read(new File("Images" + File.separator + "Character" + File.separator + characterHitImage)));
            gameOver = CompatibleImage.toCompatibleImage(ImageIO.read(new File("Images" + File.separator + "Character" + File.separator + characterGameOverImage)));

        } catch (Exception e) {
            System.out.println("Error al cargar imagen" + ":" + e.toString());
        }

        actual = normal;

        actualStep = 0; //Inicio Animación
        this.respawnPoint = respawnPoint;
        positionX = respawnPoint;
        staticPosition = 150;
        nx = 1;
        nx2 = 800;
        lives = 3;
        multiplier = 1;
        pixPerStep = speed;
        flickerCount = 0;
        invincibilityCount = 0;
        score = 0;

        steps = stepsPersonaje;

        this.jumpSound = jumpSound;
        this.dashSound = dashSound;
    }

    /**
     * Moves the character forward
     */
    public void move() {
        positionX += pixPerStep;
        nx += pixPerStep;
        nx2 += pixPerStep;
        if (score < 99999999) {
            score += multiplier;
        }
        if (nx2 >= 1600) {
            nx2 = 800;
            nx = 0;
        }
    }

    /**
     * Flickers the character when hit
     *
     * @param time the time in ms you want the avatar to flicker
     */
    public void hit(int time, boolean pause) {
        if (!hitAllowed) {
            actual = hit;
            paintCharacter = false;
            if (!pause) {
                flickerCount++;
            }
            if (flickerCount % 2 == 0) {
                paintCharacter = true;
            }

            if (flickerCount == time / pixPerStep) {
                paintCharacter = true;
                flickerCount = 0;
                actual = normal;
                hitAllowed = true;
            }
        }
    }

    public void invincibility(int time, boolean pause) {
        if (invincibilityOn) {
            hitAllowed = false;
            actual = hit;
            paintCharacter = false;
            if (!pause) {
                invincibilityCount++;
            }
            if (invincibilityCount % 2 == 0) {
                paintCharacter = true;
            }

            if (invincibilityCount == time / pixPerStep) {
                paintCharacter = true;
                invincibilityCount = 0;
                actual = normal;
                invincibilityOn = false;
                hitAllowed = true;
            }
        }
    }

    /**
     * Methods when keys are pressed
     *
     * @param e Key Event
     */
    public void keyPressed(KeyEvent e) {

        if (keyAllowed) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                deltaY = 1;

                sounds = new MultithreadSystem();
                sounds.newThread("SFX/" + jumpSound);
                soundsPlayer = new Thread(sounds);
                soundsPlayer.start();
            }

            if (key == KeyEvent.VK_DOWN) {
                sounds = new MultithreadSystem();
                sounds.newThread("SFX" + File.separator + dashSound);
                soundsPlayer = new Thread(sounds);
                soundsPlayer.start();

                dashPressed = true;
            }
        }
    }

    /**
     * Methods when keys are released
     *
     * @param e Key Event
     */
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            deltaY = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            dashPressed = false;
        }
    }

    /**
     * Returns the position of the first loop of the background image
     *
     * @return nX
     */
    public int getnX() {
        return nx;
    }

    /**
     * Sets the position of the first loop of the background image
     *
     * @param nx
     */
    public void setnX(int nx) {
        this.nx = nx;
    }

    /**
     * Returns numbers of lives left
     *
     * @return number of lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets number of lives left
     *
     * @param lives
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Gets the position of the second loop of the background image
     *
     * @return nx2
     */
    public int getnX2() {
        return nx2;
    }

    /**
     * Sets the position of the second loop of the background image
     *
     * @param nx2
     */
    public void setnX2(int nx2) {
        this.nx2 = nx2;
    }

    /**
     * Return the character actual x coordinate
     *
     * @return actual position
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Sets the character actual x coordinate
     *
     * @param positionX
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Return the character actual y coordinate
     *
     * @return positionY
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Sets the character actual y coordinate
     *
     * @param positionY
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * Returns the corresponding frame of the image depending on what part of it
     * its being used
     *
     * @param step
     * @return the avatar's cropped image
     */
    public BufferedImage getImage(int step) {
        if (isJumping) {
            return actual.getSubimage((actual.getWidth() / (steps)) * (steps - 2), 0, actual.getWidth() / (steps), actual.getHeight());
        } else if (dashPressed) {
            return actual.getSubimage((actual.getWidth() / (steps)) * (steps - 1), 0, actual.getWidth() / (steps), actual.getHeight());
        } else if (lives <= 0) {
            return gameOver;
        } else {
            return actual.getSubimage((actual.getWidth() / (steps)) * actualStep, 0, actual.getWidth() / (steps), actual.getHeight());
        }
    }

    /**
     * Sets the character image
     *
     * @param i the image to be used for replacement
     */
    public void setImage(BufferedImage i) {
        actual = i;
    }
/**
 * Accesor for respawnPoint used for checkpoints
 * @return respawnPoint
 */
    public int getRespawnPoint() {
        return respawnPoint;
    }
/**
 * Mutator for respawnPoint used for checkpoints
 * @param respawnPoint 
 */
    public void setRespawnPoint(int respawnPoint) {
        this.respawnPoint = respawnPoint;
    }
}
