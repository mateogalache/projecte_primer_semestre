package Business.Characters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class Character {

    @SerializedName(value = "name") private  String nomPersonatge;
    @SerializedName(value = "player") private String nomJugador;
    @SerializedName(value = "xp") private int xpPoints;
    @SerializedName(value = "body") private int body;
    @SerializedName(value = "mind") private int mind;
    @SerializedName(value = "spirit") private int spirit;
    @SerializedName(value = "class") private String tipusPersonatge;

    @Expose(deserialize = false, serialize = false) private boolean conscient;

    @Expose(deserialize = false, serialize = false) private int actualLifePoints;

    @Expose(deserialize = false, serialize = false) private int totalLifePoints;


    /**
     * Constructor of the class character
     * @param nomPersonatge name of the character
     * @param nomJugador name of the player
     * @param xpPoints experience of the character
     * @param mind mind stat
     * @param body body stat
     * @param spirit spirit stat
     * @param tipusPersonatge character class
     */
    public Character(String nomPersonatge, String nomJugador, int xpPoints, int mind, int body, int spirit, String tipusPersonatge) {
        this.nomPersonatge = nomPersonatge;
        this.nomJugador = nomJugador;
        this.xpPoints = xpPoints;
        this.mind = mind;
        this.body = body;
        this.spirit = spirit;
        this.tipusPersonatge = tipusPersonatge;
    }

    /**
     * Function to get the character's name
     * @return character's name
     */

    public String getNomPersonatge() {
        return nomPersonatge;
    }

    /**
     * Function to set a new character name
     * @param nomPersonatge characer name
     */
    public void setNomPersonatge(String nomPersonatge) {
        this.nomPersonatge = nomPersonatge;
    }

    /**
     * Function to get the player's name
     * @return player's name
     */
    public String getNomJugador() {
        return nomJugador;
    }

    /**
     * Function to set the player's name
     * @param nomJugador player's name
     */
    public void setNomJugador(String nomJugador) {
        this.nomJugador = nomJugador;
    }

    /**
     * Function to get xp points
     * @return xp points
     */
    public int getXpPoints() {
        return xpPoints;
    }

    /**
     * Function to set xpPoints
     * @param xpPoints xp points
     */
    public void setXpPoints(int xpPoints) {
        this.xpPoints = xpPoints;
    }

    /**
     * Function to get body stat
     * @return body stat
     */
    public int getBody() {
        return body;
    }

    /**
     * Function to set vody stat
     * @param body body stat
     */
    public void setBody(int body) {
        this.body = body;
    }

    /**
     * Function to get mind stat
     * @return mind stat
     */
    public int getMind() {
        return mind;
    }

    /**
     * Function to set mind stat
     * @param mind mind stat
     */
    public void setMind(int mind) {
        this.mind = mind;
    }

    /**
     * Function to get the spirit stat
     * @return spirit stat
     */
    public int getSpirit() {
        return spirit;
    }

    /**
     * Function to set spirit stat
     * @param spirit spirit stat
     */
    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    /**
     * Function to get the character class
     * @return character class
     */
    public String getTipusPersonatge() {
        return tipusPersonatge;
    }

    /**
     * Function to get the level of the character
     * @return character level
     */
    public int getNivellInicial() {
        return xpPoints/100 + 1;
    }

    /**
     * Function to get total life points
     * @return total life points
     */
    public int getTotalLifePoints() {
        return totalLifePoints;
    }

    /**
     * Function to get if the character is conscient
     * @return boolean conscient or not
     */
    public boolean isConscient() {
        return conscient;
    }

    /**
     * Function to set if the character is conscient
     * @param conscient boolean conscient or not
     */
    public void setConscient(boolean conscient) {
        this.conscient = conscient;
    }

    /**
     * Function to get the actual life points
     * @return actual life points
     */
    public int getActualLifePoints() {
        return actualLifePoints;
    }

    /**
     * Function to set actual life points
     * @param actualLifePoints actual life points
     */
    public void setActualLifePoints(int actualLifePoints) {
        this.actualLifePoints = actualLifePoints;
    }

    /**
     * Function to set total life points
     * @param totalLifePoints total life points
     */
    public void setTotalLifePoints(int totalLifePoints) {
        this.totalLifePoints = totalLifePoints;
    }

    /**
     * Abstract funciton to get the preparaction action of the adventurer class
     * @return string of the preparation action
     */
    public abstract String preparationAction();

}
