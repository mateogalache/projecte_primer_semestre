package Business;

import Business.Characters.Adventurer;
import Business.Characters.Character;
import Persistance.CharacterDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharacterManager {

    /**
     * Function to get all the characters from the json
     * @return list of the characters
     * @throws IllegalStateException needed to read the json
     * @throws IOException needed to read the json
     */
    public List<String> getAllCharacters() throws IllegalStateException, IOException {
        CharacterDAO characterDAO = new CharacterDAO();
        List<Character> characters = characterDAO.readCharactersFromJson();
        List<String> names = new ArrayList<String>();

        for (Character character : characters) {
            names.add(character.getNomPersonatge());
        }
        return names;
    }

    /**
     * Function to check if the name of the user is in the json
     * @param name name of the user
     * @return boolean (depends on the function inListCharacter())
     * @throws IOException
     */
    public boolean checkNameUser(String name) throws IOException {
        CharacterDAO personatgeDAO = new CharacterDAO();
        List<Character> personatgeList = personatgeDAO.readCharactersFromJson();
        return inListCharacter(name,personatgeList);
    }

    /**
     * Function to check if name passed is in the json
     * @param name name to be checked
     * @param personatgeList list of characters
     * @return boolean (true = is in the list)
     */
    public boolean inListCharacter(String name, List<Character> personatgeList) {
        boolean inList = false;
        for (int i = 0; i < personatgeList.size(); i++) {
            if (personatgeList.get(i) != null) {
                if (name.equals(personatgeList.get(i).getNomPersonatge())) {
                    inList = true;
                }
            }
        }
        return inList;
    }

    /**
     * Function to throw dice of 6
     * @return random number
     */
    private int throwD6() {
        Dice dice = new Dice("D6", 6);
        return dice.throwDice();
    }

    /**
     * Function to generate random stats for the characters
     * @return values of the stats
     */
    public int[] generateStat() {
        int[] values = new int[3];
        values[0] = throwD6();
        values[1] = throwD6();
        values[2] = calculateEstadistics(values[0], values[1]);
        return values;
    }

    /**
     * Function to calculate the final value of the stats
     * @param firstValue first value to calculate the stats
     * @param secondValue second value to calculate the stats
     * @return value of the stat
     */
    private int calculateEstadistics(int firstValue, int secondValue) {
        int suma = firstValue + secondValue;
        int value = 0;
        if (suma == 2) {value = -1;}
        if (suma >= 3 && suma <= 5) {value = 0;}
        if (suma >= 6 && suma <= 9) {value = 1;}
        if (suma >= 10 && suma <= 11) {value = 2;}
        if (suma == 12) {value = 3;}
        return value;
    }

    /**
     * Function to create the character
     * @param name name of the character
     * @param playerName name of the player
     * @param level level of the character
     * @param body body stat
     * @param mind mind stat
     * @param spirit spirit stat
     * @throws IOException needed to read the json
     */
    public void createCharacter(String name, String playerName, int level, int body, int mind, int spirit) throws IOException{
        int xpPoints = calculateInitialLevel(level);
        CharacterDAO characterDAO = new CharacterDAO();
        Adventurer adventurer = new Adventurer(name, playerName, xpPoints, body, mind, spirit, "adventurer");
        characterDAO.addCharacterToJSON(adventurer);
    }

    /**
     * Function to calculate xp points of the character depending on the level
     * @param nivellInicial level
     * @return xp points
     */
    private int calculateInitialLevel(int nivellInicial) {
        int xpPoints = 0;
        if (nivellInicial > 1) {
            xpPoints = (nivellInicial - 1) * 100;
        }
        return xpPoints;
    }

    /**
     * Function to get the player's name from the json
     * @param playerName player's name
     * @return list of player's name
     * @throws FileNotFoundException needed to read the json
     */
    public List<String> getCharactersFromPlayer(String playerName) throws FileNotFoundException {
        CharacterDAO personatgeDAO = new CharacterDAO();
        List<Character> personatges = personatgeDAO.readCharactersFromJson();
        List<String> names = new ArrayList<String>();
        playerName = playerName.toUpperCase();

        boolean found = false;
        for (int i = 0; i < personatges.size(); i++) {
            String actualPlayerName = personatges.get(i).getNomJugador().toUpperCase();
            if (actualPlayerName.equals(playerName) || actualPlayerName.contains(playerName)) {
                found = true;
                names.add(personatges.get(i).getNomPersonatge());
            }
        }
        if (found) {
            return names;
        } else {
            return null;
        }
    }

    /**
     * Function to get one character from the json depending on the character name
     * @param nameCharacter character name
     * @return the chracter
     * @throws IOException needed to read the json
     */
    public Character getCharacterFromIndex(String nameCharacter) throws IOException{
        CharacterDAO personatgeDAO = new CharacterDAO();
        return personatgeDAO.getCharacter(nameCharacter);
    }

    /**
     * Function to delete a character from the json
     * @param character the character
     * @throws IOException needed to read the json
     */
    public void deleteCharacter(Character character) throws IOException {
        CharacterDAO personatgeDAO = new CharacterDAO();
        personatgeDAO.deleteCharacterFromJSON(character);
    }

    /**
     * Function to create the adventurer class
     * @param personatge the character
     * @return the adventurer created
     */
    public Character initCharacter(Character personatge) {

        return new Adventurer(personatge);
    }

    /**
     * Function to calculate the damage of the character
     * @param attacker character
     * @return damage
     */
    public int calculateAttack(Character attacker) {

        return throwD6() + attacker.getBody();
    }

    /**
     * Function to calculate the 'curacio' made from the character
     * @param mind mind stat
     * @return curacio
     */
    public int makeCuracio(int mind) {
        Dice dice = new Dice("d8",8);
        return dice.throwDice() + mind;
    }
}
