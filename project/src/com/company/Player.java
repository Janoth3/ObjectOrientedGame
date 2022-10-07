package com.company;

import java.util.ArrayList;

//player inherits from entity
public class Player extends Entity{
    //every player has these unique attributes to it
    int defence, XP, gold, level;
    ArrayList<String> inventory;

    //constructor for player (base stats)
    public Player(String name){
        this.name = name;
        this.health = 100;
        this.attackDamage = 50;
        this.defence = 50;
        this.critChance = 10;
        this.critDamage = 60;
        this.XP = 0;
        this.level = 1;
        this.gold = 1000;
        this.inventory = new ArrayList<>();
    }
    //allows player to level up (minor increments to all attributes) if they have enough xp (100)
    public void possibleLevelUp(){
        if(this.getXP()>=100){
            this.setLevel(this.getLevel()+1);
            this.setHealth(this.getHealth()+10);
            this.setAttackDamage(this.getAttackDamage()+10);
            this.setCritChance(this.getCritChance()+1);
            this.setCritDamage(this.getCritDamage()+10);
            this.setDefence(this.getDefence()+10);
            this.setXP(0);
        }
    }
    //allows player to block an attack (sustains chip damage most of the time)
    public String block(Enemy mainEnemy) throws RuntimeException{
        boolean mainEnemyCrit = mainEnemy.isAttackCrit();
        if(mainEnemyCrit){
            if(this.getDefence()<mainEnemy.getCritDamage()){
                this.setHealth(this.getHealth()-(mainEnemy.getCritDamage()-this.getDefence()));
                return("You block an attack but sustain some chip damage! THE ATTACK WAS CRITICAL");
            }
            else if(this.getDefence()>mainEnemy.getCritDamage()){
                this.setHealth(this.getHealth()-(this.getDefence()-mainEnemy.getCritDamage()));
                return("You block an attack but sustain some chip damage! THE ATTACK WAS CRITICAL");
            }
            else if(this.getDefence()==mainEnemy.getCritDamage()){
                return("You perfectly block an attack! THE ATTACK WAS CRITICAL");
            }
        }
        else if(!mainEnemyCrit){
            if(this.getDefence()<mainEnemy.getAttackDamage()){
                this.setHealth(this.getHealth()-(mainEnemy.getAttackDamage()-this.getDefence()));
                return("You block an attack but sustain some chip damage!");
            }
            else if(this.getDefence()>mainEnemy.getAttackDamage()){
                this.setHealth(this.getHealth()-(this.getDefence()-mainEnemy.getAttackDamage()));
                return("You block an attack but sustain some chip damage!");
            }
            else if(this.getDefence()==mainEnemy.getAttackDamage()){
                return("You block an attack perfectly!");
            }
        }
        throw new RuntimeException("Error, String not returned");
    }
    //allows player to have an item removed from their inventory
    public void removeItem(String itemToRemove){
        boolean removed = false;
        for (int i = 0; i<this.inventory.size(); i++){
            if(this.inventory.get(i).equals(itemToRemove)&&removed==false){
                this.inventory.remove(i);
                removed=true;
            }
        }
    }
    //getter and setter methods for all unique player attributes
    public void addInventory(String item){
        inventory.add(item);
    }
    public int getGold(){
        return gold;
    }
    public void setGold(int valueToSet){
        this.gold = valueToSet;
    }
    public int getDefence(){return defence;}
    public void setDefence(int valueToSet){this.defence = valueToSet;}
    public int getLevel(){return level;}
    public void setLevel(int valueToBeSet){this.level=valueToBeSet;}
    public int getXP(){return XP;}
    public void setXP(int valueToSet){this.XP = valueToSet;}
}
