package Business;

import Business.Characters.Character;
import Persistance.AdventureDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdventureManager {
    private Party party;
    private List<Combat> combats;

    /**
     * Function to get the adventure from the json
     * @return list of the adventures
     * @throws FileNotFoundException needed to read the json
     * @throws IOException needed to read the json
     */
    public List<Adventure> getAdventures() throws FileNotFoundException, IOException {
        AdventureDAO adventureDAO = new AdventureDAO();

        return adventureDAO.readAdventuresFromJson();
    }

    /**
     * Function to check if the adventure name exists in the json
     * @param adventureName adventure's name
     * @return boolean(true = the adventure name is not in the json).
     * @throws FileNotFoundException needed to read the json
     * @throws IOException needed to read the json
     */
    public boolean checkName(String adventureName) throws FileNotFoundException, IOException{
        AdventureDAO adventureDAO = new AdventureDAO();
        List<Adventure> adventures = adventureDAO.readAdventuresFromJson();
        boolean able = true;

        for (Adventure adventure : adventures) {
            if (adventure.getName().equals(adventureName)) {
                able = false;
                break;
            }
        }
        return able;
    }

    /**
     * function to add the adventure to the json
     * @param adventureName adventure's name
     * @param combats list of the combats
     * @throws IOException needed to write the json
     */
    public void createAdventure(String adventureName, List<Combat> combats) throws IOException {
        // Create Adventure Object
        Adventure adventure = new Adventure(adventureName, combats);
        AdventureDAO adventureDAO = new AdventureDAO();
        // Add adventure to JsonFIle
        adventureDAO.addAdventuretoJSON(adventure);
    }

    /**
     * Function to get the list of combats
     * @return the list of combats
     */
    public List<Combat> getCombats() {
        return combats;
    }

    /**
     * Function to add combats to the list of combats
     * @param combat combat to be added
     */
    public void addCombat(Combat combat) {
        List<Combat> combatList;
        if (this.combats == null) {
            combatList = new ArrayList<>();
        } else {
            combatList = combats;
        }
        combatList.add(combat);
        setCombats(combatList);
    }

    /**
     * Function to set the list of combats
     * @param combats list of combats
     */
    public void setCombats(List<Combat> combats) {
        this.combats = combats;
    }

    /**
     * Function to get the names of the adventures
     * @return list of the names of the adventures
     * @throws IOException needed to read the json
     */
    public List<String> getAdventureNames() throws IOException{
        List<Adventure> adventures = getAdventures();
        List<String> names = new ArrayList<>();
        for (Adventure adventure: adventures) {
            names.add(adventure.getName());
        }
        return names;
    }

    /**
     * Function to set the party
     * @param party the party
     */
    public void setParty(Party party) {
        this.party = party;
    }

    /**
     * Function to get the party of the adventure
     * @return the party
     */
    public Party getParty() {
        return party;
    }

    /**
     * Function to add a character to the party
     * @param index index where we want to add the character
     * @param personatge character to be added
     */
    public void addCharacterToParty(int index, Character personatge) {
        party.setPersonatge(personatge,index);
    }

    /**
     * Function to initialize the party
     * @param partySize party size
     */
    public void initParty(int partySize) {
        this.party = new Party(partySize);
    }

    /**
     * Function to check if the character is already in the party
     * @param character character to be added
     * @return boolean (true = the character is in the party)
     * @throws IOException needed to read the json
     */
    public boolean characterAlreadyInParty(int character) throws IOException{
        CharacterManager personatgeManager = new CharacterManager();
        Character personatge = personatgeManager.getCharacterFromIndex(personatgeManager.getAllCharacters().get(character));
        return personatgeManager.inListCharacter(personatge.getNomPersonatge(), Arrays.asList(party.getPersonatges()));
    }


}
