import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * The selector in BattleWorlds. Implements path finding to find shortest path from selected ally to the desired location of the selector.
 * Is only present when the game world state is "gameplay". Deals with hovering over allies and enemies, displaying their stats and initiating 
 * ally movement upon selection.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Selector extends Actor
{
    // DATA
    protected int r, c;
    protected boolean active; // whether the selector has selected an ally
    protected Ally selectedAlly;
    // PATH FINDING
    protected ArrayList<Point> path = new ArrayList<Point>();
    protected boolean pathPossible;   
    protected int[][] map;
    // ANIMATION
    protected SimpleTimer animationTimer = new SimpleTimer();
    protected GreenfootImage[] selectionFrames = new GreenfootImage[2];
    protected Highlight selectionIndicator = new Highlight("GreenHighlight.png");
    protected int imageIndex = 0;    
    // MISC
    protected SimpleTimer moveTimer = new SimpleTimer();
    protected SimpleTimer timer = new SimpleTimer(), timer2 = new SimpleTimer();
    protected HoverWindow hoverWindow;
    protected boolean hoverAdded;
    protected BattleWorldCharacter curHovering;

    public Selector() {        
        for(int i = 0; i < 2; i++) {
            selectionFrames[i] = new GreenfootImage("images/Animations/Selector/Selector0" + i + ".png");
        }
        setImage("images/Animations/Selector/Selector00.png");        
        timer2.mark();
    }

    public void addedToWorld(World w) {
        r = GameWorld.getYCell(getY());
        c = GameWorld.getXCell(getX());
    }

    public void act() {
        checkMovement();
        checkSelect();    
        checkDeselect();
        checkConfirmMove();
        checkHovering();
        animateSelector();
    }

    /**
     * Checks for input (WASD) from the user to move the selector.
     */
    public void checkMovement() {
        if (moveTimer.millisElapsed() > 65) {
            if (Greenfoot.isKeyDown("w") && canMoveTo(r - 1, c)) {
                r--;
                setLocation(GameWorld.getX(c), GameWorld.getY(r));
                if (active) {
                    removeHighlight();
                    checkPath();    
                }
            }
            else if (Greenfoot.isKeyDown("a") && canMoveTo(r, c - 1)) {
                c--;
                setLocation(GameWorld.getX(c), GameWorld.getY(r));
                if (active) {
                    removeHighlight();
                    checkPath();    
                }
            }
            else if (Greenfoot.isKeyDown("s") && canMoveTo(r + 1, c)) {
                r++;
                setLocation(GameWorld.getX(c), GameWorld.getY(r));
                if (active) {
                    removeHighlight();
                    checkPath();       
                }
            }
            else if (Greenfoot.isKeyDown("d") && canMoveTo(r, c + 1)) {
                c++;
                setLocation(GameWorld.getX(c), GameWorld.getY(r));
                if (active) {
                    removeHighlight();
                    checkPath();    
                }
            }

            moveTimer.mark();
        }
    }

    public void animateSelector() {
        if (hoveringOverAlly()) {
            setImage("Selector2.png");   
        }
        else if (animationTimer.millisElapsed() > 300) {
            animationTimer.mark();
            setImage(selectionFrames[imageIndex]);
            imageIndex = (imageIndex + 1) % selectionFrames.length;
        }
    }

    /**
     * Checks the possible cases when a user triggers the selector.
     */
    public void checkSelect() {
        BattleWorld bw = (BattleWorld)getWorld();
        Ally a = (Ally)getOneObjectAtOffset(0, 0, Ally.class);
        BattleWorldCharacter bwc = (BattleWorldCharacter)getOneObjectAtOffset(0, 0, BattleWorldCharacter.class);

        if (timer2.millisElapsed() > 500 && !active && Greenfoot.isKeyDown("k") && hoveringOverAlly() && !a.moved) { // ally selected
            active = true;
            bw.addObject(selectionIndicator, GameWorld.getX(c), GameWorld.getY(r));
            selectedAlly = a;
            timer.mark();
            checkPath();
        }
        else if (timer2.millisElapsed() > 500 && !active && Greenfoot.isKeyDown("k") && !hoveringOverAlly()) { // ground selected to end turn
            bw.state = "decision";
            bw.addObject(new EndTurnWindow(selectedAlly), bw.getWidth() - 220, bw.getHeight() / 2);
        }
        else if (timer2.millisElapsed() > 500 && !active && Greenfoot.isKeyDown("u") && bwc != null) { // show stats
            bw.state = "decision";
            bw.addObject(new StatWindow(bwc, "gameplay"), bw.getWidth() / 2, bw.getHeight() / 2);
        }
    }

    /**
     * Checks if user deselected their current ally.
     */
    public void checkDeselect() {
        if (active && Greenfoot.isKeyDown("j")) {
            deselect();
        }
    }

    public void deselect() {
        active = false;
        getWorld().removeObject(selectionIndicator);
        removeHighlight();
    }

    /**
     * Checks if selector is hovering over a character, if so displays that character's information.
     */
    public void checkHovering() {
        BattleWorldCharacter bwc = (BattleWorldCharacter)getOneObjectAtOffset(0, 0, BattleWorldCharacter.class);
        if (bwc != null && hoverAdded && bwc != curHovering) {
            removeHoverWindow();
            hoverWindow = new HoverWindow(bwc);
            getWorld().addObject(hoverWindow, 160, 115);
            hoverAdded = true;
            curHovering = bwc;
        }
        else if (bwc != null && !hoverAdded) {
            removeHoverWindow();
            hoverWindow = new HoverWindow(bwc);
            getWorld().addObject(hoverWindow, 160, 115);
            hoverAdded = true;
            curHovering = bwc;
        }
        else if (bwc == null) {
            removeHoverWindow();
        }
    }

    public void removeHoverWindow() {
        if (hoverAdded && getWorld() != null) {
            getWorld().removeObject(hoverWindow);
            hoverAdded = false;    
        }
    }

    public void removeSelf() {
        removeHoverWindow();
        if (getWorld() != null) {
            getWorld().removeObject(this);    
        }
    }

    /**
     * Checks if there is a possible path from the selected ally to current selector(cursor) location.
     * If yes, highlights the shortest path.
     */
    public void checkPath() {   
        // find shortest path from Ally to selector
        map = ((GameWorld)getWorld()).getMap();
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        boolean[][] vis = new boolean[GameWorld.GRID_HEIGHT][GameWorld.GRID_WIDTH];
        Point start = new Point(selectedAlly.getR(), selectedAlly.getC());
        Queue<Point> Q = new LinkedList<Point>();
        Point[][] prev = new Point[GameWorld.GRID_HEIGHT][GameWorld.GRID_WIDTH]; // keeps track of nodes in shortest path

        Q.add(start);
        vis[start.r][start.c] = true;
        pathPossible = false;

        while (!Q.isEmpty()) {
            Point cur = Q.poll();
            if (cur.r == r && cur.c == c && map[r][c] == 2) { // if node is enemy and selector is on one (user wishes to attack)
                if (!checkProdeusVsTheBeing()) break;
                // get path from ally to just 1 tile before the enemy
                getPath(start, prev[cur.r][cur.c], prev);
                if (path.size() <= selectedAlly.moveLimit) {
                    highlightPath();
                    getWorld().addObject(new Highlight("RedHighlight.png"), GameWorld.getX(c), GameWorld.getY(r));   
                    
                    pathPossible = true;
                }
                break;
            }
            else if (map[cur.r][cur.c] == 2) { // if node is enemy but selector is not on one, ignore (path should not include enemies)
                continue;
            }
            else if (cur.r == r && cur.c == c /*!(cur.r == start.r && cur.c == start.c)*/) { // if destination is empty tile
                getPath(start, cur, prev);
                if (path.size() <= selectedAlly.moveLimit) {
                    highlightPath();
                    pathPossible = true;
                }
                break;
            }

            for (int j = 0; j < 4; j++) { // checks 4 cardinal offsets
                int nr = cur.r + dy[j], nc = cur.c + dx[j];
                BattleWorld bw = (BattleWorld)getWorld();
                if (nc >= 0 && nc < GameWorld.GRID_WIDTH && nr >= 0 && nr < GameWorld.GRID_HEIGHT && !vis[nr][nc] && (bw.tileAvailable(nr, nc) || map[nr][nc] == 2)) {
                    // add enemy cells into the queue but ignore them when polled unless selector is currently on an enemy
                    Q.add(new Point(nr, nc));
                    vis[nr][nc] = true;
                    prev[nr][nc] = cur;
                }
            }
        }
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

    public void highlightPath() {
        for (Point coord : path) {
            getWorld().addObject(new Highlight("BlueHighlight.png"), GameWorld.getX(coord.c), GameWorld.getY(coord.r));   
        }
    }

    public void removeHighlight() {
        // remove all BlueHighlight's from the world
        List<Highlight> l = getWorld().getObjects(Highlight.class);
        for (Highlight h : l) {
            if (h != selectionIndicator) {
                getWorld().removeObject(h);    
            }
        }
    }

    /**
     * Checks if user has confirmed his location to move an Ally to.
     */
    public void checkConfirmMove() {
        if (timer.millisElapsed() > 500 && active && Greenfoot.isKeyDown("k") && pathPossible) {
            if (map[r][c] == 2) {
                selectedAlly.selectedEnemy = (Enemy)getOneObjectAtOffset(0, 0, Enemy.class);
            }
            selectedAlly.startMoving(path);
            deselect();
            timer2.mark();
            BattleWorld bw = (BattleWorld)getWorld();
            bw.state = "character moving";
        }
    }

    public boolean hoveringOverAlly() {
        return (Ally)getOneObjectAtOffset(0, 0, Ally.class) != null;
    }

    public boolean canMoveTo(int r, int c) {
        return r >= 0 && r < GameWorld.GRID_HEIGHT && c >= 0 && c < GameWorld.GRID_WIDTH;
    }
    
    public boolean checkProdeusVsTheBeing() {
        Enemy e = (Enemy)getOneObjectAtOffset(0, 0, Enemy.class);
        GameWorld gw = (GameWorld)getWorld();
        if (gw instanceof Chapter6 && e.name.equals("The Being") && !selectedAlly.name.equals("Prodeus")) {
            return false;
        }
        return true;
    }
}
