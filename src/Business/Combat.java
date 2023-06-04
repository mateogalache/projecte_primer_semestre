package Business;

import java.util.List;

public class Combat {

    private final int combatNumber;
    private List<Monster> monsters;
    private int[] monstersQuantity;

    public Combat(int combatNumber, List<Monster> monsters, int[] monstersQuantity) {
        this.combatNumber = combatNumber;
        this.monsters = monsters;
        this.monstersQuantity = monstersQuantity;
    }

    public int getCombatNumber() {
        return combatNumber;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public int[] getMonstersQuantity() {
        return monstersQuantity;
    }

    public void setMonstersQuantity(int[] monstersQuantity) {
        this.monstersQuantity = monstersQuantity;
    }
}
