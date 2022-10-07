package com.company;
//all imports needed for the code to work
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopGUI extends Frame {
    //this is the textarea that will display everything we do/what we already have/how much gold we have left
    private static TextArea transactionHistory = new TextArea();
    public static void print(String text){
        transactionHistory.setText(text);
    }
    //method that either buys an item for a player or informs them that they do not have enough gold to do so
    public static void buy(Player mainPlayer, String item){
        //if the player does not have enough gold to buy anything
        if(mainPlayer.getGold()<10){
            print("You do not have enough gold for this item!" + inventoryCheck(mainPlayer));
        }
        //process of buying an item
        else{
            mainPlayer.setGold(mainPlayer.getGold()-10);
            mainPlayer.addInventory(item);
            print("You bought a " + item + "!\nYou have " + mainPlayer.getGold() + " gold left.\n" + inventoryCheck(mainPlayer));
        }
    }
    //method that checks and displays what the player has in their inventory
    public static String inventoryCheck(Player mainPlayer){
        //tallying up what items there are in the inventory
        int strengthPotionNum=0;
        int healthPotionNum=0;
        int revivePotionNum=0;
        int speedPotionNum=0;
        int critPotionNum=0;
        for (int i = 0; i<mainPlayer.inventory.size(); i++){
            if(mainPlayer.inventory.get(i).equals("StrengthPotion")){
                strengthPotionNum++;
            }
            else if(mainPlayer.inventory.get(i).equals("HealthPotion")){
                healthPotionNum++;
            }
            else if(mainPlayer.inventory.get(i).equals("RevivePotion")){
                revivePotionNum++;
            }
            else if(mainPlayer.inventory.get(i).equals("SpeedPotion")){
                speedPotionNum++;
            }
            else if(mainPlayer.inventory.get(i).equals("CritPotion")){
                critPotionNum++;
            }
        }
        //printing out what the player has in their inventory
        return "You have: " + strengthPotionNum + " Strength Potions, " + healthPotionNum + " Health Potions, " + revivePotionNum + " Revive Potions, " + speedPotionNum + " Speed Potions & " + critPotionNum + " Crit Potions.";
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
    public ShopGUI(Player mainPlayer){
        //setting up the title and layout of our shop GUI
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        this.setTitle("AdventureMania");
        this.setLayout(new GridLayout(2,1));

        //adding the transaction info box to our GUI and printing a welcome message
        this.add(transactionHistory);
        print("Welcome to the Shop!\nYou have " + mainPlayer.getGold() + " gold left.\n Each item costs 10 gold.\n" + inventoryCheck(mainPlayer));

        //this is the panel we will have all our buy options on
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(3,2, 10, 10));

        //creating the strength potion and adding it to our panel
        Button strengthPotion = new Button("Strength Potion");
        strengthPotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                buy(mainPlayer, "StrengthPotion");
            }
        });
        buttonPanel.add(strengthPotion, BorderLayout.WEST);

        //creating the health potion and adding it to our panel
        Button healthPotion = new Button("Health Potion");
        healthPotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                buy(mainPlayer, "HealthPotion");
            }
        });
        buttonPanel.add(healthPotion, BorderLayout.EAST);

        //creating the revive potion and adding it to our panel
        Button revivePotion = new Button("Revive Potion");
        revivePotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                buy(mainPlayer, "RevivePotion");
            }
        });
        buttonPanel.add(revivePotion, BorderLayout.WEST);

        //creating the speed potion and adding it to our panel
        Button speedPotion = new Button("Speed Potion");
        speedPotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                buy(mainPlayer, "SpeedPotion");
            }
        });
        buttonPanel.add(speedPotion, BorderLayout.EAST);

        //creating the crit potion and adding it to our panel
        Button critPotion = new Button("Crit Potion");
        critPotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                buy(mainPlayer, "CritPotion");
            }
        });
        buttonPanel.add(critPotion, BorderLayout.WEST);

        //creating the exit button and adding it to our panel
        Button exit = new Button("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                new MainGameGUI(mainPlayer);
                dispose();
            }
        });
        buttonPanel.add(exit, BorderLayout.EAST);

        //adding the button panel to our GUI
        this.add(buttonPanel);

        //adding a window closer to GUI
        WindowCloser windowCloser = new WindowCloser();
        this.addWindowListener(windowCloser);

        //finally, making our GUI visible to the user
        this.setVisible(true);
    }
}
