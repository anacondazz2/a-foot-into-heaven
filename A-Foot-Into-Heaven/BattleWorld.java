import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.io.*;

/**
 * Battle worlds are the main grid worlds where characters move around in between phases. Varies based on which chapter it is.
 * 
 * @author Patrick Hu, Jonathan Zhao
 * @version Jan 2023
 */
public abstract class BattleWorld extends GameWorld
{
    // DATA
    protected String phase = "player";
    protected ArrayList<Ally> allies = new ArrayList<Ally>();
    protected static ArrayList<Ally> ALLIES = new ArrayList<Ally>();
    protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    protected Selector selector = new Selector();
    protected boolean selectorAdded; // whether selector is in the world
    protected int turns = 0;
    protected int chapterNumber;
    // ENEMY PHASE
    private int i; // index used for going through each enemy during enemy phase
    protected Enemy curMovingEnemy;
    // MISC
    protected MenuWindow menuWindow;
    protected ChapterCard chapterCard;

    public BattleWorld(int width, int height, int pixelSize) {    
        super(width, height, pixelSize);
        setPaintOrder(ChapterCard.class, DialogueText.class, HoverWindow.class, Selector.class);
        state = "gameplay";
        addObject(selector, GameWorld.getX(0), GameWorld.getY(0)); 
        // CHAPTER CARD
        String s = this.getClass().getSimpleName();
        chapterNumber = Integer.parseInt(s.substring(s.length() - 1));
        if (chapterNumber != 1) {
            chapterCard = new ChapterCard("ChapterImages/Ch" + chapterNumber + ".png");
            addObject(chapterCard, getWidth() / 2, getHeight() / 2);
            state = "card";
        }
    }

    public void act() {
        checkStateAndPhase();
        checkMenu();
        checkClear();
    }

    public void checkStateAndPhase() {        
        // PHASE SWITCH
        if (phase == "player" && state == "gameplay" && getNumAlliesMoved() == allies.size()) {
            addObject(new BattlePhaseCard("EnemyPhase.png"), getWidth() / 2, getHeight() / 2);
            state = "card";
        }
        else if (phase == "enemy" && state == "gameplay" && getNumEnemiesMoved() == enemies.size()) {
            addObject(new BattlePhaseCard("PlayerPhase.png"), getWidth() / 2, getHeight() / 2);
            state = "card";
        }
        // PHASES
        if (phase == "player" && (state == "gameplay" || state == "dialogue")) {
            if (this instanceof Chapter7) {
                Soundtrack.intrusiveRevolutionary.playLoop();
                Soundtrack.stopAllExceptIntrusiveRevolutionary();
            }
            else {
                Soundtrack.farInForeignLands.playLoop();
                Soundtrack.stopAllExceptFarInForeignLands();    
            }
        }
        if (phase == "enemy" && (state == "gameplay" || state == "dialogue")) {
            Soundtrack.aSweepingFog.playLoop();
            Soundtrack.stopAllExceptASweepingFog();
        }
        // ENEMY MOVEMENT
        if (phase == "enemy" && state == "gameplay") {
            moveEnemies();
        }
        // SELECTOR
        if (state == "gameplay" && phase == "player" && !selectorAdded) {
            addSelector();
            selectorAdded = true;
        }
        else if (state != "gameplay") {
            selector.removeSelf();
            selectorAdded = false;
        }
    }

    public void checkMenu() {
        if (Greenfoot.isKeyDown("escape") && state == "gameplay") {
            menuWindow = new MenuWindow(state);
            addObject(menuWindow, getWidth() / 2, getHeight() / 2);
            state = "menu";
        }
    }

    public void checkClear() {
        if (state == "clear") {
            save();
            Greenfoot.setWorld(new Intermission("images/Intermissions/Intermission" + chapterNumber + ".png", "images/Text/Intermission" + chapterNumber + "/", chapterNumber));
        }
    }

    public void startEnemyPhase() {
        resetAllyVariables();
        phase = "enemy";
    }

    public void startPlayerPhase() {
        turns++;
        resetEnemyVariables();
        phase = "player";
    }

    /**
     * Resets all necessary Ally variables such as Ally.moved and alliesMoved after player phase is over.
     */
    public void resetAllyVariables() {
        for (Ally a : allies) {
            a.moved = false;
            a.getImage().setTransparency(255);
            a.selectedEnemy = null;
        }
    }

    public void resetEnemyVariables() {
        i = 0;
        curMovingEnemy = null;
        for (Enemy e : enemies) {
            e.moved = false;
            e.getImage().setTransparency(255);
        }
    }

    public void moveEnemies() {
        if (i == 0 && curMovingEnemy == null) {
            curMovingEnemy = enemies.get(i);
            curMovingEnemy.startMoving();   
            i++;
        }
        if (curMovingEnemy.moved && i < enemies.size()) {
            Greenfoot.delay(30);
            curMovingEnemy = enemies.get(i);
            curMovingEnemy.startMoving();    
            i++;
        }
    }

    public void removeAlly(Ally a) {
        allies.remove(a);
        map[a.r][a.c] = 0;  
        removeObject(a);
    }

    public void removeEnemy(Enemy e) {
        enemies.remove(e);
        map[e.r][e.c] = 0;
        i -= (i == 0) ? 0 : 1;
        if (e.isBoss) {
            removeObject(e.bossIcon);
        }
        removeObject(e);
    }

    public int getNumAlliesMoved() {
        int ret = 0;
        for (Ally a : allies) {
            if (a.moved) {
                ret++;
            }
        }
        return ret;
    }

    public int getNumEnemiesMoved() {
        int ret = 0;
        for (Enemy e : enemies) {
            if (e.moved) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * Saves the most recent chapter achieved with the army present at that time.
     * Only called once a chapter has been complete.
     */
    public void save() {
        int newScore = 1;
        if (this instanceof Chapter1) {
            newScore = 2;
        }
        if (this instanceof Chapter2) {
            newScore = 3;
        }
        if (this instanceof Chapter3) {
            newScore = 4;
        }
        if (this instanceof Chapter4) {
            newScore = 5;
        }
        if (this instanceof Chapter5) {
            newScore = 6;
        }
        if (this instanceof Chapter6) {
            newScore = 7;
        }

        ALLIES = Ally.getClones(allies); //  save clones to master array of allies

        if (UserInfo.isStorageAvailable()) {
            UserInfo myInfo = UserInfo.getMyInfo();
            myInfo.setScore(newScore);
            myInfo.store();  // write back to server
        }
    }

    /**
     * Replenishes all allies' hp at start of a chapter.
     */
    public void replenish() {
        for (Ally a : allies) {
            a.health = a.maxHealth; // replenish
        }
    }

    public boolean tileAvailable(int r, int c) {
        int[][] map = getMap();
        return map[r][c] == 0 || map[r][c] == 7 || map[r][c] == 8 || map[r][c] == 15 || map[r][c] == 22 || map[r][c] == 25;
    }

    public Ally findAlly(String name) {
        for (Ally a : allies) {
            if (a.name.equals(name)) {
                return a;
            }
        }
        return null;
    }

    /**
     * For testing chapters, buff() adds allies to ALLIES.
     */
    public static void buff() {
        for (int i = 0; i < 1; i++) {
            AllyArcher a = new AllyArcher("");
            a.atk = 100;
            a.moveLimit = 100;
            a.maxHealth = a.health = 66;
            ALLIES.add(a);
        }
        AllyCrusader prodeus = new AllyCrusader("Prodeus");
        prodeus.atk = 100;
        prodeus.moveLimit = 100;
        prodeus.maxHealth = prodeus.health = 66;
        prodeus.weapon = "Spear";
        ALLIES.add(prodeus);

        AllyHero hero = new AllyHero("Hero");
        hero.atk = 100;
        hero.moveLimit = 100;
        hero.maxHealth = prodeus.health = 66;
        hero.weapons.add("Fire");
        hero.weapons.add("Water");
        hero.weapons.add("Ice");
        hero.weapons.add("Sword");
        hero.weapons.add("Spear");
        hero.weapons.add("Bow");
        hero.weapon = "Sword";
        ALLIES.add(hero);
    }

    /**
     * Adds selector back into world at the position it left off at.
     */
    public void addSelector() {
        addObject(selector, GameWorld.getX(selector.c), GameWorld.getY(selector.r));
    }

    public void initializeGrid() {
        for (int r = 0; r < GRID_HEIGHT; r++) {
            for (int c = 0; c < GRID_WIDTH; c++) {
                if (map[r][c] == 0) {
                    addObject(new Cell("EnvironmentTiles/cloud.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 3) {
                    addObject(new Cell("EnvironmentTiles/mountain.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 4) {
                    addObject(new Cell("EnvironmentTiles/forest.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 5) {
                    addObject(new Cell("EnvironmentTiles/house.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 6) {
                    addObject(new Cell("EnvironmentTiles/market.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 7) {
                    addObject(new Cell("EnvironmentTiles/hill.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 8) {
                    addObject(new Cell("EnvironmentTiles/grass.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 9) {
                    addObject(new Cell("EnvironmentTiles/forestforest.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 10) {
                    addObject(new Cell("EnvironmentTiles/foresthill.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 11) {
                    addObject(new Cell("EnvironmentTiles/forestmountain.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 12) {
                    addObject(new Cell("EnvironmentTiles/foresttree.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 13) {
                    addObject(new Cell("EnvironmentTiles/foresttree1.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 14) {
                    addObject(new Cell("EnvironmentTiles/river.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 15) {
                    addObject(new Cell("EnvironmentTiles/road.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 16) {
                    addObject(new Cell("EnvironmentTiles/blacksmith.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 17) {
                    addObject(new Cell("EnvironmentTiles/wizardhut.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 18) {
                    addObject(new Cell("EnvironmentTiles/cloudtree.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 19) {
                    addObject(new Cell("EnvironmentTiles/colosseum.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 20) {
                    addObject(new Cell("EnvironmentTiles/deserthouse.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 21) {
                    addObject(new Cell("EnvironmentTiles/house1.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 22) {
                    addObject(new Cell("EnvironmentTiles/placeofslumbertile.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 23) {
                    addObject(new Cell("EnvironmentTiles/slumberwallhorizontal.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 24) {
                    addObject(new Cell("EnvironmentTiles/slumberwallvertical.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 25) {
                    addObject(new Cell("EnvironmentTiles/castletile.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 26) {
                    addObject(new Cell("EnvironmentTiles/castlewallvertical.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
                else if (map[r][c] == 27) {
                    addObject(new Cell("EnvironmentTiles/roadtree.png"), GameWorld.getX(c), GameWorld.getY(r));
                }
            }
        } 
    }

    /**
     * BattleWorld Map Legend:
     * 
     * Example Map:
     * {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
     * {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
     * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
     * 
     * 0 - cloud
     * 1 - Ally
     * 2 - Enemy
     * 3 - mountain
     * 4 - forest
     * 5 - house
     * 6 - market
     * 7 - hill
     * 8 - grass
     * 9 - forestforest
     * 10 - foresthill
     * 11 - forestmountain
     * 12 - foresttree
     * 13 - foresttree1
     * 14 - river
     * 15 - road
     * 16 - blacksmith
     * 17 - wizardhut
     * 18 - cloudtree
     * 19 - colosseum
     * 20 - deserthouse
     * 21 - house1
     * 22 - placeofslumbertile
     * 23 - slumberwallhorizontal
     * 24 - slumberwallvertical
     * 25 - castletile
     * 26 - castlewallvertical
     * 27 - roadtree
     */
}
