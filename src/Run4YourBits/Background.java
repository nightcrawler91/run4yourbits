package Run4YourBits;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class is in charge of painting and managing the different types of
 * obstacles as well as the character and its collisions
 */
public class Background extends JPanel implements ActionListener, Runnable {

    PauseMenu pauseMenu;
    BufferedImage image, frame;
    Character character;
    Obstacle[] obstacleArray;
    Timer time;
    //CONSTANTES    
    final static int screenLength = 800;
    final static int borderWidth = 24;
    final static int screenDifferential = 74;//pixeles entre el personaje y el largo de la pantalla
    final static int characterFlow = 10; //numero de veces que se ejecuta el timer antes de avanzar la animación del dude
    final static int fontSize = 18; //tamaño fuente
    final static int flickerTime = 150;//tiempo que el mono "flashea"
    final static int invincibilityTime = 5000;//tiempo de invencibilidad para power up de estrella
    //FIN CONSTANTES
    int levelLength;
    int stepControl;
    int actualHeight;
    int standardHeight;
    int maxHeightValue;
    int obstacleDelta;// este entero nos permite pintar el obstaculo al nivel del piso y no el del personaje
    int delta; // Es la variable que va a moderar a qué altura estarán nuestros powerUp
    int auxContWin;
    int auxContLose;
    int lowObstacleHeight;
    Thread jump;
    Thread soundPlayer;
    //static boolean debug = false;
    boolean jumpCycleDone;
    boolean runDone;
    boolean maxHeight;
    boolean shrinkHeart;
    boolean pause;
    boolean gameWon;
    boolean gameLost;
    static boolean playMusic;
    static Font fontScore, fontMultiplier;
    Lives lives;
    Color stringColor;
    static MultithreadSystem sounds;
    static Thread musicPlayer;
    static String backgroundMusic;
    String hitSound, score, characterImage, characterHitImage, characterGameOverImage,
            backgroundImage, frameImage, lowObstacleImage, highObstacleImage,
            musicFile, jumpSound, dashSound;
    int characterSteps, speed, obstacleDistance, respawnPoint;
    ArrayList highscores;

    /**
     * This method creates a completely new level based on user input
     *
     * @param levelLength the length of the level in pixels (or screens if you
     * go pixels/800)
     * @param actualHeight the height of the avatar that varies depending on the
     * movements it makes
     * @param maxHeightValue the maximum height allowed for jumps
     * @param characterImage filename of the avatar that will be displayed
     * @param characterHitImage filename of the avatar image when hit
     * @param characterGameOverImage filename of the avatar image when dead
     * @param characterSteps number of frames the character image has
     * @param speed speed at which the character moves
     * @param respawnPoint starter point
     * @param backgroundImage filename of the background image
     * @param frameImage filename of the frame image
     * @param lowObstacleImage filename of the image for Low Obstacles
     * @param highObstacleImage filename of the image for High Obstacles
     * @param obstacleDistance minimum distance between obstacles
     * @param lowObstacleHeight y coordenate for Low Obstacles
     * @param musicFile filename of the background music
     * @param jumpSound filename of the jumping sound for that level
     * @param hitSound filename of the hitting sound for that level
     * @param dashSound filename of the ducking sound for that level
     * @param stringColor this defines the color of the highscore and the
     * multiplier, the possible values are defined by java.awt.Color
     * @param playMusic to play or not to play the music, that is the question
     * @see Character
     * @see Obstacle
     * @see HighObstacle
     * @see LowObstacle
     */
    public Background(int levelLength, int actualHeight, int maxHeightValue,
            String characterImage, String characterHitImage, String characterGameOverImage, int characterSteps, int speed, int respawnPoint,
            String backgroundImage, String frameImage,
            String lowObstacleImage, String highObstacleImage, int obstacleDistance, int lowObstacleHeight,
            String musicFile, String jumpSound, String hitSound, String dashSound,
            int delta, Color stringColor, boolean playMusic) {


        stepControl = 0;
        this.levelLength = levelLength;
        this.standardHeight = actualHeight;
        this.actualHeight = this.standardHeight;
        this.maxHeightValue = maxHeightValue;
        this.delta = delta;
        this.respawnPoint = respawnPoint;
        this.lowObstacleHeight = lowObstacleHeight;
        auxContWin = 0;
        auxContLose = 0;


        jumpCycleDone = false;
        runDone = false;
        maxHeight = false;
        shrinkHeart = false;
        pause = false;
        gameWon = false;
        gameLost = false;
        this.playMusic = playMusic;

        character = new Character(characterImage, characterHitImage, characterGameOverImage, characterSteps, speed,
                jumpSound, dashSound, respawnPoint);

        lives = new Lives();

        obstacleArray = Obstacle.getObstacleArray(levelLength, obstacleDistance,
                lowObstacleHeight, (standardHeight + obstacleDelta) / 2, standardHeight + delta,
                lowObstacleImage, highObstacleImage);

        this.stringColor = stringColor;
        this.characterImage = characterImage;
        this.characterHitImage = characterHitImage;
        this.characterGameOverImage = characterGameOverImage;
        this.characterSteps = characterSteps;
        this.speed = speed;
        this.backgroundImage = backgroundImage;
        this.frameImage = frameImage;
        this.lowObstacleImage = lowObstacleImage;
        this.highObstacleImage = highObstacleImage;
        this.obstacleDistance = obstacleDistance;
        this.musicFile = musicFile;
        this.jumpSound = jumpSound;
        this.hitSound = hitSound;
        this.dashSound = dashSound;



        addKeyListener(new AL());
        setFocusable(true);
        this.requestFocus();


        try {
            image = CompatibleImage.toCompatibleImage(ImageIO.read(new File("Images" + File.separator + "Background" + File.separator + backgroundImage)));
            frame = CompatibleImage.toCompatibleImage(ImageIO.read(new File("Images" + File.separator + frameImage)));
            fontScore = (Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts" + File.separator + "score.ttf")))).deriveFont(Font.BOLD, 18);
            fontMultiplier = (Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts" + File.separator + "multi.ttf")))).deriveFont(Font.BOLD, fontSize + 10);

//            Thread.sleep(2000);

        } catch (IOException | FontFormatException e) {
            System.out.println("Error loading" + ":" + e.toString());
        }

        obstacleDelta = (character.getImage(character.actualStep).getHeight(null)) - (obstacleArray[0].getImage().getHeight(null));

        time = new Timer(5, this);
        time.start();
        if (playMusic) {
            sounds = new MultithreadSystem();//SE INICIA THREAD PARA LA MUSICA DE FONDO.
            musicPlayer = new Thread(sounds);
            backgroundMusic = "Music/" + musicFile;//SE INICIA EL VALOR STRING DE LA MUSICA DE FONDO DEL NIVEL.
            backMusic(backgroundMusic);
        }
    }

    /**
     * Starts and manages background music
     *
     * @param MusisBck filename for background music
     */
    public static void backMusic(String MusisBck) {
        sounds.newThread(MusisBck);//INICIA MUSICA DEL NIVEL 14/MARCH/2012.
        if (playMusic) {
            musicPlayer.start();
            if (!musicPlayer.isAlive()) {
                sounds.newThread(MusisBck);
                musicPlayer.start();
            }
        }
    }

    /**
     * In charge of managing all the events in the level
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (!pause) {
            character.move();
        }

        condWin(levelLength);

        condLose();

        stepForward();

        repaint();
    }

    /**
     * Makes the character go forward based on the Timer, it also give the lives
     * their animation
     */
    public void stepForward() {//este método avanza el paso del sprite en base al tiempo del Timer, también anima los corazones
        if (!pause) {
            stepControl++;
        }
        if (stepControl % characterFlow == 0) {//Cada 100 ms (10 del timer *10) se avanzara un paso, 
            character.actualStep++;     //al estar en funcion del timer si avanza la velocidad también avanza la del sprite
            if (character.actualStep > character.steps - 3) {
                character.actualStep = 0;
            }
        }
        if (stepControl % (characterFlow * (character.steps - 2)) == 0) {
            shrinkHeart = !shrinkHeart;//oscilación para encojer/desencoger
        }
    }

    /**
     * Determines when the user has reached the desired length and displays a
     * message, it also saves the highscore
     *
     * @param dist the desired length in pixels
     */
    public void condWin(int dist) {

        if (character.getPositionX() >= dist) {

            if (auxContWin == 0) {
                gameWon = true;
                sounds.sound.sfx.stop();                             ////////////////////Detiene musica.
                auxContWin++;
                HighscoreDialog hd = new HighscoreDialog(this, sounds, soundPlayer, "Music/thm FF3Victory.wav");
            }
        }
    }

    /**
     * Checks if the character has lost all his lives and acts accordingly
     */
    public void condLose() {

        if (character.lives == 0) {
            character.isAlive = false;
            if (!character.isAlive && auxContLose == 0) {
                gameLost = true;
                sounds.sound.sfx.stop();                               ////////////////////Detiene musica.
                auxContLose++;

                HighscoreDialog hd = new HighscoreDialog(this, sounds, soundPlayer, "Music/thm gameOver01.wav");
            }
        }
    }

    /**
     * Paints everything
     *
     * @param g Graphics component to realize the painting
     */
    @Override
    public void paint(Graphics g) {


        if (character.deltaY == 1 && runDone == false) {
            runDone = true;
            jump = new Thread(this);
            jump.start();
        }

        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(image, screenLength - character.getnX2(), 0, null);

        if (character.getPositionX() > screenDifferential) {
            g2d.drawImage(image, screenLength - character.getnX(), 0, null);
        }

        for (int i = 0; i < obstacleArray.length; i++) {
            if (obstacleArray[i] != null) {

                if (obstacleArray[i].getPositionX() == -character.staticPosition //si ya salio de pantalla

                        || obstacleArray[i].getPositionX() >= levelLength //si esta fuera del largo del nivel

                        || (obstacleArray[i] instanceof PowerUp && //si es un powerUp y chocas con el
                        obstacleArray[i].getPositionX()
                        == character.staticPosition - 3 && !character.isJumping)) {//destruir el powerup cuando choca y se detecta el efecto

                    obstacleArray[i] = null; //se borra el obstaculo
                }

                if (obstacleArray[i] != null) {

                    if (!pause) {
                        obstacleArray[i].move(character.pixPerStep);
                    }
                    if (obstacleArray[i].getPositionX() <= screenLength + character.staticPosition) {
                        obstacleArray[i].setVisible(true);
                    }

                    if (obstacleArray[i].isVisible()) {
                        g2d.drawImage(obstacleArray[i].getImage(), obstacleArray[i].getPositionX(), obstacleArray[i].getPositionY(), null);
                    }
                    obstacleArray[i].checkCollisions(character, sounds, soundPlayer, hitSound, this);

                }
            }

        }

        if (character.paintCharacter) {
            g2d.drawImage(character.getImage(character.actualStep), character.staticPosition, actualHeight, null);
        }

        character.hit(flickerTime, pause);
        character.invincibility(invincibilityTime, pause);


        if (shrinkHeart) {//esto nos regresa una imagen un 5% mas chica
            g2d.drawImage(lives.getImage(character.getLives()),
                    borderWidth + 5,
                    borderWidth + 5,
                    (int) (lives.getImage(character.getLives()).getWidth() * .95),
                    (int) (lives.getImage(character.getLives()).getHeight() * .95),
                    null);
        } else {
            g2d.drawImage(lives.getImage(character.getLives()),
                    borderWidth + 5,
                    borderWidth + 5,
                    null);
        }

        g2d.drawImage(frame, 0, 0, null);

        g2d.setFont(fontScore);
        g2d.setColor(stringColor);
        g2d.drawString("score:" + String.format("%08d", character.score), 500, borderWidth * 2);
        if (gameWon) {
            g2d.drawString("YOU WIN!", 380, borderWidth * 4);
        }
        if (gameLost) {
            g2d.drawString("DEAD", 380, borderWidth * 4);
        }
        g2d.setFont(fontMultiplier);
        g2d.drawString(character.multiplier + "", (screenLength / 2) - 75, (borderWidth * 2) + 10);
    }

    /**
     * Listens for keyboard events and acts accordingly
     */
    private class AL extends KeyAdapter {

        /**
         * Methods when keys are released
         *
         * @param e Key Event
         */
        @Override
        public void keyReleased(KeyEvent e) {
            character.keyReleased(e);
        }

        /**
         * Methods when keys are pressed
         *
         * @param e Key Event
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (!pause) {
                character.keyPressed(e);
            }

            if (key == KeyEvent.VK_P || key == KeyEvent.VK_ESCAPE) {
                pause();
            }
        }
    }

    /**
     * Method that controls jumping
     */
    public void cycle() {
        ///////////////////CAMBIO: Limite de cuanto puede brincar el personaje principal. - Rocha
        if (!pause) {

            if (maxHeight == false) {
                actualHeight--;
            }

            if (actualHeight == maxHeightValue) {
                maxHeight = true;
            }

            if (maxHeight == true && actualHeight <= standardHeight) {
                actualHeight++;
                if (actualHeight == standardHeight) {
                    jumpCycleDone = true;
                    character.keyAllowed = true;
                }
            }
        }
    }

    /**
     * Displays the pause menu
     */
    public void pause() {
        pause = true;
        pauseMenu = new PauseMenu(this);
    }

    /**
     * This calls the jump safely
     */
    @Override
    public void run() {
        //variables para sacar longitud del salto
        character.isJumping = true;
        character.keyAllowed = false; //esto evita la interferecia del teclado durante el salto        

        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        while (jumpCycleDone == false) {
            cycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = 8 - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
            }

            beforeTime = System.currentTimeMillis();
        }

        jumpCycleDone = false;
        maxHeight = false;
        runDone = false;
        character.isJumping = false;
    }

    /**
     * Accesor for character
     *
     * @return character
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Accesor for actualHeight
     *
     * @return actualHeight
     */
    public int getActualHeight() {
        return actualHeight;
    }

    /**
     * Accesor for delta
     *
     * @return delta
     */
    public int getDelta() {
        return delta;
    }

    /**
     * Accesor for levelLength
     *
     * @return levelLength
     */
    public int getLevelLength() {
        return levelLength;
    }

    /**
     * Accesor for maxHeightValue
     *
     * @return maxHeightValue
     */
    public int getMaxHeightValue() {
        return maxHeightValue;
    }

    /**
     * Accesor for stringColor
     *
     * @return stringColor
     */
    public Color getStringColor() {
        return stringColor;
    }

    /**
     * Accesor for backgroundImage
     *
     * @return backgroundImage
     */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Accesor for characterGameOverImage
     *
     * @return characterGameOverImage
     */
    public String getCharacterGameOverImage() {
        return characterGameOverImage;
    }

    /**
     * Accesor for characterHitImage
     *
     * @return characterHitImage
     */
    public String getCharacterHitImage() {
        return characterHitImage;
    }

    /**
     * Accesor for characterImage
     *
     * @return characterImage
     */
    public String getCharacterImage() {
        return characterImage;
    }

    /**
     * Accesor for characterSteps
     *
     * @return characterSteps
     */
    public int getCharacterSteps() {
        return characterSteps;
    }

    /**
     * Accesor for dashSound
     *
     * @return dashSound
     */
    public String getDashSound() {
        return dashSound;
    }

    /**
     * Accesor for frameImage
     *
     * @return frameImage
     */
    public String getFrameImage() {
        return frameImage;
    }

    /**
     * Accesor for highObstacleImage
     *
     * @return highObstacleImage
     */
    public String getHighObstacleImage() {
        return highObstacleImage;
    }

    /**
     * Accesor for hitSound
     *
     * @return hitSound
     */
    public String getHitSound() {
        return hitSound;
    }

    /**
     * Accesor for jumpSound
     *
     * @return jumpSound
     */
    public String getJumpSound() {
        return jumpSound;
    }

    /**
     * Accesor for lowObstacleImage
     *
     * @return lowObstacleImage
     */
    public String getLowObstacleImage() {
        return lowObstacleImage;
    }

    /**
     * Accesor for musicFile
     *
     * @return musicFile
     */
    public String getMusicFile() {
        return musicFile;
    }

    /**
     * Accesor for obstacleDistance
     *
     * @return
     */
    public int getObstacleDistance() {
        return obstacleDistance;
    }

    /**
     * Accesor for speed
     *
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Accesor for musicPlayer
     *
     * @return musicPlayer
     */
    public static Thread getMusicPlayer() {
        return musicPlayer;
    }

    /**
     * Accesor for standardHeight
     *
     * @return standardHeight
     */
    public int getStandardHeight() {
        return standardHeight;
    }

    /**
     * Accesor for lowObstacleHeight
     *
     * @return lowObstacleHeight
     */
    public int getLowObstacleHeight() {
        return lowObstacleHeight;
    }

    /**
     * Sets the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }
}