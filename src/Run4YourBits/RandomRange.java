package Run4YourBits;

import java.util.Random;

/**
 * This class creates random numbers in a certain range, it includes inclusive
 * and exclusive methods
 */
public class RandomRange extends Random {

    /**
     * Returns a new random number upper and lower ends inclusive
     *
     * @param min lower end of the range
     * @param max upper end of the range
     * @return new number within the specified range
     */
    public int nextInclusiveInclusive(int min, int max) {
        return nextInt(max - min + 1) + min;
    }

    /**
     * Returns a new random number upper end exclusive and inner end inclusive
     *
     * @param min lower end of the range
     * @param max upper end of the range
     * @return new number within the specified range
     */
    public int nextExclusiveInclusive(int min, int max) {
        return nextInt(max - min) + 1 + min;
    }

    /**
     * Returns a new random number with upper and lower ends exclusive
     *
     * @param min lower end of the range
     * @param max upper end of the range
     * @return new number within the specified range
     */
    public int nextExclusiveExclusive(int min, int max) {
        return nextInt(max - min - 1) + 1 + min;
    }

    /**
     * Returns a new random number upper end inclusive and inner end exclusive
     *
     * @param min lower end of the range
     * @param max upper end of the range
     * @return new number within the specified range
     */
    public int nextInclusiveExclusive(int min, int max) {
        return nextInt(max - min) + min;
    }
}