import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The blue highlight that shows movement possibilities.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Highlight extends Image
{
    public Highlight(String path) {
        super(path);
        getImage().setTransparency(100);
    }
}
