import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * The beginning cutscene.
 * 
 * @author Jonathan Zhao
 * @version Jan 2023
 */
public class CutsceneWorld extends GameWorld
{
    private int actCount = 0;
    private int mod; // affects speed of animation
    protected ArrayList<GreenfootImage> frames = Images.imgs.get("cutscene");
    private int i = 0;
    private boolean animationFinished;
    private Dialogue d;

    public CutsceneWorld() {
        super(1200, 800, 1);

        d = new Dialogue("images/Text/Intro/", "normal");
        setBackground(frames.get(0));
    }

    public void act() {
        actCount++;
        if (!animationFinished) {
            animateCutscene();    
        }
        checkDialogueFinished();
    }

    public void animateCutscene() {
        if (i <= 25) {
            mod = 27;
        }
        else {
            mod = 10;
        }
        if (i == 13 || i == 25) {
            Greenfoot.delay(170);
            i++;
        }
        if (actCount % mod == 0 && i < frames.size()) {
            setBackground(frames.get(i));
            i++;
            if (i == frames.size()) {
                animationFinished = true;
                addObject(d, 0, 0);
            }
            i %= frames.size();
        }
    }

    public void checkDialogueFinished() {
        if (d.i == d.dialogues.size()) {
            Greenfoot.setWorld(new Chapter1());
        }
    }
}
