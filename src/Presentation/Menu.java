package Presentation;

import Business.Characters.Character;
import Business.Monster;
import Business.Party;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.lang.Boolean.FALSE;

public class Menu {

    public void initialMessage(){
        welcome();
    }

    private void titleSimpleRPG(){
        System.out.println("____ _ __ __ ____ ___ ___ _____");
        System.out.println("/ /() / / / /___ / / / __ // _  / /");
    }

    private void welcome(){
        System.out.println("Welcome to Simple RPG\n");
        System.out.println("Loading data...");
    }

    public void dataSuccesfull(){
        System.out.println("Data was successfully loaded.");
    }

    public void errorReader(boolean errorCharacter,boolean errorAdventure, boolean errorMonsters){
        if(errorCharacter){
            System.out.println("Error: The characters.json file can't be accessed.");
        }
        if(errorAdventure){
            System.out.println("Error: The adventures.json file can't be accessed.");
        }
        if(errorMonsters){
            System.out.println("Error: The monsters.json file can't be accessed.");
        }
    }

    public void showMenu(){
        System.out.println("The tavern keeper looks at you and says:");
        System.out.println("\"Welcome adventurer! How can I help you?\"\n");
        System.out.println("\t1) Character creation");
        System.out.println("\t2) List characters");
        System.out.println("\t3) Create an adventure");
        System.out.println("\t4) Start an adventure");
        System.out.println("\t5) Exit\n");

    }

    public int askOption(){
        System.out.print("Your answer: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void finalMessage(){
        System.out.println("\nTavern keeper: \"Are you leaving already? See you soon, adventurer.\"");
    }

    public void promptTavernKeeper() {
        System.out.print("Tavern keeper: ");
    }

    public void nameCreation(){
        System.out.println("Tavern keeper: \"Oh, so you are new to this land.\"");
        System.out.println("\"What's your name?\"\n");
    }

    public String askCharacterName() {
        Scanner scanner = new Scanner(System.in);
        String name;
        System.out.print("-> Enter your name: ");
        try {
            name = scanner.nextLine();
            if (correctNameFormat(name) == FALSE) {
                System.out.println("Please, provide a correct format for name");
                name = askCharacterName();
            }
        } catch (InputMismatchException exception) {
            promptTavernKeeper();
            System.out.println("Please, provide a correct format for name");
            name = askCharacterName();
        }
        return toCorrectFormat(name);
    }

    private String toCorrectFormat(String name) {
        name = name.toLowerCase();
        String[] parts = name.split(" ");
        String correctedFormat = "";

        for (int i = 0; i < parts.length; i++) {
            correctedFormat += parts[i].toUpperCase().charAt(0);
            correctedFormat += parts[i].substring(1) + " ";
        }
        correctedFormat = correctedFormat.trim();
        return correctedFormat;
    }

    private boolean correctNameFormat(String name) {
        char[] chars = name.toCharArray();
        boolean correctFormat = true;
        for (int i = 0; i < chars.length; i++) {
            if (java.lang.Character.isAlphabetic(chars[i]) == FALSE && chars[i] != ' ') {
                correctFormat = FALSE;
            }
        }
        if (name == "") {
            correctFormat = false;
        }
        return correctFormat;
    }

    public void nameNotDisponible() {
        promptTavernKeeper();
        System.out.println("Adventurer, this name is not yet available");
    }

    public String askPlayerName(String name) {
        Scanner scanner = new Scanner(System.in);
        promptTavernKeeper();
        System.out.println("Hello, " + name + ", be welcome.");
        System.out.println("And now, if I may break the fourth wall, who is your Player?");
        System.out.print("\n-> Enter player's name: ");

        String player = scanner.nextLine();
        while (player == "") {
            System.out.println("Please, provide a correct format for name\n");
            System.out.print("-> Enter player's name: ");
            player = scanner.nextLine();
        }
        return player;
    }

    public int askLevel() {
        System.out.println();
        promptTavernKeeper();
        System.out.println("I see, I see...");
        System.out.println("Now, are you an experienced adventurer? ");

        int level = askOptionLevel();

        while (level < 1 || level > 10) {
            System.out.println("Wrong value!");
            level = askOptionLevel();
        }

        return level;
    }

    public int askOptionLevel() {
        System.out.print("\n-> Enter the character's level [1..10]: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


    public void generatingStats(int level) {
        System.out.println();
        promptTavernKeeper();
        System.out.println("Oh, so you are level " + level + "!");
        System.out.println("Great, let me take a closer look at you...");
        System.out.println("\nGenerating your stats...\n");
    }

    public void showSingleStat(String string, int firstValue, int secondValue) {
        int sum = firstValue + secondValue;
        System.out.println(string + ":   You rolled " + sum + " (" + firstValue + " and "+ secondValue + ").");
    }

    public void listStats(int body, int mind, int spirit) {

        System.out.println("\nYour stats are:");
        System.out.println(" -Body: " + body);
        System.out.println(" -Mind: " + mind);
        System.out.println(" -Spirit: " + spirit);
    }

    public void characterCreated(String name){
        System.out.println("The new character " + name + " has been created");
    }

    public String listCharactersMessage() {
        Scanner scanner = new Scanner(System.in);
        promptTavernKeeper();
        System.out.println("Lads! The Boss wants to see you, come here!");
        System.out.println("Who Piques your Interest?\n");
        System.out.print("-> Enter the name of the Player to filter: ");
        return scanner.nextLine();
    }


    public void showAllCharacters(List<String> allCharacters) {
        System.out.println("You watch as all adventurers get up from their charis and approach you.");
        for(int i = 0;i<allCharacters.size();i++){
            System.out.println("\t" + (i+1) + ". " + allCharacters.get(i));
        }
    }

    public void showSomeCharacters(List<String> someCharacters){
        System.out.println("You watch as some adventurers get up from their charis and approach you.");
        for(int i = 0;i<someCharacters.size();i++){
            System.out.println("\t" + (i+1) + ". " + someCharacters.get(i));
        }
    }

    public int meetCharacter(int size) {

        int option;
        do {
            option = askOptionCharacter(size);
        } while (option > size || option < 1);

        return option;
    }

    private int askOptionCharacter(int size){
        System.out.print("Who would you like to meet [0.." + size + "]: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void showCharacterStats(Character character) {
        System.out.println("\t* Name:\t" + character.getNomPersonatge());
        System.out.println("\t* Player:\t" + character.getNomJugador());
        System.out.println("\t* Class:\t" + character.getTipusPersonatge());
        System.out.println("\t* Level:\t" + character.getNivellInicial());
        System.out.println("\t* XP:\t" + character.getXpPoints());
        System.out.println("\t* Body:\t" + character.getBody());
        System.out.println("\t* Mind:\t" + character.getMind());
        System.out.println("\t* Spirit:\t" + character.getSpirit());
    }



    public String askToDeleteCharacter(String nomPersonatge) {
        System.out.println();
        System.out.println("[Enter name to delete or press enter to cancel]");
        System.out.print("Do you want to delete " + nomPersonatge + "? ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void characterDeleted(String nameCharacter) {
        promptTavernKeeper();
        System.out.println("\"I'm sorry kiddo, but you have to leave.\"\n");
        System.out.println("Character " + nameCharacter + " left the Guild.");
    }

    public void nextLine(){
        System.out.println();
    }

    public int askEncounters(int maxEncounters, int maxErrors) {
        System.out.print("-> How many encounters do you want [1.." + maxEncounters + "]: ");
        int times = 0;
        return askOptionEncounters(1, maxEncounters, maxErrors, times);
    }

    private boolean inRange(int option, int minValue, int maxValue) {
        return option >= minValue && option <= maxValue;
    }

    private int askOptionEncounters(int min, int max, int maxErrors, int times) {
        int option = 0;
        Scanner scanner = new Scanner(System.in);

        times++;
        if (times <= maxErrors) {
            try {
                option = scanner.nextInt();

                if (inRange(option, min, max) == FALSE) {
                    if (times < maxErrors) {
                        System.out.print("Incorrect value, please try again: ");
                    }
                    option = askOptionEncounters(min, max, maxErrors, times);

                }
            } catch (InputMismatchException e) {
                if (times < maxErrors) {
                    System.out.print("Incorrect value, please try again: ");
                }
                option = askOptionEncounters(min, max, maxErrors, times);

            }
            return option;
        } else {
            return 0;
        }
    }

    public void nEncounters(int encounters) {
        promptTavernKeeper();
        System.out.println( encounters + " encounters? That is too much for me...");
        System.out.println();
    }

    /**
     * Shows the menu of the encounter
     * @param encounterIndex index of the encounter
     * @param maxEncounters max encounters for this adventure
     * @param monsters list of monsters at the moment
     * @param quantity amount of monsters at the moment
     */
    public void encounterMenu(int encounterIndex, int maxEncounters, List<String> monsters, List<Integer> quantity) {
        System.out.println("\n* Encounter " + encounterIndex + " / " + maxEncounters);
        System.out.println("* Monsters in encounter:");
        if (monsters.isEmpty()) {
            System.out.println("\t# Empty\n");
        } else {
            for (int i = 0; i < monsters.size(); i++) {
                System.out.println("\t" + (i+1) + ". " + monsters.get(i) + "(x" + quantity.get(i) + ")");
            }
            System.out.println();
        }
    }

    /**
     * menu of the combat estate
     * @return option choosen
     */
    public int combatManagerMenu() {
        System.out.println("1. Add monster");
        System.out.println("2. Remove monster");
        System.out.println("3. Continue\n");

        System.out.print("-> Enter an option [1..3]: ");
        return askOption(1,3);
    }

    /**
     * shows the list of monsters and their challenge
     * @param monsterNames list of monsters
     * @param challenge list of challenges of the monsters
     */
    public void showMonsterList(List<String> monsterNames, List<String> challenge) {
        for (int i = 0; i < monsterNames.size(); i++) {
            System.out.println((i+1) + ". " + monsterNames.get(i) + " (" + challenge.get(i) + ")");
        }
        System.out.println();
    }

    /**
     * function that asks the monster index to add
     * @param max maxValue possible
     * @return index of the monster
     */
    public int askMonsterIndex(int max) {
        System.out.print("-> Choose a monster to add [1.." + max + "]: ");
        return askOption(1, max) - 1; // substract 1 for easily operations in controller
    }

    /**
     * function that asks the monster quantity
     * @param monsterName name of the monster
     * @return amount of monsters of that type
     */
    public int askMonsterQuantity(String monsterName) {
        System.out.print("-> How many " + monsterName + "(s) do you want to add: ");
        return askOption(1);
    }

    /**
     * asks amount of monsters to remove
     * @param max max value possible
     * @return amount of monsters to remove
     */
    public int askMonsterQuantity(int max) {
        System.out.print("-> Which monster do you want to delete: ");
        return askOption(1, max);
    }

    /**
     * Shows the name and the quantity of monsters removed
     * @param name name of the monster
     * @param amount amount of monsters removed
     */
    public void deletedFromEncounter(String name, int amount) {
        System.out.println(amount + " " + name + " were removed from the encounter.");
    }
    private int askOption(int min, int max) {
        int option;
        Scanner scanner = new Scanner(System.in);
        try {
            option = scanner.nextInt();
            if (inRange(option, min, max) == FALSE) {
                System.out.print("Incorrect value, please try again: ");
                option = askOption(min, max);
            }
        }   catch (InputMismatchException e) {
            System.out.print("Incorrect value, please try again: ");
            option = askOption(min,max);
        }

        return option;
    }
    private int askOption(int min) {
        int option;
        Scanner scanner = new Scanner(System.in);
        try {
            option = scanner.nextInt();
            if (option < min) {
                System.out.print("Incorrect value, please try again: ");
                option = askOption(min);
            }
        }   catch (InputMismatchException e) {
            System.out.print("Incorrect value, please try again: ");
            option = askOption(min);
        }

        return option;
    }

    public void planningAnAdventure() {
        promptTavernKeeper();
        System.out.println("Planning an adventure? Good luck with that!\n");
    }
    public String nameAdventure() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("-> Name your adventure: ");

        String name = scanner.nextLine();

        while (name.equals("")) {
            System.out.println("Please provide a correct format for name\n");
            System.out.print("-> Name your adventure: ");
            name = scanner.nextLine();
        }
        return name;
    }



    public void adventureNameNotAble() {
        promptTavernKeeper();
        System.out.println("This Adventure already exists!");
    }


    public void adventureNameCorrect(String adventureName) {
        promptTavernKeeper();
        System.out.println("You plan to undertake " + adventureName + ", really?");
        System.out.println("How long will that take?\n");
    }
    public void errorNoAddedMonsters() {
        promptTavernKeeper();
        System.out.println("Error, you haven't added any monsters already!\n");
    }

    public int playAdventureMenu(List<String> adventures) {
        promptTavernKeeper();
        System.out.println("So, you are looking to go on an adventure?");
        System.out.println("Where do you fancy going?\n");

        System.out.println("Available adventures: ");
        for (int i = 0; i < adventures.size(); i++) {
            System.out.println("\t" +(i+1) + "." + adventures.get(i));
        }

        System.out.print("\n-> Choose an adventure: ");
        return askOption(1, adventures.size());
    }

    public int askPartySize(String adventureName) {
        promptTavernKeeper();
        System.out.println(adventureName + "it is!");

        System.out.print("-> Choose a number of characters [3..5]: ");
        return askOption(3,5);
    }

    /**
     * shows message of party size
     * @param partySize party size
     */
    public void askCharacterMessage(int partySize) {
        promptTavernKeeper();
        System.out.println("Great, " + partySize + " it is.");
        System.out.println("Who among these lads shall join you?\n");
    }
    public void startAdventure(String name) {
        promptTavernKeeper();
        System.out.println("Great, good luck on your adventure lads!\n");
        System.out.println("The " + name + " will start soon...");
    }
    public void showParty(Party party, int actual, int size) {
        System.out.println("---------------------------");
        System.out.println("Your Party (" + actual + "/" + size + ")");

        for (int i = 0; i < party.getPersonatges().length; i++) {
            if (party.getPersonatges()[i] == null) {
                System.out.println((i+1) + ". Empty");
            } else {
                System.out.println((i + 1) + ". " + party.getPersonatges()[i].getNomPersonatge());
            }
        }
        System.out.println("---------------------------");
    }

    public int listCharacters(List<String> allCharacters, int index) {
        System.out.println("Available characters: ");
        for (int i = 0; i < allCharacters.size(); i++) {
            System.out.println("\t" + (i+1) + ". " + allCharacters.get(i));
        }

        System.out.print("\n-> Choose character " + index + " in your party: ");
        return askOption(1, allCharacters.size());
    }

    public void characterAlreadyInParty(String nomPersonatge) {
        promptTavernKeeper();
        System.out.println("Error, " + nomPersonatge + " is already in your party!\n");
    }


    public void titleEncounter(int i, List<Monster> monsters, int[] monstersQuantity) {
        System.out.println("---------------------");
        System.out.println("Starting Encounter " + (i+1) +":");
        for(int j=0;j<monstersQuantity.length;j++){
            System.out.println("\t- "+monstersQuantity[j]+"x "+monsters.get(j).getName());
        }
        System.out.println("---------------------\n");
    }

    public void titlePreparation() {
        System.out.println("-------------------------");
        System.out.println("*** Preparation stage ***");
        System.out.println("-------------------------");
    }

    public void showPreparation(List<String> gethabilities, Character[] personatges) {

        for(int i=0;i<personatges.length;i++){
            System.out.println(personatges[i].getNomPersonatge() + " uses " +gethabilities.get(i) + ". Their Spirit increases in +1." );
        }

    }

    public void showOrder(List<String> allParticipants, List<Integer> allInitiatives) {
        System.out.println("Rolling initiative...");
        for(int i=0;i<allParticipants.size();i++){
            System.out.println("\t- "+allInitiatives.get(i) + "\t" + allParticipants.get(i));
        }
    }

    public void titleCombatStage() {
        System.out.println("--------------------");
        System.out.println("*** Combat stage ***");
        System.out.println("--------------------");
    }

    public void showPartyPoints(Party party) {
        Character[] charactersParty = party.getPersonatges();
        for(Character character : charactersParty){
            System.out.println("\t- "+character.getNomPersonatge() + "     " + character.getActualLifePoints() + " / " + character.getTotalLifePoints() + " hit points");
        }
        nextLine();
    }

    public void makeAttack(Character attacker, int damageAttack, String opponent, int mult) {
        if(mult==0){
            System.out.println("\n" + attacker.getNomPersonatge() + " attacks " + opponent + " with Sword slash.");
            System.out.println("Fails and deals " + damageAttack + " physical damage.");
        }else if(mult==2){
            System.out.println("\n" + attacker.getNomPersonatge() + " attacks " + opponent + " with Sword slash.");
            System.out.println("Critical hit and deals " + damageAttack + " physical damage.");
        }else{
            System.out.println("\n" + attacker.getNomPersonatge() + " attacks " + opponent + " with Sword slash.");
            System.out.println("Hits and deals " + damageAttack + " physical damage.");
        }

    }

    public void showRound(int round) {
        System.out.println("Round "+round + ":");
        System.out.println("Party:");
    }

    public void endRound(int round){
        System.out.println("End of round "+round +".");
        nextLine();
    }

    public void makeAttackMonster(Monster attacker, int damageAttack, String opponent, int mult, String damageType) {
        if(mult ==  0){
            System.out.println("\n" + attacker.getName() + " attacks " + opponent + ".");
            System.out.println("Fails and deals " + damageAttack + " " + damageType.toLowerCase() + " damage.");
        } else if (mult == 2) {
            System.out.println("\n" + attacker.getName() + " attacks " + opponent + ".");
            System.out.println("Critical hit and deals " + damageAttack + " " + damageType.toLowerCase() + " damage.");
        }else{
            System.out.println("\n" + attacker.getName() + " attacks " + opponent + ".");
            System.out.println("Hits and deals " + damageAttack + " " + damageType.toLowerCase() + " damage.");
        }
    }

    public void lastMessage(boolean cDead, boolean mDead, String name) {
        if(cDead){
            promptTavernKeeper();
            System.out.println("\"Lad, wake up. Yes, your party fell unconscious.\"");
            System.out.println("\"Don't worry, you are safe back at the Tavern.\"");
        }
        else{
            System.out.println("Congratulations, your party completed \"" + name + "\"");
        }
    }

    public void dieCharacter(String nomPersonatge) {
        System.out.println(nomPersonatge + " falls unconscious.");
    }

    public void dieMonster(String s) {
        System.out.println(s + " dies.");
    }

    public void restStageTitle() {
        System.out.println("All enemies are defeated.\n");
        System.out.println("------------------------");
        System.out.println("*** Short rest stage ***");
        System.out.println("------------------------");
    }

    public void gainXp(int numberXp, Character character, boolean levelUp) {
        if (levelUp){
            System.out.println(character.getNomPersonatge() + " gains " + numberXp + " xp. " + character.getNomPersonatge() + " levels up. They are now lvl " + (character.getXpPoints()/100 - 1) + "!");
        }else{
            System.out.println(character.getNomPersonatge() + " gains " + numberXp + " xp.");
        }

    }

    public void makeCuracio(int makeCuracio, String nomPersonatge) {
        System.out.println(nomPersonatge + " uses Bandage time. Heals " + makeCuracio + " hit points.");
    }

    public void makeCuracioDead(String nomPersonatge) {
        System.out.println(nomPersonatge + " is unconscious.");
    }
}
