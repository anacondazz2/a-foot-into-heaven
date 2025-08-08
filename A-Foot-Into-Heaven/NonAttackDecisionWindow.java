import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The window when you do not move onto an enemy.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class NonAttackDecisionWindow extends NonAttackInterface
{
    public NonAttackDecisionWindow(Ally a) {
        super(a);
        setImage("Panels/NonAttackDecisionWindow.png");
        timer.mark();
    }
    
    public void act() {
        checkUserInput();
        checkGoBack();
    }
    
    public void checkUserInput() {
        BattleWorld bw = (BattleWorld)getWorld();
        if (Greenfoot.isKeyDown("c")) { // wait
            bw.state = "gameplay";
            removeSelf();
        }
    }
    
    public void checkGoBack() {
        if (Greenfoot.isKeyDown("j") && timer.millisElapsed() > 500) {
            BattleWorld bw = ((BattleWorld)getWorld());
            int[][] map = bw.getMap();
            a.moved = false;
            a.selectedEnemy = null;
            a.getImage().setTransparency(255);
            map[a.r][a.c] = 0; // clear spot
            map[a.backLocation.r][a.backLocation.c] = 1;
            a.setLocation(GameWorld.getX(a.backLocation.c), GameWorld.getY(a.backLocation.r));
            bw.state = "gameplay";
            removeSelf();
        }
    }
}
