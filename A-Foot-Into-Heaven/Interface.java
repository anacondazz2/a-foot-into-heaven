import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Interface that has to do with unit actions.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public abstract class Interface extends Image
{
    protected Ally a;
    protected BattleWorldCharacter bwc;
    protected SimpleTimer timer = new SimpleTimer(); // add a buffer before user can choose to go back and prevents going back twice in a row
    
    public Interface(Ally a) {
        super();
        this.a = a;
    }
    
    public Interface(BattleWorldCharacter bwc) {
        super();
        this.bwc = bwc;
    }
}
