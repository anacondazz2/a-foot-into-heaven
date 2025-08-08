import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The first chapter. It is the tutorial world. Closterium.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Chapter1 extends BattleWorld
{
    // CHARACTERS
    private AllyHero hero = new AllyHero("Hero");
    private AllyCrusader prodeus = new AllyCrusader("Prodeus");
    private EnemyFootSoldier e1 = new EnemyFootSoldier(false);
    private EnemyFootSoldier e2 = new EnemyFootSoldier(false);
    private EnemyFootSoldier boss = new EnemyFootSoldier(false);
    // DIALOGUES
    Dialogue oldManInstructions = new Dialogue("images/Text/OldManInstructions/", "gameplay"); // images/ needed for Java.io.File detection
    Dialogue prodeusInstructions = new Dialogue("images/Text/ProdeusInstructions/", "gameplay");
    Dialogue postChap1 = new Dialogue("images/Text/PostChap1/", "clear");

    public Chapter1() {
        super(1200, 800, 1);
        allies.add(hero);
        enemies.add(e1);
        enemies.add(e2);
        enemies.add(boss);

        map = new int[][] {
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 0, 0},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0},
            {4, 3, 3, 3, 3, 4, 4, 3, 4, 4, 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 18, 18, 0, 0},
            {3, 3, 4, 4, 4, 4, 4, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 0, 0},
            {3, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 18, 5, 5, 5, 18, 0, 5},
            {4, 4, 4, 4, 4, 0, 4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 4, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 5, 0},
            {0, 0, 0, 4, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {4, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {4, 4, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 5, 0, 0, 0},
            {4, 4, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 4, 4, 4, 4, 4, 0, 4, 4, 0, 0, 0, 0, 0, 0, 18, 5, 5, 18, 0, 5, 5, 5, 5},
            {3, 4, 4, 4, 4, 3, 3, 4, 4, 4, 3, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 18, 18, 0},
            {3, 3, 3, 4, 3, 3, 4, 4, 4, 3, 3, 4, 4, 4, 4, 4, 0, 0, 0, 18, 0, 0, 0, 0},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0}
        };
        
        initializeGrid();
        spawn();
        setupStats();
        replenish();
    }

    public void act() {
        super.act();
        if (turns == 0 && state == "gameplay" && !oldManInstructions.added) {
            addObject(oldManInstructions, 0, 0);
        }
        if (turns == 1 && !prodeusInstructions.added && prodeus.getWorld() == null) {
            addObject(prodeusInstructions, 0, 0);
            hero.weapons.add("Sword");
            hero.weapons.add("Spear");
            hero.weapons.add("Bow");
            hero.weapon = "Sword";
            addObject(prodeus, GameWorld.getX(2), GameWorld.getY(8));
            allies.add(prodeus);
        }
    }
    
    public void spawn() {
        // ALLIES
        addObject(hero, GameWorld.getX(4), GameWorld.getY(8));
        // ENEMIES
        addObject(e1, GameWorld.getX(19), GameWorld.getY(7));
        addObject(e2, GameWorld.getX(19), GameWorld.getY(9));
        addObject(boss, GameWorld.getX(21), GameWorld.getY(8));
    }
    
    public void setupStats() {
        // ALLIES
        hero.maxHealth = hero.health = 30; 
        hero.atk = 7;
        hero.def = 3;
        hero.weapon = "Fists";
        prodeus.maxHealth = prodeus.health = 35;
        prodeus.atk = 10; 
        prodeus.def = 4;
        // ENEMIES
        for (Enemy e : enemies) {
            if (e.isBoss) { // first check if boss since boss is also a foot soldier
                e.maxHealth = e.health = 18;
                e.atk = 4;
                e.def = 2;
            }
            else if (e instanceof EnemyFootSoldier) {
                e.maxHealth = e.health = 9;
                e.atk = 3;
            }
        }
    }
    
    public void checkClear() {  
        if (state == "clear" && postChap1.getWorld() == null) {
            save();
            addObject(postChap1, 0, 0);
        }
        if (postChap1.i == postChap1.dialogues.size()) {
            Greenfoot.setWorld(new Intermission("images/Intermissions/Intermission1.png", "images/Text/Intermission1/", 1));
        }
    }
}
