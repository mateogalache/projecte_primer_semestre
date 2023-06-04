package Business.Characters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class Character {

    @SerializedName(value = "name")  String nomPersonatge;
    @SerializedName(value = "player") String nomJugador;
    @SerializedName(value = "xp") int xpPoints;
    @SerializedName(value = "body") int body;
    @SerializedName(value = "mind") int mind;
    @SerializedName(value = "spirit") int spirit;
    @SerializedName(value = "class") String tipusPersonatge;

    @Expose(deserialize = false, serialize = false) private boolean conscient;

    @Expose(deserialize = false, serialize = false) private int actualLifePoints;

    @Expose(deserialize = false, serialize = false) private int totalLifePoints;




    public Character(String nomPersonatge, String nomJugador, int xpPoints, int mind, int body, int spirit, String tipusPersonatge) {
        this.nomPersonatge = nomPersonatge;
        this.nomJugador = nomJugador;
        this.xpPoints = xpPoints;
        this.mind = mind;
        this.body = body;
        this.spirit = spirit;
        this.tipusPersonatge = tipusPersonatge;
    }

    public String getNomPersonatge() {
        return nomPersonatge;
    }

    public void setNomPersonatge(String nomPersonatge) {
        this.nomPersonatge = nomPersonatge;
    }

    public String getNomJugador() {
        return nomJugador;
    }

    public void setNomJugador(String nomJugador) {
        this.nomJugador = nomJugador;
    }

    public int getXpPoints() {
        return xpPoints;
    }

    public void setXpPoints(int xpPoints) {
        this.xpPoints = xpPoints;
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public int getMind() {
        return mind;
    }

    public void setMind(int mind) {
        this.mind = mind;
    }

    public int getSpirit() {
        return spirit;
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    public String getTipusPersonatge() {
        return tipusPersonatge;
    }

    public int getNivellInicial() {
        return xpPoints/100 + 1;
    }

    public int getTotalLifePoints() {
        return totalLifePoints;
    }

    public boolean isConscient() {
        return conscient;
    }

    public void setConscient(boolean conscient) {
        this.conscient = conscient;
    }

    public int getActualLifePoints() {
        return actualLifePoints;
    }

    public void setActualLifePoints(int actualLifePoints) {
        this.actualLifePoints = actualLifePoints;
    }

    public void setTotalLifePoints(int totalLifePoints) {
        this.totalLifePoints = totalLifePoints;
    }
    public abstract String preparationAction();

}
