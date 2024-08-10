import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class simply to show an image.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Image extends Actor
{
    public Image() {
        
    }
    
    public Image(String path) {
        setImage(path);
    }
    
    public Image(TextImage ti) {
        setImage(ti);
    }
    
    public void removeSelf() {
        if (getWorld() != null) {
            getWorld().removeObject(this);    
        }
    }
}
