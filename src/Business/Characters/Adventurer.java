package Business.Characters;

public class Adventurer extends Character {
    /**
     * Constructor of the class adventurer
     * @param nomPersonatge name of the character
     * @param nomJugador name of the player
     * @param xpPoints experience points of the character
     * @param mind mind stat
     * @param body body stat
     * @param spirit spirit stat
     * @param tipusPersonatge character class
     */
    public Adventurer(String nomPersonatge, String nomJugador, int xpPoints, int mind, int body, int spirit, String tipusPersonatge) {
        super(nomPersonatge, nomJugador, xpPoints, mind, body, spirit, tipusPersonatge);
    }

    /**
     * Constructor of the class adventurer when a character is given
     * @param personatge character to create
     */
    public Adventurer(Character personatge) {
        super(personatge.getNomPersonatge(), personatge.getNomJugador(), personatge.getXpPoints(), personatge.getMind(), personatge.getBody(), personatge.getSpirit(), personatge.getTipusPersonatge());

    }


    /**
     * Function to get preparation action
     * @return string of the action
     */
    @Override

    public String preparationAction() {
        return " uses Self-motivated. Their Spirit increases in +1.";
    }
}
