package edu.uta.CSE1325.Models;

import java.util.ArrayList;

public class Map {

  Creature[][] map = new Creature[25][25];

  public Map(ArrayList<Player> players) {
    for (int i = 0; i < 25; i++) {
      for (int j = 0; j < 25; j++) {
        map[i][j] = null;
      }
    }

    for (Player player : players) {
      map[player.getX()][player.getY()] = player;
    }
  }

  public Map(ArrayList<Player> players, ArrayList<Monster> monsters) {
    for (int i = 0; i < 25; i++) {
      for (int j = 0; j < 25; j++) {
        map[i][j] = null;
      }
    }

    for (Player player : players) {
      map[player.getX()][player.getY()] = player;
    }

    for (Monster monster : monsters) {
      map[monster.getX()][monster.getY()] = monster;
    }
  }

  public boolean isOccupied(int x, int y) {
    boolean occupied = true;
    if (x > 24 || x < 0 || y > 24 || y < 0) {
      return true;
    } else if (map[x][y] == null) {
      occupied = false;
    }
    return occupied;
  }

  public void set(int x, int y, Player player) {
    map[x][y] = player;
  }

  public void free(int x, int y) {
    map[x][y] = null;
  }

  @Override
  public String toString() {

    // Create a string of the map
    String mapString = "";
    for (int i = 0; i < 25; i++) {
      for (int j = 0; j < 25; j++) {
        if (map[i][j] == null) {
          mapString += ".";
        } else if (map[i][j] instanceof Player) {
          mapString += map[i][j].getName().charAt(0);
        } else if (map[i][j] instanceof Monster) {
          mapString += "M";
        }
      }
      mapString += "\n";
    }
    return mapString;
  }

}
