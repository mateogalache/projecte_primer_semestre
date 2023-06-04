package Business;

public class Monster {

    private final String name;
    private final String challenge;
    private final int experience;
    private final int hitPoints;
    private final int initiative;
    private final String damageDice;
    private final String damageType;



    public Monster(String name, String challenge, int experience, int hitPoints, int initiative, String damageDice, String damageType) {
        this.name = name;
        this.challenge = challenge;
        this.experience = experience;
        this.hitPoints = hitPoints;
        this.initiative = initiative;
        this.damageDice = damageDice;
        this.damageType = damageType;
    }


    public String getName() {
        return name;
    }

    public String getChallenge() {
        return challenge;
    }

    public int getExperience() {
        return experience;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getInitiative() {
        return initiative;
    }

    public String getDamageDice() {
        return damageDice;
    }

    public String getDamageType() {
        return damageType;
    }
}
