import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Card is an introduction or visual that covers most of the screen and
 * notifies the player of a change in the game state or setting.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public abstract class Card extends Actor
{
    protected int actCount = 0;
    protected boolean isFadingIn = true;
    protected int mod = 1;
    
    /**
     * Creates a card of an image.
     */
    public Card(String path) {
        setImage(path);
        getImage().setTransparency(0);
    }
    
    public Card(String path, int mod) {
        setImage(path);
        this.mod = mod;
    }
    
    /**
     * Creates a card of just text.
     */
    public Card(TextImage ti) {
        setImage(ti);
    }
    
    public Card(TextImage ti, int mod) {
        setImage(ti);
        this.mod = mod;
    }
    
    public void act() {
        actCount++;
        if (isFadingIn) {
            fadeIn();
        }
        else {
            fadeOut();
        }
    }    
    
    public void fadeIn() {
        int newTrans = getImage().getTransparency() + 4;    
        if (newTrans >= 255) {
            isFadingIn = false;
        }
        if (actCount % mod == 0 && newTrans <= 255) {
            getImage().setTransparency(newTrans);
        }
    }
    
    public void fadeOut() {
        int newTrans = getImage().getTransparency() - 4;
        if (newTrans <= 0) {
            getWorld().removeObject(this);
        }
        if (actCount % mod == 0 && newTrans >= 0) {
            getImage().setTransparency(newTrans);
        }
    }
}
