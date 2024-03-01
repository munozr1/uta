package edu.uta.CSE1325.Views;

// import java.util.concurrent.Flow;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
// import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends JFrame {
    private JPanel mainPanel;
    private JButton guessButton;
    private JTextField guessInput;

    public GameView() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(440, 280);

        this.configureMainPanel();

    }


    private void configureMainPanel(){
      this.mainPanel = new JPanel();
      // create input field
      this.guessInput = new JTextField(10);
      // create guess button
      this.guessButton = new JButton("Guess");
      this.guessButton.setActionCommand("GameView.GuessNumber");

      this.mainPanel.add(this.guessInput);
      this.mainPanel.add(this.guessButton);
      this.add(this.mainPanel, BorderLayout.CENTER);
    }

    public void setListener(ActionListener listener)
    {
      this.guessButton.addActionListener(listener);
    }


    public void clearInput() {
        this.guessInput.setText("");
    }

    public int getInput() {
        return Integer.parseInt(this.guessInput.getText());
    }


    public void won(int tries) {
      JOptionPane.showMessageDialog(this, "YOU WIN! \n It took you "+ tries+" tries to win!!");
    }





}
