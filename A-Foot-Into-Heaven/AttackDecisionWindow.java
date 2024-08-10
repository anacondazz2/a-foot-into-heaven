import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Window that asks whether you want to attack the enemy or to wait.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class AttackDecisionWindow extends AttackInterface
{
    public AttackDecisionWindow(Ally a, Enemy e) {
        super(a, e);
        setImage("Panels/AttackDecisionWindow.png");
        timer.mark();
    }

    public void act() {
        checkUserInput();
        checkGoBack();
    }

    public void checkUserInput() {
        BattleWorld bw = (BattleWorld)getWorld();
        if (Greenfoot.isKeyDown("z")) { // attack
            getWorld().addObject(new WeaponSelectWindow(a, e), getWorld().getWidth() - 250, getWorld().getHeight() / 2);
            removeSelf();
        }
        else if (Greenfoot.isKeyDown("c")) { // wait
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
