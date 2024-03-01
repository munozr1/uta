package edu.uta.CSE1325.Models;

import java.util.Random;

public class GameModel {
  private int numberToGuess;
  private int tries;
  private Random generator = new Random();
  public GameModel(){
    this.setNumberToGuess();
  }

  public void setNumberToGuess(){
    this.numberToGuess = generator.nextInt(100) + 1;
    this.tries = 1;
    System.out.println("Number to guess is: "+ this.numberToGuess);
  }
  
  public int getNumberToGuess(){
    return this.numberToGuess;
  }

  public int getTries(){
    return this.tries;
  }

  public boolean checkUserGuess(int userGuess){
    boolean guessed = false;
    if(userGuess == this.numberToGuess)
      guessed = true;
    else 
      this.tries++;
    return guessed;
  }

}