package edu.uta.CSE1325.Controllers;


import edu.uta.CSE1325.Models.GameModel;
import edu.uta.CSE1325.Views.GameView;

public class ApplicationController {
  
  public static void main(String[] args) {
    GameModel model = new GameModel();
    GameView view = new GameView();
    GameController controller = new GameController(view, model);
    view.setVisible(true);
    }
}
