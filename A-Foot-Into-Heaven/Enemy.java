import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * The enemy classes. Enemies can either be bosses or regular cronies that will target an ally in range if they find one
 * that provides them with a weapon advantage. Otherwise they will choose a random ally and move towards them after finding
 * the shortest path.
 * 
 * @author Patrick Hu 
 * @version Jan 2023
 */
public abstract class Enemy extends BattleWorldCharacter
{
    // DATA
    protected Ally target; // AI will determine which target enemy chooses to attack
    protected boolean willAttack; // if enemy can reach its target within its move limit, willAttack will be true
    protected int hitXp, killXp; // xp rewarded for hitting 
    protected boolean isBoss;
    // MOVEMENT
    protected int endIndex; // for movement
    // DEATH ANIMATION
    protected boolean isFlashing, flip; // if enemy about to attack, flash as an indicator
    protected int j = 0;
    protected Image bossIcon = new Image("BossIcon.png");

    public Enemy(boolean isBoss) {
        name = getName();
        this.isBoss = isBoss;
        hitXp = isBoss ? GameWorld.getRandomNumberInRange(35, 50) : GameWorld.getRandomNumberInRange(25, 30);
        killXp = isBoss ? GameWorld.getRandomNumberInRange(55, 80) : GameWorld.getRandomNumberInRange(35, 55);
        crit = isBoss ? 5 : 0;
        moveLimit = isBoss ? 0 : moveLimit;
    }

    public void addedToWorld(World w) {
        super.addedToWorld(w);
        map[r][c] = 2;
        if (isBoss) {
            w.addObject(bossIcon, GameWorld.getX(c), GameWorld.getX(r));
        }
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
        if (isFlashing) {
            flash();    
        }
    }

    public String getName() {
        String s = this.getClass().getSimpleName();
        String[] r = s.split("(?=\\p{Upper})");
        String ret = "";
        for (String ss : r) {
            if (ss.equals("Enemy")) {
                continue;
            }
            ret += ss + " ";
        }
        if (isBoss) ret += " Boss";
        return ret;
    }

    /**
     * Initiates the movement of the enemy.
     */
    public void startMoving() {
        getTargetAlly();
        if (target != null) { // only bosses can not have a target
            isMoving = true;
            i = path.size() - 1;
            if (path.size() <= moveLimit) {
                endIndex = -1;
                willAttack = true;
            }
            else {
                endIndex = path.size() - moveLimit - 1;
                willAttack = false;
            }
            prevLocation = new Point(r, c);
            if (!isBoss) map[r][c] = 0; // clear spot
        }
        else {
            moved = true;
            getImage().setTransparency(150);
        }
    }

    /**
     * Detects surrounding allies and calculates weapon advantages to determine a good target.
     */
    public void getTargetAlly() { // for now just gets a random ally
        ArrayList<Ally> allies = ((BattleWorld)getWorld()).allies;
        // try to find a weapon counter
        String counter = "";
        for (Ally a : allies) {
            switch (a.weapon) {
                case "Sword":
                    counter = "Bow";
                    break;
                case "Spear":
                    counter = "Sword";
                    break;
                case "Bow":
                    counter = "Spear";
                    break;
                case "Fire":
                    counter = "Water";
                    break;
                case "Water":
                    counter = "Ice";
                    break;
                case "Ice":
                    counter = "Fire";
                    break;
            }

            if (weapons.contains(counter)) {
                if (checkPath(a)) {
                    target = a;
                    weapon = counter;
                    return;
                }
            }
        }

        // if no counter, attack random ally in range
        for (Ally a : allies) {
            if (checkPath(a)) {
                target = a;
                weapon = weapons.get(Greenfoot.getRandomNumber(weapons.size()));
                return;
            }
        }
        // if no allies in rage, choose a random target to move towards, unless is boss
        if (isBoss) target = null;
        else {
            for (Ally a : allies) {
                checkPath(a); // calls getPath() that creates path to `a`
                if (path.size() > 0) {
                    target = a;
                    weapon = weapons.get(Greenfoot.getRandomNumber(weapons.size()));    
                }
            }
        }
        // if no enemy is blocked behind by other enemies, target will remain null
    }

    /**
     * Checks if there is a path to target ally given enemy's move limit restriction.
     * Does NOT just return whether a path of any length is possible.
     */
    public boolean checkPath(Ally target) {
        // find shortest path from Ally to selector
        map = ((GameWorld)getWorld()).getMap();
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        boolean[][] vis = new boolean[GameWorld.GRID_HEIGHT][GameWorld.GRID_WIDTH];
        Point start = new Point(r, c);
        Queue<Point> Q = new LinkedList<Point>();
        Point[][] prev = new Point[GameWorld.GRID_HEIGHT][GameWorld.GRID_WIDTH]; // keeps track of nodes in shortest path

        Q.add(start);
        vis[start.r][start.c] = true;
        boolean pathPossible = false;

        while (!Q.isEmpty()) {
            Point cur = Q.poll();
            if (cur.r == target.getR() && cur.c == target.getC()) { // if node is target ally
                getPath(start, prev[cur.r][cur.c], prev);
                pathPossible = true;
                break;
            }
            else if (map[cur.r][cur.c] == 1) { // if node is any other ally, ignore
                continue;
            }

            for (int j = 0; j < 4; j++) { // checks 4 cardinal offsets
                int nr = cur.r + dy[j], nc = cur.c + dx[j];
                BattleWorld bw = (BattleWorld)getWorld();
                if (nc >= 0 && nc < GameWorld.GRID_WIDTH && nr >= 0 && nr < GameWorld.GRID_HEIGHT && !vis[nr][nc] && (bw.tileAvailable(nr, nc) || map[nr][nc] == 1)) {
                    Q.add(new Point(nr, nc));
                    vis[nr][nc] = true;
                    prev[nr][nc] = cur;
                }
            }
        }

        return pathPossible && path.size() <= moveLimit;
    }

    /**
     * Stores the path from selectedAlly to current selector position.
     */
    public void getPath(Point start, Point end, Point[][] prev) {
        path.clear(); 
        while (end.r != start.r || end.c != start.c) {
            path.add(end);
            end = prev[end.r][end.c];
        }
    }

    public void move() {
        if (i == endIndex) {
            BattleWorld bw = ((BattleWorld)getWorld());
            isMoving = false;
            moved = true;
            map[r][c] = 2;
            getImage().setTransparency(150);
            if (willAttack) {
                ((BattleWorld)getWorld()).state = "other";
                isFlashing = true;
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

    public void flash() {
        if (j == 11) {
            BattleWorld bw = (BattleWorld)getWorld();
            Greenfoot.setWorld(new AttackAnimationWorld(bw, target, this, "enemy"));
            bw.state = "attack animation";
            isFlashing = false;
            j = 0;
        }
        if (actCount % 10 == 0) {
            if (flip) {
                getImage().setTransparency(100);    
            }
            else {
                getImage().setTransparency(255);    
            }
            flip = !flip;
            j++;
        }
    }

    public boolean canMoveTo(int r, int c) {
        return r >= 0 && r < GameWorld.GRID_HEIGHT && c >= 0 && c < GameWorld.GRID_WIDTH && map[r][c] == 0;
    }
}
