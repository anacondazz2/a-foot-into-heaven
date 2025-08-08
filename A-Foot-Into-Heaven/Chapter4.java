import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Chapter 4 world. Vasmerc.
 * 
 * @author Jonathan Zhao, Patrick Hu
 * @version Jan 2023
 */
public class Chapter4 extends BattleWorld
{
    AllyWizard venetha = new AllyWizard("Venetha");
    EnemyCavalry e1 = new EnemyCavalry(false);
    EnemyCavalry e2 = new EnemyCavalry(false);
    EnemyCavalry e3 = new EnemyCavalry(false);
    EnemyCavalry e4 = new EnemyCavalry(false);
    EnemyCavalry e5 = new EnemyCavalry(false);
    EnemyArcher e6 = new EnemyArcher(false);
    EnemyCavalry boss = new EnemyCavalry(true);

    public Chapter4() {
        super(1200, 800, 1);
        allies = Ally.getClones(ALLIES); // clone the saved copy
        allies.add(venetha);
        enemies.add(e1);
        enemies.add(e2);
        enemies.add(e3);
        enemies.add(e4);
        enemies.add(e5);
        enemies.add(e6);
        enemies.add(boss);

        map = new int[][] {
            {19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19},
            {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15},
            {15, 20, 15, 20, 15, 20, 15, 20, 15, 15, 15, 15, 15, 15, 15, 20, 15, 20, 15, 20, 15, 20, 15, 20},
            {20, 15, 20, 15, 20, 15, 20, 15, 20, 15, 15, 16, 15, 15, 20, 15, 20, 15, 20, 15, 20, 15, 20, 15},
            {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 16, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15},
            {20, 15, 20, 15, 20, 15, 20, 15, 20, 15, 15, 15, 15, 15, 20, 15, 20, 15, 20, 15, 20, 15, 20, 15},
            {15, 20, 15, 20, 15, 20, 15, 20, 15, 15, 15, 15, 15, 15, 15, 20, 15, 20, 15, 20, 15, 20, 15, 20},
            {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15},
            {27, 27, 15, 27, 15, 27, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 27, 15, 27, 15, 27},
            {27, 27, 27, 27, 15, 27, 27, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 27, 27, 15, 27, 27, 27},
            {27, 27, 27, 27, 27, 27, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 27, 27, 27, 27, 27}
        };

        initializeGrid();
        spawn();
        setupStats();
        replenish();
    }

    public void act() {
        super.act();
    }

    public void spawn() {
        // ALLIES
        for (Ally a : allies) {
            while (true) {
                int r = getRandomNumberInRange(GameWorld.GRID_HEIGHT - 4, GameWorld.GRID_HEIGHT);
                int c = getRandomNumberInRange(7, GameWorld.GRID_WIDTH - 7);
                if (tileAvailable(r, c)) {                      
                    addObject(a, GameWorld.getX(c), GameWorld.getY(r));         
                    map[r][c] = 1; // just to be safe in case the loop runs faster than Ally's addedToWorld()
                    break;
                }
            }
        }
        // ENEMIES
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (e.isBoss) continue;
            while (true) {
                int r = getRandomNumberInRange(6, 10);
                int c = i < enemies.size() / 2 ? getRandomNumberInRange(0, GameWorld.GRID_WIDTH / 2) : getRandomNumberInRange(GameWorld.GRID_WIDTH / 2, GameWorld.GRID_WIDTH);
                if (tileAvailable(r, c)) {                      
                    addObject(e, GameWorld.getX(c), GameWorld.getY(r));         
                    map[r][c] = 2; // just to be safe in case the loop runs faster than Ally's addedToWorld()
                    break;
                }
            }
        }
        // boss
        int r = 2;
        int c = GameWorld.GRID_WIDTH / 2;
        addObject(boss, GameWorld.getX(c), GameWorld.getY(r));
    }

    public void setupStats() {
        // ALLIES
        venetha.maxHealth = venetha.health = 30;
        venetha.atk = 15;
        venetha.def = 5;
        venetha.ev = 6;
        venetha.spd = 5;
        // ENEMIES
        for (Enemy e : enemies) {
            if (e instanceof EnemyArcher) {
                e.maxHealth = e.health = 18;
                e.atk = 7;
                e.def = 3;
                e.ev = 2;
            }
            if (e instanceof EnemyCavalry) {
                e.maxHealth = e.health = 22;
                e.atk = 6;
                e.def = 6;
                e.ev = 2;
                e.spd = 3;
            }
            if (e.isBoss) {
                e.maxHealth = e.health = 35;
                e.atk = 8;
                e.def = 5;
                e.ev = 4;
                e.spd = 3;
            }
        }
    }
}
