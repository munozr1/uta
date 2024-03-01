package edu.uta.CSE1325.Views;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import javax.swing.*;
// import javax.swing.border.LineBorder;

// import edu.uta.CSE1325.Models.Player;

import java.awt.*;
import edu.uta.CSE1325.Models.Player;

public class AttackMenuView extends JPanel {
  private JLabel activePlayerName;
  private JButton attackButton;

  public AttackMenuView() {
    // this.setSize(500, 500);
    setBorder(new LineBorder(Color.green));
    activePlayerName = new JLabel();
    // align activePlayerName to the center of the panel
    activePlayerName.setHorizontalAlignment(JLabel.CENTER);
    this.add(activePlayerName);

    attackButton = new JButton("Attack");
    attackButton.setActionCommand("AttackMenuView.Attack");
    this.add(attackButton);
  }

  public void setMenu(Player activePlayer) {
    activePlayerName.setText(activePlayer.getName());
  }

}
