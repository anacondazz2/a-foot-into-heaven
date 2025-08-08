import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.io.*;

/**
 * Ally classes. All data and logic pertaining to Ally's in the BattleWorld are here.
 * A cloning method is included for saving the player's army after beating a chapter.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Ally extends BattleWorldCharacter
{
    protected Enemy selectedEnemy; // whether character has selected an enemy to move to
    protected int xp = 0, xpNeeded = 50, xpGained;
    protected Point backLocation; // location to go back to

    public Ally(String name) {
        this.name = name;
        crit = 15;
    }

    public void addedToWorld(World w) {
        super.addedToWorld(w);
        map[r][c] = 1;
    }

    public void act() {
        super.act();
        if (isMoving) {
            move();
            walkAnimate();
        }
        else {
            idleAnimate();
        }
    }

    /**
     * Method for starting the movement of an ally.
     */
    public void startMoving(ArrayList<Point> path) {
        i = path.size() - 1;
        isMoving = true;
        this.path = path;
        map[r][c] = 0; // clear spot
        backLocation = new Point(r, c); // save location to go back to
    }

    /**
     * Moves towards the selector's location if path was possible.
     */
    public void move() {
        if (i == -1) { // reached destination
            BattleWorld bw = ((BattleWorld)getWorld());
            isMoving = false;
            moved = true;
            map[r][c] = 1;
            getImage().setTransparency(150);
            bw.selector.selectedAlly = null;
            if (selectedEnemy != null) {
                bw.state = "decision";
                bw.addObject(new AttackDecisionWindow(this, selectedEnemy), bw.getWidth() - 250, bw.getHeight() / 2);
            }
            else {
                bw.state = "decision";
                bw.addObject(new NonAttackDecisionWindow(this), bw.getWidth() - 250, bw.getHeight() / 2);
            }

            return;
        }

        Point p = path.get(i);
        if (moveTimer.millisElapsed() > 200) {
            prevLocation = new Point(r, c);
            setLocation(GameWorld.getX(p.c), GameWorld.getY(p.r));
            updateCoords();
            checkDirection();
            i--;
            moveTimer.mark();
        }
    }

    public void increaseXp(int amount) {
        xp += amount;
        xpGained += amount;
    }

    /**
     * Calculates how many levels gained from total xp gained after a fight.
     * If leveled up, increases stats. 
     * 
     * @return String message with all information (xp gained, amount of level ups and stat increases).
     */
    public String getLevelUpMsg() {
        String msg;
        String whitespace = "     ";

        if (xp >= xpNeeded) {
            int numLevelUps = 0;
            while (true) {
                if (xp - xpNeeded < 0) {
                    break;
                }
                xp -= xpNeeded;
                numLevelUps++;
                xpNeeded += GameWorld.getRandomNumberInRange(30, 40);
            }
            level += numLevelUps;
            int increase = 1 * numLevelUps;

            int numStatIncreases = GameWorld.getRandomNumberInRange(1, potentialStats.size() + 1);
            List<String> upgradedStats = GameWorld.pickNRandom(potentialStats, numStatIncreases);
            msg = "\n" + whitespace + "XP +" + xpGained + " --> LEVEL +" + numLevelUps + whitespace + "\n";
            for (String s : upgradedStats) {
                switch (s) {
                    case "health":
                        msg += whitespace + "MAX HP +" + increase + whitespace + "\n";
                        maxHealth += increase;
                        checkHealth();
                        break;
                    case "atk":
                        msg += whitespace + "ATK +" + increase + whitespace + "\n";
                        atk += increase;
                        break;
                    case "def":
                        msg += whitespace + "DEF +" + increase + whitespace + "\n";
                        def += increase;
                        break;
                    case "ev":
                        msg += whitespace + "EV +" + increase + whitespace + "\n";
                        ev += increase;
                        break;
                    case "spd":
                        msg += whitespace + "SPD +" + increase + whitespace + "\n";
                        spd += increase;
                        break;
                }
            }
            msg += " \n";
            xpGained = 0;
        }
        else {
            msg = "\n" + whitespace + "XP +" + xpGained + whitespace + "\n \n";   
            xpGained = 0;
        }

        return msg;
    }

    public Ally getClone() {
        Ally a = null;
        switch (className) {
            case "AllyArcher":
                a = new AllyArcher(name);
                break;
            case "AllyCavalry":
                a = new AllyCavalry(name);
                break;
            case "AllyCrusader":
                a = new AllyCrusader(name);
                break;
            case "AllyFootSoldier":
                a = new AllyFootSoldier(name);
                break;
            case "AllyHero":
                a = new AllyHero(name);
                break;
            case "AllyWizard":
                a = new AllyWizard(name);
                break;
        }

        a.level = level;
        a.xp = xp;
        a.xpNeeded = xpNeeded;
        a.maxHealth = maxHealth;
        a.health = health;
        a.atk = atk;
        a.def = def;
        a.ev = ev;
        a.spd = spd;
        a.terrainMultiplier = terrainMultiplier;
        a.moveLimit = moveLimit;
        a.weapons = weapons;
        a.weapon = weapon;

        return a;
    }

    public static ArrayList<Ally> getClones(ArrayList<Ally> allies) {
        ArrayList<Ally> clones = new ArrayList<Ally>();
        for (Ally a : allies) {
            clones.add(a.getClone());
        }
        return clones;
    }
}
