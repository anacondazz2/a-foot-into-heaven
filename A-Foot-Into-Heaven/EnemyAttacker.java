import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Subclass of Attacker's that are enemies.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class EnemyAttacker extends Attacker
{    
    Enemy me;
    Ally other;
    
    public EnemyAttacker(BattleWorldCharacter me, BattleWorldCharacter other) {
        super(me, other);
        this.me = (Enemy)me;
        this.other = (Ally)other;
        path = "images/Animations/EnemyAnimations/" + className + "Animations/Attack/" + me.weapon + "/";
        critPath = "images/Animations/EnemyAnimations/" + className + "Animations/Crit/" + me.weapon + "/";
    }
    
    public void act() {
        super.act();
    }
    
    public void checkDeath() {
        if (me.health <= 0) {
            die();
            AttackAnimationWorld w = (AttackAnimationWorld)getWorld();
            w.returnWorld.removeEnemy(me);
            if (me.name.equals("The Being")) {
                Dialogue d = new Dialogue("images/Text/TheBeingDying/", "normal");
                w.addObject(d, 0, 0);
            }
            else if (me.name.equals("Prodeus")) {
                Dialogue d = new Dialogue("images/Text/ProdeusDying/", "normal");
                w.addObject(d, 0, 0);
            }
            if (w.returnWorld.enemies.size() == 0) {
                w.returnState = "clear";
            }
            other.increaseXp(me.killXp);
        }
    }
    
    public void animate() {
        super.animate();
    }
}
