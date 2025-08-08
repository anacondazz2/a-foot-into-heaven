import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ally cavalry class.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class AllyCavalry extends Ally
{
    public AllyCavalry(String name) {
        super(name);
        moveLimit = 8;
        weapons.add("Spear");
        className = "AllyCavalry";
    }
    
    public void act() {
        super.act();
    }
}
