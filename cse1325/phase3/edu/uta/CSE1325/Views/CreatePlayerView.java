package edu.uta.CSE1325.Views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.uta.CSE1325.Models.Player;
import edu.uta.CSE1325.Models.*;

public class CreatePlayerView extends JPanel implements ActionListener, DocumentListener {
    private JTextField nameTextField;
    private JComboBox<File> avatarComboBox;
    private JComboBox<Weapon> weaponComboBox;
    private int Strength = 0;
    private int Dexterity = 0;
    private int Constitution = 0;
    private Weapon weapon;
    private String playerFilePath = "";
    private ArrayList<Weapon> weapons;
    private JButton submitButton;
    private JButton cancelButton;
    private EditPlayerStatsView statsButtons;
    private PlayerCardView playerCard;
    private Player newPlayer = new Player();
    private String defweapons = "weapons.csv";
    GridBagConstraints constraints = new GridBagConstraints();

    public CreatePlayerView(ActionListener listener)throws IOException {
        setLayout(new GridBagLayout());
        statsButtons = new EditPlayerStatsView(this);
        playerCard = new PlayerCardView(newPlayer);
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        this.configureTextFields();
        this.configureWeapons();
        this.configureAvatarImages();
        
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 250, 0, 0);
        this.add(statsButtons, constraints);
        constraints.gridy = 1;
        constraints.gridx = 0;
        this.add(playerCard, constraints);

        this.configureButtons(constraints, listener);

        
    }
    private void configureTextFields(){
        nameTextField = new JTextField(10);
        nameTextField.getDocument().addDocumentListener(this);
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 10,0,0);
        this.add(nameTextField, constraints);
    }

    private void configureAvatarImages() {
        File files[] = new File("img/").listFiles();
        avatarComboBox = new JComboBox<File>(files);
        avatarComboBox.addActionListener(this);
        avatarComboBox.setActionCommand("CreatePlayerView.ChangeAvatar");
        this.add(avatarComboBox, constraints);
    }

    private void configureWeapons() throws IOException {
        weapons = GameUtility.createWeapons(defweapons);
        weaponComboBox = new JComboBox<Weapon>(weapons.toArray(new Weapon[weapons.size()]));
        weaponComboBox.setActionCommand("CreatePlayerView.ChangeWeapon");
        weaponComboBox.addActionListener(this);
        this.add(weaponComboBox, constraints);
    }

    private void configureButtons(GridBagConstraints constraints, ActionListener listener){
        JPanel buttonPanel = new JPanel();
        // Stats panel
        buttonPanel.setLayout(new GridBagLayout());
        // Create button
        constraints.gridx = 0;
        submitButton.addActionListener(listener);
        submitButton.setActionCommand("CreatePlayerView.Submit");
        submitButton.setEnabled(false);
        buttonPanel.add(submitButton, constraints);

        // Cancel button
        constraints.gridx = 1;
        cancelButton.addActionListener(listener);
        cancelButton.setActionCommand("CreatePlayerView.Cancel");
        buttonPanel.add(cancelButton, constraints);

        // Add cancel and submit buttons to panel
        constraints.gridx = 0;
        constraints.gridy = 2;
        // constraints.gridwidth = 2;
        constraints.insets = new Insets(30, 5, 0, 0);
        add(buttonPanel, constraints);
    }

    public int getStrength() {
        return Strength;
    }
    public int getConstitution() {
        return Constitution;
    }
    public int getDexterity() {
        return Dexterity;
    }

    public void clearFields() {
        nameTextField.setText("");
    }

    public String getAvatarPath() {
        int index = avatarComboBox.getSelectedIndex();
        File f = avatarComboBox.getItemAt(index);
        return f.getAbsolutePath();
    }

    public String getPlayerName(){
        return nameTextField.getText().toUpperCase();
    }

    private void setWeapon() {
        this.weapon = weapons.get(weaponComboBox.getSelectedIndex());
    }
    
    public Weapon getWeapon() {
        return this.weapon;
    }



    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        submitButton.setEnabled(nameTextField.getDocument().getLength() != 0 );
        playerCard.setNameLabel(nameTextField.getText());
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        submitButton.setEnabled(nameTextField.getDocument().getLength() != 0);
        playerCard.setNameLabel(nameTextField.getText());
    }
    
    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        final String action = e.getActionCommand();
        switch (action) {
            case "CreatePlayerView.ChangeAvatar":
                playerCard.setAvatarLabel(avatarComboBox.getSelectedItem().toString());
                break;
            case "CreatePlayerView.ChangeWeapon":
                this.setWeapon();
                break;
            case "EditPlayerStatsView.IncrementStrength":
                this.Strength++;
                statsButtons.decrementPoints();
                System.out.println("Strength: " + Strength);
                break;
            case "EditPlayerStatsView.IncrementDexterity":
                this.Dexterity++;
                statsButtons.decrementPoints();
                System.out.println("Dexterity: " + Dexterity);
                break;
            case "EditPlayerStatsView.IncrementConstitution":
                this.Constitution++;
                statsButtons.decrementPoints();
                System.out.println("Constitution: " + Constitution);
                break;
            case "EditPlayerStatsView.DecrementStrength":
                this.Strength--;
                statsButtons.incrementPoints();
                System.out.println("Strength: " + Strength);
                break;
            case "EditPlayerStatsView.DecrementDexterity":
                this.Dexterity--;
                statsButtons.incrementPoints();
                System.out.println("Dexterity: " + Dexterity);
                break;
            case "EditPlayerStatsView.DecrementConstitution":
                this.Constitution--;
                statsButtons.incrementPoints();
                System.out.println("Constitution: " + Constitution);
                break;
            default:
                System.out.println("Unknown action in EditPlayerStatsView: " + action);
                break;
        }
    }
    public String getPlayerFilePath() {
        return this.playerFilePath;
    }
}
