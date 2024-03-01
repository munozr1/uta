package edu.uta.CSE1325.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.uta.CSE1325.Views.GameView;
import edu.uta.CSE1325.Models.GameModel;


public class GameController {
    private GameView mainView;
    private GameModel mainModel;
    GameController(GameView view, GameModel model){
      this.mainModel = model;
      this.mainView = view;
      this.mainView.setListener(new GameListener());
    }

    private void checkUserGuess(){
      boolean win = this.mainModel.checkUserGuess(this.mainView.getInput());
      if(win){
        this.mainView.clearInput();
        this.mainView.won(this.mainModel.getTries());
        this.mainModel.setNumberToGuess();
      }

    }

    class GameListener implements ActionListener{

      @Override
      public void actionPerformed(ActionEvent e) {
        final String action = e.getActionCommand();
        switch(action){
          case "GameView.GuessNumber":
            System.out.println("Guessing Number");
            checkUserGuess();
            break;
          default:
          System.out.println("Unhandled action");
          break;
        }
        
      }

    }

}