package edu.uta.CSE1325;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Tests the Weaons class for functionality
 */
public class WeaponTest {

    public static void main(String[] args) throws IOException {
        // Weapon Blunt_Sword = new Weapon("Great Blunt Sword", "1d2", 0);
        // System.out.println(Blunt_Sword.toString());

        ArrayList<Weapon> weapons = GameUtility.createWeapons(args[0]);

        for (Weapon weapon : weapons) {
            System.out.println(weapon.toString());
        }
    }
}
