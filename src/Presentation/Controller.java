package Presentation;

import Business.*;
import Business.Characters.Character;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;

public class Controller {

    private boolean cDead = false,mDead = false;
    private static final int MAX_ENCOUNTERS = 4;
    private static final int MAX_ERRORS = 3;
    private Menu menu;

    private static final String CHARACTERS_FILE_NAME = "characters.json";
    private static final String MONSTERS_FILE_NAME = "monsters.json";
    private static final String ADVENTURES_FILE_NAME = "adventures.json";


    /**
     * Function to run the progam
     */
    public void run() {
        menu = new Menu();
        menu.initialMessage();
        if(loadLocalData()[0] || loadLocalData()[1] || loadLocalData()[2]){
            menu.errorReader(loadLocalData()[0],loadLocalData()[1],loadLocalData()[2]);
        }else{
            try{
                int option;
                menu.dataSuccesfull();
                do{
                    menu.showMenu();
                    do{
                        option = menu.askOption();
                    }while(option > 5);
                    CharacterManager characterManager = new CharacterManager();
                    switchOperation(option,characterManager);
                }while(option != 5);

            }catch (IOException e) {

            }



        }



    }

    /**
     * Function that call other functions depending on the option chosen in the switch of the menu.
     * @param option option chose
     * @param characterManager controller of the class character to get values of the characters
     * @throws IOException needed to read/write the json
     */
    private void switchOperation(int option,CharacterManager characterManager) throws IOException{
        switch(option){
            case 1:
                menu.nameCreation();
                String name = menu.askCharacterName();
                if(characterManager.checkNameUser(name)){
                    menu.nameNotDisponible();
                }else{
                    String playerName = menu.askPlayerName(name);
                    int level = menu.askLevel();
                    menu.generatingStats(level);

                    int[] bodyValues = characterManager.generateStat();
                    menu.showSingleStat("body", bodyValues[0], bodyValues[1]);

                    int[] mindValues = characterManager.generateStat();
                    menu.showSingleStat("mind", mindValues[0], mindValues[1]);

                    int[] spiritValues = characterManager.generateStat();
                    menu.showSingleStat("spirit", spiritValues[0], spiritValues[1]);

                    int body = bodyValues[2];
                    int mind = mindValues[2];
                    int spirit = spiritValues[2];

                    menu.listStats(body, mind, spirit);
                    characterManager.createCharacter(name, playerName, level, body, mind, spirit);
                    menu.characterCreated(name);
                }
                menu.nextLine();
                break;
            case 2:
                String player = menu.listCharactersMessage();
                int size = 0;
                String nameCharacter = "";
                if(player.equals("")){
                    size = characterManager.getAllCharacters().size();
                    menu.showAllCharacters(characterManager.getAllCharacters());
                    nameCharacter = characterManager.getAllCharacters().get(menu.meetCharacter(size)-1);
                }else{
                    List<String> someCharacters = characterManager.getCharactersFromPlayer(player);
                    size = someCharacters.size();
                    menu.showSomeCharacters(someCharacters);
                    nameCharacter = someCharacters.get(menu.meetCharacter(size)-1);
                }

                Character selectedCharacted = characterManager.getCharacterFromIndex(nameCharacter);
                menu.showCharacterStats(selectedCharacted);
                boolean delete = askToDeleteCharacter(nameCharacter);
                if (delete) {
                    menu.characterDeleted(nameCharacter);
                    characterManager.deleteCharacter(selectedCharacted);
                }
                menu.nextLine();
                break;
            case 3:
                menu.planningAnAdventure();
                String adventureName = menu.nameAdventure();
                if (checkAdventureName(adventureName)) {
                    // continue with the process
                    menu.adventureNameCorrect(adventureName);

                    // ask amount of encounters for the Adventure
                    int encounters = menu.askEncounters(MAX_ENCOUNTERS, MAX_ERRORS);
                    if (encounters != 0) {
                        menu.nEncounters(encounters);
                        // create every Encounter
                        AdventureManager adventureManager = new AdventureManager();

                        createAdventure(adventureName, adventureManager, encounters);
                    }
                } else {
                    // show error message and exit
                    menu.adventureNameNotAble();
                }
                break;
            case 4:
                AdventureManager adventureManager = new AdventureManager();

                int index = menu.playAdventureMenu(adventureManager.getAdventureNames()) -1; // ask the adventure we are going to play

                // 1st init party
                int partySize = menu.askPartySize(adventureManager.getAdventures().get(index).getName()); // ask the size of the party
                menu.askCharacterMessage(partySize);
                createParty(partySize, characterManager, adventureManager);

                // 2nd start to play the Adventure
                Adventure adventure = adventureManager.getAdventures().get(index);
                menu.startAdventure(adventure.getName());
                playAdventure(adventure,adventureManager,characterManager);
                menu.nextLine();
                break;
            case 5:
                menu.finalMessage();
                break;

        }
    }

    /**
     * Function that plays the adventure
     * @param adventure class adventure where we have information about it
     * @param adventureManager manager of the adventure
     * @param characterManager manager of the characters
     * @throws IOException needed to read/write the json
     */
    private void playAdventure(Adventure adventure, AdventureManager adventureManager, CharacterManager characterManager) throws IOException {
        CombatManager combatManager = new CombatManager();
        Party party = adventureManager.getParty();
        for(int j=0;j<party.getPersonatges().length;j++) {
            party.getPersonatges()[j].setTotalLifePoints((10 + party.getPersonatges()[j].getBody()) * party.getPersonatges()[j].getNivellInicial());
            party.getPersonatges()[j].setActualLifePoints((10 + party.getPersonatges()[j].getBody()) * party.getPersonatges()[j].getNivellInicial());
        }
        for(int i=0;i<adventure.getCombats().size();i++){
            mDead = false;
            combatManager.orderAllParticipants(party, adventure.getCombats().get(i));
            menu.titleEncounter(i,adventure.getCombats().get(i).getMonsters(),adventure.getCombats().get(i).getMonstersQuantity());
            menu.titlePreparation();
            menu.showPreparation(party.gethabilities(),adventureManager.getParty().getPersonatges());
            for(int j=0;j<party.getPersonatges().length;j++){
                party.getPersonatges()[j].setSpirit(party.getPersonatges()[j].getSpirit()+1);
            }
            combatManager.orderMonsters(adventure.getCombats().get(i));
            menu.showOrder(combatManager.getAllParticipants(),combatManager.getAllInitiatives());
            menu.titleCombatStage();
            int round = 0;
            while(!mDead && !cDead){
                round++;
                menu.showRound(round);
                menu.showPartyPoints(party);
                int monsterIndex = 0;
                for(int j=0;j<combatManager.getAllParticipants().size();j++){
                    int mult = combatManager.calculateMultiplier();
                    if(isCharacter(party.getPersonatges(),combatManager.getAllParticipants(),j)){
                        Character attacker = characterManager.getCharacterFromIndex(combatManager.getAllParticipants().get(j));
                        attackCharacter(attacker,combatManager,characterManager,party, mult);
                        if(monstersDead(combatManager.getActualLifeMonsters())){
                            mDead = true;
                            break;
                        }
                    }else{
                        attackMonster(combatManager, party,monsterIndex,mult,j);
                        if(charactersDead(party)){
                            cDead  = true;
                            break;
                        }
                    }
                }
                menu.endRound(round);
            }
            if(cDead){
                break;
            }
            menu.restStageTitle();
            restStage(adventure,party,characterManager,i);
        }
        menu.lastMessage(cDead,mDead,adventure.getName());
    }

    /**
     * Function to make the rest stage in the adventure
     * @param adventure class adventure
     * @param party the party of the adventure
     * @param characterManager manager of the characters
     * @param i int to know in what combat/encounter we are,
     */
    private void restStage(Adventure adventure, Party party,CharacterManager characterManager,int i) {
        int numberXp = 0;
        for(int j = 0;j<adventure.getCombats().get(i).getMonsters().size();j++){
            numberXp = numberXp + (adventure.getCombats().get(i).getMonsters().get(j).getExperience() * adventure.getCombats().get(i).getMonstersQuantity()[j]);
        }
        for(Character character: party.getPersonatges()){
            int oldXp = character.getXpPoints();
            int oldLevel = oldXp/100;
            character.setXpPoints(oldXp + numberXp);
            boolean levelUp = character.getXpPoints() / 100 > oldLevel;
            menu.gainXp(numberXp,character,levelUp);
        }
        menu.nextLine();
        for(Character character: party.getPersonatges()){
            if (character.getActualLifePoints() > 0){
                int curacio = characterManager.makeCuracio(character.getMind());
                character.setActualLifePoints(character.getActualLifePoints() + curacio);
                menu.makeCuracio(curacio,character.getNomPersonatge());
            }else{
                menu.makeCuracioDead(character.getNomPersonatge());
            }
        }
    }

    /**
     * Function that makes the attack of the monsters
     * @param combatManager manager of the combat
     * @param party party of the adventure (the monster will randomly attack one character from the party)
     * @param monsterIndex the monster that attacks
     * @param mult multiplier attack(1=normal,2=critic,0=fails).
     * @param j int to know the attacker inside the allParticipants list
     * @throws FileNotFoundException needed to read/write the json
     */

    private void attackMonster(CombatManager combatManager, Party party, int monsterIndex, int mult, int j) throws FileNotFoundException {
        if (combatManager.getActualLifeMonsters().get(monsterIndex) > 0){
            Monster attacker = combatManager.getMonsterByName(combatManager.getAllParticipants().get(j));
            int damageAttack = combatManager.calculateMonsterAttack(attacker) * mult;
            int indexOpponent;
            do{
                indexOpponent = combatManager.randomAttack(party.getPersonatges().length) - 1;
            }while(party.getPersonatges()[indexOpponent].getActualLifePoints() == 0);
            party.getPersonatges()[indexOpponent].setActualLifePoints(party.getPersonatges()[indexOpponent].getActualLifePoints()- damageAttack);
            menu.makeAttackMonster(attacker, damageAttack,party.getPersonatges()[indexOpponent].getNomPersonatge(),mult,attacker.getDamageType());
            if(party.getPersonatges()[indexOpponent].getActualLifePoints() <= 0){
                party.getPersonatges()[indexOpponent].setActualLifePoints(0);
                menu.dieCharacter(party.getPersonatges()[indexOpponent].getNomPersonatge());
            }
        }
    }

    /**
     * Function that makes the attack of the characters
     * @param attacker character that attacks
     * @param combatManager manager of the combats
     * @param characterManager manager of the character
     * @param party party of the adventure
     * @param mult multiplier attack(1=normal,2=critic,0=fails).
     */
    private void attackCharacter(Character attacker, CombatManager combatManager, CharacterManager characterManager, Party party, int mult) {
        if(combatManager.getActualLife(party,attacker) > 0){
            int damageAttack = characterManager.calculateAttack(attacker) * mult;
            int indexOpponent = combatManager.monsterToBeAttacked(combatManager.getActualLifeMonsters());
            combatManager.getActualLifeMonsters().set(indexOpponent,combatManager.getActualLifeMonsters().get(indexOpponent)- damageAttack);
            menu.makeAttack(attacker, damageAttack,combatManager.getOnlyMonsters().get(indexOpponent),mult);
            if(combatManager.getActualLifeMonsters().get(indexOpponent) <= 0){
                menu.dieMonster(combatManager.getOnlyMonsters().get(indexOpponent));
            }
        }
    }


    /**
     * Function to know if all monsters are dead
     * @param actualLifeMonsters list of the life of each monster with the initiative order.
     * @return boolean (true=all monsters are dead)
     */
    private boolean monstersDead(List<Integer> actualLifeMonsters) {
        boolean dead = true;
        for(int i =0;i<actualLifeMonsters.size();i++){
            if(actualLifeMonsters.get(i) > 0){
                dead = false;
            }
        }
        return dead;
    }

    /**
     * Function to know if all the characters are dead
     * @param party party of the adventure
     * @return boolean(true = all the characters are dead)
     */
    private boolean charactersDead(Party party) {
        boolean dead = true;
        for(int i =0;i<party.getPersonatges().length;i++){
            if(party.getPersonatges()[i].getActualLifePoints() > 0){
                dead = false;
                break;
            }
        }
        return dead;
    }

    /**
     * Function to know if the attacker is a character
     * @param onlyCharacters list of the characters with the initiative order
     * @param allParticipants all participants (included monsters) with the initiative order
     * @param i int to know what participant we are looking for
     * @return boolean (true = the attacker is a character).
     */
    private boolean isCharacter(Character[] onlyCharacters, List<String> allParticipants, int i){
        boolean isCharacter = false;
            for(int j = 0;j<onlyCharacters.length;j++){
                if(onlyCharacters[j].getNomPersonatge().equals(allParticipants.get(i))){
                    isCharacter = true;
                }
            }
        return isCharacter;
    }

    /**
     * Function to load the data from the json
     * @return boolean[] of the errors if we can't load the data from one or more json
     */
    private boolean[] loadLocalData() {
        boolean[] errors = new boolean[3];

        try{
            CharacterManager characterManager = new CharacterManager();
            characterManager.getAllCharacters();
        } catch(IOException e){
            errors[0] = true;
        }
        try{
            AdventureManager adventureManager = new AdventureManager();
            adventureManager.getAdventures();
        }catch(IOException e){
            errors[1] = true;
        }
        try{
            CombatManager combatManager = new CombatManager();
            combatManager.getMonsters();
        }catch(IOException e){
            errors[2] = true;
        }
        return errors;
    }

    /**
     * Function that ask the user wants to delete the character
     * @param nomPersonatge name of the character
     * @return boolean(true = to delete the user)
     */
    private boolean askToDeleteCharacter(String nomPersonatge) {
        boolean answer = false;
        String response = menu.askToDeleteCharacter(nomPersonatge);

        // To make the program case Insensitive, we pass both strings to UpperCase
        response = response.toUpperCase();
        nomPersonatge = nomPersonatge.toUpperCase();

        if (response.equals(nomPersonatge)) {
            answer = true;
        }

        return answer;
    }

    /**
     * Function to check if the adventure name exists
     * @param adventureName adventure name
     * @return boolean (true = adventure name does not exist)
     * @throws IOException needed to read the json
     */
    private boolean checkAdventureName(String adventureName) throws IOException{
        AdventureManager adventureManager = new AdventureManager();
        return adventureManager.checkName(adventureName);
    }

    /**
     * Function to create the adventure
     * @param adventureName adventure name
     * @param adventureManager manager of the adventure
     * @param encounters number of combats of the adventure
     * @throws IOException needed to read the json
     */
    private void createAdventure(String adventureName, AdventureManager adventureManager, int encounters) throws IOException{


        int index = adventureManager.getAdventures().size();


        // for each encounter/combat
        for (int i = 0; i < encounters; i++) {
            //
            CombatManager combatManager = new CombatManager();
            createCombat(adventureManager, combatManager, i, encounters);

        }

        adventureManager.createAdventure(adventureName, adventureManager.getCombats());
    }

    /**
     * Function to create the combat
     * @param adventureManager manager of the adventure
     * @param combatManager manager of the combat
     * @param combatIndex number of combat we are
     * @param encounters number of total combats
     * @throws IOException needed to read the json
     */
    private void createCombat(AdventureManager adventureManager, CombatManager combatManager, int combatIndex, int encounters) throws IOException {

        List<String> monsters = new ArrayList<>();
        List<Integer> monsterQuantity = new ArrayList<>();
        boolean empty = true;
        int option = 0;
        // while we don't want to stop adding/removing monsters || we haven't add any monster
        while (option != 3 || combatManager.getCombat().getMonsters().isEmpty()) {
            menu.encounterMenu(combatIndex+1, encounters, monsters, monsterQuantity);
            option = menu.combatManagerMenu();
            switch (option) {
                case 1: { // add Monsters

                    // first list all the monsters
                    menu.showMonsterList(combatManager.listMonsterNames(), combatManager.listMonsterChallenges());
                    // then ask monster index
                    int monsterIndex = menu.askMonsterIndex(combatManager.getMonsters().size());

                    boolean add = addMonstersToCombat(monsters, monsterIndex);
                    if (add) {
                        // after ask monster quantity
                        int monsterAmount = menu.askMonsterQuantity(combatManager.getMonsters().get(monsterIndex).getName());
                        int length = monsterQuantity.size();

                        addMonsterQuantityToCombat(monsterQuantity, monsters, monsterIndex, monsterAmount, combatManager);

                        // Now we have the list of Monsters and it's Quantity

                        combatManager.setCombat(combatManager.createCombat(combatIndex, monsters, monsterQuantity));

                        empty = false;
                    }
                    break;
                }
                case 2: { // remove Monsters
                    if (empty) {
                        // Error: You haven't added monsters already
                        menu.errorNoAddedMonsters();
                    } else {
                        menu.encounterMenu(combatIndex+1, encounters, monsters, monsterQuantity);
                        int index = menu.askMonsterQuantity(combatManager.getCombat().getMonsters().size()) - 1;
                        menu.deletedFromEncounter(combatManager.getCombat().getMonsters().get(index).getName(), combatManager.getCombat().getMonstersQuantity()[index]);
                        // combatManager.setCombat(combatManager.); //= updateEncounter(combat);
                        combatManager.updateCombat(index, monsters, monsterQuantity);
                        if (combatManager.getCombat().getMonsters().size() == 0) {
                            empty = true;
                        }

                    }
                    break;
                }
                case 3: { // continue
                    if (monsters.isEmpty()) {
                        //Error: You haven't added monsters already
                        menu.errorNoAddedMonsters();
                    } else {
                        //combats.add(createEncounter(i));
                        ///combats.add(combat);
                        adventureManager.addCombat(combatManager.getCombat()); // add new combat to the list
                    }
                    break;
                }
            }
        }
    }

    /**
     * Function to add the quantity of the monsters to the combat
     * @param monsterQuantity list of monsters quantity
     * @param monsters list of monster (name)
     * @param monsterIndex index of the monster we want to add
     * @param monsterAmount number of the monster we want to add
     * @param combatManager manager of the combat
     * @throws IOException needed to read the json
     */
    private void addMonsterQuantityToCombat(List<Integer> monsterQuantity, List<String> monsters, int monsterIndex, int monsterAmount, CombatManager combatManager) throws IOException {
        // get the name of the monster to operate with him
        String monsterName = combatManager.getMonsters().get(monsterIndex).getName();
        // check that the monster is not a boss

            // search the monster in the list and update
            boolean found = false;
            for (int i = 0; i < monsterQuantity.size(); i++) {
                if (monsterName.equals(monsters.get(i))) {
                    found = true;
                    // update the QuantityList
                    monsterQuantity.set(i,monsterQuantity.get(i) + monsterAmount);
                }
            }

            if (!found) {
                monsterQuantity.add(monsterAmount);
            }


    }

    /**
     * Function to add monsters to the combat
     * @param monsters list of the monsters name
     * @param monsterIndex index of the monster to add
     * @return boolean (true = the mosnter haven't been added yet).
     * @throws FileNotFoundException needed to read the json
     */
    private boolean addMonstersToCombat(List<String> monsters, int monsterIndex) throws FileNotFoundException{
        // Variables:
        CombatManager combatManager = new CombatManager();
        boolean boss;
        boolean add = true;
        // now check either it's or not a boss

        // now check that it don't exist already
        if (monsters.contains(combatManager.listMonsterNames().get(monsterIndex)) == FALSE) {
            monsters.add(combatManager.listMonsterNames().get(monsterIndex));
        }

        return add;
    }

    /**
     * Function to create the party
     * @param partySize size of the party(number of characters)
     * @param personatgeManager manager of the characters
     * @param adventureManager manager of the adventure
     * @throws IOException needed to read the json
     */
    private void createParty(int partySize, CharacterManager personatgeManager, AdventureManager adventureManager) throws IOException{
        //Init party
        adventureManager.initParty(partySize);

        // fill party with characters
        for (int i = 0; i < partySize; i++) {
            // 1st show Actual status of Party
            menu.showParty(adventureManager.getParty(), i, partySize);
            // 2nd ask user to select the character to add
            int character = menu.listCharacters(personatgeManager.getAllCharacters(), i + 1) - 1;

            // 3rd check if character already exists in party
            boolean alreadyInParty = adventureManager.characterAlreadyInParty(character);
            while (alreadyInParty) {
                // get name of character and display error message
                String nameCharacter = personatgeManager.getCharacterFromIndex(personatgeManager.getAllCharacters().get(character)).getNomPersonatge();
                menu.characterAlreadyInParty(nameCharacter);

                // update again index of character to be added
                character = menu.listCharacters(personatgeManager.getAllCharacters(), i + 1) - 1;
                alreadyInParty = adventureManager.characterAlreadyInParty(character);
            }

            // 4rt add the character previously selected to the party
            adventureManager.addCharacterToParty(i,personatgeManager.initCharacter(personatgeManager.getCharacterFromIndex(personatgeManager.getAllCharacters().get(character))));
            adventureManager.getParty().setConscient(i, true);
        }
        // final print of the party status
        menu.showParty(adventureManager.getParty(), partySize, partySize);

    }







}
