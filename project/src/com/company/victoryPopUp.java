package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class victoryPopUp extends AttackGUI{
    public victoryPopUp(Player mainPlayer, JLabel stringToOutput){
            //dialog that lets the user know they've won a fight
            JDialog victoryMessage = new JDialog();
            victoryMessage.setLayout(new GridLayout(2, 1));
            victoryMessage.setSize(750, 300);
            victoryMessage.setLocationRelativeTo(null);

            //adding out victory message to our dialog
            victoryMessage.add(stringToOutput);

            //giving the player the appropriate rewards for winning a fight
            mainPlayer.setXP(mainPlayer.getXP()+25);
            mainPlayer.setGold(mainPlayer.getGold()+10);

            //seeing if the player can level up (if they have enough xp)
            mainPlayer.possibleLevelUp();

            //creating an exit button and adding it to our dialog
            Button exit = new Button("Exit");
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPlayer.setHealth(100+(10*(mainPlayer.getLevel()-1)));
                    new MainGameGUI(mainPlayer);
                    victoryMessage.setVisible(false);
                    dispose();
                }
            });
            victoryMessage.add(exit);

            //making our dialog visible to the user (whenever they win a fight)
            victoryMessage.setVisible(true);
    }
}
