package Business.Characters;

public class Adventurer extends Character {
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

    public String getPreparationHability() {
        return "Their spirit increases in +";
    }
    @Override

    public String preparationAction() {
        return "Self-motivated";
    }
}
