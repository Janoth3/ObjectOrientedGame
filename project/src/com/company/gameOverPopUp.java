package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameOverPopUp extends AttackGUI{
    public gameOverPopUp(Player mainPlayer){
        //dialog that lets the user know they've lost the fight
        JDialog gameOverMessage = new JDialog();
        gameOverMessage.setLayout(new GridLayout(2, 1));
        gameOverMessage.setSize(750, 300);
        gameOverMessage.setLocationRelativeTo(null);
        //adding a game over message
        JLabel stringToOutput = new JLabel("You get knocked out.");
        gameOverMessage.add(stringToOutput);
        //adding an exit button to our message
        Button exit = new Button("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPlayer.setHealth(100+(10*(mainPlayer.getLevel()-1)));
                new MainGameGUI(mainPlayer);
                gameOverMessage.setVisible(false);
                dispose();
            }
        });
        gameOverMessage.add(exit);

        //making our game over message visible to the user (whenever they lose a fight)
        gameOverMessage.setVisible(true);
    }
}
