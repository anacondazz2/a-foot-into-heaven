import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Manages all of the songs used in the game.
 * 
 * @author Jonathan Zhao
 * @version Jan 2023
 */
public class Soundtrack extends Actor
{
    public static GreenfootSound aSweepingFog;
    public static GreenfootSound anInsurmountableHindrance;
    public static GreenfootSound farInForeignLands;
    public static GreenfootSound inAnUnfalteringField;
    public static GreenfootSound intrusiveRevolutionary;
    public static GreenfootSound lullabyOfFairies;
    public static GreenfootSound meadowOfDahlias;
    public static GreenfootSound theStrolling;
    public static GreenfootSound anUnwantedVisitor;
    
    static {
        aSweepingFog = new GreenfootSound("sounds/A_Sweeping_Fog.mp3");
        anInsurmountableHindrance = new GreenfootSound("An_Insurmountable_Hindrance.mp3");
        farInForeignLands = new GreenfootSound("Far_In_Foreign_Lands.mp3");
        inAnUnfalteringField = new GreenfootSound("In_An_Unfaltering_Field.mp3");
        intrusiveRevolutionary = new GreenfootSound("Intrusive_Revolutionary.mp3");
        lullabyOfFairies = new GreenfootSound("Lullaby_of_Fairies.mp3");
        meadowOfDahlias = new GreenfootSound("Meadow_of_Dahlias.mp3");
        theStrolling = new GreenfootSound("The_Strolling.mp3");
        anUnwantedVisitor = new GreenfootSound("An_Unwanted_Visitor.mp3");
    }
    
    public static void setVolume() {
        aSweepingFog.setVolume(40);
        anInsurmountableHindrance.setVolume(50);
        farInForeignLands.setVolume(50);
        inAnUnfalteringField.setVolume(50);
        intrusiveRevolutionary.setVolume(50);
        lullabyOfFairies.setVolume(50);
        meadowOfDahlias.setVolume(50);
        theStrolling.setVolume(50);
        anUnwantedVisitor.setVolume(50);
    }
    
    /**
     * Stops music
     */
    public static void stopAll() {
        aSweepingFog.stop();
        anInsurmountableHindrance.stop();
        farInForeignLands.stop();
        inAnUnfalteringField.stop();
        intrusiveRevolutionary.stop();
        lullabyOfFairies.stop();
        meadowOfDahlias.stop();
        theStrolling.stop();
        anUnwantedVisitor.stop();
    }
    
    /**
     * Pauses music
     */
    public static void pauseAll() {
        aSweepingFog.pause();
        anInsurmountableHindrance.pause();
        farInForeignLands.pause();
        inAnUnfalteringField.pause();
        intrusiveRevolutionary.pause();
        lullabyOfFairies.pause();
        meadowOfDahlias.pause();
        theStrolling.pause();
        anUnwantedVisitor.pause();
    }
    
    /**
     * stops music except A Sweeping Fog
     */
    public static void stopAllExceptASweepingFog() {
        anInsurmountableHindrance.stop();
        farInForeignLands.stop();
        inAnUnfalteringField.stop();
        intrusiveRevolutionary.stop();
        lullabyOfFairies.stop();
        meadowOfDahlias.stop();
        theStrolling.stop();
        anUnwantedVisitor.stop();
    }
    
    /**
     * stops music except An Insurmountable Hindrance
     */
    public static void stopAllExceptAnInsurmountableHindrance() {
        aSweepingFog.stop();
        farInForeignLands.stop();
        inAnUnfalteringField.stop();
        intrusiveRevolutionary.stop();
        lullabyOfFairies.stop();
        meadowOfDahlias.stop();
        theStrolling.stop();
        anUnwantedVisitor.stop();
    }
    
    /**
     * stops music except Far In Foreign Lands
     */
    public static void stopAllExceptFarInForeignLands() {
        aSweepingFog.stop();
        anInsurmountableHindrance.stop();
        inAnUnfalteringField.stop();
        intrusiveRevolutionary.stop();
        lullabyOfFairies.stop();
        meadowOfDahlias.stop();
        theStrolling.stop();
        anUnwantedVisitor.stop();
    }
    
    /**
     * stops music except In An Unfaltering Field
     */
    public static void stopAllExceptInAnUnfalteringField() {
        aSweepingFog.stop();
        anInsurmountableHindrance.stop();
        intrusiveRevolutionary.stop();
        lullabyOfFairies.stop();
        meadowOfDahlias.stop();
        theStrolling.stop();
        farInForeignLands.stop();
        anUnwantedVisitor.stop();
    }
    
    /**
     * stops music except Intrusive Revolutionary
     */
    public static void stopAllExceptIntrusiveRevolutionary() {
        aSweepingFog.stop();
        anInsurmountableHindrance.stop();
        inAnUnfalteringField.stop();
        lullabyOfFairies.stop();
        meadowOfDahlias.stop();
        theStrolling.stop();
        farInForeignLands.stop();
        anUnwantedVisitor.stop();
    }
    
    /**
     * stops music except Lullaby of Fairies
     */
    public static void stopAllExceptLullabyOfFairies() {
        aSweepingFog.stop();
        anInsurmountableHindrance.stop();
        inAnUnfalteringField.stop();
        intrusiveRevolutionary.stop();
        meadowOfDahlias.stop();
        theStrolling.stop();
        farInForeignLands.stop();
        anUnwantedVisitor.stop();
    }
    
    /**
     * stops music except A Sweeping Fog
     */
    public static void stopAllExceptMeadowOfDahlias() {
        aSweepingFog.stop();
        anInsurmountableHindrance.stop();
        inAnUnfalteringField.stop();
        intrusiveRevolutionary.stop();
        lullabyOfFairies.stop();
        theStrolling.stop();
        farInForeignLands.stop();
        anUnwantedVisitor.stop();
    }
    
    /**
     * stops music except The Strolling
     */
    public static void stopAllExceptTheStrolling() {
        aSweepingFog.stop();
        anInsurmountableHindrance.stop();
        inAnUnfalteringField.stop();
        intrusiveRevolutionary.stop();
        lullabyOfFairies.stop();;
        farInForeignLands.stop();
        anUnwantedVisitor.stop();
        meadowOfDahlias.stop();
    }
    
    /**
     * stops music except An Unwanted Visitor
     */
    public static void stopAllExceptAnUnwantedVisitor() {
        aSweepingFog.stop();
        anInsurmountableHindrance.stop();
        inAnUnfalteringField.stop();
        intrusiveRevolutionary.stop();
        lullabyOfFairies.stop();;
        farInForeignLands.stop();
        meadowOfDahlias.stop();
        theStrolling.stop();
    }
    
    /**
     * pauses music except A Sweeping Fog
     */
    public static void pauseAllExceptASweepingFog() {
        anInsurmountableHindrance.pause();
        farInForeignLands.pause();
        inAnUnfalteringField.pause();
        intrusiveRevolutionary.pause();
        lullabyOfFairies.pause();
        meadowOfDahlias.pause();
        theStrolling.pause();
        anUnwantedVisitor.pause();
    }
    
    /**
     * pauses music except An Insurmountable Hindrance
     */
    public static void pauseAllExceptAnInsurmountableHindrance() {
        aSweepingFog.pause();
        farInForeignLands.pause();
        inAnUnfalteringField.pause();
        intrusiveRevolutionary.pause();
        lullabyOfFairies.pause();
        meadowOfDahlias.pause();
        theStrolling.pause();
        anUnwantedVisitor.pause();
    }
    
    /**
     * pauses music except Far In Foreign Lands
     */
    public static void pauseAllExceptFarInForeignLands() {
        aSweepingFog.pause();
        anInsurmountableHindrance.pause();
        inAnUnfalteringField.pause();
        intrusiveRevolutionary.pause();
        lullabyOfFairies.pause();
        meadowOfDahlias.pause();
        theStrolling.pause();
        anUnwantedVisitor.pause();
    }
    
    /**
     * pauses music except In An Unfaltering Field
     */
    public static void pauseAllExceptInAnUnfalteringField() {
        aSweepingFog.pause();
        anInsurmountableHindrance.pause();
        farInForeignLands.pause();
        intrusiveRevolutionary.pause();
        lullabyOfFairies.pause();
        meadowOfDahlias.pause();
        theStrolling.pause();
        anUnwantedVisitor.pause();
    }
    
    /**
     * pauses music except Intrusive Revolutionary
     */
    public static void pauseAllExceptIntrusiveRevolutionary() {
        aSweepingFog.pause();
        anInsurmountableHindrance.pause();
        farInForeignLands.pause();
        inAnUnfalteringField.pause();
        lullabyOfFairies.pause();
        meadowOfDahlias.pause();
        theStrolling.pause();
        anUnwantedVisitor.pause();
    }
    
    /**
     * pauses music except Lullaby of Fairies
     */
    public static void pauseAllExceptLullabyOfFairies() {
        aSweepingFog.pause();
        anInsurmountableHindrance.pause();
        farInForeignLands.pause();
        inAnUnfalteringField.pause();
        intrusiveRevolutionary.pause();
        meadowOfDahlias.pause();
        theStrolling.pause();
        anUnwantedVisitor.pause();
    }
    
    /**
     * pauses music except Meadow of Dahlias
     */
    public static void pauseAllExceptMeadowOfDahlias() {
        aSweepingFog.pause();
        anInsurmountableHindrance.pause();
        farInForeignLands.pause();
        inAnUnfalteringField.pause();
        intrusiveRevolutionary.pause();
        lullabyOfFairies.pause();
        theStrolling.pause();
        anUnwantedVisitor.pause();
    }
    
    /**
     * pauses music except The Strolling
     */
    public static void pauseAllExceptTheStrolling() {
        aSweepingFog.pause();
        anInsurmountableHindrance.pause();
        farInForeignLands.pause();
        inAnUnfalteringField.pause();
        intrusiveRevolutionary.pause();
        lullabyOfFairies.pause();
        meadowOfDahlias.pause();
        anUnwantedVisitor.pause();
    }
    
    /**
     * pauses music except The Strolling
     */
    public static void pauseAllExceptAnUnwantedVisitor() {
        aSweepingFog.pause();
        anInsurmountableHindrance.pause();
        farInForeignLands.pause();
        inAnUnfalteringField.pause();
        intrusiveRevolutionary.pause();
        lullabyOfFairies.pause();
        meadowOfDahlias.pause();
    }
}
