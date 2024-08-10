import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Interfaces that do not involve preparing for attacking.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class NonAttackInterface extends Interface
{
    protected Ally a; // ally that the window applies to
    protected SimpleTimer timer = new SimpleTimer();
    
    public NonAttackInterface(Ally a) {
        super(a);
        this.a = a;
    }
}
