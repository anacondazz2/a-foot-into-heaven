import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The enemy crusader class.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class EnemyCrusader extends EnemyCavalry
{
    public EnemyCrusader(boolean isBoss) {
        super(isBoss);
        moveLimit = isBoss ? 0 : 8;
        className = "EnemyCrusader";
    }
    
    public void act() {
        super.act();
    }
}
