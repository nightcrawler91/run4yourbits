package Run4YourBits;

/**
 * Usually with around the same height as the character these obstacles must be
 * avoided by jumping so you won't crash with them and lose a life by doing so.
 */
public class LowObstacle extends Obstacle {

    /**
     * Constructs the obstacle using the default constructor
     *
     * @param x the initial x coordinate of the obstacle
     * @param y the initial y coordinate of the obstacle
     * @param obstacleImage filename of the obstacle image
     * @see Obstacle
     */
    public LowObstacle(int x, int y, String obstacleImage) {
        super(x, y, obstacleImage);
    }

    /**
     * Checks whether the obstacle has collided with the avatar and takes action
     *
     * @param character the character which the obstacle collides with
     * @param sounds Thread used to save thread creation
     * @param hitSound filename of the hitting sound for that level
     * @param back Background used for its local variables
     */
    @Override
    public void checkCollisions(Character character, MultithreadSystem sounds, Thread soundPlayer, String hitSound, Background back) {
        if (getPositionX() == character.staticPosition+character.pixPerStep /* && 
                character.staticPosition <= getPositionX() + getImage().getWidth()-getImage().getWidth()/4*/) {
            if (back.actualHeight <= (back.standardHeight - (getImage().getHeight()*3/4))) {

                if (character.multiplier < 4) {
                    character.multiplier++;
                }
            } else {

                if (character.hitAllowed) {
                    sounds = new MultithreadSystem();
                    sounds.newThread("SFX/" + hitSound);//Effecto de sonido cuando hay colision.
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