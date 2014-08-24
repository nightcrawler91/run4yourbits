package Run4YourBits;

/**
 * This type of obstacle hovers in midair, requiring you to duck in order to
 * avoid crashing with them and lose a life by doing so
 */
public class HighObstacle extends Obstacle {

    /**
     * Constructs the obstacle using the default constructor
     *
     * @param x the initial x coordinate of the obstacle
     * @param y the initial y coordinate of the obstacle
     * @param obstacleImage filename of the obstacle image
     * @see Obstacle
     */
    public HighObstacle(int x, int y, String obstacleImage) {
        super(x, y, obstacleImage);
    }

    /**
     * Checks whether the obstacle has collided with the avatar and takes action
     *
     * @param character the character which the obstacle collides with
     * @param sounds Thread used to save thread creation
     * @param hitSound filename of the hitting sound for that level
     */
    @Override
    public void checkCollisions(Character character, MultithreadSystem sounds,Thread soundPlayer, String hitSound,Background back) {

        if (character.staticPosition == getPositionX()) {
            if (character.dashPressed) {
                if (character.multiplier < 4) {
                    character.multiplier++;
                }
            }
        }
        if (getPositionX() <= character.staticPosition && character.staticPosition <= (getPositionX() + getImage().getWidth()-20)) {
            if (!character.dashPressed || character.isJumping) {
                if (character.hitAllowed) {
                    sounds = new MultithreadSystem();
                    sounds.newThread("SFX/" + hitSound);//Efecto de sonido cuando hay colision.
                    soundPlayer = new Thread(sounds);
                    soundPlayer.start();
                    character.multiplier = 1;
                    character.lives--;
                    character.hitAllowed = false;
                }
            }
        }
    }
}
