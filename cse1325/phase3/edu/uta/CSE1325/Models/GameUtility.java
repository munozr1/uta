package edu.uta.CSE1325.Models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Contains usefull utility functions the the main game.
 * This class only provides static methods
 */
public class GameUtility {
    /**
     * Checks if the player can move to the given location. If the location is not
     * occupied, the player can move to the
     * location. If the location is occupied, cannot move to the location.
     * 
     * Does not return until player has moved to a location.
     */
    public static void movePlayer(Map map, Player player, Scanner in) {
        int x = 0, y = 0;
        char move;
        int moves = 0;
        boolean validMove = true;
        while (moves < 5) {
            GameUtility.clearScreen();
            System.out.println(map.toString());
            if (!validMove) {
                System.out.println("Invalid move. Please try again.");
            }
            System.out.println(
                    player.getName() + ", where would you like to move? (up: u, down: s, left: a, right: d ): ");
            move = in.next().charAt(0);
            switch (move) {
                case 'a':
                    x = player.getX();
                    y = player.getY() - 1;
                    break;
                case 'd':
                    x = player.getX();
                    y = player.getY() + 1;
                    break;
                case 's':
                    x = player.getX() + 1;
                    y = player.getY();
                    break;
                case 'w':
                    x = player.getX() - 1;
                    y = player.getY();
                    break;
                default:
                    System.out.println("Invalid move");
                    break;
            }

            if (x < 0 || y < 0 || x > 25 || y > 25 || map.isOccupied(x, y)) {
                validMove = false;
            } else {
                map.free(player.getX(), player.getY());
                map.set(x, y, player);
                player.move(x, y);
                canAttack(map, player, in);
                moves++;
                validMove = true;
            }
        }
    }

    private static void canAttack(Map map, Player player, Scanner in) {
        if (map.isOccupied(player.getX() + 1, player.getY())) {
            System.out.println("Would you like to attack? (y/n)");
            if (in.next().charAt(0) == 'y') {
                player.attack(map.map[player.getX() + 1][player.getY()]);
            }
        } else if (map.isOccupied(player.getX() - 1, player.getY())) {
            System.out.println("Would you like to attack? (y/n)");
            if (in.next().charAt(0) == 'y') {
                player.attack(map.map[player.getX() - 1][player.getY()]);
            }
        } else if (map.isOccupied(player.getX(), player.getY() + 1)) {
            System.out.println("Would you like to attack? (y/n)");
            if (in.next().charAt(0) == 'y') {
                player.attack(map.map[player.getX()][player.getY() + 1]);
            }
        } else if (map.isOccupied(player.getX(), player.getY() - 1)) {
            System.out.println("Would you like to attack? (y/n)");
            if (in.next().charAt(0) == 'y') {
                player.attack(map.map[player.getX()][player.getY() - 1]);
            }
        }

    }

    /**
     * Rolls a virtual dice an x amount of times and returns the value
     * 
     * @param input A string value with format [NUMDICE]d[DICETYPE]
     * @return An integer which represents the sum of the die rolled
     */
    public static int rollDice(String input) {
        String[] tokens = input.split("d");
        Random generator = new Random();
        int numDice = Integer.parseInt(tokens[0]);
        int diceType = Integer.parseInt(tokens[0]);
        int result = 0;
        for (int i = 0; i < numDice; i++) {
            result += generator.nextInt(diceType) + 1;
        }
        return result;
    }

    // function that returns a number between 0 and 25
    public static int getRandomNumber(int i) {
        Random generator = new Random();
        return generator.nextInt(i);
    }

    public static Player characterCreationMenu(Scanner in, ArrayList<Weapon> weapons) {
        Player player = null;
        clearScreen();
        System.out.println("\n\n1. Manual Stats\n2. Random Stats\n3");
        // Get the user's menu choice
        int menuChoice = in.nextInt();

        switch (menuChoice) {
            case 1:
                clearScreen();
                player = manualStats(in, weapons);
                clearScreen();
                System.out.println(player.toString());
                break;
            case 2:
                clearScreen();
                player = randomStats(in, weapons);
                clearScreen();
                System.out.println(player.toString());
                break;
            default:
                System.out.println("Invalid Choice\n");
                // Get the user's menu choice again if invalid
                menuChoice = in.nextInt();
                break;
        }

        return player;
    }

    public static boolean confirm(Scanner in) {
        System.out.println("Confirm? (y/n)");
        String confirmStr = in.next();
        if (confirmStr.equals("y")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a new player object manually
     * 
     * @param in scanner object
     * @return Player object
     */
    private static Player manualStats(Scanner in, ArrayList<Weapon> weapons) {
        String name;
        int str = 0;
        int dex = 0;
        int con = 0;
        int points = 10;
        Weapon weapon = null;
        int menuChoice;
        System.out.println("Enter Character Name: ");
        in.nextLine();
        name = in.nextLine();
        while (points > 0) {
            printPlayerCreationStatState(dex, con, str, points);
            menuChoice = in.nextInt();
            switch (menuChoice) {
                case 1:
                    str++;
                    points--;
                    break;
                case 2:
                    dex++;
                    points--;
                    break;
                case 3:
                    con++;
                    points--;
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
        in.nextLine();
        System.out.println("\nSelect a weapon\n");
        int i = 0;
        for (Weapon w : weapons) {
            System.out.println(i + ". " + w.toString() + '\n');
            i++;
        }
        weapon = weapons.get(in.nextInt());
        return new Player(weapon, name, str, dex, con);
    }

    /**
     * Creates a weapons array
     */
    public static ArrayList<Weapon> createWeapons(String filename) throws IOException {
        String delimiter = ",";
        ArrayList<Weapon> weapons = new ArrayList<Weapon>();
        Scanner in = new Scanner(Path.of(filename));
        while (in.hasNextLine()) {
            // Read the first line and split it into tokens
            String line = in.nextLine();
            String[] tokens = line.split(delimiter);
            // Create a new weapon object and add it to the array
            weapons.add(new Weapon(tokens[0], tokens[1], Integer.parseInt(tokens[2]))); // create infinite
        }
        return weapons;
    }

    /**
     * Prints the current state of the player creation process
     * 
     * @param dex int
     * @param con int
     * @param str int
     * @param rem int
     */
    private static void printPlayerCreationStatState(int dex, int con, int str, int rem) {
        clearScreen();
        System.out.println("STR: " + str + "\nDEX: " + dex + "\nCON: " + con + "\nRemaining: " + rem
                + "\n\n1. Add STR" + "\n2. Add DEX" + "\n3. Add CON" + "\n4. Reset" + "\n5. Finish");
    }

    /**
     * Creates a new player object randomly
     * 
     * @param in Scanner object
     * @return Player object
     */
    private static Player randomStats(Scanner in, ArrayList<Weapon> weapons) {
        String name;
        System.out.println("Enter Character Name: ");
        in.nextLine();
        name = in.nextLine();
        name.replace('\n', ' ');
        Random generator = new Random();
        Weapon weapon = weapons.get(generator.nextInt(weapons.size()));
        return new Player(name, weapon);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Saves a charecter from memory to local player directory
     * 
     * @param in      Scanner object
     * @param players ArrayList of Player objects in memory
     */
    public static void saveCharecter(Scanner in, ArrayList<Player> players) {
        int index = 1;
        for (Player player : players) {
            System.out.println(index + "." + player.getName());
            index++;
        }
        System.out.println("Enter the index of the charecter you want to save");
        int choice = in.nextInt();
        if (choice > players.size()) {
            System.out.println("Invalid index");
            return;
        }
        players.get(choice - 1).saveToCsv(players.get(choice - 1));
    }

    /**
     * Loads a charecter from a file
     * 
     * @param in
     * @param players
     * @return Player object or null if the player does not exist
     * @throws CsvReadException
     */
    public static Player loadCharecter(Scanner in, ArrayList<Player> players) throws CsvReadException {
        ArrayList<File> files = new ArrayList<File>();
        // open a directory and get all files in it
        File dir = new File("./saved/players/");
        // add all files in dir to the files arraylist
        for (File file : dir.listFiles()) {
            files.add(file);
        }
        // print all files in the directory
        int index = 0;
        for (File file : files) {
            index++;
            System.out.println(index + ". " + file.getName());
        }
        // get user input for file to load
        int fileChoice = in.nextInt();
        // get the file from the files arraylist
        File playerCsv = files.get(fileChoice - 1);

        String playerCsvLine;
        try (Scanner loadCharecterFromCsv = new Scanner(playerCsv)) {
            playerCsvLine = loadCharecterFromCsv.nextLine();

        } catch (Exception e) {
            System.out.println("Error loading charecter");
            return null;
        }
        // create a new player object from the file
        return GameUtility.loadPlayerFromCsv(playerCsvLine);

    }

    /**
     * Creates a player from a line of a csv file, use this function when loading a
     * charecter with JFile Chooser
     * 
     * @param line line of the csv file
     * @return a player object
     * @throws CsvReadException
     */
    public static Player loadPlayerFromCsv(String line) throws CsvReadException {
        String Name;
        int HP;
        int STR;
        int DEX;
        int CON;
        String WeaponName;
        String WeaponDiceType;
        int Bonus;
        String[] attributes = line.split(",");
        Player player = null;
        Weapon weapon = null;
        String filePath = "";
        if (attributes.length != 9) {
            throw new CsvReadException(line);
        }

        try {
            Name = attributes[0];
            GameUtility.validateName(Name);
            HP = Integer.parseInt(attributes[1]);
            STR = Integer.parseInt(attributes[2]);
            DEX = Integer.parseInt(attributes[3]);
            CON = Integer.parseInt(attributes[4]);
            WeaponName = attributes[5];
            WeaponDiceType = attributes[6];
            Bonus = Integer.parseInt(attributes[7]);
            filePath = attributes[8];
            weapon = new Weapon(WeaponName, WeaponDiceType, Bonus);
            player = new Player(weapon, Name, STR, DEX, CON, HP, filePath);
        } catch (NumberFormatException e) {
            // e.printStackTrace();
            throw new CsvReadException(line);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new CsvReadException(line);
        }

        return player;
    }

    /**
     * Creates Monsters from a csv file, use this function when loading a
     * 
     * @return a Monsters arraylist
     * @throws CsvReadException
     * @throws FileNotFoundException
     */
    public static ArrayList<Creature> loadMonstersFromCsv() throws CsvReadException, FileNotFoundException {
        ArrayList<Creature> monsters = new ArrayList<Creature>();
        File file = new File("./monsters.csv");
        Scanner in = new Scanner(file);
        String Name;
        MonsterEnum monsterType;
        int HP;
        int STR;
        int DEX;
        int CON;
        int Bonus;

        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] attributes = line.split(",");
            if (attributes.length != 7) {
                in.close();
                throw new CsvReadException(line);
            }
            try {
                Name = attributes[0];
                GameUtility.validateName(Name);
                monsterType = MonsterEnum.valueOf(attributes[1]);
                HP = Integer.parseInt(attributes[2]);
                STR = Integer.parseInt(attributes[3]);
                DEX = Integer.parseInt(attributes[4]);
                CON = Integer.parseInt(attributes[5]);
                Bonus = Integer.parseInt(attributes[6]);
                monsters.add(new Monster(Name, monsterType, HP, STR, DEX, CON, Bonus));
            } catch (NumberFormatException e) {
                // e.printStackTrace();
                in.close();
                throw new CsvReadException(line);
            } catch (ParseException e) {
                e.printStackTrace();
                in.close();
                throw new CsvReadException(line);
            }
        }
        in.close();
        return monsters;
    }

    public static void validateName(String name) throws ParseException {
        // Check the name does not exceed 24 characters
        if (name.length() > 24) {
            throw new ParseException("Cannot be longer than 24 charecters", 0);
        }
        // Check if the first letter of the name is capital
        if (!Character.isUpperCase(name.charAt(0))) {
            throw new ParseException("Must start with capital letter", 0);
        }
        // Check each charecter is a letter or a number
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i))) {
                throw new ParseException("Must not contain numbers or special charecters", i);
            }
        }
    }

    /**
     * 
     * @param visited
     * @param row
     * @param col
     * @return whether the move is valid
     */
    static boolean isValid(Coordinate node) {
        if (node.getX() < 0 || node.getY() < 0 || node.getY() >= 25 || node.getX() >= 25)
            return false;
        return true;
    }

    private static boolean isNextToPlayer(Coordinate monster, Coordinate player) {
        if (monster.getX() == (player.getX() - 1) || monster.getY() == (player.getY() - 1)) {
            return true;
        }
        return false;
    }

    // use bfs to find the shortest path
    /**
     * moves the monster to the closest player
     * 
     * @param map   map object
     * @param start the positoin of the monster
     * @param end   the position of the player
     */
    public static Coordinate moveMonster(Map map, Coordinate start, Coordinate end) {
        // for x position , if negative then move left, if positive then move right
        int distanceX = start.getX() - end.getX();
        // for y position , if negative then move up, if positive then move down
        int distanceY = start.getY() - end.getY();

        for (int moves = 0; moves < 5; moves++) {
            if (distanceX < distanceY && !isNextToPlayer(start, end)) {
                if (distanceX < 0 && isValid(new Coordinate(start.getX() - 1, start.getY())))
                    start.setX(start.getX() - 1);
                else if (isValid(new Coordinate(start.getX() + 1, start.getY())))
                    start.setX(start.getX() + 1);
            }
            if (distanceX > distanceY && !isNextToPlayer(start, end)) {
                if (distanceY < 0 && isValid(new Coordinate(start.getX(), start.getY() + 1)))
                    start.setY(start.getY() + 1);
                else if (isValid(new Coordinate(start.getX(), start.getY() - 1)))
                    start.setY(start.getY() - 1);
            }
        }
        return start;
    }

}
