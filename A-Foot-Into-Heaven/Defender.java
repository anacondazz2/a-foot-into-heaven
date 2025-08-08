import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Subclass of AttackAnimationActor that is a defender. Defenders, like attackers, have specific logic to 
 * their animation since they always strike after the attacker. Therefore their animate() method is 
 * exclusive to them and not all AttackAnimationActor's.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public abstract class Defender extends AttackAnimationActor
{
    public Defender(BattleWorldCharacter me, BattleWorldCharacter other) {
        super(me, other);
    }

    public void addedToWorld(World w) {
        super.addedToWorld(w);
    }

    public void act() {
        super.act();
    }

    public void animate() {
        AttackAnimationWorld w = (AttackAnimationWorld)getWorld();
        AttackAnimationActor otherActor = w.getOtherActor(this);

        if (actCount % 7 == 0) {
            // FRAME OF IMPACT
            if (i == frameOfImpact) {
                if (willHit) {
                    otherActor.cutHp();
                    dmgIndicatorIsAnimating = true;
                    getWorld().addObject(dmgIndicator, getWorld().getWidth() / 2, getWorld().getHeight() / 2);

                    if (willCrit) {
                        Font font = new Font("Candara", true, false, 150);
                        TextCard t = new TextCard("Crit!", font, Color.WHITE, null, 5);
                        getWorld().addObject(t, getWorld().getWidth() / 2, 150);
                    }

                    if (me instanceof Ally) {
                        ((Ally)me).increaseXp(((Enemy)other).hitXp);
                    }
                }
                else {
                    TextCard t = new TextCard("Miss!", font, Color.WHITE, null, 5);
                    getWorld().addObject(t, getWorld().getWidth() / 2, 100);
                }
            }

            // ATTACK ANIMATION
            if (isAnimating) {
                setImage(frames.get(i));
                i++;
                if (i == frames.size()) {
                    isAnimating = false;
                    if (!otherActor.isDying) {
                        otherActor.finished = true;
                    }
                }
                else i %= frames.size(); 
            }

            // DMG INDICATOR
            if (dmgIndicatorIsAnimating) {
                dmgIndicator.setImage(dmgIndicators.get(damage_i));
                damage_i++;
                if (damage_i == dmgIndicators.size()) {
                    dmgIndicatorIsAnimating = false;
                    getWorld().removeObject(dmgIndicator);
                }
                else damage_i %= dmgIndicators.size();
            }

            // SET FINISHED
            // defender must be alive if animate() is called
            // defender always finishes regardless of whether killed or didn't kill attacker
            if (!isAnimating && !dmgIndicatorIsAnimating) {
                finished = true;
            }
        }
    }
}
