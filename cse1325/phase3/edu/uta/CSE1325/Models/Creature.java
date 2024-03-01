package edu.uta.CSE1325.Models;

import java.util.*;

public abstract class Creature implements Comparable<Creature> {

    protected String Name;
    protected int HitPoints = 50;
    protected int ArmorClass = 15;
    protected int Experience = 0;
    protected int Strength = 0;
    protected int Dexterity = 0;
    protected int Constitution = 0;
    protected Date CreationDate = new Date();
    private boolean isAlive = true;
    protected int x;
    protected int y;
    private int frameY;
    private int frameX;

    public int getFrameX() {
        return frameX;
    }

    public void setFrameX(int frameX) {
        this.frameX = frameX;
    }

    public int getFrameY() {
        return frameY;
    }

    public void setFrameY(int frameY) {
        this.frameY = frameY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * Get x position of the creature
     * 
     * @return
     */
    public int getX() {
        return x;
    }

    /*
     * Set x position of the creature
     */
    public void setX(int x) {
        this.x = x;
    }

    /*
     * Get y position of the creature
     */
    public int getY() {
        return y;
    }

    /*
     * Set y position of the creature
     */
    public void setY(int y) {
        this.y = y;
    }

    // create abstract class called attack
    public abstract void attack(Creature victim);

    /**
     * getter : Gets the Name of the player
     * 
     * @return String
     */
    public String getName() {
        return this.Name;
    }

    /**
     * getter : Gets the ArmorClass of the player
     * 
     * @return int
     */
    public int getArmorClass() {
        return this.ArmorClass;
    }

    /**
     * getter : Gets the Experience of the player
     * 
     * @return int
     */
    public int getExperience() {
        return this.Experience;
    }

    /**
     * getter : Gets the HitPoints of the player
     * 
     * @return int
     */
    public int getHitPoints() {
        return this.HitPoints;
    }

    /**
     * getter : Gets the Strength of the player
     * 
     * @return int
     */
    public int getStrength() {
        return this.Strength;
    }

    /**
     * getter : Gets the Dexterity of the player
     * 
     * @return int
     */
    public int getDexterity() {
        return this.Dexterity;
    }

    /**
     * getter : Gets the Constitution of the player
     * 
     * @return int
     */
    public int getConstitution() {
        return this.Constitution;
    }

    /**
     * getter : Gets the Creation Date of the player
     * 
     * @return int
     */
    public Date getCreationDate() {
        return this.CreationDate;
    }

    /**
     * Decreases the players HP by the amount of damage taken
     * 
     * @param damage How much damage the player takes (decreased from HP)
     * @return void
     */
    public void takeDamage(int damage) {
        this.HitPoints -= damage;
        if (this.HitPoints <= 0) {
            this.isAlive = false;
            this.HitPoints = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        return o != null
                && getClass() == o.getClass()
                && Name.equals(((Creature) o).getName());
    }

    @Override
    public int compareTo(Creature o) {
        int other = ((Creature) o).getHp();
        return this.getHp() - other;
    }

    public int getHp() {
        return HitPoints;
    }

    @Override
    public String toString() {
        return Name + " (" + HitPoints + ")";
    }

}