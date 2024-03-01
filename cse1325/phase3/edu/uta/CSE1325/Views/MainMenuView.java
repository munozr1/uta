package edu.uta.CSE1325.Views;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
// import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import edu.uta.CSE1325.Models.Player;

import java.awt.event.ActionListener;
// import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

public class MainMenuView extends JPanel {
    private JMenuBar menuBar;// this is built here but added to the mainframe
    private PlayerLobbyView playerLobby;
    // private GridBagConstraints constraints = new GridBagConstraints();

    public MainMenuView(ActionListener listener) {
        // playerComboBox.setActionCommand("PlayerLobbyView.ChangeActivePlayer");
        // playerComboBox.addActionListener(listener);
        // this.add(playerComboBox);
        this.configureablePlayerLobby(listener);
        this.configurableMenuBar(listener);
        this.configureableMainMenu(listener);
    }

    private void configureableMainMenu(ActionListener listener) {
        this.setSize(new Dimension(1540, 880));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton newButton = new JButton("New Character");
        JButton editButton = new JButton("Edit Character");
        JButton loadButton = new JButton("Load Character");
        JButton startGame = new JButton("Start Game");
        JButton exitGame = new JButton("Exit");
        newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitGame.setAlignmentX(Component.CENTER_ALIGNMENT);

        exitGame.addActionListener(listener);
        exitGame.setActionCommand("MainMenuView.Exit");
        newButton.addActionListener(listener);
        newButton.setActionCommand("MainMenuView.NewCharacter");
        editButton.addActionListener(listener);
        editButton.setActionCommand("MainMenuView.EditCharacter");
        loadButton.addActionListener(listener);
        loadButton.setActionCommand("MainMenuView.LoadCharacter");
        startGame.addActionListener(listener);
        startGame.setActionCommand("MainMenuView.StartGame");

        // center the buttons
        this.add(Box.createVerticalGlue());
        this.add(startGame);
        this.add(newButton);
        this.add(editButton);
        this.add(loadButton);
        this.add(exitGame);
        this.add(Box.createVerticalGlue());
    }

    public Player getActivePlayer() {
        return playerLobby.getActivePlayer();
    }

    public void addPlayer(Player p) {
        if (p instanceof Player && p != null) {
            playerLobby.addPlayer(p);
            // playerComboBox.addItem(p);
        }
        revalidate();
        repaint();
    }

    private void configurableMenuBar(ActionListener listener) {
        // TODO Auto-generated method stub
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New Character");
        JMenuItem saveItem = new JMenuItem("Save Character");
        JMenuItem editItem = new JMenuItem("Edit Character");
        JMenuItem loadItem = new JMenuItem("Load Character");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Set up listeners

        newItem.addActionListener(listener);
        newItem.setActionCommand("MainMenuView.NewCharacter");
        saveItem.addActionListener(listener);
        saveItem.setActionCommand("MainMenuView.SaveCharacter");
        editItem.addActionListener(listener);
        editItem.setActionCommand("MainMenuView.EditCharacter");
        loadItem.addActionListener(listener);
        loadItem.setActionCommand("MainMenuView.Load Character");
        exitItem.addActionListener(listener);
        exitItem.setActionCommand("MainMenuView.Exit");
        // add items to menu
        fileMenu.add(newItem);
        fileMenu.add(editItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // this.add(menuBar);

    }

    private void configureablePlayerLobby(ActionListener listener) {
        this.playerLobby = new PlayerLobbyView(listener);
        this.add(playerLobby);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public void removePlayer(Player activePlayer) {
        if (activePlayer != null) {
            playerLobby.removePlayer(activePlayer.getName());
        }
    }

}
