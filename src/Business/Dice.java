package Business;

import java.util.Random;

public class Dice {
    private final String name;
    private final int nCares;

    /**
     * Consstructor of the dice
     * @param name name of the dice (D + number)
     * @param nCares max number
     */
    public Dice(String name, int nCares){
        this.name = name;
        this.nCares = nCares;
    }

    /**
     * Function that simulates the dice throwed and return its value
     * @return value obtained by throwing dice
     */
    public int throwDice() {
        Random random = new Random();
        return random.nextInt(nCares) + 1;
    }
}