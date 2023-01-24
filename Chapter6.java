import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Chapter6 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chapter6 extends BattleWorld
{
    private EnemyFootSoldier e1 = new EnemyFootSoldier(false);
    private EnemyFootSoldier e2 = new EnemyFootSoldier(false);
    private EnemyFootSoldier e3 = new EnemyFootSoldier(false);
    private EnemyArcher e4 = new EnemyArcher(false);
    private EnemyArcher e5 = new EnemyArcher(false);
    private EnemyArcher e6 = new EnemyArcher(false);
    private EnemyCavalry e7 = new EnemyCavalry(false);
    private EnemyCavalry e8 = new EnemyCavalry(false);
    private EnemyCavalry e9 = new EnemyCavalry(false);
    private EnemyCavalry e10 = new EnemyCavalry(false);
    private EnemyWizard e11 = new EnemyWizard(false);
    private EnemyWizard e12 = new EnemyWizard(false);
    private EnemyWizard e13 = new EnemyWizard(false);
    private EnemyWizard e14 = new EnemyWizard(false);
    private EnemyCrusader boss = new EnemyCrusader(true);
    
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
        enemies.add(e9);
        enemies.add(e10);
        enemies.add(e11);
        enemies.add(e12);
        enemies.add(e13);
        enemies.add(e14);
        enemies.add(boss);
        
        map = new int[][] {
            {3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 26, 18, 4, 4, 4, 4, 4, 4, 0, 0},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 26, 18, 4, 4, 4, 4, 4, 0, 0, 0},
            {3, 3, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 26, 0, 18, 0, 18, 4, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 18, 18, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 4, 0, 0, 0, 0, 0},
            {3, 3, 3, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 26, 0, 0, 4, 0, 0, 18, 0, 0},
            {3, 3, 3, 3, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 26, 0, 18, 4, 4, 4, 18, 0, 0},
            {3, 3, 3, 3, 3, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 26, 18, 4, 4, 4, 4, 18, 4, 0},
            {3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 26, 18, 18, 4, 4, 4, 4, 18, 4}
        };
        
        initializeGrid();
        spawn();
        setupStats();
    }

    public void act() {
        super.act();
    }
    
    public void spawn() {
        // ALLIES
        for (Ally a : allies) {
            while (true) {
                int r = getRandomNumberInRange(3, 7);
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
        addObject(bossIcon, GameWorld.getX(c), GameWorld.getY(r));
    }
    
    public void setupStats() {
        for (Enemy e : enemies) {
            if (e instanceof EnemyFootSoldier) {
                e.maxHealth = e.health = 32;
                e.atk = 9;
                e.def = 5;
                e.ev = 2;
            }
            if (e instanceof EnemyArcher) {
                e.maxHealth = e.health = 24;
                e.atk = 9;
                e.ev = 4;
                e.spd = 4;
            }
            if (e instanceof EnemyWizard) {
                e.maxHealth = e.health = 22;
                e.atk = 9;
                e.ev = 4;
                e.spd = 3;
            }
            if (e instanceof EnemyCavalry) {
                e.maxHealth = e.health = 32;
                e.atk = 8;
                e.ev = 3;
                e.spd = 5;
            }
            if (e.isBoss) {
                boss.name = "The Being";
                e.maxHealth = e.health = 66;
                e.atk = 25;
                e.def = 25;
                e.ev = 10;
                e.spd = 15;
            }
        }
    }
}
