import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Worlds with story and dialogue that occur after beating a chapter.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Intermission extends GameWorld
{
    int chapterNumber;
    Dialogue d;

    public Intermission(String path, String dialoguePath, int chapterNumber) {
        super(1200, 800, 1);
        setBackground(path);
        this.chapterNumber = chapterNumber;
        d = new Dialogue(dialoguePath, "");
        addObject(d, 0, 0);
    }

    public void act() {
        Soundtrack.theStrolling.playLoop();
        Soundtrack.pauseAllExceptTheStrolling();
        if (d.finished) {
            switch (chapterNumber) {
                case 1:
                    Greenfoot.setWorld(new Chapter2());
                    Soundtrack.theStrolling.stop();
                    break;
                case 2:
                    Greenfoot.setWorld(new Chapter3());
                    Soundtrack.theStrolling.stop();
                    break;
                case 3:
                    Greenfoot.setWorld(new Chapter4());
                    Soundtrack.theStrolling.stop();
                    break;
                case 4:
                    Greenfoot.setWorld(new Chapter5());
                    Soundtrack.theStrolling.stop();
                    break;
                case 5:
                    Greenfoot.setWorld(new Chapter6());
                    Soundtrack.theStrolling.stop();
                    break;
                case 6:
                    Greenfoot.setWorld(new Chapter7());
                    Soundtrack.theStrolling.stop();
                    break;
                case 7:
                    Greenfoot.setWorld(new VictoryWorld());
                    Soundtrack.theStrolling.stop();
                    break;
            }
        }
    }
}
