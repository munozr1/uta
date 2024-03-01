package edu.uta.CSE1325;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Defines a player object
 */
public class Player extends Creature {
    private Weapon Weapon;
    private int Level = 0;
    private int Experience = 0;
    private boolean armed = true;
    private String playerFilePath = "";

    public void setPlayerFilePath(String playerFilePath) {
        this.playerFilePath = playerFilePath;
    }

    Player(final Weapon weapon, final String name, final int level, final int ac, final int e, final int hp,
            final int s, final int dex, final int con, String filePath2) {
        this.Weapon = weapon;
        this.Name = name;
        this.Level = level;
        this.ArmorClass = ac;
        this.Experience = e;
        this.HitPoints = hp;
        this.Strength = s;
        this.Dexterity = dex;
        this.Constitution = con;
        this.playerFilePath = filePath2;

    }

    Player(final Weapon weapon, final String name,
            final int s, final int dex, final int con) {
        this.Weapon = weapon;
        this.Name = name;
        this.HitPoints += returnModifier(con);
        this.Strength = s;
        this.Dexterity += returnModifier(dex);
        this.Constitution += con;
    }

    Player(String name, Weapon weapon) {
        this.Name = name;
        this.Weapon = weapon;
        this.randomizeStats();
    }

    public Player(Weapon weapon2, String name2, int sTR, int dEX, int cON, int hP, String filePath) {
        this.Weapon = weapon2;
        this.Name = name2;
        this.Strength = sTR;
        this.Dexterity = dEX;
        this.Constitution = cON;
        this.HitPoints = hP;
        this.playerFilePath = filePath;
    }

    /**
     * Attacks the victim by rolling against thier Armour Class (AC). If the hit
     * roll is equal
     * to or greater than the target's AC value, roll damage.
     * 
     * @param victim
     */
    public void attack(Creature victim) {
        int damageToInflict = 0;
        if (GameUtility.rollDice(this.Weapon.getDiceType()) >= victim.ArmorClass) {
            // TODO
            damageToInflict = rollHit();
            victim.takeDamage(damageToInflict);
            System.out.println(this.getName() + "  attacks " + victim.getName() + " with " + Weapon.getName() + "( "
                    + victim.getArmorClass() + " ) to hit...Hits!");
        } else {
            System.out.println(this.getName() + "  attacks " + victim.getName() + " with " + Weapon.getName() + "( "
                    + victim.getArmorClass() + " ) to hit...Missed!");
        }
    }

    /**
     * Attempts to disarm a player for 2 turns
     * 
     * @param victim Player
     */
    public void dissarm(Player victim) {
        if (GameUtility.rollDice("d20") + this.Strength > GameUtility.rollDice("d20") + victim.getStrength()) {
            System.out.print("\n" + this.Name + " successfully disarmed: " + victim.getName());
            victim.dissarmed();
        } else {
            System.out.print("\n" + this.Name + " failed to disarmed: " + victim.getName());
        }
    }

    /**
     * Checks if the player is disarmed
     */
    public boolean dissarmed() {
        return armed;

    }

    /**
     * Moves the player to a specified location
     * 
     * @param map
     * @param x
     * @param y
     * @return true if the player can move to the location, false otherwise
     */
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Rolls the dice type to determine how much damage the victim will take this
     * turn
     * 
     * @return How much damage the victim will take
     */
    private int rollHit() {
        return GameUtility.rollDice(this.Weapon.getDiceType()) + this.Weapon.getBonus();
    }

    /**
     * Randomizes a charecters stats
     */
    public void randomizeStats() {
        int points = 10;
        Random generator = new Random();
        // set strength
        this.Strength = generator.nextInt(7) + 1;
        points -= this.Strength;
        // set dexterity
        if (points > 0)
            this.Dexterity = generator.nextInt(points) + 1;
        points -= this.Dexterity;
        this.ArmorClass = this.ArmorClass + this.Dexterity;
        // set constitution
        this.Constitution = points;
        // calc modifiers
        this.HitPoints += returnModifier(this.Constitution);
        this.ArmorClass += returnModifier(this.ArmorClass);

    }

    private int returnModifier(int num) {
        int ret = 0;
        if (num > 5)
            ret = num;
        if (num < 5)
            ret = num * -1;
        return ret;
    }

    /**
     * getter : Gets the Weapon of the player
     * 
     * @return int
     */
    public Weapon getWeapon() {
        return this.Weapon;
    }

    public void saveToCsv(Player p) {
        FileWriter writer;
        try {
            if (playerFilePath.equals(""))
                p.setPlayerFilePath("./saved/players/" + p.getName() + ".csv");
            writer = new FileWriter(playerFilePath);
            writer.write(Player.writeToCsv(p));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String writeToCsv(Player p) {
        String playerCsvLine = p.getName() + "," + p.getHp() + "," + p.getStrength() + "," +
                p.getDexterity() + "," + p.getConstitution() + "," + p.Weapon.getName() + "," + p.Weapon.getDiceType()
                + "," + p.Weapon.getBonus() + "," + p.getPlayerFilePath();
        return playerCsvLine;
    }

    private String getPlayerFilePath() {
        return this.playerFilePath;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Name: " + this.Name + '\n' +
                "Level: " + this.Level + '\n' +
                "XP: " + this.Experience + '\n' +
                "HP: " + this.HitPoints + '\n' +
                "STR: " + this.Strength + '\n' +
                "DEX: " + this.Dexterity + '\n' +
                "CON: " + this.Constitution + '\n' +
                "Creation Date: " + this.CreationDate + '\n';
    }

}
