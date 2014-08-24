package Run4YourBits;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * This class defines the behavior of the standard obstacle and defines its
 * methods
 */
public abstract class Obstacle implements Comparable {

    private int positionX, positionY;
    private BufferedImage image;
    private boolean visible;//control para saber cuando pintar el objeto   
    static Obstacle[] obstacleArray;

    /**
     * Constructs the obstacle using the default constructor
     *
     * @param x the initial x coordinate of the obstacle
     * @param y the initial y coordinate of the obstacle
     * @param obstacleImage filename of the obstacle image
     */
    public Obstacle(int x, int y, String obstacleImage) {

        this.positionX = x;
        this.positionY = y;

        try {
            image = CompatibleImage.toCompatibleImage(ImageIO.read(new File(
                    "Images" + File.separator + "Obstacles" + File.separator + obstacleImage)));
            visible = false;
        } catch (Exception e) {
            System.out.println("Error loading image" + ":" + e.toString());
        }
    }
    // Métodos propios

    /**
     * Moves the obstacle backwards the specified pixels to simulate that they
     * are moving with the background
     *
     * @param pixPerStep
     */
    public void move(int pixPerStep) {
        positionX -= pixPerStep;
    }

    /**
     * Generates an array of Obstacles "randomizing" their position and type
     *
     * @param levelLength length of the current level
     * @param distanceBetween minimum distance between obstacles
     * @param heightLowObs y coordinate for LowObstacles
     * @param heightHighObs y coordinate for HighObstacles
     * @param heightPowerUp y coordinate for PowerUps
     * @param lowObstacleImage filename of the image for LowObstacles
     * @param highObstacleImage filename of the image for HighObstacles
     * @return the array of Obstacles
     */
    public static Obstacle[] getObstacleArray(int levelLength, int distanceBetween,
            int heightLowObs, int heightHighObs, int heightPowerUp, String lowObstacleImage, String highObstacleImage) {
        int totalNumber = (levelLength / distanceBetween) * 2; // To generate the array easier
        int typeChooser;
        int powerUpDelimiter = 0; // Delimit our number of powerUp's number to (totalNumber/4).

        obstacleArray = new Obstacle[totalNumber + (totalNumber / 4)];
        RandomRange randomRange = new RandomRange();
        int randomNum = randomRange.nextInclusiveInclusive(500, 500 + distanceBetween);
        obstacleArray[0] = new LowObstacle(randomNum, heightLowObs, lowObstacleImage);

        int avoidRepeats = 0;
        int randomPowerUp;



        for (int i = 1; i < ((totalNumber + (totalNumber / 4))); i++) {

            randomNum += obstacleArray[i - 1].getImage().getWidth() + distanceBetween;
            randomNum = randomRange.nextInclusiveInclusive(randomNum, randomNum + distanceBetween);
            if (randomNum >= levelLength) {
                randomNum = randomRange.nextInclusiveInclusive(randomNum, randomNum + distanceBetween);
            }

            if (powerUpDelimiter < (totalNumber / 4)) {
                typeChooser = randomRange.nextInclusiveInclusive(0, 2);
            } else {
                typeChooser = randomRange.nextInclusiveInclusive(0, 1);
            }
            switch (typeChooser) {
                case 0:
                    obstacleArray[i] = new LowObstacle(randomNum, heightLowObs, lowObstacleImage);
                    break;
                case 1:
                    obstacleArray[i] = new HighObstacle(randomNum, heightHighObs, highObstacleImage);
                    break;
                case 2:
                    randomPowerUp = randomRange.nextInclusiveInclusive(0, 3);
                    
                    if (avoidRepeats != 0) {
                        while (avoidRepeats == randomPowerUp) {
                            randomPowerUp = randomRange.nextInclusiveInclusive(0, 3);
                        }
                        avoidRepeats = randomPowerUp;
                    }
                    switch (randomPowerUp) {
                        case 0:
                            obstacleArray[i] = new PowerUp(randomNum, heightPowerUp,"extraLife.png",PowerUp.possiblePowers.EXTRALIFE);
                            break;
                        case 1:
                            obstacleArray[i] = new PowerUp(randomNum, heightPowerUp,"invincible.png",PowerUp.possiblePowers.INVINCIBILITY);
                            break;
                        case 2:
                            obstacleArray[i] = new PowerUp(randomNum, heightPowerUp,"minusLife.png",PowerUp.possiblePowers.MINUSLIFE);
                            break;
                        case 3:
                            obstacleArray[i] = new PowerUp(randomNum, heightPowerUp,"speed.png",PowerUp.possiblePowers.SPEED);
                            break;
                    }
                    powerUpDelimiter++;
                    break;
            }
        }

        obstacleArray[totalNumber + (totalNumber / 4) - 1] = new Checkpoint(levelLength / 2);

//        Arrays.sort(obstacleArray);
//        
//        for (int i = 1; i < obstacleArray.length; i++) {
//            if(obstacleArray[i].positionX-obstacleArray[i-1].positionX<distanceBetween){
//                obstacleArray[i].positionX+=distanceBetween+obstacleArray[i].getImage().getWidth();
//            }
//        }
        return obstacleArray;
    }
    
/**
 * Calls the checkCollisions method in each type of obstacle in order to check
 * whether the obstacle has collided with the avatar and takes action 
 * @param character the avatar that collides with the obstacles
 * @param sounds MultithreadSystem for sounds
 * @param soundPlayer Thread for playing the sounds
 * @param hitSound String that defines the hut sound for Low and High obstacles in each character
 * @param back the actual background
 */
    public abstract void checkCollisions(Character character, MultithreadSystem sounds,Thread soundPlayer, String hitSound,Background back);

    // Getters y setters. 
    /**
     * Gets the obstacle image
     *
     * @return the obstacle image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Sets the obstacle image
     *
     * @param image filename of the image to be set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Get the x coordinate of the obstacle
     *
     * @return positionX
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Set the x coordinate of the obstacle
     *
     * @param positionX the s coordinate to be set
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Get the y coordinate of the obstacle
     *
     * @return positionY
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Set the y coordinate of the obstacle
     *
     * @param positionY the y coordinate to be set
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * Returns whether the obstacle is visible or not
     *
     * @return the visibility of the obstacle
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the obstacle visibility
     *
     * @param isVisible boolean value which determines visibility
     */
    public void setVisible(boolean isVisible) {
        this.visible = isVisible;
    }

    /*
     * Method that sorts the highscores
     */
    @Override
    public int compareTo(Object x) {
        Obstacle aux = (Obstacle) x;
        if (this.positionX < aux.positionX) {
            /*
             * instance lt received
             */
            return -1;
        } else if (this.positionX > aux.positionX) {
            /*
             * instance gt received
             */
            return 1;
        }
        /*
         * instance == received
         */
        return 0;
    }
}
