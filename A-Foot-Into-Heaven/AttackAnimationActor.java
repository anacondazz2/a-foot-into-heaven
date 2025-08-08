import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.io.File;

/**
 * Image of a battle world character used in AttackAnimationWorld.
 * All frames pertaining to the character such as their regular attack animation, crit animation, and respective damage indicator animations
 * are initialized here using java.io.File which find the number of images inside a folder. The character's health bar and label displaying the
 * current health are created here, along with the flashing and fading animations upon death of a character if their hp drops below 0.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public abstract class AttackAnimationActor extends Actor
{
    // DATA
    protected BattleWorldCharacter me, other;
    protected String className;
    protected boolean isFlashing, isFading, flip;
    protected boolean isAnimating, dmgIndicatorIsAnimating, isDying, finished; // whether it has finished all its attacks and animations such as dying
    protected int j = 0; // number of flashes when dead
    protected boolean willHit, willCrit;
    protected int frameOfImpact;
    protected double weaponMultiplier;
    // ANIMATION
    protected ArrayList<GreenfootImage> frames = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> dmgIndicators = new ArrayList<GreenfootImage>();
    protected Image dmgIndicator = new Image();
    protected int i = 0, damage_i = 0; // indexes for animation
    protected boolean framesInitialized = false;
    // FILE PATHS
    protected String path, critPath;
    // HEALTH BARS AND LABELS
    protected ArrayList<Image> ticks = new ArrayList<Image>();
    protected Image tick = new Image("HealthTick.png");
    protected Image allyHpBg = new Image("AllyHPbg.png");
    protected Image enemyHpBg = new Image("EnemyHPbg.png");
    protected Text hpLabel;
    // MISC
    protected int actCount = 0;
    protected Font font = new Font("Candara", true, false, 70);

    public AttackAnimationActor(BattleWorldCharacter me, BattleWorldCharacter other) {
        this.me = me;
        this.other = other;
        className = me.getClass().getSimpleName();
        weaponMultiplier = getWeaponMultiplier();
        willHit = determineWillHit();
        if (willHit) {
            willCrit = determineWillCrit();    
        }
        frameOfImpact = getFrameOfImpact();
        hpLabel = new Text(Integer.toString(me.health), font, Color.WHITE, null);
    }

    public void addedToWorld(World w) {
        initFrames();
    }

    public void act() {
        if (framesInitialized) {
            actCount++;
            if (isAnimating || dmgIndicatorIsAnimating)  {
                animate();
            }
            if (isFlashing) {
                flash();
            }
            if (isFading) {
                fade();
            }
            updateHealthBarAndLabel();
        }
    }

    public double getWeaponMultiplier() {
        return GameWorld.getWeaponMultiplier(me.weapon, other.weapon);
    }

    public boolean determineWillHit() {
        double bowMultiplier = me.weapon == "Bow" ? 0.9 : 1.0;
        int hitChance = 25 + (int)(me.spd * 2 * weaponMultiplier * me.terrainMultiplier * bowMultiplier - (other.ev * 3 * GameWorld.getWeaponMultiplier(other.weapon, me.weapon) * other.terrainMultiplier) + (me.spd - other.spd > 5 ? 70 : 90));
        return Greenfoot.getRandomNumber(100) < hitChance;
    }

    public boolean determineWillCrit() {
        return Greenfoot.getRandomNumber(100) <  me.crit;
    }

    /**
     * Initializes frames for the attack animation.
     */
    public void initFrames() {
        // ATTACK AND CRIT FRAMES
        if (willCrit) {
            frames = Images.getFrames(critPath, willCrit, me instanceof Ally);   
        }
        else {
            frames = Images.getFrames(path, willCrit, me instanceof Ally);
        }
        // DMG INDICATOR FRAMES
        String key = (me instanceof Ally ? "ally" : "enemy") + " " + (willCrit ? "crit" : "attack") + " dmg indicators";
        dmgIndicators = Images.imgs.get(key);

        setImage(frames.get(0));
        dmgIndicator.setImage(dmgIndicators.get(0));
        framesInitialized = true;
    }

    /**
     * Gets the frame where the weapon makes contact with the other character.
     */
    public int getFrameOfImpact() {
        if (className.equals("AllyHero")) {
            switch (me.weapon) {
                case "Sword":
                    return willCrit ? 23 : 5;
                case "Bow":
                    return willCrit ? 22 : 17;
                case "Spear":
                    return willCrit ? 21 : 15;
                case "Fire":
                    return willCrit ? 26 : 26;
                case "Water":
                    return willCrit ? 26 : 26;
                case "Ice":
                    return willCrit ? 25 : 26;
                case "BladeOfEithalon":
                    return willCrit ? 30 : 9;
            }
        }
        else if (className.equals("AllyFootSoldier") || className.equals("EnemyFootSoldier")) {
            return willCrit ? 21 : 16;
        }
        else if (className.equals("AllyCavalry") || className.equals("EnemyCavalry")) {
            return willCrit ? 7 : 2;
        }
        else if (className.equals("AllyCrusader") || className.equals("EnemyCrusader")) {
            return willCrit ? 7 : 2;
        }
        else if (className.equals("AllyArcher") || className.equals("EnemyArcher")) {
            return willCrit ? 6 : 17;
        }
        else if (className.equals("AllyWizard") || className.equals("EnemyWizard")) {
            return willCrit ? 12 : 12;
        }

        return -1;
    }

    public abstract void checkDeath();

    public abstract void animate();

    public void removeHealthBar() {
        for (Image tick : ticks) {
            getWorld().removeObject(tick);
        }
        ticks.clear();
    }

    public void drawHealthBarAndLabel() {
        int labelX = me instanceof Ally ? 50 : 600 + 50;
        int barX = me instanceof Ally ? 110 : 600 + 110;

        // LABEL
        hpLabel.setText(Integer.toString(me.health));
        getWorld().addObject(hpLabel, labelX, getWorld().getHeight() - allyHpBg.getImage().getHeight() / 2 + 8);

        // BAR
        int y1 = 700; // top row
        int y2 = y1 + tick.getImage().getHeight() + 8; // bottom row
        if (me.maxHealth <= 33) {
            // only display 1 row
            for (int i = 0, x = barX, y = getWorld().getHeight() - allyHpBg.getImage().getHeight() / 2; i < me.maxHealth; i++, x += tick.getImage().getWidth() - 6) {
                Image t = new Image("HealthTick.png");
                ticks.add(t);
                getWorld().addObject(t, x, y);
            }
        }
        else {            
            // top row - length 33
            for (int i = 0, x = barX; i < 33; i++, x += tick.getImage().getWidth() - 6) {
                Image t = new Image("HealthTick.png");
                ticks.add(t);
                getWorld().addObject(t, x, y1);
            }
            // bottom row - rest
            for (int i = 0, x = barX; i < me.maxHealth - 33; i++, x += tick.getImage().getWidth() - 6) {
                Image t = new Image("HealthTick.png");
                ticks.add(t);
                getWorld().addObject(t, x, y2);
            }
        }
        // make attacker missing health grey
        for (int i = me.maxHealth - 1; i >= me.health; i--) {
            ticks.get(i).setImage("GreyHealthTick.png");
        }
    }

    public void updateHealthBarAndLabel() {
        removeHealthBar();
        drawHealthBarAndLabel();
    }

    public void cutHp() {
        AttackAnimationWorld w = (AttackAnimationWorld)getWorld();
        boolean dealerWillCrit = w.getOtherActor(this).willCrit;
        int damageDealt = AttackAnimationWorld.calculateDamageDealtBy(other, me, dealerWillCrit);
        int j = (me.health - damageDealt) < 0 ? 0 : me.health - damageDealt;
        for (int i = me.health - 1; i >= j; i--) {
            ticks.get(i).setImage("GreyHealthTick.png");
        }
        me.health -= damageDealt;
        if (me.health < 0) {
            me.health = 0;
        }
        hpLabel.setText(Integer.toString(me.health));
        checkDeath();
    }

    public void die() {
        isFlashing = true;
        isDying = true;
    }

    public void flash() {
        if (j == 7) {
            isFlashing = false;
            isFading = true;
        }
        if (actCount % 10 == 0) {
            if (flip) {
                getImage().setTransparency(100);    
            }
            else {
                getImage().setTransparency(255);    
            }
            flip = !flip;
            j++;
        }
    }

    public void fade() {
        int newTrans = getImage().getTransparency() - 5;
        if (newTrans <= 0) {
            isFading = false;
            finished = true;
        }
        if (actCount % 2 == 0 && newTrans >= 0) {
            getImage().setTransparency(newTrans);
        }
    }

    /**
     * Since images are pre-loaded only once, we have to reset all frames' transparency or else a transparent
     * image may linger on the next fight.
     */
    public void resetTransparency() {
        for (GreenfootImage img : frames) {
            img.setTransparency(255);
        }
    }
}
