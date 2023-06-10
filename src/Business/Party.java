package Business;


import Business.Characters.Character;

import java.util.ArrayList;
import java.util.List;

public class Party {
    private Character[] personatges;
    private boolean[] conscient;

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

    /**
     * Function to set a character
     * @param personatge chracter
     * @param index index in the party
     */
    public void setPersonatge(Character personatge, int index) {
        this.personatges[index] = personatge;
    }

    /**
     * Function to set if a character is conscient in the party
     * @param index index in the party
     * @param state state of the character (conscient or not)
     */
    public void setConscient(int index, boolean state) {this.conscient[index] = state;}

    /**
     * Function that gets if a criature is conscient
     * @return boolean  if its conscient or not
     */
    public boolean[] getConscient() {
        return conscient;
    }

    /**
     * Function to get the list of habilities of the characters in the party
     * @return list of habilities
     */
    public List<String> gethabilities() {
        List<String > habilities = new ArrayList<>();

        for (Character personatge: personatges) {
            habilities.add(personatge.preparationAction());
        }

        return habilities;
    }





}
