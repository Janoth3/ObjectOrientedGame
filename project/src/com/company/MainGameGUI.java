package com.company;
//all imports needed for the code to work
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGameGUI extends Frame{
    //method that adds a TextArea to a panel with information about what to do in the game as well as the player's stats.
    public void displayInfo(Panel panelToDisplayOn, Player mainPlayer){
        panelToDisplayOn.add(new TextArea(mainPlayer.getName() + ", your aim is to defeat the boss. \nKill enemies for xp and gold. \nBuy from the shop and get stronger. \nGood luck"
                + "\n\nPlayer Stats:" + "\nHealth: " + mainPlayer.getHealth() + "\nAttack: " + mainPlayer.getAttackDamage() + "\nCrit Chance: " + mainPlayer.getCritChance() + "\nCrit Damage: " + mainPlayer.getCritDamage()
                + "\nDefence: " + mainPlayer.getDefence() + "\nLevel: " + mainPlayer.getLevel()));
    }
    //method that saves the game by putting the player's stats and inventory into a text file.
    public void exitOrSaveGame(Player mainPlayer){
        try{
            //game writes save data into a text file
            PrintWriter outputStream = new PrintWriter(new FileWriter("AdventureManiaSaveData.txt"));
            //writing data in a specific format to make it easier to read from (by making substrings wherever there's punctuation)
            outputStream.print("."+mainPlayer.getName()+","+mainPlayer.getHealth()+","+mainPlayer.getAttackDamage()+","+mainPlayer.getCritChance()+","+mainPlayer.getCritDamage()+","+mainPlayer.getDefence()+","+mainPlayer.getXP()+","+mainPlayer.getLevel()+","+mainPlayer.getGold()+","+mainPlayer.inventory);
            //closing the file as we are done writing to it
            outputStream.close();

            //making a pop up window to confirm that the game has been saved
            JDialog successfulSave = new JDialog();
            successfulSave.setSize(750, 300);
            successfulSave.setLocationRelativeTo(null);
            successfulSave.setLayout(new GridLayout(2, 1, 10, 10));
            successfulSave.add(new JLabel("Game was successfully saved!"));

            //adding an exit button to our pop up window that closes everything
            Button exitSuccessfulSaveMessage = new Button("Exit");
            exitSuccessfulSaveMessage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playSoundEffect();
                    System.exit(0);
                }
            });
            successfulSave.add(exitSuccessfulSaveMessage);

            //making our sucess message visible to the user
            successfulSave.setVisible(true);
        }catch(IOException error){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, error);
        }
    }
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
    public MainGameGUI(Player mainPlayer){
        //setting up the layout and title of our main GUI
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        this.setTitle("AdventureMania");
        this.setLayout(new GridLayout(1,2));

        //this is the panel for the left hand of our screen (for info and the attack button)
        Panel buttonPanelOne = new Panel();
        buttonPanelOne.setLayout(new GridLayout(2,1, 0, 10));

        //adding info to our main GUI
        displayInfo(buttonPanelOne, mainPlayer);

        //creating the attack button and adding it to our panel
        Button attack = new Button("Attack");
        attack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //launches an attack GUI and closes this main GUI
                playSoundEffect();
                new AttackGUI(false, mainPlayer);
                dispose();
            }
        });
        buttonPanelOne.add(attack);

        //adding our info and attack panel to the GUI
        this.add(buttonPanelOne);

        //this is the panel for the right hand side of our screen (for exiting/saving, the boss fight and the shop).
        Panel buttonPanelTwo = new Panel();
        buttonPanelTwo.setLayout(new GridLayout(3,1, 0, 10));

        //creating the exit/save button and adding it to our panel
        Button exitSave = new Button("Exit/Save");
        exitSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                exitOrSaveGame(mainPlayer);
            }
        });
        buttonPanelTwo.add(exitSave);

        //creating the boss fight button and adding it to our panel
        Button bossFight = new Button("Boss");
        bossFight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //launches an attack GUI and closes this main GUI (attack GUI recognises that this is a boss)
                playSoundEffect();
                new AttackGUI(true, mainPlayer);
                dispose();
            }
        });
        buttonPanelTwo.add(bossFight);

        //creating the shop button and adding it to our panel
        Button goShop = new Button("Shop");
        goShop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //launches a shop GUI and closes this main GUI
                playSoundEffect();
                new ShopGUI(mainPlayer);
                dispose();
            }
        });
        buttonPanelTwo.add(goShop);

        //adding our exit, boss and shop panel to our GUI
        this.add(buttonPanelTwo);

        //adding a window closer to our GUI
        WindowCloser windowCloser = new WindowCloser();
        this.addWindowListener(windowCloser);

        //finally making our GUI visible to the user
        this.setVisible(true);
    }
}
