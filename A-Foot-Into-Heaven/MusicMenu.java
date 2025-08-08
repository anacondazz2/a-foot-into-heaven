import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A world with music selection. All of our original music is here.
 * 
 * @author Jonathan Zhao
 * @version Jan 24
 */
public class MusicMenu extends GameWorld
{
    Image soundIcon = new Image("images/Buttons/MusicSelection/SoundIcon.png");
    Image pauseButton = new Image("images/Buttons/MusicSelection/PauseButton.png");
    Image backButton = new Image("images/Buttons/MusicSelection/BackButton.png");
    Image selector = new Image("images/Buttons/MusicSelection/MusicSelector.png");
    Image insurmountableButton = new Image("images/Buttons/MusicSelection/AnInsurmountableHindrance.png");
    Image fogButton = new Image("images/Buttons/MusicSelection/ASweepingFog.png");
    Image landsButton = new Image("images/Buttons/MusicSelection/FarInForeignLands.png");
    Image fieldButton = new Image("images/Buttons/MusicSelection/InAnUnfalteringField.png");
    Image revolutionaryButton = new Image("images/Buttons/MusicSelection/IntrusiveRevolutionary.png");
    Image fairiesButton = new Image("images/Buttons/MusicSelection/LullabyOfFairies.png");
    Image dahliaButton = new Image("images/Buttons/MusicSelection/MeadowOfDahlias.png");
    Image strollingButton = new Image("images/Buttons/MusicSelection/TheStrolling.png");
    Image visitorButton = new Image("images/Buttons/MusicSelection/AnUnwantedVisitor.png");
    
    private boolean added = false;
    private boolean clicked = false;
    private boolean isSoundIcon = false;
    private boolean onInsurmountable = false;
    private boolean onFog = false;
    private boolean onLands = false;
    private boolean onField = false;
    private boolean onRevolutionary = false;
    private boolean onFairies = false;
    private boolean onDahlia = false;
    private boolean onStrolling = false;
    private boolean onVisitor = false;
    
    public MusicMenu() {    
        super(1200, 800, 1);

        addObject(insurmountableButton, 600, 125);
        addObject(fogButton, 600, 200);
        addObject(landsButton, 600, 275);
        addObject(fieldButton, 600, 350);
        addObject(revolutionaryButton, 600, 425);
        addObject(fairiesButton, 600, 500);
        addObject(dahliaButton, 600, 575);
        addObject(strollingButton, 600, 650);
        addObject(visitorButton, 600, 725);
        addObject(pauseButton, 1145, 50);
        addObject(backButton, 1080, 50);
        setBackground("images/Buttons/MusicSelection/MusicBackground.png");
    }

    public void act() {
        checkHovering();
        checkClick();
    }

    public void stopped() {
        Soundtrack.aSweepingFog.pause();
        Soundtrack.anInsurmountableHindrance.pause();
        Soundtrack.farInForeignLands.pause();
        Soundtrack.inAnUnfalteringField.pause();
        Soundtrack.intrusiveRevolutionary.pause();
        Soundtrack.lullabyOfFairies.pause();
        Soundtrack.meadowOfDahlias.pause();
        Soundtrack.theStrolling.pause();
        Soundtrack.anUnwantedVisitor.pause();
    }

    public void checkHovering() {
        if(Greenfoot.mouseMoved(insurmountableButton) && !added) {
            addObject(selector, 600, 125);
            added = true;
            onInsurmountable = true;
        }
        else if(Greenfoot.mouseMoved(fogButton) && !added) {
            addObject(selector, 600, 200);
            added = true;
            onFog = true;
        }
        else if(Greenfoot.mouseMoved(landsButton) && !added) {
            addObject(selector, 600, 275);
            added = true;
            onLands = true;
        }
        else if(Greenfoot.mouseMoved(fieldButton) && !added) {
            addObject(selector, 600, 350);
            added = true;
            onField = true;
        }
        else if(Greenfoot.mouseMoved(revolutionaryButton) && !added) {
            addObject(selector, 600, 425);
            added = true;
            onRevolutionary = true;
        }
        else if(Greenfoot.mouseMoved(fairiesButton) && !added) {
            addObject(selector, 600, 500);
            added = true;
            onFairies = true;
        }
        else if(Greenfoot.mouseMoved(dahliaButton) && !added) {
            addObject(selector, 600, 575);
            added = true;
            onDahlia = true;
        }
        else if(Greenfoot.mouseMoved(strollingButton) && !added) {
            addObject(selector, 600, 650);
            added = true;
            onStrolling = true;
        }
        else if(Greenfoot.mouseMoved(visitorButton) && !added) {
            addObject(selector, 600, 725);
            added = true;
            onVisitor = true;
        }
        if(Greenfoot.mouseMoved(null) && added && !Greenfoot.mouseMoved(selector) && !Greenfoot.mouseMoved(insurmountableButton) && !Greenfoot.mouseMoved(fogButton) && !Greenfoot.mouseMoved(landsButton) && !Greenfoot.mouseMoved(revolutionaryButton) && !Greenfoot.mouseMoved(fairiesButton) && !Greenfoot.mouseMoved(fieldButton) && !Greenfoot.mouseMoved(dahliaButton) && !Greenfoot.mouseMoved(strollingButton) && !Greenfoot.mouseMoved(visitorButton)) {
            removeObject(selector);
            added = false;

            onInsurmountable = false;
            onFog = false;
            onLands = false;
            onField = false;
            onRevolutionary = false;
            onFairies = false;
            onDahlia = false;
            onStrolling = false;
            onVisitor = false;
        }
    }
    
    public void checkClick() {
        if(Greenfoot.mouseClicked(selector) && onInsurmountable) {
            if(isSoundIcon) {
                removeObject(soundIcon);
                isSoundIcon = false;
            }
            addObject(soundIcon, 100, 125);
            isSoundIcon = true;
            Soundtrack.stopAll();
            Soundtrack.anInsurmountableHindrance.playLoop();
        }
        if(Greenfoot.mouseClicked(selector) && onFog) {
            if(isSoundIcon) {
                removeObject(soundIcon);
                isSoundIcon = false;
            }
            addObject(soundIcon, 100, 200);
            isSoundIcon = true;
            Soundtrack.stopAll();
            Soundtrack.aSweepingFog.playLoop();
        }
        if(Greenfoot.mouseClicked(selector) && onLands) {
            if(isSoundIcon) {
                removeObject(soundIcon);
                isSoundIcon = false;
            }
            addObject(soundIcon, 100, 275);
            isSoundIcon = true;
            Soundtrack.stopAll();
            Soundtrack.farInForeignLands.playLoop();
        }
        if(Greenfoot.mouseClicked(selector) && onField) {
            if(isSoundIcon) {
                removeObject(soundIcon);
                isSoundIcon = false;
            }
            addObject(soundIcon, 100, 350);
            isSoundIcon = true;
            Soundtrack.stopAll();
            Soundtrack.inAnUnfalteringField.playLoop();
        }
        if(Greenfoot.mouseClicked(selector) && onRevolutionary) {
            if(isSoundIcon) {
                removeObject(soundIcon);
                isSoundIcon = false;
            }
            addObject(soundIcon, 100, 425);
            isSoundIcon = true;
            Soundtrack.stopAll();
            Soundtrack.intrusiveRevolutionary.playLoop();
        }
        if(Greenfoot.mouseClicked(selector) && onFairies) {
            if(isSoundIcon) {
                removeObject(soundIcon);
                isSoundIcon = false;
            }
            addObject(soundIcon, 100, 500);
            isSoundIcon = true;
            Soundtrack.stopAll();
            Soundtrack.lullabyOfFairies.playLoop();
        }
        if(Greenfoot.mouseClicked(selector) && onDahlia) {
            if(isSoundIcon) {
                removeObject(soundIcon);
                isSoundIcon = false;
            }
            addObject(soundIcon, 100, 575);
            isSoundIcon = true;
            Soundtrack.stopAll();
            Soundtrack.meadowOfDahlias.playLoop();
        }
        if(Greenfoot.mouseClicked(selector) && onStrolling) {
            if(isSoundIcon) {
                removeObject(soundIcon);
                isSoundIcon = false;
            }
            addObject(soundIcon, 100, 650);
            isSoundIcon = true;
            Soundtrack.stopAll();
            Soundtrack.theStrolling.playLoop();
        }
        if(Greenfoot.mouseClicked(selector) && onVisitor) {
            if(isSoundIcon) {
                removeObject(soundIcon);
                isSoundIcon = false;
            }
            addObject(soundIcon, 100, 725);
            isSoundIcon = true;
            Soundtrack.stopAll();
            Soundtrack.anUnwantedVisitor.playLoop();
        }
        if(Greenfoot.mouseClicked(pauseButton)) {
            Soundtrack.stopAll();
            if(isSoundIcon) {
                removeObject(soundIcon);
                isSoundIcon = false;
            }
        }
        if(Greenfoot.mouseClicked(backButton)) {
            Soundtrack.stopAll();
            Greenfoot.setWorld(new MainMenu());
        }
    }
}
