package Persistance;

import Business.Characters.Adventurer;
import Business.Characters.Character;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class CharacterDAO {
    private static final String PATH = "Files/characters.json";

    /**
     * Function to read the characters in the json
     * @return list of characters
     * @throws FileNotFoundException needed to write/read the json
     */
    public List<Character> readCharactersFromJson() throws FileNotFoundException {
        JsonElement fileElement = JsonParser.parseReader(new FileReader(PATH));
        //JsonObject fileObject = fileElement.getAsJsonObject();

        JsonArray charactersArray = fileElement.getAsJsonArray();
        List<Character> personatges = new ArrayList<>();

        for (JsonElement characterElement : charactersArray) {
            // create new monster to add
            Character character = readPersonatge(characterElement);
            // add monster to the list to be returned
            personatges.add(character);
        }

        return personatges;
    }

    /**
     * Function to read ONE character
     * @param characterElement character element
     * @return the character
     */
    private Character readPersonatge(JsonElement characterElement) {
        // get Json Object
        JsonObject characterObject = characterElement.getAsJsonObject();

        // extract each Data type
        String name = characterObject.get("name").getAsString();
        String player = characterObject.get("player").getAsString();
        int xp = characterObject.get("xp").getAsInt();
        int body = characterObject.get("body").getAsInt();
        int mind = characterObject.get("mind").getAsInt();
        int spirit = characterObject.get("spirit").getAsInt();
        String type = characterObject.get("class").getAsString().toLowerCase();

        return new Adventurer(name,player,xp,mind,body,spirit,type);
    }

    /**
     * Function to add a character to the json
     * @param character the character to be added
     * @throws IOException needed to write/read the json
     */
    public void addCharacterToJSON(Character character) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<Character> characters = readCharactersFromJson();

        characters.add(character);

        FileWriter fw = new FileWriter(PATH);

        gson.toJson(characters,fw);
        fw.close();
    }

    /**
     * Function to get ONE character depending on his name
     * @param name name of the character
     * @return the character
     * @throws FileNotFoundException needed to write/read the json
     */
    public Character getCharacter(String name) throws FileNotFoundException {
        List<Character> personatges = readCharactersFromJson();

        Character characterToReturn = personatges.get(0);

        for (Character characterAux : personatges) {

            if (characterAux.getNomPersonatge().equals(name)) {
                characterToReturn = characterAux;
            }
        }

        return characterToReturn;
    }

    /**
     * Function to delete a character from the json
     * @param personatge the charcter to be deleted
     * @throws IOException needed to write/read the json
     */
    public void deleteCharacterFromJSON(Character personatge) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Character> personatges = readCharactersFromJson();
        List<Character> writeCharacters = new ArrayList<>();

        for (Character value : personatges) {
            if (!value.getNomPersonatge().equals(personatge.getNomPersonatge())) {
                writeCharacters.add(value);
            }
        }

        FileWriter fw = new FileWriter(PATH);

        gson.toJson(writeCharacters,fw);
        fw.close();

    }
}
