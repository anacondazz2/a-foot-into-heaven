import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Shows the amount of damage you will do before you do it to help you make wiser choices.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class MightWindow extends AttackInterface
{
    public MightWindow(Ally a, Enemy e) {
        super(a, e);
        GreenfootImage bg = new GreenfootImage("Panels/WeaponIcons/SmallWeaponIconBG.png");
        // ALLY MIGHT
        int allyMight = AttackAnimationWorld.calculateDamageDealtBy(a, e, false);
        String allyS = Integer.toString(allyMight).length() == 1 ? " " + Integer.toString(allyMight) : Integer.toString(allyMight);
        Font font = new Font("Candara", true, false, 45);
        Text allyT = new Text(allyS, font, Color.WHITE, Color.BLUE.darker());
        // ENEMY MIGHT
        int enemyMight = AttackAnimationWorld.calculateDamageDealtBy(e, a, false);
        String enemyS = Integer.toString(enemyMight).length() == 1 ? " " + Integer.toString(enemyMight) : Integer.toString(enemyMight);
        Text enemyT = new Text(enemyS, font, Color.WHITE, Color.RED.darker());
    }
    
    public void act() {
        
    }
}
