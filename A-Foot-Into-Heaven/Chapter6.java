import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Chapter 6 world. Palace gates.
 * 
 * @author Jonathan Zhao, Patrick Hu
 * @version Jan 2023
 */
public class Chapter6 extends BattleWorld
{
    private EnemyFootSoldier e1 = new EnemyFootSoldier(false);
    private EnemyFootSoldier e2 = new EnemyFootSoldier(false);
    private EnemyArcher e3 = new EnemyArcher(false);
    private EnemyArcher e4 = new EnemyArcher(false);
    private EnemyCavalry e5 = new EnemyCavalry(false);
    private EnemyCavalry e6 = new EnemyCavalry(false);
    private EnemyWizard e7 = new EnemyWizard(false);
    private EnemyWizard e8 = new EnemyWizard(false);
    private EnemyCrusader boss = new EnemyCrusader(true);
    // DIALOGUE
    Dialogue preCh6 = new Dialogue("images/Text/PreCh6/", "gameplay"); 
    
    public Chapter6() {
        super(1200, 800, 1);
        allies = Ally.getClones(ALLIES);        
        enemies.add(e1);
        enemies.add(e2);
        enemies.add(e3);
        enemies.add(e4);
        enemies.add(e5);
        enemies.add(e6);
        enemies.add(e7);
        enemies.add(e8);
        enemies.add(boss);
        
        map = new int[][] {
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 0, 0, 26, 18, 4, 4, 4, 4, 4, 4, 4, 4},
            {3, 3, 3, 3, 3, 3, 3, 4, 18, 18, 0, 7, 0, 0, 26, 18, 4, 4, 4, 4, 4, 18, 4, 0},
            {3, 3, 3, 4, 4, 4, 4, 18, 0, 0, 0, 0, 0, 0, 26, 0, 18, 0, 18, 4, 18, 18, 0, 0},
            {3, 3, 4, 18, 18, 0, 0, 7, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 18, 18, 0, 0, 0},
            {4, 4, 18, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 26, 0, 0, 0, 0, 7, 7, 0, 0, 0},
            {18, 0, 0, 0, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 7, 0, 0, 0, 0, 0, 0},
            {18, 18, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 4, 0, 7, 0, 0, 0},
            {3, 4, 4, 18, 4, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 7, 0, 4, 7, 0, 18, 0, 0},
            {3, 3, 4, 4, 18, 18, 18, 0, 0, 7, 0, 0, 0, 0, 0, 26, 0, 18, 4, 4, 4, 18, 0, 0},
            {3, 3, 3, 3, 3, 3, 18, 4, 18, 0, 0, 7, 7, 0, 0, 26, 18, 4, 4, 4, 4, 18, 4, 0},
            {3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 18, 7, 7, 7, 0, 26, 18, 18, 4, 4, 4, 4, 18, 4}
        };
        
        initializeGrid();
        spawn();
        setupStats();
        replenish();
    }

    public void act() {
        if (turns == 0 && state == "gameplay" && !preCh6.added) {
            addObject(preCh6, 0, 0);
        }
        super.act();
    }
    
    public void spawn() {
        // ALLIES
        for (Ally a : allies) {
            while (true) {
                int r = getRandomNumberInRange(5, 11);
                int c = getRandomNumberInRange(1, 4);
                if (tileAvailable(r, c)) {
                    addObject(a, GameWorld.getX(c), GameWorld.getY(r));
                    map[r][c] = 1;
                    break;
                }
            }
        }
        // ENEMIES
        for (Enemy e : enemies) {
            if (e.isBoss) continue;
            while (true) {
                int r = getRandomNumberInRange(0, GameWorld.GRID_HEIGHT);
                int c = getRandomNumberInRange(8, GameWorld.GRID_WIDTH - 3);
                if (tileAvailable(r, c)) {
                    addObject(e, GameWorld.getX(c), GameWorld.getY(r));
                    map[r][c] = 2;
                    break;
                }
            }
        }
        
        // boss
        int r = GameWorld.GRID_HEIGHT / 2;
        int c = GameWorld.GRID_WIDTH - 1;
        addObject(boss, GameWorld.getX(c), GameWorld.getY(r));
        boss.name = "The Being";
    }
    
    public void setupStats() {
        for (Enemy e : enemies) {
            if (e instanceof EnemyFootSoldier) {
                e.maxHealth = e.health = 24;
                e.atk = 7;
                e.def = 2;
                e.ev = 2;
            }
            if (e instanceof EnemyArcher) {
                e.maxHealth = e.health = 19;
                e.atk = 7;
                e.def = 2;
                e.ev = 4;
                e.spd = 4;
            }
            if (e instanceof EnemyWizard) {
                e.maxHealth = e.health = 15;
                e.atk = 8;
                e.ev = 4;
                e.spd = 3;
            }
            if (e instanceof EnemyCavalry) {
                e.maxHealth = e.health = 25;
                e.atk = 7;
                e.def = 2;
                e.ev = 3;
                e.spd = 5;
            }
            if (e.isBoss) {
                e.maxHealth = e.health = 66;
                e.atk = 25;
                e.def = 25;
                e.ev = 50;
                e.spd = 55;
            }
        }
    }
}
