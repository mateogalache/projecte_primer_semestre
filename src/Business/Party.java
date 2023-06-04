package Business;


import Business.Characters.Character;

import java.util.ArrayList;
import java.util.List;

public class Party {
    private Character[] personatges;
    private boolean[] conscient;

    private int[] lifePoints;

    /**
     * Function that creates the party depending on the size
     * @param size size of the party
     */
    public Party(int size) {
        this.personatges = new Character[size];
        this.conscient = new boolean[size];
    }

    /**
     * Function that gets the character
     * @return the character
     */
    public Character[] getPersonatges() {
        return personatges;
    }

    public void setPersonatges(Character[] personatges) {
        this.personatges = personatges;
    }

    public void setPersonatge(Character personatge, int index) {
        this.personatges[index] = personatge;
    }

    public void setConscient(int index, boolean state) {this.conscient[index] = state;}

    /**
     * Function that gets if a criature is conscient
     * @return boolean  if its conscient or not
     */
    public boolean[] getConscient() {
        return conscient;
    }

    public List<String> gethabilities() {
        List<String > habilities = new ArrayList<>();

        for (Character personatge: personatges) {
            habilities.add(personatge.preparationAction());
        }

        return habilities;
    }



}
