package edu.uta.CSE1325;

/**
 * Defines basic structure for a weapon.
 */
public class Weapon {

    /**
     * Name of the weapon
     */
    private String Name;

    /**
     * String in format [NUMDICE]d[DICETYPE]
     */
    private String DiceType;

    /**
     * Amount of bonus damage the weapon inflicts
     */
    private int Bonus;

    Weapon(final String name, final String dicetype, final int bonus) {
        this.Bonus = bonus;
        this.DiceType = dicetype;
        this.Name = name;
    }

    /**
     * Calls the rollDice() function from the GameUtility API and returns the result
     * 
     * @return An integer that represents the amount of damage to inflict
     */

    public int rollDamage() {
        return GameUtility.rollDice(DiceType);
    }

    /**
     * getter : Gets the name of the weapon
     * 
     * @return String
     */
    public String getName() {
        return this.Name;
    }

    /**
     * getter : Gets the Dice Type of the weapon
     * 
     * @return String
     */
    public String getDiceType() {
        return this.DiceType;
    }

    /**
     * getter : Gets the Bonus of the weapon
     * 
     * @return int
     */
    public int getBonus() {
        return this.Bonus;
    }

    /**
     * setter : Sets the Name of the weapon
     * 
     * @return String
     */
    public void setName(String name) {
        this.Name = name;
    }

    /**
     * setter : Sets the Dice Type of the weapon
     * 
     * @return String
     */
    public void setDiceType(String input) {
        this.DiceType = input;
    }

    /**
     * setter : Sets the Bonues of the weapon
     * 
     * @return String
     */
    public void setBonus(int input) {
        this.Bonus = input;
    }

    @Override
    public String toString() {
        return this.Name + " " + "(" + this.DiceType + "+" + this.Bonus + ")";
    }
}