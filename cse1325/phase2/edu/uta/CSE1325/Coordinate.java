package edu.uta.CSE1325;

public class Coordinate {
    private int x;
    private int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Get x position of the creature
     */
    public int getX() {
        return x;
    }

    /*
     * get y position of the creature
     */
    public int getY() {
        return y;
    }

    /**
     * Set x position of the creature
     * 
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y position of the creature
     * 
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

}
