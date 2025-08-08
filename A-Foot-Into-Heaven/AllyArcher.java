import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ally archer class.
 * 
 * @author Patrick Hu 
 * @version Jan 2023
 */
public class AllyArcher extends Ally
{
    public AllyArcher(String name) {
        super(name);
        moveLimit = 6;
        weapons.add("Bow");
        className = "AllyArcher";
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
