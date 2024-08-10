import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Dialogue text image. Separate class needed to specify the paint order of dialogue text images which
 * should be ontop of everything else in the world. Relevant for the text when Prodeus or the The Being is killed
 * as their dialogue must be on top of the health bars and other graphics in the AttackAnimationWorld.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class DialogueText extends Image
{
    public DialogueText(String path) {
        super(path);
    }
}
