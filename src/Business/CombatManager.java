package Business;

import Business.Characters.Character;
import Persistance.MonsterDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CombatManager {
    private Combat combat;
    private Party party;
    private List<String> characterOrder; // sorted List of Character's name in pitch
    private int[] characterOrderValues; // sorted array of the initiatives calculated
    private List<String> monsterOrder;

    private int[] actualLifePoints; // array that contains the actual life points of all the criatures on pitch (sorted by initiative)


    // to get the initial party lifePoints we can check it through the party attribute (party.getLifePoints())
    private int[] partyLifePoints; // actual party lifePoints

    private List<Integer> allInitiatives;
    private List<String> allParticipants;

    private List<Integer> actualLifeMonsters;
    private List<String> onlyMonsters;
    private List<Integer> onlyMonstersInitiatives;


    public List<Monster> getMonsters() throws IOException {
        MonsterDAO monsterDAO = new MonsterDAO();
        return monsterDAO.readMonstersFromJson();
    }
    public void resizeCombatQuantityLength(Combat combat, int i) {
        int[] auxArray = Arrays.copyOf(combat.getMonstersQuantity(), combat.getMonstersQuantity().length + i);
        combat.setMonstersQuantity(auxArray);
    }

    public void removeElementFromArray(Combat combat, int index) {
        int[] auxArray = new int[combat.getMonstersQuantity().length];
        int j = 0;
        for (int i = 0; i < combat.getMonstersQuantity().length; i++) {
            if (i != index) {
                auxArray[j] = combat.getMonstersQuantity()[i];
                j++;
            }

        }
        combat.setMonstersQuantity(auxArray);
        resizeCombatQuantityLength(combat, -1);

    }

    public List<String> listMonsterNames() throws FileNotFoundException {
        MonsterDAO monsterDAO = new MonsterDAO();
        List<Monster> monsters = monsterDAO.readMonstersFromJson();
        List<String> monsterNames = new ArrayList<>();

        for (Monster monster : monsters) {
            monsterNames.add(monster.getName());
        }
        return monsterNames;
    }

    public List<String> listMonsterChallenges() throws FileNotFoundException {
        MonsterDAO monsterDAO = new MonsterDAO();
        List<Monster> monsters = monsterDAO.readMonstersFromJson();
        List<String> monsterChallenge = new ArrayList<>();

        for (Monster monster : monsters) {
            monsterChallenge.add(monster.getChallenge());
        }
        return monsterChallenge;
    }



    public void initParty(Party party) {
        this.party = party;
    }


    private void sortOrder(int[] characterValues, int[] monsterValues) {
        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        boolean end = false;
        int i = 0;
        int j = 0;
        while (!end) {
            if (i == characterValues.length && j == monsterValues.length) {
                end = true;
            }  else {
                if (i == characterValues.length) {
                    while (j < monsterValues.length) {
                        names.add(monsterOrder.get(j));
                        values.add(monsterValues[j]);
                        j++;
                    }
                }

                else if (j == monsterValues.length) {
                    while (i < characterValues.length) {
                        names.add(characterOrder.get(i));
                        values.add(characterValues[i]);
                        i++;
                    }
                } else {
                    if (characterValues[i] > monsterValues[j]) {
                        names.add(characterOrder.get(i));
                        values.add(characterValues[i]);
                        i++;
                    } else {
                        names.add(monsterOrder.get(j));
                        values.add(monsterValues[j]);
                        j++;
                    }
                }


            }
        }
        this.characterOrder = names;
        int[] aux = new int[values.size()];
        for (int k = 0; k < values.size(); k++) {
            aux[k] = values.get(k);
        }
        this.characterOrderValues = aux;
    }

    /**
     * function that returns the combat managed by the combat manager class
     * @return combat
     */
    public Combat getCombat() {
        return combat;
    }
    public void setCombat(Combat combat) {
        this.combat = combat;
    }
    public Combat createCombat(int combatIndex, List<String> monsters, List<Integer> monsterQuantity) throws FileNotFoundException{
        MonsterDAO monsterDAO = new MonsterDAO();
        List<Monster> monsterList = new ArrayList<>();

        for (String monsterName: monsters) {
            monsterList.add(monsterDAO.getMonster(monsterName));
        }
        int[] quantity = new int[monsterQuantity.size()];
        for (int i = 0; i < monsterQuantity.size(); i++) {
            quantity[i] = monsterQuantity.get(i);
        }

        return new Combat(combatIndex, monsterList, quantity);
    }

    public void updateCombat(int index, List<String> monsters, List<Integer> quantity) {
        // 1st Remove monster form List
        combat.getMonsters().remove(index);

        // 2nd Remove monster amount from monsterQuantity[]
        removeElementFromArray(combat, index);

        // update lists
        monsters.remove(index);
        quantity.remove(index);
    }

    public Integer calculateIniative(int spirit) {
        return throwd12() + spirit;
    }

    private int throwd12(){
        Dice dice = new Dice("D12",12);
        return dice.throwDice();
    }

    public Monster getMonsterByName(String name) throws FileNotFoundException {
        MonsterDAO monsterDAO = new MonsterDAO();
        return monsterDAO.getMonster(name);
    }

    public int calculateMonsterAttack(Monster attacker) {
        int n = Integer.parseInt(attacker.getDamageDice().substring(1));
        Dice dice = new Dice(attacker.getDamageDice(),n);
        return dice.throwDice();
    }

    private int diceCombatAttack() {
        Dice dice = new Dice("d10",10);
        return dice.throwDice();
    }

    public int randomAttack(int size) {
        String sDice = "d" + size;
        Dice dice = new Dice(sDice,size);
        return dice.throwDice();
    }

    public void orderAllParticipants(Party party, Combat combat) {
        allInitiatives = new ArrayList<>();
        allParticipants = new ArrayList<>();
        for(int j=0;j<party.getPersonatges().length;j++){
            allInitiatives.add(calculateIniative(party.getPersonatges()[j].getSpirit()));
        }
        for(Character character: party.getPersonatges()){
            allParticipants.add(character.getNomPersonatge());
        }
        for(int j=0;j<combat.getMonsters().size();j++){
            for(int m=0;m<combat.getMonstersQuantity()[j];m++){
                allParticipants.add(combat.getMonsters().get(j).getName());
                allInitiatives.add(combat.getMonsters().get(j).getInitiative());
            }
        }
        int n = allInitiatives.size();
        for (int m = 0; m < n - 1; m++) {
            for (int j = 0; j < n - m - 1; j++) {
                if (allInitiatives.get(j) < allInitiatives.get(j + 1)) {
                    // Swap the integers
                    int tempInt = allInitiatives.get(j);
                    allInitiatives.set(j, allInitiatives.get(j + 1));
                    allInitiatives.set(j + 1, tempInt);

                    // Swap the corresponding strings
                    String tempStr = allParticipants.get(j);
                    allParticipants.set(j, allParticipants.get(j + 1));
                    allParticipants.set(j + 1, tempStr);
                }
            }
        }
    }

    public List<Integer> getAllInitiatives(){
        return allInitiatives;
    }
    public List<String> getAllParticipants(){
        return allParticipants;
    }

    public void orderMonsters(Combat combat){
        onlyMonsters = new ArrayList<>();
        onlyMonstersInitiatives = new ArrayList<>();
        actualLifeMonsters = new ArrayList<>();
        for(int j=0;j<combat.getMonsters().size();j++){
            for(int m=0;m<combat.getMonstersQuantity()[j];m++){
                onlyMonsters.add(combat.getMonsters().get(j).getName());
                onlyMonstersInitiatives.add(combat.getMonsters().get(j).getInitiative());
                actualLifeMonsters.add(combat.getMonsters().get(j).getHitPoints());
            }
        }
        int t = onlyMonstersInitiatives.size();
        for (int m = 0; m < t - 1; m++) {
            for (int j = 0; j < t - m - 1; j++) {
                if (onlyMonstersInitiatives.get(j) < onlyMonstersInitiatives.get(j + 1)) {
                    // Swap the integers
                    int tempInt = onlyMonstersInitiatives.get(j);
                    onlyMonstersInitiatives.set(j, onlyMonstersInitiatives.get(j + 1));
                    onlyMonstersInitiatives.set(j + 1, tempInt);

                    // Swap the corresponding strings
                    String tempStr = onlyMonsters.get(j);
                    onlyMonsters.set(j, onlyMonsters.get(j + 1));
                    onlyMonsters.set(j + 1, tempStr);

                    int tempInt2 = actualLifeMonsters.get(j);
                    actualLifeMonsters.set(j, actualLifeMonsters.get(j + 1));
                    actualLifeMonsters.set(j + 1, tempInt2);
                }
            }
        }
    }

    public List<String> getOnlyMonsters(){
        return onlyMonsters;
    }
    public List<Integer> getActualLifeMonsters(){
        return actualLifeMonsters;
    }
    public List<Integer> getOnlyMonstersInitiatives(){
        return onlyMonstersInitiatives;
    }

    public int calculateMultiplier() {
        int mult = 1;
        if(diceCombatAttack() < 2){
            mult = 0;
        } else if (diceCombatAttack() == 10) {
            mult = 2;
        }
        return mult;
    }

    public int getActualLife(Party party,Character attacker){
        int n = 0;
        for(int i=0;i<party.getPersonatges().length;i++){
            if(party.getPersonatges()[i].getNomPersonatge().equals(attacker.getNomPersonatge())){
                n = i;
            }
        }
        return party.getPersonatges()[n].getActualLifePoints();
    }

    public int monsterToBeAttacked(List<Integer> actualLifeMonsters) {
        int life=0,index=0;
        for(int i=0;i<actualLifeMonsters.size();i++){
            if(actualLifeMonsters.get(i) > life){
                life = actualLifeMonsters.get(i);
                index = i;
            }
        }
        return index;
    }
}
