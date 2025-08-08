import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.io.File;

/**
 * Class that handles all dialogue by taking in the path to the files and adding them to the world.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Dialogue extends Actor
{
    protected ArrayList<DialogueText> dialogues = new ArrayList<DialogueText>();
    protected DialogueText curDialogue;
    protected int i = 0;
    protected SimpleTimer timer = new SimpleTimer();
    protected String returnState;
    protected int actCount = 0;
    protected boolean added;
    protected boolean finished;

    public Dialogue(String path, String returnState) {
        this.returnState = returnState;
        int size = new File(path).list().length;
        for (int i = 0; i < size; i++) {
            dialogues.add(new DialogueText(path + "Text" + i + ".png"));
        }
    }

    public void addedToWorld(World w) {
        if (w instanceof GameWorld) {
            GameWorld gw = (GameWorld)w;
            gw.state = "dialogue";
        }
        added = true;
    }

    public void act() {
        actCount++;
        displayDialogues();    
    }

    public void displayDialogues() {
        if (i == dialogues.size()) {
            finished = true;
            GameWorld gw = ((GameWorld)getWorld());
            if (gw instanceof BattleWorld) {
                BattleWorld bw = (BattleWorld)getWorld();  
                bw.selector.timer2.mark(); // so player doesn't instantly select ground after dialogue
            }
            gw.state = returnState;
            gw.removeObject(this);
            return;
        }
        curDialogue = dialogues.get(i);
        if (curDialogue.getWorld() == null) {
            getWorld().addObject(curDialogue, getWorld().getWidth() / 2, getWorld().getHeight() - 150);    
        }
        if ((Greenfoot.mouseClicked(curDialogue) || (Greenfoot.isKeyDown("k")) && timer.millisElapsed() > 200)) {
            timer.mark();
            i++;
            getWorld().removeObject(curDialogue);
        }
    }
}
