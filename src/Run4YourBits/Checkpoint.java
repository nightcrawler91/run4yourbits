package Run4YourBits;
/**
 * This class defines the behavior of the checkpoint
 */
public class Checkpoint extends Obstacle{
    int initialPosition;
/**
 * Constructs a checkpoint based on input position
 * @param x the x coordenate for the checkpoint
 */    
public Checkpoint(int x){
        super(x,25,"checkpoint2.png");
        initialPosition=x;
    }
/**
 * Checks for collisions and acts accordingly
 * @param character the avatar to collide with
 * @param sounds MultithreadSystem used for sound playback
 * @param soundPlayer Thread used for sounds
 * @param hitSound String to locate the correct sound
 * @param back Level to use and modify values
 */
    @Override
    public void checkCollisions(Character character, MultithreadSystem sounds,Thread soundPlayer, String hitSound,Background back) {

        if (character.staticPosition == getPositionX()||character.staticPosition == getPositionX()+1) {        
                    character.respawnPoint=initialPosition;
                    sounds = new MultithreadSystem();
                    hitSound="snd checkPoint.wav";
                    sounds.newThread("SFX/" + hitSound);//Efecto de sonido cuando hay colision.
                    soundPlayer = new Thread(sounds);
                    soundPlayer.start();
                }
            }
        }