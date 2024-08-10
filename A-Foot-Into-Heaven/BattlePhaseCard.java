import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Image that displays the current battle phase, either player or enemy.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class BattlePhaseCard extends Card
{
    public BattlePhaseCard(String path) {
        super(path);
        mod = 1;
    }
    
    public void act() {
        super.act();
    }
    
    public void fadeOut() {
        int newTrans = getImage().getTransparency() - 5;
        if (newTrans <= 0) {
            BattleWorld bw = (BattleWorld)getWorld();
            bw.state = "gameplay";
            if (bw.phase == "player") {
                bw.startEnemyPhase();
            }
            else {
                bw.startPlayerPhase();
            }
            bw.removeObject(this);
        }
        if (actCount % 1 == 0 && newTrans >= 0) {
            getImage().setTransparency(newTrans);
        }
    }
}
