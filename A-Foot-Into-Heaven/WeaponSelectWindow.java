import greenfoot.*;  // (World, Actor, GreenfootWeaponIcon, Greenfoot and MouseInfo)
import java.util.*;

/**
 * The Weapon selection window.
 * 
 * @author Patrick Hu
 * @version Jan 2023
 */
public class WeaponSelectWindow extends AttackInterface
{
    private Image sword;
    private Image spear;
    private Image bow;
    private Image fire;
    private Image water;
    private Image ice;
    private Image bladeOfEithalon;
    private ArrayList<Image> icons = new ArrayList<Image>();

    public WeaponSelectWindow(Ally a, Enemy e) {
        super(a, e);
        setup();
    }

    public void addedToWorld(World w) {
        addWeaponIcons();
    }

    public void act() {
        checkGoBack();
        checkUserInput();
    }

    public void setup() {
        sword = new Image("Panels/WeaponIcons/Sword.png");
        spear = new Image("Panels/WeaponIcons/Spear.png");
        bow = new Image("Panels/WeaponIcons/Bow.png");
        fire = new Image("Panels/WeaponIcons/Fire.png");
        water = new Image("Panels/WeaponIcons/Water.png");
        ice = new Image("Panels/WeaponIcons/Ice.png"); 
        bladeOfEithalon = new Image("Panels/WeaponIcons/BladeOfEithalon.png"); 

        // determine ally's weapons and bg size
        if (a instanceof AllyHero) {
            setImage("Panels/WeaponIcons/BigWeaponIconBG.png");
        }
        else {
            setImage("Panels/WeaponIcons/SmallWeaponIconBG.png");
        }
        for (String weapon : a.weapons) {
            switch (weapon) {
                case "Sword":
                    icons.add(sword);
                    break;
                case "Spear":
                    icons.add(spear);
                    break;
                case "Bow":
                    icons.add(bow);
                    break;
                case "Fire":
                    icons.add(fire);
                    break;
                case "Water":
                    icons.add(water);
                    break;
                case "Ice":
                    icons.add(ice);
                    break;
                case "BladeOfEithalon":
                    icons.add(bladeOfEithalon);
                    break;
            }
        }
    }

    public void addWeaponIcons() {
        int y = getY() - getImage().getHeight() / 2 + 43;
        for (int i = 0; i < icons.size(); i++, y += 60) {
            Image icon = icons.get(i);
            getWorld().addObject(icon, getX(), y);
        }
    }

    public void checkUserInput() {
        if (Greenfoot.isKeyDown("1") && icons.contains(sword)) {
            a.weapon = "Sword";
            changeWorlds();
        }
        else if (Greenfoot.isKeyDown("2") && icons.contains(spear)) {
            a.weapon = "Spear";
            changeWorlds();
        }
        else if (Greenfoot.isKeyDown("3") && icons.contains(bow)) {
            a.weapon = "Bow";
            changeWorlds();
        }
        else if (Greenfoot.isKeyDown("4") && icons.contains(fire)) {
            a.weapon = "Fire";
            changeWorlds();
        }
        else if (Greenfoot.isKeyDown("5") && icons.contains(water)) {
            a.weapon = "Water";
            changeWorlds();
        }
        else if (Greenfoot.isKeyDown("6") && icons.contains(ice)) {
            a.weapon = "Ice";
            changeWorlds();
        }
        else if (Greenfoot.isKeyDown("7") && icons.contains(bladeOfEithalon)) {
            a.weapon = "BladeOfEithalon";
            changeWorlds();
        }
    }

    public void changeWorlds() {
        removeWeaponIcons();
        BattleWorld bw = (BattleWorld)getWorld();
        bw.state = "attack animation";
        removeSelf();
        Greenfoot.setWorld(new AttackAnimationWorld(bw, a, e, "ally"));
    }

    public void checkGoBack() {
        if (Greenfoot.isKeyDown("j")) {
            BattleWorld bw = ((BattleWorld)getWorld());
            bw.addObject(new AttackDecisionWindow(a, e), bw.getWidth() - 250, bw.getHeight() / 2);
            removeWeaponIcons();
            removeSelf();
        }
    }

    public void removeWeaponIcons() {
        for (Image icon : icons) {
            icon.removeSelf();
        }
    }
}
