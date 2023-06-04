package Persistance;

import Business.Monster;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MonsterDAO {

    private static final String PATH = "Files/monsters.json";
    public List<Monster> readMonstersFromJson() throws FileNotFoundException {
        JsonElement fileElement = JsonParser.parseReader(new FileReader(PATH));


        JsonArray monstersArray = fileElement.getAsJsonArray();
        List<Monster> monsters = new ArrayList<>();

        for (JsonElement monsterElement : monstersArray) {

            // create new monster to add
            Monster monster = readMonster(monsterElement);
            // add monster to the list to be returned
            monsters.add(monster);
        }

        return monsters;
    }

    private Monster readMonster(JsonElement monsterElement) {
        // get Json Object
        JsonObject monsterObject = monsterElement.getAsJsonObject();

        // extract each Data type
        String name = monsterObject.get("name").getAsString();
        String challenge = monsterObject.get("challenge").getAsString();
        int experience = monsterObject.get("experience").getAsInt();
        int hitPoints = monsterObject.get("hitPoints").getAsInt();
        int initiative = monsterObject.get("initiative").getAsInt();
        String damageDice = monsterObject.get("damageDice").getAsString();
        String damageType = monsterObject.get("damageType").getAsString();

        return new Monster(name, challenge, experience, hitPoints, initiative, damageDice, damageType);

    }
    public Monster getMonster(String monsterName) throws FileNotFoundException{
        List<Monster> monsters = readMonstersFromJson();
        int index = 0;
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getName().equals(monsterName)) {
                index = i;
                break;
            }
        }
        return monsters.get(index);
    }
}
