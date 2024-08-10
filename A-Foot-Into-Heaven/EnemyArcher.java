import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Enemy archer class.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class EnemyArcher extends Enemy
{
    public EnemyArcher(boolean isBoss) {
        super(isBoss);
        moveLimit = isBoss ? 0 : 5;    
        weapons.add("Bow");
        className = "EnemyArcher";
    }
    
    public void act() {
        super.act();
        checkOnHill();
    }
    
    public void checkOnHill() {
        BattleWorld bw = (BattleWorld)getWorld();
        int[][] map = bw.getMap();
        if (map[r][c] == 7) {
            terrainMultiplier = 1.3;
        }
        else {
            terrainMultiplier = 1;
        }
    }
}
