import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.io.File;

/**
 * Class used to store frames for the attack animation in a hashmap so as to not constantly
 * create new GreenfootImages of the same frames.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class Images extends Actor
{
    protected static final Hashtable<String, ArrayList<GreenfootImage>> imgs = new Hashtable<String, ArrayList<GreenfootImage>>();
    protected static final Hashtable<String, GreenfootImage> imgs2 = new Hashtable<String, GreenfootImage>();
    
    static {
        initMiscImgs();
        initDmgImgs();
        initCutsceneImgs();
        initMainMenuImgs();
    }

    public static ArrayList<GreenfootImage> getFrames(String path, boolean willCrit, boolean isAlly) {
        if (imgs.containsKey(path)) {
            return imgs.get(path);
        }
        ArrayList<GreenfootImage> frames = new ArrayList<GreenfootImage>();
        int numFrames = new File(path).list().length;
        for (int i = 0; i < numFrames; i++) {
            String zeroes = i < 10 ? "00" : "0";
            frames.add(new GreenfootImage(path + (willCrit ? "Crit" : "Attack") + zeroes + i + ".png"));
            if (!isAlly) {
                frames.get(i).mirrorHorizontally();
            }
        }
        imgs.put(path, frames);
        return frames;
    }
    
    public static void initMiscImgs() {
        imgs2.put("attack animation world bg", new GreenfootImage("BattleBackground.png"));
    }
    
    public static void initDmgImgs() {
        // ALLY DMG INDICATORS
        ArrayList<GreenfootImage> frames = new ArrayList<GreenfootImage>();
        for (int i = 0; i < 3; i++) {
            frames.add(new GreenfootImage("Animations/DamageAnimations/Attack/Attack0" + i + ".png"));
        }
        imgs.put("ally attack dmg indicators", frames);
        
        frames = new ArrayList<GreenfootImage>();
        for (int i = 0; i < 3; i++) {
            frames.add(new GreenfootImage("Animations/DamageAnimations/Crit/Crit0" + i + ".png"));
        }
        imgs.put("ally crit dmg indicators", frames);
        
        // ENEMY DMG INDICATORS
        frames = new ArrayList<GreenfootImage>();
        for (int i = 0; i < 3; i++) {
            frames.add(new GreenfootImage("Animations/DamageAnimations/Attack/Attack0" + i + ".png"));
            frames.get(i).mirrorHorizontally();
        }
        imgs.put("enemy attack dmg indicators", frames);
        
        frames = new ArrayList<GreenfootImage>();
        for (int i = 0; i < 3; i++) {
            frames.add(new GreenfootImage("Animations/DamageAnimations/Crit/Crit0" + i + ".png"));
            frames.get(i).mirrorHorizontally();
        }
        imgs.put("enemy crit dmg indicators", frames);
    }
    
    public static void initCutsceneImgs() {
        ArrayList<GreenfootImage> frames = new ArrayList<GreenfootImage>();
        for (int i = 0; i < 50; i++) {
            String zeroes = i >= 10 ? "0" : "00";
            frames.add(new GreenfootImage("images/Cutscenes/Cutscene" + zeroes + i + ".png"));
        }
        imgs.put("cutscene", frames);
    }
    
    public static void initMainMenuImgs() {
        // START SWORD SHINE
        ArrayList<GreenfootImage> frames = new ArrayList<GreenfootImage>();
        for (int i = 0; i < 13; i++) {
            String zeroes = i >= 10 ? "0" : "00";
            frames.add(new GreenfootImage("images/Animations/ShineAnimations/Shine" + zeroes + i + ".png"));
        }
        imgs.put("start sword shine", frames);
        // HERO CAPE
        frames = new ArrayList<GreenfootImage>();
        for (int i = 0; i < 5; i++) {
            frames.add(new GreenfootImage("images/Animations/TitleScreenAnimations/TitleScreen0" + i + ".png"));
        }
        imgs.put("hero cape", frames);
    }
}
