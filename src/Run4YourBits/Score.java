package Run4YourBits;

import java.io.Serializable;

/**
 * Structure for saving score with the user's name
 */
public class Score implements Comparable, Serializable {

    String name;
    int score;

    /**
     * Score constructor
     *
     * @param name The user's name
     * @param score User's score
     */
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /*
     * Method that sorts the highscores
     */
    @Override
    public int compareTo(Object x) {
        Score aux = (Score) x;
        if (this.score > aux.score) {
            /*
             * instance lower than received
             */
            return -1;
        } else if (this.score < aux.score) {
            /*
             * instance greater than received
             */
            return 1;
        }
        /*
         * instance == received
         */
        return 0;
    }

    /**
     * Accesor for the user's name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for the user's name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accesor for the game's score
     *
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Mutator for the game's score
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }
}