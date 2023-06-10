package Business;

import java.util.List;

public class Combat {

    private final int combatNumber;
    private List<Monster> monsters;
    private int[] monstersQuantity;

    /**
     * Constructor of the combat
     * @param combatNumber combat number
     * @param monsters list of monsters
     * @param monstersQuantity quantity of each monster
     */
    public Combat(int combatNumber, List<Monster> monsters, int[] monstersQuantity) {
        this.combatNumber = combatNumber;
        this.monsters = monsters;
        this.monstersQuantity = monstersQuantity;
    }

    /**
     * Function to get the number of the combat/encounter
     * @return number of combat
     */
    public int getCombatNumber() {
        return combatNumber;
    }

    /**
     * Function to get the list of monsters in the combat
     * @return list of combats
     */
    public List<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Function to set the list of monsters
     * @param monsters list of monsters
     */
    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    /**
     * Function to get the quantity of each monster
     * @return array with quantity of each monster
     */
    public int[] getMonstersQuantity() {
        return monstersQuantity;
    }

    /**
     * Function to set the monsters quantity
     * @param monstersQuantity monsters quantity
     */
    public void setMonstersQuantity(int[] monstersQuantity) {
        this.monstersQuantity = monstersQuantity;
    }
}
