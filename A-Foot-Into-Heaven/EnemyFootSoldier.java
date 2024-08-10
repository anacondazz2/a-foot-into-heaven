import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Enemy Foot Soldier class.
 * 
 * @author Patrick Hu 
 * @version Jan 2023
 */
public class EnemyFootSoldier extends Enemy
{
    public EnemyFootSoldier(boolean isBoss) {
        super(isBoss);
        moveLimit = isBoss ? 0 : 5;
        weapons.add("Spear");
        className = "EnemyFootSoldier";
    }
    
    public void act() {
        super.act();
    }
}
