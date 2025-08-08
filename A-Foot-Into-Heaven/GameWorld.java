import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Summary: The Holy Insurrection: A Foot Into Heaven
 * This game is a turn-based strategy RPG. You meet allies who all have a common goal in reclaiming the throne from the corrupt ruler who usurped the throne, The Being.
 * It takes great inspiration from the game Fire Emblem, particularly the Game Boy Advance ones.
 * 
 * Details:
 * 
 * In the game, each unit is a specific class. Each class has unique weapons they can use and a specific amount of tiles they can move each turn. The goal of the game
 * is to defeat the enemies using strategy, such as the weapon triangles. Much of this is explained in the "Details" tab.
 * 
 * Stats. Units have 5 different stats (MaxHealth, ATK, DEF, EV, SPD). Upon leveling up, a stat has a chance of increasing by 1. These stats are used in numerous 
 * calculations involving damage and hit chance. Along with stats, some terrain also provides advantages. The most notable is the hill and how it boosts archers' damage
 * and hit chance.
 * 
 * Pathfinding. Pathfinding is implemented to allow character movements. The in game selector finds the shortest path from the selected ally to the desired location
 * taking into account the ally's move limit restriction and obstacles in the map. If an enemy is selected to attack, the last node in the path is removed to make the ally
 * stop 1 space before the enemy. Enemies use the same algorithm to move towards and attack allies.
 * 
 * User experience. Care was taken to implement most user experience features such as the back key <j> which can be used in multiple scenarios such as during the weapon
 * select interface, going back to your starting location after selecting a location, and deselecting your selection. Menu can be accessed during the game through <esc> 
 * and a details and controls visual is also provided.
 * 
 * Enemy AI. Enemy AI is also implemented in an intellectual way. The enemy finds paths to optimal targets rather than attacking whatever is most convenient. They also switch
 * weapons to their best possible weapon to optimize the damage they deal to you. Bosses stay still, but they will attack if you stand next to them.
 * 
 * Load. The game saves at the end of every chapter, saving which chapter you completed. The load button will bring you back to the most recent chapter completed, with information
 * of units saved as well. This means that character stats and their status (dead or alive) are saved as well. This feature is useful if you get a game over or if you
 * accidentally killed one of your characters that you did not want to die. Note that the highest chapter is saved using Greenfoot's UserInfo class while the army stats are stored
 * in a static array of Ally's. If the code is altered or the application closed the saved army will be wiped. Saving the army by writing to files was attempted, but proved to be
 * too difficult to write an array of objects and read them from the file.
 * 
 * Pre-initialized images. Due to the high quantity of frames and animation included in the game, Greenfoot will allocate a very large amount of heap space and RAM. Although there
 * is little way around this issue, an Images class was created which initialized all frames at the beginning of the program. Frames involved in a fight animation are initalized as
 * needed, and not created and destroyed repretively as the frames are accessed through a static hashmap.
 * 
 * Death. If the main character or the character Prodeus dies, there will be a game over. This will prompt you to return to main menu, where you may restart the chapter by
 * clicking the Load button. However, if any of your other units fall in battle, they will be considered critically injured. This means that they will not move on to
 * following chapters, as if they had actually died.
 * 
 * Menu. Apart from the main menu, you can open a menu during your turn of the game by pressing the escape key. In the menu, you can look at controls, further details of the
 * game, or you can quit/load the game. If you want to exit the menu and return to the game, press the j key, as it represents the back button. It should be noted that on
 * all menus, including the main menu, the game menu, and the music menu (album), only the mouse can click on buttons. Otherwise, the game is played using WASD to move, k
 * to select, j to deselect, and u(when hovering over the target character) to check stats of characters.
 * 
 * Album. Our game is completely original. Everything you see and hear is created by us. To highlight this, there is an album where you can listen to any of our original tracks.
 * The album is on the main menu.
 * 
 * 
 * Credits:
 * Art and animations - Jonathan Zhao
 * Music - Jonathan Zhao
 * Story - Jonathan Zhao
 * TextImage class - Danpost
 * 
 * 
 * @author Jonathan Zhao, Patrick Hu 
 * @version Jan 24 2023
 */
public class GameWorld extends World
{
    protected int width, height;
    protected static int BLOCK_SIZE;
    protected static int GRID_WIDTH, GRID_HEIGHT;
    protected static int X_OFFSET, Y_OFFSET;
    protected int[][] map;
    protected String state;
    protected int actCount = 0;

    public GameWorld(int width, int height, int pixelSize) {    
        super(width, height, pixelSize);
        width = 1200;
        height = 800;
        BLOCK_SIZE = 50;
        GRID_WIDTH = width / BLOCK_SIZE; 
        GRID_HEIGHT = height / BLOCK_SIZE; 
        X_OFFSET = BLOCK_SIZE / 2;
        Y_OFFSET = BLOCK_SIZE / 2;
        
        setPaintOrder(DialogueText.class);
    }

    /**
     * Returns Greenfoot world's X coordinate given a cell number along the x-axis.
     */
    public static int getX(int cellNumber){
        return (cellNumber * BLOCK_SIZE) + X_OFFSET;
    }

    /**
     * Returns the cell number along the x-axis given an X coordinate.
     */
    public static int getXCell(int coordinate){
        if ((coordinate - X_OFFSET) % BLOCK_SIZE == 0) {
            return (coordinate - X_OFFSET) / BLOCK_SIZE;
        }
        else return (coordinate - X_OFFSET) % BLOCK_SIZE;
    }

    /**
     * Returns Greenfoot world's Y coordinate given a cell number along the y-axis.
     */
    public static int getY(int cellNumber){
        return (cellNumber * BLOCK_SIZE) + Y_OFFSET;
    }

    /**
     * Returns the cell number along the y-axis given an Y coordinate.
     */
    public static int getYCell(int coordinate){
        if ((coordinate - Y_OFFSET) % BLOCK_SIZE == 0) {
            return (coordinate - Y_OFFSET) / BLOCK_SIZE;
        }
        else return (coordinate - Y_OFFSET) % BLOCK_SIZE;
    }

    public int[][] getMap() {
        return map;
    }

    /**
     * Gets random number from start to end exclusive.
     */
    public static int getRandomNumberInRange(int start, int end) {
        int x = Greenfoot.getRandomNumber(end - start);
        return start + x;
    }

    /**
     * Returns the weapon multiplier for weapon1 given weapon1 vs weapon2.
     * 
     * @param w1 First weapon
     * @param w2 Second weapon
     */
    public static double getWeaponMultiplier(String w1, String w2) {
        if ((w1 == "Sword" && w2 == "Bow") || (w1 == "Bow" && w2 == "Spear") || (w1 == "Spear" && w2 == "Sword") || (w1 == "Fire" && w2 == "Ice") || (w1 == "Ice" && w2 == "Water") || (w1 == "Water" && w2 == "Fire")) {
            return 1.3;
        }
        else if ((w1 == "Bow" && w2 == "Sword") || (w1 == "Spear" && w2 == "Bow") || (w1 == "Sword" && w2 == "Spear") || (w1 == "Ice" && w2 == "Fire") || (w1 == "Water" && w2 == "Ice") || (w1 == "Fire" && w2 == "Water")) {
            return 0.7;   
        }
        else return 1;
    }
    
    /**
     * Picks n random items from a list and returns them.
     */
    public static List<String> pickNRandom(List<String> lst, int n) {
        List<String> copy = new ArrayList<String>(lst);
        Collections.shuffle(copy);
        return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
    }

    public void stopped() {
        Soundtrack.pauseAll();
    }
}
