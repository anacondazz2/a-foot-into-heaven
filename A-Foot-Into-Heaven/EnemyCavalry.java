import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Enemy cavalry class.
 * 
 * @author Patrick Hu 
 * @version Jan 2023
 */
public class EnemyCavalry extends Enemy
{
    public EnemyCavalry(boolean isBoss) {
        super(isBoss);
        moveLimit = isBoss ? 0 : 8;
        weapons.add("Spear");
        className = "EnemyCavalry";
    }
    
    public void act() {
        super.act();
    }
}
