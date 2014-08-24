package Run4YourBits;
/**
 * Defines the behavior for the different Power-Ups
 * @author jrlpp
 */
public class PowerUp extends Obstacle {
/**
 * Enumeration used for assigning powers
 */
    enum possiblePowers {

        EXTRALIFE, INVINCIBILITY, MINUSLIFE, SPEED
    }
    possiblePowers power;
/**
 * Creates a new PowerUp
 * @param x x position
 * @param y y position
 * @param image Image for the Power-Up
 * @param power Assigend power
 */
    public PowerUp(int x, int y, String image, possiblePowers power) {
        super(x, y, image);
        this.power = power;
    }
/**
 * Checks whether the avatar has collided with the Power-Up and takes action
 * @param character the avatar to collide with
 * @param sounds MultithreadSystem used for sound playback
 * @param soundPlayer Thread used for sound playback
 * @param powerUpSound String used t o locate each sound file 
 */
    @Override
    public void checkCollisions(Character character, MultithreadSystem sounds,Thread soundPlayer, String powerUpSound, Background back) {
        if (getPositionX() == character.staticPosition) {
            if (!character.isJumping) {

                switch (power) {
                    case EXTRALIFE:
                        character.lives++;
                        powerUpSound = "snd extraLife.wav";
                        setVisible(false);
                        break;
                    case INVINCIBILITY:
                        character.invincibilityOn=true;
                        powerUpSound = "snd invincibility.wav";
                        setVisible(false);
                        break;
                    case MINUSLIFE:
                        character.lives--;
                        character.hitAllowed = false;
                        powerUpSound = back.hitSound;
                        setVisible(false);
                        break;
                    case SPEED:
                        character.pixPerStep++;
                        powerUpSound = "snd speed.wav";
                        setVisible(false);
                        break;
                }
                sounds = new MultithreadSystem();
                sounds.newThread("SFX/" + powerUpSound);
                soundPlayer = new Thread(sounds);
                soundPlayer.start();                
            }
        }
    }
}