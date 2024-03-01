package edu.uta.CSE1325.Controllers;

import java.io.IOException;
import javax.swing.*;

public class GameController {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ApplicationController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}