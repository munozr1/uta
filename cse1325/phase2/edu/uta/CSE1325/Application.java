package edu.uta.CSE1325;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Application {

  public static void main(String args[]) throws IOException {
    // players array
    // Player[] players = new Player[1];
    ArrayList<Monster> monsters = new ArrayList<Monster>();
    ArrayList<Player> players = new ArrayList<Player>();
    // scanner to get user inputS
    Scanner in = new Scanner(System.in);
    // user input for menu`
    int menuChoice;
    // weapons file name or path
    String weaponsFileName = "weapons.csv";

    System.out.println("1. Start Game\n2. Create Charecters\n3. Load Charecter\n4. Save Charecter\n3.Exit\n\n");
    menuChoice = in.nextInt();

    // create weapons from file
    ArrayList<Weapon> weapons = GameUtility.createWeapons(weaponsFileName);

    // Main Menu
    while (true) {
      switch (menuChoice) {
        case 1:
          GameUtility.clearScreen();
          if (players.size() < 2) {
            System.out.println("Not enough players created. Please create players.");
            System.out
                .println("1. Start Game\n2. Create Charecters\n3. Load Charecter\n4. Save Charecter\n5.Exit\n\n");
            menuChoice = in.nextInt();
            break;
          }
          startGame(players, in, monsters);
          break;
        case 2:
          GameUtility.clearScreen();
          createCharecters(in, players, weapons);
          GameUtility.clearScreen();
          System.out.println("1. Start Game\n2. Create Charecters\n3. Load Charecter\n4. Save Charecter\n5.Exit\n\n");
          menuChoice = in.nextInt();
          break;
        case 3:
          GameUtility.clearScreen();
          try {
            players.add(loadCharecter(in, players));
          } catch (CsvReadException e) {
            System.out.println("Error loading charecter");
            e.printStackTrace();
          }
          // GameUtility.clearScreen();
          System.out.println("1. Start Game\n2. Create Charecters\n3. Load Charecter\n4. Save Charecter\n5.Exit\n\n");
          menuChoice = in.nextInt();
          break;
        case 4:
          GameUtility.clearScreen();
          saveCharecter(in, players);
          System.out.println("1. Start Game\n2. Create Charecters\n3. Load Charecter\n4. Save Charecter\n5.Exit\n\n");
          menuChoice = in.nextInt();
          break;
        case 5:
          System.out.println("Exit");
          System.exit(0);
          break;
        default:
          GameUtility.clearScreen();
          System.out.println("1. Start Game\n2. Create Charecters\n3. Load Charecter\n 4. Save Charecter\n5.Exit\n\n");
          System.out.println("Invalid Choice\n\n");
          menuChoice = in.nextInt();
          break;
      }
    }
  }

  private static void saveCharecter(Scanner in, ArrayList<Player> players) {
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

  private static Player loadCharecter(Scanner in, ArrayList<Player> players) throws CsvReadException {
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

  private static void createCharecters(Scanner in, ArrayList<Player> players, ArrayList<Weapon> weapons) {
    Player temp = null;
    boolean conf = false;
    while (players.size() < 2) {
      temp = GameUtility.characterCreationMenu(in, weapons);
      conf = GameUtility.confirm(in);
      if (conf) {
        players.add(temp);
      }
    }
  }

  private static void pvp(ArrayList<Player> players, Scanner in) {
    GameUtility.clearScreen();
    Map map = new Map(players);
    for (Player player : players) {
      System.out.println(player.toString());
    }
    System.out.println(map.toString());
    while (players.size() > 1) {
      for (Player player : players) {
        if (player.isAlive)
          GameUtility.movePlayer(map, player, in);
        else
          players.remove(player);
      }
    }
    System.out.println("Game Over\n\n");
    System.out.println("Player " + players.get(0).getName() + " wins!");
  }

  private static void pvm(ArrayList<Player> players, Scanner in, ArrayList<Monster> monsters) {
    GameUtility.clearScreen();
    Map map = new Map(players);
    System.out.println(map.toString());
    while (!players.isEmpty() || !monsters.isEmpty()) {
      for (Player player : players) {
        if (player.isAlive)
          GameUtility.movePlayer(map, player, in);
        else
          players.remove(player);
      }
      for (Monster monster : monsters) {
        if (monster.isAlive)
          monster.move(players, map);
        else
          monsters.remove(monster);

      }

    }
  }

  private static void startGame(ArrayList<Player> players, Scanner in, ArrayList<Monster> monsters) {
    System.out.println("1. Play with random Monsters\n2. Play with players onyly (PVP)\n3. Back");
    int choice = in.nextInt();
    switch (choice) {
      case 1:
        System.out.println("Random Monsters");
        pvm(players, in, monsters);
        break;
      case 2:
        System.out.println("PVP");
        pvp(players, in);
        break;
      case 3:
        break;
      default:
        System.out.println("Invalid Choice");
        break;
    }
  }

}