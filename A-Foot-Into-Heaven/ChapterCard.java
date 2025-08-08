import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Shows which chapter it is.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class ChapterCard extends Card
{
    private SimpleTimer timer = new SimpleTimer();
    private boolean mark;

    public ChapterCard(String path) {
        super(path);
        mod = 1;
    }

    public void act() {
        super.act();
    }

    public void fadeOut() {
        if (getImage().getTransparency() >= 250 && !mark) {
            timer.mark();
            mark = true;
        }
        if (timer.millisElapsed() > 800) {
            int newTrans = getImage().getTransparency() - 5;
            if (newTrans <= 0) {
                BattleWorld bw = (BattleWorld)getWorld();
                bw.state = "gameplay";
                bw.removeObject(this);
            }
            if (actCount % 3 == 0 && newTrans >= 0) {
                getImage().setTransparency(newTrans);
            }    
        }
    }
}
