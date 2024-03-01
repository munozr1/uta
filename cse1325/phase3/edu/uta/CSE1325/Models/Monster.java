package edu.uta.CSE1325.Models;

import java.util.ArrayList;

public class Monster extends Creature {

    private MonsterEnum monsterType;

    Monster(String name, MonsterEnum type, int hp, int str, int dex, int con, int ac) {
        this.ArmorClass = ac;
        this.HitPoints = hp;
        this.Strength = str;
        this.Dexterity = dex;
        this.Constitution = con;
        this.Name = name;
        this.monsterType = type;
    }

    /**
     * Attacks the victim by rolling against thier Armour Class (AC). If the hit
     * roll is equal
     * to or greater than the target's AC value, roll damage.
     * 
     * @param victim
     */
    @Override
    public void attack(Creature victim) {
        int damageToInflict = 0;
        if (GameUtility.rollDice("d20") + this.Dexterity >= victim.ArmorClass) {
            // TODO
            damageToInflict = rollHit();
            victim.takeDamage(damageToInflict);
            System.out.println(this.getName() + "  attacks " + victim.getName() + " with " + "( "
                    + victim.getArmorClass() + " ) to hit...Hits!");
        } else {
            System.out.println(this.getName() + "  attacks " + victim.getName() + " with " + "( "
                    + victim.getArmorClass() + " ) to hit...Missed!");
        }
    }

    public void move(ArrayList<Player> players, Map map) {
        final Player closestPlayer = this.getClosestPlayer(players);
        final Coordinate coordinate = GameUtility.moveMonster(map, new Coordinate(this.x, this.y),
                new Coordinate(closestPlayer.getX(), closestPlayer.getY()));
        this.x = coordinate.getX();
        this.y = coordinate.getY();
    }

    /**
     * Rolls the dice type to determine how much damage the victim will take this
     * turn
     * 
     * @return How much damage the victim will take
     */
    private int rollHit() {
        return GameUtility.rollDice("d6") + this.Strength;
    }

    /**
     * Determine the closest player to this monster
     * 
     * @return the closest player to the monster
     */
    private Player getClosestPlayer(ArrayList<Player> players) {
        Player closestPlayer = null;
        double closestDistance = 0;
        double distance = 0;
        for (Player player : players) {
            // formula for distance between 2 points = sqrt( (x2-x1)^2 + (y2-y1)^2 )
            double x = Math.pow(Math.abs(player.getX() - this.getX()), 2);
            double y = Math.pow(Math.abs(player.getY() - this.getY()), 2);
            distance = Math.sqrt(x + y);
            if (closestPlayer == null || distance < closestDistance) {
                closestPlayer = player;
                closestDistance = distance;
            }
        }
        return closestPlayer;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Name: " + this.Name + '\n' +
                "Type" + this.monsterType + '\n' +
                "XP: " + this.Experience + '\n' +
                "HP: " + this.HitPoints + '\n' +
                "STR: " + this.Strength + '\n' +
                "DEX: " + this.Dexterity + '\n' +
                "CON: " + this.Constitution + '\n' +
                "Creation Date: " + this.CreationDate + '\n';
    }
}
