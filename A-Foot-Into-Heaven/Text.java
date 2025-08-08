import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Image of just text.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Text extends Image
{
    private Font font;
    private Color color;
    private Color bgColor;
    
    public Text(String s, Font font, Color color, Color bgColor) {
        super(new TextImage(s, font, color, bgColor));
        this.font = font;
        this.color = color;
        this.bgColor = bgColor;
    }
    
    public void setText(String s) {
        setImage(new TextImage(s, font, color, bgColor));
    }
}
