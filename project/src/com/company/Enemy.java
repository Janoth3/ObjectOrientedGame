package com.company;

import java.util.Random;

//enemy inherits from entity
public class Enemy extends Entity{
    //enemy has no unique attributes
    //enemy constructor
    public Enemy(boolean isBoss, Player mainPlayer){
        //different base stats for whether it is a boss or not
        if(!isBoss){
            this.name = randomNameSelection();
            this.health = 100;
            this.attackDamage = 30;
            this.critChance = 10;
            this.critDamage = 40;
        }
        else if(isBoss){
            this.name = "Elemental Archon";
            this.health = (mainPlayer.getHealth())*10;
            this.attackDamage = (mainPlayer.getAttackDamage())/2;
            this.critChance = 10;
            this.critDamage = mainPlayer.getCritDamage();
        }
    }
    //allows a non-boss enemy to have a name randomly selected from a preset of 5 names
    public String randomNameSelection(){
        String[] possibleNames = {"Automaton", "Elemental Lifeform", "Hilichurl", "Mystical Beast", "Abyss Mage"};
        Random rand = new Random();
        int randomNumber = rand.nextInt(5);
        return possibleNames[randomNumber];
    }
}
