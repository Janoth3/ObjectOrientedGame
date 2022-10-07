package com.company;
//all imports needed for the code to work
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TitleScreenGUI extends Frame {
    //method that creates a new player with base stats and begins a new game with said player
    public void startNewGame(){
        new MainGameGUI(new Player(JOptionPane.showInputDialog("Name", "Enter name of Adventurer")));
        dispose();
    }
    //method that loads a player from a previous save and resumes a game with said player
    public void loadGameFromSave(){
        try{
            //save data is read from a text file that contains all the necessary information
            BufferedReader inputStream = new BufferedReader(new FileReader("AdventureManiaSaveData.txt"));
            String wholeFile = inputStream.readLine();
            inputStream.close();
            //separate file for the inventory of the player as that itself has to be split into each individual item
            String inventoryString = null;
            for (int i = 0; i<wholeFile.length(); i++){
                if(wholeFile.charAt(i)=='['){
                    inventoryString=wholeFile.substring(i);
                }
            }
            //separating the data from the file into the appropriate unique attributes of the player and storing them in an array
            int lengthSoFar = 0;
            int whichNodeToAssign = 0;
            String[] infoFromFile = new String[10];
            for (int i = 0; i<wholeFile.length(); i++){
                if(wholeFile.charAt(i)=='.'){
                    lengthSoFar=i;
                }
                if(wholeFile.charAt(i)==','){
                    infoFromFile[whichNodeToAssign]=wholeFile.substring((lengthSoFar+1),(i));
                    lengthSoFar=i;
                    whichNodeToAssign++;
                }
                if(wholeFile.charAt(i)=='['){
                    i=wholeFile.length();
                }
            }
            //creating a new player and setting their attributes as the values from the array
            Player mainPlayer = new Player(infoFromFile[0]);
            mainPlayer.setHealth(Integer.parseInt(infoFromFile[1]));
            mainPlayer.setAttackDamage(Integer.parseInt(infoFromFile[2]));
            mainPlayer.setCritChance(Integer.parseInt(infoFromFile[3]));
            mainPlayer.setCritDamage(Integer.parseInt(infoFromFile[4]));
            mainPlayer.setDefence(Integer.parseInt(infoFromFile[5]));
            mainPlayer.setXP(Integer.parseInt(infoFromFile[6]));
            mainPlayer.setLevel(Integer.parseInt(infoFromFile[7]));
            mainPlayer.setGold(Integer.parseInt(infoFromFile[8]));
            //splitting up the data about the player's inventory into every individual item
            for (int i = 0; i<inventoryString.length(); i++){
                if(inventoryString.charAt(i)=='['){
                    lengthSoFar=i;
                }
                if(inventoryString.charAt(i)==','||inventoryString.charAt(i)==']'){
                    //each individual item is then added to this new player's inventory
                    mainPlayer.inventory.add(inventoryString.substring((lengthSoFar+1),(i)));
                    lengthSoFar=i+1;
                }
            }
            //finally, a new game is made with this player so as to resume from where the user left off
            new MainGameGUI(mainPlayer);
        }catch(IOException error){
            System.out.println(error);
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
    public TitleScreenGUI(){
        //setting up the layout and title of our game GUI
        this.setLayout(new GridLayout(2,1));
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        this.setTitle("AdventureMania");

        //adding a header to our title screen
        JLabel titleText = new JLabel("<html><h1>Welcome to AdventureMania!</h1></html>");
        titleText.setFont(new Font("Serif", Font.BOLD, 28));
        titleText.setForeground(Color.blue);
        titleText.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleText);

        //this is the panel our NewGame, Load and Exit buttons will be on
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(3,1, 0, 10));

        //making the NewGame button and adding it to the panel
        Button startNewGame = new Button("New game");
        startNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                startNewGame();
            }
        });
        buttonPanel.add(startNewGame);

        //making the LoadGame button and adding it to the panel
        Button loadGame = new Button("Load game");
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                loadGameFromSave();
            }
        });
        buttonPanel.add(loadGame);

        //making the Exit button and adding it to the panel
        Button exit = new Button("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundEffect();
                System.exit(0);
            }
        });
        buttonPanel.add(exit);

        //adding the panel to our GUI
        this.add(buttonPanel);

        //adding a window closer to our GUI
        WindowCloser windowCloser = new WindowCloser();
        this.addWindowListener(windowCloser);

        //and finally, displaying our GUI to the user
        this.setVisible(true);
    }
}
