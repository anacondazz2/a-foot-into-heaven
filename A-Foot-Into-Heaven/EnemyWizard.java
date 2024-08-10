import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The enemy wizard class.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class EnemyWizard extends Enemy
{
    public EnemyWizard(boolean isBoss) {
        super(isBoss);
        moveLimit = isBoss ? 0 : 6;
        weapons.add("Fire");
        weapons.add("Water");
        weapons.add("Ice");
        className = "EnemyWizard";
    }
    
    public void act() {
        super.act();
    }
}
