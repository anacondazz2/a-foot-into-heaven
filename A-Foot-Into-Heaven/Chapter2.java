import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Chapter 2 world. Forume.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Chapter2 extends BattleWorld
{   
    private AllyCavalry ipos = new AllyCavalry("Ipos");
    private AllyArcher effitos = new AllyArcher("Effitos");
    private EnemyFootSoldier e1 = new EnemyFootSoldier(false);
    private EnemyFootSoldier e2 = new EnemyFootSoldier(false);
    private EnemyFootSoldier e3 = new EnemyFootSoldier(false);
    private EnemyFootSoldier e4 = new EnemyFootSoldier(false);
    private EnemyArcher e5 = new EnemyArcher(false);
    private EnemyArcher e6 = new EnemyArcher(false);
    private EnemyArcher boss = new EnemyArcher(true);
    // DIALOGUES
    Dialogue preCh2 = new Dialogue("images/Text/PreCh2/", "gameplay"); 

    public Chapter2() {
        // level1 should take in array of allies but for now hardcode them
        super(1200, 800, 1);
        allies = Ally.getClones(ALLIES); // clone the saved copy
        allies.add(ipos);
        allies.add(effitos);
        enemies.add(e1);
        enemies.add(e2);
        enemies.add(e3);
        enemies.add(e4);
        enemies.add(e5);
        enemies.add(e6);
        enemies.add(boss);

        map = new int[][] {
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {3, 18, 3, 4, 18, 3, 3, 3, 4, 4, 3, 3, 3, 3, 4, 3, 18, 3, 3, 18, 3, 4, 3, 3},
            {4, 4, 0, 3, 0, 18, 3, 3, 0, 18, 4, 18, 3, 4, 0, 0, 3, 18, 3, 3, 0, 4, 4, 3},
            {4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 18, 0, 0, 0, 0, 0, 0, 4, 0, 0, 18, 0},
            {5, 5, 0, 0, 5, 5, 0, 0, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6},
            {0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0},
            {0, 5, 0, 5, 5, 5, 0, 0, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6, 0, 6},
            {4, 0, 0, 0, 0, 18, 0, 4, 0, 0, 18, 0, 18, 0, 4, 0, 0, 0, 4, 0, 0, 0, 18, 4},
            {4, 4, 0, 4, 0, 4, 3, 0, 18, 0, 4, 18, 18, 3, 4, 0, 3, 3, 4, 18, 3, 0, 4, 4, 3},
            {3, 18, 3, 4, 18, 3, 3, 18, 4, 4, 3, 3, 3, 3, 4, 3, 18, 3, 3, 18, 3, 4, 3, 3},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3}
        };
        
        initializeGrid();
        spawn();
        setupStats();
        replenish();
    }
    
    public void act() {
        if (turns == 0 && state == "gameplay" && !preCh2.added) {
            addObject(preCh2, 0, 0);
        }
        super.act();
    }
    
    public void spawn() {
        // ALLIES
        for (Ally a : allies) {
            while (true) {
                int r = getRandomNumberInRange(4, 11);
                int c = getRandomNumberInRange(1, 4);
                if (tileAvailable(r, c)) {                      
                    addObject(a, GameWorld.getX(c), GameWorld.getY(r));         
                    map[r][c] = 1; // just to be safe in case the loop runs faster than Ally's addedToWorld()
                    break;
                }
            }
        }
        // ENEMIES
        // foot soldiers
        addObject(e1, GameWorld.getX(15), GameWorld.getY(6));
        addObject(e2, GameWorld.getX(15), GameWorld.getY(7));
        addObject(e4, GameWorld.getX(15), GameWorld.getY(8));
        addObject(e3, GameWorld.getX(15), GameWorld.getY(9));
        // archers
        addObject(e5, GameWorld.getX(19), GameWorld.getY(7));
        addObject(e6, GameWorld.getX(19), GameWorld.getY(8));
        // boss
        addObject(boss, GameWorld.getX(21), GameWorld.getY(8));
    }
    
    public void setupStats() {
        // ALLIES
        Ally hero = findAlly("Hero");
        hero.weapons.add("Fire");
        hero.weapons.add("Water");
        hero.weapons.add("Ice");
        ipos.maxHealth = 28;
        ipos.atk = 8;
        ipos.def = 5;
        effitos.maxHealth = 20;
        effitos.atk = 10;
        effitos.def = 1;
        // ENEMIES
        for (Enemy e : enemies) {
            if (e instanceof EnemyFootSoldier) {
                e.maxHealth = e.health = 17;
                e.atk = 4;
                e.ev = 1;
            }
            if (e instanceof EnemyArcher) {
                e.maxHealth = e.health = 14;
                e.atk = 4;
                e.ev = 1;
                e.spd = 1;
            }
            if (e.isBoss) {
                e.maxHealth = e.health = 20;
                e.atk = 5;
                e.def = 2;
                e.ev = 2;
                e.spd = 1;
            }
        }
    }
}
