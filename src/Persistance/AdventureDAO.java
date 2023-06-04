package Persistance;

import Business.Adventure;
import Business.Combat;
import Business.Monster;
import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdventureDAO {
    private static final String PATH = "Files/adventures.json";

    private JsonElement initFile() throws IOException, FileNotFoundException{
        File file = new File(PATH);
        if (!file.exists()) {
            file.createNewFile();
        }

        return JsonParser.parseReader(new FileReader(PATH));
    }

    public List<Adventure> readAdventuresFromJson() throws FileNotFoundException, IllegalAccessError {
        JsonElement fileElement = JsonParser.parseReader(new FileReader(PATH));

        if (fileElement.isJsonNull()) {
            return new ArrayList<Adventure>();
        } else {

            JsonArray adventuresArray = fileElement.getAsJsonArray();
            List<Adventure> adventures = new ArrayList<Adventure>();

            for (JsonElement adventureElement : adventuresArray) {

                // create new Adventure to add
                Adventure adventure = readAdventure(adventureElement);
                // add Adventure to the list to be returned
                adventures.add(adventure);
            }

            return adventures;
        }
    }

    private Adventure readAdventure(JsonElement adventureElement) throws FileNotFoundException, IllegalAccessError {

        // get Json Object
        JsonObject adventureObject = adventureElement.getAsJsonObject();

        // extract each Data type
        String name = adventureObject.get("name").getAsString();
        List<Combat> combats = new ArrayList<>();

        JsonArray jsonCombats = adventureObject.getAsJsonArray("combats");

        for (JsonElement combatElement: jsonCombats) {

            Combat combat = readCombat(combatElement);
            combats.add(combat);

        }
        return new Adventure(name, combats);
    }

    private Combat readCombat(JsonElement combatElement) {
        JsonObject combatObject = combatElement.getAsJsonObject();

        int combatNumber = combatObject.get("combatNumber").getAsInt();
        JsonArray monstersJsonArray = combatObject.getAsJsonArray("monsters");
        List<Monster> monsters = new ArrayList<>();

        for (JsonElement monsterElement : monstersJsonArray) {
            Monster monster = readMonster(monsterElement);
            monsters.add(monster);
        }

        int[] quantity = new int[monsters.size()];
        // read quantity array from json
        JsonArray quantityJsonArray = combatObject.getAsJsonArray("monstersQuantity");
        for (int i = 0; i < monsters.size(); i++) {
            quantity[i] = quantityJsonArray.get(i).getAsInt();
        }
        return new Combat(combatNumber, monsters, quantity);
    }

    private Monster readMonster(JsonElement monsterElement) {
        JsonObject monsterObject = monsterElement.getAsJsonObject();

        String name = monsterObject.get("name").getAsString();
        String challenge = monsterObject.get("challenge").getAsString();
        int experience = monsterObject.get("experience").getAsInt();
        int hitPoints = monsterObject.get("hitPoints").getAsInt();
        int initiative = monsterObject.get("initiative").getAsInt();
        String damageDice = monsterObject.get("damageDice").getAsString();
        String damageType = monsterObject.get("damageType").getAsString();

        return new Monster(name, challenge, experience, hitPoints, initiative, damageDice, damageType);
    }


    public void addAdventuretoJSON(Adventure adventure) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonElement fileElement = initFile();

        List<Adventure> adventures = readAdventuresFromJson();

        adventures.add(adventure);
        FileWriter fw = new FileWriter(PATH);

        gson.toJson(adventures,fw);
        fw.close();
    }
}
