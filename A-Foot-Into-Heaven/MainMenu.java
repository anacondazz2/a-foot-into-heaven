import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * The initial menu that at the beginning of the game.
 * 
 * @author Jonathan Zhao
 * @version Jan 2023
 */
public class MainMenu extends GameWorld
{
    // ANIMATION
    protected StartSwordShineEffect shine = new StartSwordShineEffect();
    private SimpleTimer animationTimer = new SimpleTimer();
    protected ArrayList<GreenfootImage> frames = Images.imgs.get("hero cape");
    private int imageIndex = 0;
    // BUTTONS
    protected Image playButton = new Image("Buttons/PlayButton.png");
    protected Image albumButton = new Image("Buttons/AlbumButton.png");
    protected Image controlsButton = new Image("Buttons/ControlsButton.png");
    protected Image loadButton = new Image("Buttons/LoadButton.png");
    protected Image selector = new Image("TitleSelector.png");
    // MISC
    private boolean added = false;
    private boolean onPlay, onAlbum, onControls, onLoad;

    public MainMenu() {    
        super(1200, 800, 1);
        state = "normal";
        setBackground(frames.get(0));
        setPaintOrder(StatWindow.class);

        // Add buttons
        addObject(playButton, 150, 400);
        addObject(albumButton, 150, 480);
        addObject(controlsButton, 150, 560);
        addObject(loadButton, 150, 640);

        // Set soundtrack volume
        Soundtrack.setVolume();
    }
    
    static {
        resetSaves();
    }

    public void act() {
        Soundtrack.stopAll();
        if (state == "normal") {
            checkHovering();
            checkClick();    
        }
        animateTitleScreen();
    }

    public void checkHovering() {
        if (Greenfoot.mouseMoved(playButton) && !added) {
            addObject(selector, playButton.getX(), playButton.getY());
            added = true;
            onPlay = true;
            onAlbum = onControls = onLoad = false;
        }
        else if (Greenfoot.mouseMoved(albumButton) && !added) {
            addObject(selector, albumButton.getX(), albumButton.getY());
            added = true;
            onAlbum = true;
            onPlay = onControls = onLoad = false;
        }
        else if (Greenfoot.mouseMoved(controlsButton) && !added) {
            addObject(selector, controlsButton.getX(), controlsButton.getY());
            added = true;
            onControls = true;
            onPlay = onAlbum = onLoad = false;
        }
        else if (Greenfoot.mouseMoved(loadButton) && !added) {
            addObject(selector, loadButton.getX(), loadButton.getY());
            added = true;
            onLoad = true;
            onPlay = onAlbum = onControls = false;
        }
        if (Greenfoot.mouseMoved(null) && added && !Greenfoot.mouseMoved(selector) && !Greenfoot.mouseMoved(playButton) && !Greenfoot.mouseMoved(albumButton) && !Greenfoot.mouseMoved(controlsButton) && !Greenfoot.mouseMoved(loadButton)) {
            removeObject(selector);
            added = false;
            onPlay = onAlbum = onControls = onLoad = false;
        }
    }

    public void checkClick() {
        if (Greenfoot.mouseClicked(selector) && onPlay) {
            addObject(shine, 600, 400);
        }
        if (Greenfoot.mouseClicked(selector) && onAlbum) {
            Greenfoot.setWorld(new MusicMenu());
        }
        if (Greenfoot.mouseClicked(selector) && onControls) {
            Greenfoot.setWorld(new Visual("Controls.png", this));
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
    }

    /**
     * Rewrites the most recent chapter achieved/visited. Done at the start of the program since
     * the static array of allies is likely wiped from closing the previous session, thus loading should not
     * send the player into a high chapter with an empty army. Instead their most recent chapter gets reset to 1.
     */
    public static void resetSaves() {
        if (UserInfo.isStorageAvailable()) {
            UserInfo myInfo = UserInfo.getMyInfo();
            myInfo.setScore(1);
            myInfo.store();  
        }
    }

    public void animateTitleScreen() {
        if (animationTimer.millisElapsed() < 120) {
            return;
        }
        animationTimer.mark();
        setBackground(frames.get(imageIndex));
        imageIndex = (imageIndex + 1) % frames.size();
    }
}
