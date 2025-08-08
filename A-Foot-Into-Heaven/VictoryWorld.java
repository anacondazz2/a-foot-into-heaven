import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The victory world.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class VictoryWorld extends GameWorld
{
    protected Image mainMenuButton = new Image("Buttons/MainMenuButton.png");
    protected boolean isHovering;
    
    public VictoryWorld() {
        super(1200, 800, 1);   
        setBackground("VictoryScreen.png");
        getBackground().setTransparency(0);
        addObject(mainMenuButton, getWidth() - 90, 70);
    }
    
    public void act() {
        actCount++;
        Soundtrack.meadowOfDahlias.playLoop();
        Soundtrack.pauseAllExceptMeadowOfDahlias();
        fadeIn();
        checkHovering();
        checkClick();
    }
    
    public void fadeIn() {
        int newTrans = getBackground().getTransparency() + 2;    
        if (newTrans >= 255) {
            return;
        }
        if (actCount % 3 == 0 && newTrans <= 255) {
            getBackground().setTransparency(newTrans);
        }
    }
    
    public void checkHovering() {
        if (Greenfoot.mouseMoved(mainMenuButton) && !isHovering) {
            mainMenuButton.getImage().setTransparency(255);
            isHovering = true;
        }
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(mainMenuButton)) {
            mainMenuButton.getImage().setTransparency(190);
            isHovering = false;
        }
    }
    
    public void checkClick() {
        if (Greenfoot.mouseClicked(mainMenuButton)) {
            Greenfoot.setWorld(new MainMenu());
        }
    }
}
