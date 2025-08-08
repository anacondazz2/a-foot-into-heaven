import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A window with informational text such as stats that provide the user with an
 * option to go back.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class StatWindow extends Image
{    
    String returnState;
    
    public StatWindow(BattleWorldCharacter bwc, String returnState) {
        this.returnState = returnState;
        String whitespace = "     ";
        String stats = "\n" + whitespace + bwc.name + whitespace + "\n\n";
        
        if (bwc instanceof Ally) {
            Ally a = (Ally)bwc;
            stats += whitespace + "LEVEL - " + bwc.level + whitespace + "\n";
            stats += whitespace + "XP - " + a.xp + "/" + a.xpNeeded + whitespace + "\n";
        }
        stats += whitespace + "HP - " + bwc.health + "/" + bwc.maxHealth + whitespace + "\n";
        stats += whitespace + "ATK - " + bwc.atk + whitespace + "\n";
        stats += whitespace + "DEF - " + bwc.def + whitespace + "\n";
        stats += whitespace + "EV - " + bwc.ev + whitespace + "\n";
        stats += whitespace + "SPD - " + bwc.spd + whitespace + "\n";
        stats += whitespace + "Move Limit - " + bwc.moveLimit + whitespace + "\n";
        stats += whitespace + "Current Weapon - " + (bwc.weapon == "" ? "Not yet chosen" : bwc.weapon) + whitespace + "\n \n";
        
        Font font = new Font("Candara", true, false, 45);
        Color fontColor = (bwc instanceof Ally) ? Color.WHITE : Color.WHITE;
        Color bgColor = (bwc instanceof Ally) ? Color.BLUE.darker() : Color.RED.darker();
        TextImage ti = new TextImage(stats, font, fontColor, bgColor);
        ti.setTransparency(230);
        setImage(ti);
    }
    
    public StatWindow(String s, Font font, Color textColor, Color bgColor, int trans, String returnState) {
        this.returnState = returnState;
        TextImage ti = new TextImage(s, font, textColor, bgColor);
        ti.setTransparency(trans);
        setImage(ti);
    }

    public void act() {
        checkGoBack();
    }

    public void checkGoBack() {
        if (Greenfoot.isKeyDown("j")) {
            GameWorld gw = ((GameWorld)getWorld());
            gw.state = returnState;
            removeSelf();
        }
    }
}
