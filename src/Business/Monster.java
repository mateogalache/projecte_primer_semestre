package Business;

public class Monster {

    private final String name;
    private final String challenge;
    private final int experience;
    private final int hitPoints;
    private final int initiative;
    private final String damageDice;
    private final String damageType;


    /**
     * Constructor of the monster
     * @param name name of the monster
     * @param challenge type of monster
     * @param experience experience when it dies
     * @param hitPoints life points
     * @param initiative initiative value
     * @param damageDice damage from the monster
     * @param damageType type of damage
     */
    public Monster(String name, String challenge, int experience, int hitPoints, int initiative, String damageDice, String damageType) {
        this.name = name;
        this.challenge = challenge;
        this.experience = experience;
        this.hitPoints = hitPoints;
        this.initiative = initiative;
        this.damageDice = damageDice;
        this.damageType = damageType;
    }

    /**
     * Function to get the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Function to get the type of monster
     * @return type of monster
     */
    public String getChallenge() {
        return challenge;
    }

    /**
     * Function to get the experience
     * @return experience
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Function to get the life
     * @return life points
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Function to get the initiative value
     * @return initiative value
     */
    public int getInitiative() {
        return initiative;
    }

    /**
     * Function to get the damage
     * @return the damage
     */
    public String getDamageDice() {
        return damageDice;
    }

    /**
     * Function to get the type of damage
     * @return type of damage
     */
    public String getDamageType() {
        return damageType;
    }
}
