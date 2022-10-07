package com.company;
//all imports needed for the code to work
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

public class AttackGUI extends Frame {
    //this is where we'll output info about what's going on in the fight as well as how our player and enemy are doing
    private static TextArea fightInfo = new TextArea();
    private static TextArea entityInfo = new TextArea();
    public AttackGUI() {}
    //method that prints info about the enemy's and player's stats at any given time
    public void printToEI(Player mainPlayer, Enemy mainEnemy){entityInfo.setText("Enemy Stats:" + "\nHealth: " + mainEnemy.getHealth() + "\nAttack: " + mainEnemy.getAttackDamage() + "\nCrit Chance: " + mainEnemy.getCritChance() + "\nCrit Damage: " + mainEnemy.getCritDamage()
            + "\n\nPlayer Stats:" + "\nHealth: " + mainPlayer.getHealth() + "\nAttack: " + mainPlayer.getAttackDamage() + "\nCrit Chance: " + mainPlayer.getCritChance() + "\nCrit Damage: " + mainPlayer.getCritDamage()
            + "\nDefence: " + mainPlayer.getDefence());}
    //method that allows us to print what is happening in the fight to the user
    public void print(String text){
        fightInfo.setText(text);
    }
    //method that checks at any given time to see if a fight is over and gives the relevant ending to every fight end scenario
    public void checkToEndFight(Enemy mainEnemy, Player mainPlayer){
        if(mainEnemy.getHealth()<=0||mainPlayer.getHealth()<=0){
            if(mainEnemy.getHealth()<=0&&mainPlayer.getHealth()>0){
                //if the user defeats the boss
                    if(mainEnemy.getName().equals("Elemental Archon")){
                        new victoryPopUp(mainPlayer, new JLabel("Congratulations! You've beaten the game. Feel free to do whatever now."));
                        dispose();
                    }
                    //if the user defeats a normal enemy
                    else{
                        new victoryPopUp(mainPlayer, new JLabel("You Defeated the " + mainEnemy.getName() + "! You gain 25 xp and 10 gold"));
                        dispose();
                    }
            }
            else if(mainPlayer.getHealth()<=0&&mainEnemy.getHealth()>0){
                //if the user is knocked out but has a revive potion
                if(mainPlayer.inventory.contains("RevivePotion")){
                    mainPlayer.removeItem("RevivePotion");
                    mainPlayer.setHealth(100);
                    print("You drink a revive potion and gain 100 health!");
                    printToEI(mainPlayer, mainEnemy);
                }
                //if the user is knocked out and doesn't have any revive potions
                else{
                    new gameOverPopUp(mainPlayer);
                    dispose();
                }
            }
            else if(mainEnemy.getHealth()<=0&&mainPlayer.getHealth()<=0){
                //if the user defeats the boss but also gets knocked out
                if(mainEnemy.getName().equals("Elemental Archon")){
                    new victoryPopUp(mainPlayer, new JLabel("You Defeated the " + mainEnemy.getName() + " but also get knocked out. They manage to escape..."));
                    dispose();
                }
                //if the user defeats a normal enemy but also gets knocked out
                else{
                    new victoryPopUp(mainPlayer, new JLabel("You Defeated the " + mainEnemy.getName() + " but also get knocked out. You gain 25 xp and 10 gold"));
                    dispose();
                }
            }
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
    public AttackGUI(boolean isBoss, Player mainPlayer){
        //making a new enemy for the fight
        Enemy mainEnemy = new Enemy(isBoss, mainPlayer);

        //setting up the title and layout of our attack GUI
        this.setTitle("AdventureMania");
        this.setLayout(new GridLayout(2,1));
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);

        //this is the panel where we will display all our relevant information about the fight
        Panel topHalfPanel = new Panel();
        topHalfPanel.setLayout(new GridLayout(1, 2, 10, 10));

        //adding all relevant info to the panel
        topHalfPanel.add(fightInfo, BorderLayout.WEST);
        topHalfPanel.add(entityInfo, BorderLayout.EAST);
        this.add(topHalfPanel);

        //displaying a welcome message to the user about the fight
        if(isBoss){
            print("You have " + mainPlayer.getHealth() + " health." +  "\nThe Elemental Archon has gone insane! Stop them once and for all...");
        }
        else{
            print("You have " + mainPlayer.getHealth() + " health." +  "\nYou encounter a " + mainEnemy.getName() + "!");
        }

        //displaying the player's and enemy's stats at the start of the fight
        printToEI(mainPlayer, mainEnemy);

        //this is the panel where we will display all the options the user has in the fight
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(2,2, 10, 10));

        //creating the attack button and adding it to the panel
        Button attack = new Button("Attack");
        attack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                print(mainPlayer.attack(mainEnemy)+"\n"+mainEnemy.attack(mainPlayer));
                printToEI(mainPlayer, mainEnemy);
                checkToEndFight(mainEnemy, mainPlayer);
            }
        });
        buttonPanel.add(attack, BorderLayout.EAST);

        //creating the inventory button and adding it to the panel
        Button inventory = new Button("Inventory");
        inventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                new inventoryPopUp(mainPlayer, mainEnemy);
            }
        });
        buttonPanel.add(inventory, BorderLayout.WEST);

        //creating the block button and adding it to the panel
        Button block = new Button("Block");
        block.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                print(mainPlayer.block(mainEnemy));
                printToEI(mainPlayer, mainEnemy);
                checkToEndFight(mainEnemy, mainPlayer);
            }
        });
        buttonPanel.add(block, BorderLayout.EAST);

        //creating the run button and adding it to the panel
        Button run = new Button("Run");
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                new MainGameGUI(mainPlayer);
                dispose();
            }
        });
        buttonPanel.add(run, BorderLayout.WEST);

        //adding our button panel to our GUI
        this.add(buttonPanel);

        //adding a window closer to our GUI
        WindowCloser windowCloser = new WindowCloser();
        this.addWindowListener(windowCloser);

        //making our GUI visible to the user
        this.setVisible(true);
    }
}
