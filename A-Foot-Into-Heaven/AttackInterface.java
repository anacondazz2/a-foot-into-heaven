import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Interface that deals with when you have the option to attack.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public abstract class AttackInterface extends Interface
{
    protected Enemy e;
    
    public AttackInterface(Ally a, Enemy e) {
        super(a);
        this.e = e;
    }
}
