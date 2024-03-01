package edu.uta.CSE1325.Views;

// import java.util.ArrayList;

import javax.swing.BoxLayout;
// import javax.swing.JComboBox;
// import javax.swing.JOptionPane;
import javax.swing.JPanel;
import edu.uta.CSE1325.Models.Player;
import java.awt.event.ActionListener;
import java.awt.*;

public class PlayerLobbyView extends JPanel {

    private GridBagConstraints constraints = new GridBagConstraints();
    private ActionListener listener;
    private Player activePlayer;

    public PlayerLobbyView(ActionListener listener) {
        this.listener = listener;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        constraints.gridx = 0;
        constraints.gridy = 0;
    }

    public void addPlayer(Player p) {
        PlayerCardView newPView = new PlayerCardView(p);
        activePlayer = p;
        newPView.setAvatarLabelAction(listener);
        constraints.gridx++;
        constraints.gridy = 1;
        this.add(newPView, constraints);
        System.out.println("GRIDX: " + constraints.gridx);
        revalidate();
        repaint();
    }

    public void removePlayer(String name) {
        Component[] cards = this.getComponents();
        for (Component card : cards) {
            if (card instanceof PlayerCardView) {
                PlayerCardView p = (PlayerCardView) card;
                if (p.getNameLabel().equals(name)) {
                    this.remove(p);
                    revalidate();
                    repaint();
                }
            }
        }
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

}
