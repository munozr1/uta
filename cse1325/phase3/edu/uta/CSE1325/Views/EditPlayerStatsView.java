
package edu.uta.CSE1325.Views;

import java.awt.event.ActionListener;
import javax.swing.*;
// import javax.swing.border.LineBorder;

// import edu.uta.CSE1325.Models.Player;

import java.awt.*;

public class EditPlayerStatsView extends JPanel {
    private JButton AddStrengthButton;
    private JButton SubStrengthButton;
    private JButton SubDexterityButton;
    private JButton AddDexterityButton;
    private JButton SubConstitutionButton;
    private JButton AddConstitutionButton;
    private int points = 15;

    private JLabel pointsLabel;
    private JLabel StrengthLabel;
    private JLabel DexterityLabel;
    private JLabel ConstitutionLabel;

    public EditPlayerStatsView(ActionListener listener) {
        setLayout(new GridLayout(10, 3, 1, 5));
        // setBorder(new LineBorder(Color.green));
        // create labels
        pointsLabel = new JLabel("Points: " + points);
        StrengthLabel = new JLabel("Strength");
        DexterityLabel = new JLabel("Dexterity");
        ConstitutionLabel = new JLabel("Constitution");
        // create buttons
        AddStrengthButton = new JButton("+");
        SubStrengthButton = new JButton("-");
        AddDexterityButton = new JButton("+");
        SubDexterityButton = new JButton("-");
        AddConstitutionButton = new JButton("+");
        SubConstitutionButton = new JButton("-");
        // set button listeners
        AddStrengthButton.addActionListener(listener);
        SubStrengthButton.addActionListener(listener);
        AddDexterityButton.addActionListener(listener);
        SubDexterityButton.addActionListener(listener);
        AddConstitutionButton.addActionListener(listener);
        SubConstitutionButton.addActionListener(listener);
        // set button actions
        AddStrengthButton.setActionCommand("EditPlayerStatsView.IncrementStrength");
        SubStrengthButton.setActionCommand("EditPlayerStatsView.DecrementStrength");
        AddDexterityButton.setActionCommand("EditPlayerStatsView.IncrementDexterity");
        SubDexterityButton.setActionCommand("EditPlayerStatsView.DecrementDexterity");
        AddConstitutionButton.setActionCommand("EditPlayerStatsView.IncrementConstitution");
        SubConstitutionButton.setActionCommand("EditPlayerStatsView.DecrementConstitution");
        // set default button enabledness
        SubConstitutionButton.setEnabled(false);
        SubDexterityButton.setEnabled(false);
        SubStrengthButton.setEnabled(false);
        // add buttons to layout

        // add components to panel
        add(pointsLabel);

        add(StrengthLabel);
        add(AddStrengthButton);
        add(SubStrengthButton);

        add(DexterityLabel);
        add(AddDexterityButton);
        add(SubDexterityButton);

        add(ConstitutionLabel);
        add(AddConstitutionButton);
        add(SubConstitutionButton);
    }

    public void setPoints(int p) {
        this.points -= p;
    }

    public int getPoints() {
        return points;
    }

    public void decrementPoints() {
        points--;
        pointsLabel.setText("Points: " + points);
        this.setButtonState();
        revalidate();
        repaint();
    }

    public void incrementPoints() {
        points++;
        pointsLabel.setText("Points: " + points);
        this.setButtonState();
        revalidate();
        repaint();
    }

    private void setButtonState() {
        if (points <= 15 && points > 0) {
            AddStrengthButton.setEnabled(true);
            AddDexterityButton.setEnabled(true);
            AddConstitutionButton.setEnabled(true);
        } else {
            AddStrengthButton.setEnabled(false);
            AddDexterityButton.setEnabled(false);
            AddConstitutionButton.setEnabled(false);
        }
        if (points < 15) {
            SubStrengthButton.setEnabled(true);
            SubDexterityButton.setEnabled(true);
            SubConstitutionButton.setEnabled(true);
        } else {
            SubStrengthButton.setEnabled(false);
            SubDexterityButton.setEnabled(false);
            SubConstitutionButton.setEnabled(false);
        }

    }

}
