import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The world when you lose the game (Prodeus or the Hero dies).
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class GameOverWorld extends GameWorld
{
    protected Image mainMenuButton = new Image("Buttons/MainMenuButton.png");
    protected boolean isHovering;
    protected GreenfootImage bg = new GreenfootImage("DeathScreen.png");
    
    public GameOverWorld() {
        super(1200, 800, 1);
        setBackground(bg);       
        bg.setTransparency(0);
        mainMenuButton.getImage().setTransparency(190);
        addObject(mainMenuButton, 400, 300);
    }
    
    public void act() {
        actCount++;
        Soundtrack.anUnwantedVisitor.playLoop();
        Soundtrack.stopAllExceptAnUnwantedVisitor();
        fadeIn();
        checkHovering();
        checkClick();
    }
    
    public void fadeIn() {
        int newTrans = bg.getTransparency() + 15;
        if (newTrans >= 255) {
            return;
        }
        if (actCount % 5 == 0 && newTrans <= 255) {
            bg.setTransparency(newTrans);
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
