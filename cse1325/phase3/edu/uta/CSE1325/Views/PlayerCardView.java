package edu.uta.CSE1325.Views;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
// import javax.swing.border.LineBorder;

import edu.uta.CSE1325.Models.Player;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerCardView extends JPanel {
    private JLabel nameLabel;
    private JButton avatarLabel;

    public PlayerCardView(Player p) {
        if (p == null) {
            System.out.println("Player was NULL");
            p = new Player();
        }else{
            System.out.println(p.toString());
        }
        // setBorder(new LineBorder(Color.red));
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());
        this.nameLabel = new JLabel();
        this.avatarLabel = new JButton();
        this.setPlayer(p);

        constraints.insets = new Insets(0, 0, 5, 0);
        this.add(nameLabel, constraints);
        constraints.gridy = 1;
        this.add(avatarLabel, constraints);
    }

    public void setPlayer(Player p) {
        this.nameLabel.setText(p.getName());
        this.avatarLabel.setIcon(new ImageIcon(p.getAvatarPath()));
        File f = new File(p.getAvatarPath());
        try {
            Image img = ImageIO.read(f.getAbsoluteFile());
            Image newimg = img.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
            img.flush();
            avatarLabel.setIcon(new ImageIcon(newimg));
            newimg.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        revalidate();
        repaint();
    }

    public void setAvatarLabel(String imgpath)
    {
        this.avatarLabel.setIcon(new ImageIcon(imgpath));
        File f = new File(imgpath);
        try {
            Image img = ImageIO.read(f.getAbsoluteFile());
            Image newimg = img.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
            img.flush();
            avatarLabel.setIcon(new ImageIcon(newimg));
            newimg.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        revalidate();
        repaint();
    }

    public String getNameLabel() {
        return nameLabel.getText();
    }
    public void setNameLabel(String text){
        nameLabel.setText(text);
        revalidate();
        repaint();
    }

    public void  setAvatarLabelAction(ActionListener listener) {
        avatarLabel.setActionCommand("PlayerCardView.SelectPlayer");
        avatarLabel.addActionListener(listener);
    }

    

}
