import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Subclass of Attacker's that are allies.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class AllyAttacker extends Attacker
{    
    Ally me;
    Enemy other;

    public AllyAttacker(BattleWorldCharacter me, BattleWorldCharacter other) {
        super(me, other);
        this.me = (Ally)me;
        this.other = (Enemy)other;
        path = "images/Animations/AllyAnimations/" + className + "Animations/Attack/" + me.weapon + "/";
        critPath = "images/Animations/AllyAnimations/" + className + "Animations/Crit/" + me.weapon + "/";
    }

    public void act() {
        super.act();
    }

    public void checkDeath() {
        if (me.health <= 0) {
            die();
            AttackAnimationWorld w = (AttackAnimationWorld)getWorld();
            if (me.name.equals("Prodeus") && w.returnWorld instanceof Chapter6) {
                w.returnWorld.save();
                Greenfoot.setWorld(new Intermission("Intermissions/Intermission6.png", "images/text/Intermission6/", 6));
            }
            else if (me.name.equals("Hero") || me.name.equals("Prodeus")) {
                w.returnState = "lost";
            }
            else w.returnWorld.removeAlly(me);
        }
    }

    public void animate() {
        super.animate();
    }
}
