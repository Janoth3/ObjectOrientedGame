package com.company;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class inventoryPopUp extends AttackGUI{
    //method for playing sound effect (whenever a button is pressed)
    public void playSoundEffect(){
        try{
            new playSound();
        }catch (MalformedURLException error) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, error);
        } catch (LineUnavailableException error) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, error);
        } catch (UnsupportedAudioFileException error) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, error);
        } catch (IOException error) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, error);
        }
    }
    public inventoryPopUp(Player mainPlayer, Enemy mainEnemy){
        super();
        //making a new pop up that displays what the user can do from their inventory
        JDialog inventoryItems = new JDialog();
        inventoryItems.setLayout(new GridLayout(7, 1));
        inventoryItems.setSize(750, 300);
        inventoryItems.setLocationRelativeTo(null);

        //adding an interaction message screen to our pop up
        JLabel stringToOutput = new JLabel(ShopGUI.inventoryCheck(mainPlayer));
        inventoryItems.add(stringToOutput);

        //creating the strength potion button and adding it to our pop up
        Button strengthPotion = new Button("Strength Potion");
        strengthPotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                if(mainPlayer.inventory.contains("StrengthPotion")){
                    inventoryItems.setVisible(false);
                    mainPlayer.removeItem("StrengthPotion");
                    mainPlayer.setAttackDamage(mainPlayer.getAttackDamage()+10);
                    mainPlayer.setCritDamage(mainPlayer.getCritDamage()+10);
                    print("You drink a strength potion and increase your attack and crit damage by 10!");
                    printToEI(mainPlayer, mainEnemy);
                }
                else{
                    inventoryItems.setVisible(false);
                    print("You do not have any of those potions!");
                }
            }
        });
        inventoryItems.add(strengthPotion);

        //creating the health potion button and adding it to our pop up
        Button healthPotion = new Button("Health Potion");
        healthPotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                if(mainPlayer.inventory.contains("HealthPotion")){
                    inventoryItems.setVisible(false);
                    mainPlayer.removeItem("HealthPotion");
                    mainPlayer.setHealth(mainPlayer.getHealth()+30);
                    print("You drink a health potion and increase your health by 30!");
                    printToEI(mainPlayer, mainEnemy);
                }
                else{
                    inventoryItems.setVisible(false);
                    print("You do not have any of those potions!");
                }
            }
        });
        inventoryItems.add(healthPotion);

        //creating the revive potion button and adding it to our pop up
        Button revivePotion = new Button("Revive Potion");
        revivePotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                if(mainPlayer.inventory.contains("RevivePotion")){
                    inventoryItems.setVisible(false);
                    print("Revive potions are automatically consumed when you die.");
                }
                else{
                    inventoryItems.setVisible(false);
                    print("You do not have any of those potions!");
                }
            }
        });
        inventoryItems.add(revivePotion);

        //creating the speed potion button and adding it to our pop up
        Button speedPotion = new Button("Speed Potion");
        speedPotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                if(mainPlayer.inventory.contains("SpeedPotion")){
                    inventoryItems.setVisible(false);
                    mainPlayer.removeItem("SpeedPotion");
                    boolean mainPlayerCrit = mainPlayer.isAttackCrit();
                    if(mainPlayerCrit){
                        mainPlayer.attackCrit(mainEnemy);
                        print("You drink a speed potion and attack the enemy an extra time! YOU DEAL A CRITICAL HIT");
                    }
                    else if(!mainPlayerCrit){
                        mainPlayer.attackNormal(mainEnemy);
                        print("You drink a speed potion and attack the enemy an extra time!");
                    }
                    printToEI(mainPlayer, mainEnemy);
                    checkToEndFight(mainEnemy, mainPlayer);
                }
                else{
                    inventoryItems.setVisible(false);
                    print("You do not have any of those potions!");
                }
            }
        });
        inventoryItems.add(speedPotion);

        //creating the crit potion button and adding it to our pop up
        Button critPotion = new Button("Crit Potion");
        critPotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                if(mainPlayer.inventory.contains("CritPotion")){
                    inventoryItems.setVisible(false);
                    mainPlayer.removeItem("CritPotion");
                    mainPlayer.setCritChance(mainPlayer.getCritChance()+10);
                    print("You drink a crit potion and increase your crit chance by 10!");
                    printToEI(mainPlayer, mainEnemy);
                }
                else{
                    inventoryItems.setVisible(false);
                    print("You do not have any of those potions!");
                }
            }
        });
        inventoryItems.add(critPotion);

        //creating the exit button and adding it to our pop up
        Button exit = new Button("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                inventoryItems.setVisible(false);
            }
        });
        inventoryItems.add(exit);

        //making our pop up visible to the user
        inventoryItems.setVisible(true);
    }
}
