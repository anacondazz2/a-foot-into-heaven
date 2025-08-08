import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Menu window is only available in BattleWorld.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class MenuWindow extends Image
{
    protected Image instructionsButton = new Image("Buttons/menuInstructionsButton.png");
    protected Image controlsButton = new Image("Buttons/menuControlsButton.png");
    protected Image loadButton = new Image("Buttons/menuLoadButton.png");
    protected Image quitButton = new Image("Buttons/menuQuitButton.png");
    protected Image selector = new Image("menuSelector.png");
    protected boolean added = false;
    protected boolean onInstructions, onQuit, onControls, onLoad;
    protected String returnState;
    protected SimpleTimer timer = new SimpleTimer();
    private int actCount = 0;

    public MenuWindow(String returnState) {
        super("MenuBG.png");
        this.returnState = returnState;
        timer.mark();
    }

    public void addedToWorld(World w) {
        int startY = 320;
        int spacing = 75;
        getWorld().addObject(instructionsButton, getWorld().getWidth() / 2, startY);
        getWorld().addObject(controlsButton, getWorld().getWidth() / 2, startY + spacing);
        getWorld().addObject(loadButton, getWorld().getWidth() / 2, startY + spacing * 2);
        getWorld().addObject(quitButton, getWorld().getWidth() / 2, startY + spacing * 3);
    }

    public void act() {
        Soundtrack.lullabyOfFairies.playLoop();
        Soundtrack.pauseAllExceptLullabyOfFairies();
        BattleWorld bw = (BattleWorld)getWorld();
        if (bw.state == "menu") {
            checkHovering();
            checkClick();
            checkGoBack();   
        }
    }

    public void checkHovering() {
        if (Greenfoot.mouseMoved(instructionsButton) && !added) {
            getWorld().addObject(selector, instructionsButton.getX(), instructionsButton.getY());
            added = true;
            onInstructions = true;
            onQuit = onControls = onLoad = false;
        }
        else if (Greenfoot.mouseMoved(controlsButton) && !added) {
            getWorld().addObject(selector, controlsButton.getX(), controlsButton.getY());
            added = true;
            onControls = true;
            onInstructions = onQuit = onLoad = false;
        }
        else if (Greenfoot.mouseMoved(loadButton) && !added) {
            getWorld().addObject(selector, loadButton.getX(), loadButton.getY());
            added = true;
            onLoad = true;
            onInstructions = onQuit = onControls = false;
        }
        else if (Greenfoot.mouseMoved(quitButton) && !added) {
            getWorld().addObject(selector, quitButton.getX(), quitButton.getY());
            added = true;
            onQuit = true;
            onInstructions = onControls = onLoad = false;
        }
        if (Greenfoot.mouseMoved(null) && added && !Greenfoot.mouseMoved(selector) && !Greenfoot.mouseMoved(instructionsButton) && !Greenfoot.mouseMoved(quitButton) && !Greenfoot.mouseMoved(controlsButton) && !Greenfoot.mouseMoved(loadButton)) {
            getWorld().removeObject(selector);
            added = false;
            onInstructions = onQuit = onControls = onLoad = false;
        }
    }

    public void checkClick() {
        if (Greenfoot.mouseClicked(selector) && onInstructions) {
            Greenfoot.setWorld(new Visual("Details.png", (GameWorld)getWorld()));
        }
        if (Greenfoot.mouseClicked(selector) && onControls) {
            Greenfoot.setWorld(new Visual("Controls.png", (GameWorld)getWorld()));
        }
        if (Greenfoot.mouseClicked(selector) && onLoad) {
            if (UserInfo.isStorageAvailable()) {
                UserInfo myInfo = UserInfo.getMyInfo();
                switch (myInfo.getScore()) {
                    case 1:
                        Greenfoot.setWorld(new Chapter1());
                        break;
                    case 2:
                        Greenfoot.setWorld(new Chapter2());
                        break;
                    case 3:
                        Greenfoot.setWorld(new Chapter3());
                        break;
                    case 4:
                        Greenfoot.setWorld(new Chapter4());
                        break;
                    case 5:
                        Greenfoot.setWorld(new Chapter5());
                        break;
                    case 6:
                        Greenfoot.setWorld(new Chapter6());
                        break;
                    case 7:
                        Greenfoot.setWorld(new Chapter7());
                        break;  
                }
            }
        }
        if (Greenfoot.mouseClicked(selector) && onQuit) {
            Greenfoot.setWorld(new MainMenu());
            Soundtrack.lullabyOfFairies.stop();
        }
    }

    public void checkGoBack() {
        if (Greenfoot.isKeyDown("j") && timer.millisElapsed() > 500) {
            BattleWorld bw = (BattleWorld)getWorld();
            bw.state = returnState;
            removeSelf();    
        }
    }

    public void removeSelf() {
        getWorld().removeObject(controlsButton);
        getWorld().removeObject(loadButton);
        getWorld().removeObject(instructionsButton);
        getWorld().removeObject(quitButton);
        getWorld().removeObject(selector);
        super.removeSelf();
    }
}
