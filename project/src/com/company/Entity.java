package com.company;

import java.util.Random;

abstract class Entity {
    //every entity has a name, health, attackDamage, critChance and critDamage
    String name;
    int health, attackDamage, critChance, critDamage;

    //getter and setter methods for each attribute (no setter for name)
    public String getName(){return name;}
    public int getHealth(){
        return health;
    }
    public void setHealth(int valueToSet){
        this.health = valueToSet;
    }
    public int getAttackDamage(){
        return attackDamage;
    }
    public void setAttackDamage(int valueToSet){
        this.attackDamage = valueToSet;
    }
    public int getCritChance(){
        return critChance;
    }
    public void setCritChance(int valueToSet){
        this.critChance = valueToSet;
    }
    public int getCritDamage(){
        return critDamage;
    }
    public void setCritDamage(int valueToSet){
        this.critDamage = valueToSet;
    }
    //calculating if a hit is critical or not (via randomly generated number)
    public boolean isAttackCrit (){
        Random random = new Random();
        int randomGeneratedNumber = random.nextInt(100);
        if(randomGeneratedNumber<=this.critChance){
            return true;
        }
        else{
            return false;
        }
    }
    //attacking another entity
    public String attack(Entity entity){
        if(isAttackCrit()){
            attackCrit(entity);
            return "YOU SUSTAIN A CRITICAL HIT!";
        }
        else{
            attackNormal(entity);
            return "The " + this.getName() + " attacks you!";
        }
    }
    //attacking another entity with normal attack damage
    public void attackNormal(Entity entity){
        entity.setHealth(entity.getHealth()-this.attackDamage);
    }
    //attacking another entity with critical attack damage
    public void attackCrit(Entity entity){
        entity.setHealth(entity.getHealth()-this.critDamage);
    }
}
