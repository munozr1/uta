package edu.uta.CSE1325.Views;

import javax.imageio.ImageIO;
import javax.swing.*;
// import javax.swing.border.LineBorder;

import java.awt.Image;
// import edu.uta.CSE1325.Models.Map;
import edu.uta.CSE1325.Models.Player;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CombatView extends JPanel {
  private Player activePlayer;
  private Image[][] map;
  private ArrayList<Player> players;
  private AttackMenuView attackMenu;

  public CombatView(ArrayList<Player> players) {
    attackMenu = new AttackMenuView();
    // setBorder(new LineBorder(Color.red));
    this.players = players;
    map = new Image[8][8];
    // add the attack menue to the top left of the screen
    // this.add(attackMenu, BorderLayout.EAST);
  }

  public Player getActivePlayer() {
    return activePlayer;
  }

  public void setActivePlayer(Player activePlayer) {
    if (activePlayer != null) {
      this.activePlayer = activePlayer;
      attackMenu.setMenu(activePlayer);
    }
    this.activePlayer = activePlayer;
  }

  @Override
  public void paint(Graphics g) {
    // g.setColor(Color.black);
    boolean white = true;
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        if (white) {
          g.setColor(Color.BLACK);
        } else {
          g.setColor(Color.white);

        }
        g.fillRect(x * 100, y * 100, 100, 100);
        // g.outlineRect(x * 100, y * 100, 100, 100);
        white = !white;
      }
      white = !white;
    }
    for (Player p : players) {
      int x = p.getX();
      int y = p.getY();
      BufferedImage img;
      try {
        img = ImageIO.read(new File(p.getAvatarPath()));
        Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        map[x][y] = newImg;
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error painting player icon, Pleaserestart game");
      }
      g.drawImage(map[x][y], x * 100, y * 100, this);
    }
  }

}
