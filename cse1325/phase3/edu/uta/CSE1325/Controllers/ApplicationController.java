package edu.uta.CSE1325.Controllers;

import java.io.File;
import java.io.IOException;
// import java.util.concurrent.Flow;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import edu.uta.CSE1325.Models.CsvReadException;
import edu.uta.CSE1325.Models.GameUtility;
import edu.uta.CSE1325.Models.Map;
import edu.uta.CSE1325.Models.Player;
import edu.uta.CSE1325.Views.CombatView;
import edu.uta.CSE1325.Views.CreatePlayerView;
import edu.uta.CSE1325.Views.EditPlayerView;
import edu.uta.CSE1325.Views.MainMenuView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ApplicationController {
    private JFrame mainFrame;
    private MainMenuView mainMenuView;
    private CreatePlayerView createPlayerView;
    private EditPlayerView editPlayerView;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Player activePlayer;
    private Map map;
    private CombatView combatView;

    public ApplicationController() throws IOException {
        mainFrame = new JFrame("Rodrigo RPG");
        mainMenuView = new MainMenuView(new MainMenuActions());
        createPlayerView = new CreatePlayerView(new CreatePlayerActions());
        editPlayerView = new EditPlayerView(new EditPlayerActions());

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(1440, 880);
        // mainMenuView.setBackground(Color.BLACK);
        // mainFrame.add(mainMenuView, BorderLayout.NORTH);
        mainFrame.add(mainMenuView.getMenuBar(), BorderLayout.NORTH);
        mainFrame.add(mainMenuView, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private void showCreatePlayerView() {
        if (createPlayerView.isShowing()) {
            return;
        }

        mainFrame.remove(mainMenuView);
        mainFrame.add(createPlayerView);
        mainFrame.revalidate(); // recalculates the layout
        mainFrame.repaint();
    }

    private void showEditPlayerView() {
        if (editPlayerView.isShowing()) {
            return;
        }

        editPlayerView.setPlayer(activePlayer);
        mainFrame.remove(mainMenuView);
        mainFrame.add(editPlayerView);
        mainFrame.revalidate(); // recalculates the layout
        mainFrame.repaint();
    }

    public void updateActivePlayer() {
        activePlayer.setStr(editPlayerView.getStrength());
        activePlayer.setCon(editPlayerView.getConstitution());
        activePlayer.setDex(editPlayerView.getDexterity());
        activePlayer.setName(editPlayerView.getPlayerName());
        if (editPlayerView.getWeapon() != null) {
            activePlayer.setWeapon(editPlayerView.getWeapon());
        }
        saveCharecter(activePlayer);
        mainMenuView.revalidate();
        mainMenuView.repaint();
        mainFrame.repaint();
    }

    public void saveCharecter(Player p) {
        if (p.getPlayerFilePath().isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                p.setPlayerFilePath(selectedFile.getPath());
                System.out.println("saved to : " + p.getPlayerFilePath());
                p.saveToCsv(p);
            }
        } else {
            p.saveToCsv(p);
        }
        if (!players.contains(p)) {
            mainMenuView.addPlayer(p);
            players.add(p);
        }
        // else{
        // players.remove(p);
        // players.add(p)
        // }
        System.out.println("Player Lobby Length: " + players.size());
    }

    private void createNewPlayer() {

        Player p = new Player(
                createPlayerView.getWeapon(),
                createPlayerView.getPlayerName(),
                createPlayerView.getStrength(),
                createPlayerView.getDexterity(),
                createPlayerView.getConstitution(),
                createPlayerView.getAvatarPath(),
                createPlayerView.getPlayerFilePath());
        if (!players.contains(p) && players.size() < 4) {
            System.out.println("Player created");
            saveCharecter(p);
        } else if (players.contains(p)) {
            JOptionPane.showMessageDialog(mainMenuView, "PLAYER ALREADY IN LOBBY");
        } else if (players.size() == 4) {
            JOptionPane.showMessageDialog(mainMenuView, "PLAYER LOBBY IS FULL");
        } else {
            JOptionPane.showMessageDialog(mainMenuView, "ERROR CREATING PLAYER. PLEASE TRY AGAIN");
        }
        createPlayerView.clearFields();
        showMainMenuView();

    }

    private void loadPlayer() {
        // fetch a file using jfilechooser
        // load the file into the player
        // show the player view
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Scanner in = new Scanner(selectedFile)) {
                String playerCsvLine = in.nextLine();
                Player player = Player.loadFromCsv(playerCsvLine);
                mainMenuView.addPlayer(player);
                players.add(player);
                showMainMenuView();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading file");
            } catch (CsvReadException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Player Lobby Length: " + players.size());
        mainMenuView.repaint();
        mainFrame.repaint();
    }

    private void showMainMenuView() {

        if (mainMenuView.isShowing()) {
            return;
        }

        mainFrame.remove(createPlayerView);
        mainFrame.remove(editPlayerView);
        mainFrame.add(mainMenuView);
        mainFrame.revalidate(); // recalculates the layout
        mainFrame.repaint();
    }

    private class CreatePlayerActions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "CreatePlayerView.Cancel":
                    showMainMenuView();
                    createPlayerView.clearFields();
                    break;
                case "CreatePlayerView.Submit":
                    showMainMenuView();
                    createNewPlayer();
                    createPlayerView.clearFields();
                    break;
                default:
                    break;
            }

        }

    }

    private class EditPlayerActions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "EditPlayerView.Cancel":
                    showMainMenuView();
                    editPlayerView.clearFields();
                    break;
                case "EditPlayerView.Submit":
                    showMainMenuView();
                    updateActivePlayer();
                    saveCharecter(activePlayer);
                    editPlayerView.clearFields();
                    break;
                default:
                    System.out.println("Unhandled Action");
                    break;
            }

        }

    }

    public boolean sufficentPlayers() {
        return players.size() >= 2 ? true : false;
    }

    public void startGame() {
        System.out.println("Start game");
        if (sufficentPlayers()) {
            mainFrame.addMouseListener(new MouseActions());
            map = new Map(players);
            for (Player p : players) {
                int x = GameUtility.getRandomNumber(7);
                int y = GameUtility.getRandomNumber(7);
                while (map.isOccupied(x, y)) {
                    x = GameUtility.getRandomNumber(7);
                    y = GameUtility.getRandomNumber(7);
                }
                p.setCoordinates(x, y);
            }
            combatView = new CombatView(players);
            combatView.setActivePlayer(players.get(0));
            mainFrame.remove(mainMenuView);
            mainFrame.add(combatView);
            mainFrame.revalidate();
            mainFrame.repaint();

        } else {
            JOptionPane.showMessageDialog(mainMenuView, "Not enough Players");
        }

    }

    public Player getPlayer(int x, int y) {
        int xx = x / 100;
        int yy = y / 100;
        for (Player p : players) {
            if (p.getX() == xx && p.getY() == yy) {
                return p;
            }
        }
        return null;
    }

    private class MouseActions implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            try {
                Player p = getPlayer(e.getX(), e.getY());
                System.out.println("Player clicked: " + p.toString());
                activePlayer = p;
                combatView.setActivePlayer(p);
            } catch (NullPointerException npe) {
                System.out.println("No player clicked");
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!map.isOccupied(e.getX() / 100, e.getY() / 100) && activePlayer != null) {
                activePlayer.move(e.getX() / 100, e.getY() / 100);
                mainFrame.repaint();
            }
            activePlayer = null;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (activePlayer != null) {
                activePlayer.setX(e.getX() - 50);
                activePlayer.setY(e.getY() - 50);
                activePlayer.setFrameX(e.getX() - 50);
                activePlayer.setFrameY(e.getY() - 50);
                mainFrame.repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

    }

    private class MainMenuActions implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();
            switch (command) {
                case "MainMenuView.NewCharacter":
                    showCreatePlayerView();
                    break;
                case "MainMenuView.EditCharacter":
                    if (activePlayer != null) {
                        players.remove(activePlayer);
                        mainMenuView.removePlayer(activePlayer);
                        showEditPlayerView();
                    } else
                        JOptionPane.showMessageDialog(mainMenuView,
                                "NO PLAYER SELECTED TO EDIT\n CLICK ON A PLAYER TO EDIT THEM");
                    break;
                case "MainMenuView.LoadCharacter":
                    if (players.size() < 4)
                        loadPlayer();
                    else
                        lobbyFull();
                    break;
                case "MainMenuView.StartGame":
                    startGame();
                    break;
                case "MainMenuView.Exit":
                    mainFrame.dispose();
                    break;
                case "PlayerCardView.SelectPlayer":
                    System.out.println("Player selected");
                    activePlayer = mainMenuView.getActivePlayer();
                    break;
                default:
                    System.out.println("Unhandled action!");
                    break;
            }

        }

        private void lobbyFull() {
            JOptionPane.showMessageDialog(mainMenuView, "PLAYER LOBBY FULL");
        }

    }

}
