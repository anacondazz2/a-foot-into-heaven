import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The panel if you click on the ground that asks if you want to end turn.
 * 
 * @author Patrick Hu 
 * @version Jan 2023
 */
public class EndTurnWindow extends NonAttackInterface
{
    public EndTurnWindow(Ally a) {
        super(a);
        setImage("Panels/EndTurnWindow.png");
        timer.mark();
    }
    
    public void act() {
        checkUserInput();
        checkGoBack();
    }
        
    public void checkUserInput() {
        if (timer.millisElapsed() > 350 && Greenfoot.isKeyDown("k")) {
            BattleWorld bw = (BattleWorld)getWorld();
            for (Ally ally : bw.allies) {
                ally.moved = true;
                ally.getImage().setTransparency(150);
            }
            
            bw.state = "gameplay";
            bw.selector.timer2.mark();
            removeSelf();
        }
    }
    
    public void checkGoBack() {
        if (Greenfoot.isKeyDown("j")) {
            BattleWorld bw = (BattleWorld)getWorld();
            bw.state = "gameplay";
            removeSelf();
        }
    }
}
