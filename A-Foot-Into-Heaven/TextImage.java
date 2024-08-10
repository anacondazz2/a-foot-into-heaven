import greenfoot.*;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * CLASS: TextImage (extension of GreenfootImage)<br>
 * AUTHOR: danpost (greenfoot.org username)<br>
 * DATE: August 14, 2014<br>
 * MODIFIED: August 20, 2014 (added 'fonts' array and related methods)
 * MODIFIED: August 22, 2014 (added default value changing methods)
 * MODIFIED: January 21, 2017 (fixed some constructors missing default values)
 * <br>
 * DESCRIPTION: an extension of the GreenfootImage class that provides images with text content created to
 * give more flexibility to the GreenfootImage class in this area.  The only previous way to create a 
 * GreenfootImage with text was to use the GreenfootImage(String, int, Color, Color) constructor.  But, the
 * font name and style cannot be altered using that, as it uses default values.  Using 'drawString' on an
 * image created with a font set does not provide the user with the size of the image drawn.  The new
 * 'showText' method does not provide any way to alter the way the text is rendered.  This extension of
 * GreenfootImage resolves these issues by provided a way to render a basic text image of a given font name,
 * font style, size, text color and background color.  Multiple constructors and methods are provided to
 * allow easy usage of the class.<br>
 * <br>
 * NOTE: DO NOT MODIFY THIS CLASS -- except for the five static 'default' fields in the modifiable section below
 * fields.
 */
public final class TextImage extends GreenfootImage
{
    // constants
    /**
     * an array of all fonts currently available; a numbered listing of all fonts, by name, can be printed to the
     * terminal window by way of the 'listFonts' method; these are the String names used when naming a font
     */
    public static final Font[] fonts = convertFonts(java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
    private static Font[] convertFonts(java.awt.Font[] jfonts)
    {
        Font[] fonts = new Font[jfonts.length];
        for (int i=0; i<fonts.length; i++) fonts[i] = getGFont(jfonts[i]);
        return fonts;
    }
    private static Font getGFont(java.awt.Font jfont)
    {
        return new Font(jfont.getName(), jfont.getStyle()%2 == 1, jfont.getStyle()/2 == 1, jfont.getSize());
    }
    private static java.awt.Font getJFont(Font gfont)
    {
        return new java.awt.Font(gfont.getName(), (gfont.isItalic()?2:0)+(gfont.isBold()?1:0), gfont.getSize());
    }
    /**
     * the total number of fonts listed in the 'fonts' array
     */
    public static final int maxFonts = fonts.length;
    // default values
    /** Modification of the following values allowed */
    private static int defaultSize = 20; // probably best no less than 12 for plain text and higher for bold
    private static String defaultName = "SANS_SERIF"; // can be from Font class or from 'fonts' array
    private static int defaultStyle = 0; // Font.PLAIN = 0, Font.BOLD = 1, Font.ITALIC = 2, 1 + 2 = 3
    private static Color defaultTextColor = Color.BLACK; // 'null' draws text black
    private static Color defaultBackgroundColor = new Color(0, 0, 0, 0); // 'null' for transparent background
    /** *****  end of modifiable value section  ***** */
    private static Font defaultFont = new Font(defaultName, defaultStyle%2 == 1, defaultStyle/2 == 1, defaultSize);
    // instance fields
    private String text;
    private int size = defaultSize;
    private String name = defaultName;
    private int style = defaultStyle;
    private Font font = defaultFont;
    private Color textColor = defaultTextColor;
    private Color backgroundColor = defaultBackgroundColor;
    
    /**
     * creates a TextImage object showing the given text
     *
     * @param text the text
     */
    public TextImage(String text)
    {
        this(text, defaultFont, defaultTextColor, defaultBackgroundColor);
    }
    
    /**
     * creates a TextImage object showing the given text using the given text and background colors
     *
     * @param text the text
     * @param tColor the text color (null is black)
     * @param bColor the background color (null is transparent)
     */
    public TextImage(String text, Color tColor, Color bColor)
    {
        this(text, defaultFont, tColor, bColor);
    }
    
    /**
     * creates a TextImage object showing the given text rendered using the given font name
     *
     * @param text the text
     * @param name the font name
     */
    public TextImage(String text, String name)
    {
        this(text, getFont(name), defaultTextColor, defaultBackgroundColor);
    }
    
    /**
     * creates a TextImage object showing the given text, rendered using the given font name, 
     * using the given text and background colors
     *
     * @param text the text
     * @param name the font name
     * @param tColor the text color (null is black)
     * @param bColor the background color (null is transparent)
     */
    public TextImage(String text, String name, Color tColor, Color bColor)
    {
        this(text, getFont(name), tColor, bColor);
    }
    
    /**
     * creates a TextImage object showing the given text in the given font size
     *
     * @param text the text
     * @param size the font size
     */
    public TextImage(String text, int size)
    {
        this(text, getFont(defaultFont, size), defaultTextColor, defaultBackgroundColor);
    }
    
    /**
     * creates a TextImage object showing the given text in the given font size using the
     * given text and background colors
     *
     * @param text the text
     * @param size the font size
     * @param tColor the text color (null is black)
     * @param bColor the background color (null is transparent)
     */
    public TextImage(String text, int size, Color tColor, Color bColor)
    {
        this(text, getFont(defaultFont, size), tColor, bColor);
    }
    
    /**
     * creates a TextImage object showing the given text in the given font
     * 
     * @param text the text
     * @param font the text font
     */
    public TextImage(String text, Font font)
    {
        this(text, font, defaultTextColor, defaultBackgroundColor);
    }
    
    /**
     * creates a TextImage object showing the given text in the given font using the given text and
     * background colors
     *
     * @param text the text
     * @param font the text font
     * @param tColor the text color (null is black)
     * @param bColor the background color (null is transparent)
     */
    public TextImage(String text, Font font, Color tColor, Color bColor)
    {
        super(createImage(text, font, tColor, bColor));
        name = font.getName();
        style = (font.isItalic()?2:0)+(font.isBold()?1:0);
        size = font.getSize();
        this.text = text;
        this.font = font;
        textColor = tColor;
        backgroundColor = bColor;
    }
    
    /** ***********************       OBJECT METHODS       ********************** */
    
    /**
     * returns a duplicate TextImage object of this image text
     */
    public TextImage cloned()
    {
        return new TextImage(text, font, textColor, backgroundColor);
    }
    
    /**
     * returns a similar, but new, TextImage object showing its text in the given font
     * 
     * @param font the font
     * @return a new TextImage object
     */
    public TextImage fonted(Font font)
    {
        return new TextImage(text, font, textColor, backgroundColor);
    }
    
    /**
     * returns a similar, but new, TextImage object with text of the given font size
     *
     * @param size the font size
     * @return a new TextImage object
     */
    public TextImage sized(int size)
    {
        return new TextImage(text, getFont(font, size), textColor, backgroundColor);
    }
    
    /**
     * returns a similar, but new, TextImage object with text rendered using the given font style
     *
     * @param style the font style
     * @return a new TextImage object
     */
    public TextImage styled(int style)
    {
        return new TextImage(text, new Font(name, style%2 == 1, style/2 == 1, size), textColor, backgroundColor);
    }
    
    /**
     * returns a similar, but new, TextImage object with text rendered using the given font name
     *
     * @param name the font name
     * @return a new TextImage object
     */
    public TextImage named(String name)
    {
        return new TextImage(text, new Font(name, style%2 == 1, style/2 == 1, size), textColor, backgroundColor);
    }
    
    /**
     * returns a similar, but new, TextImage object using the given text
     *
     * @param text the text
     * @return a new TextImage object
     */
    public TextImage texted(String text)
    {
        return new TextImage(text, font, textColor, backgroundColor);
    }
    
    /**
     * returns a similar, but new, TextImage object using the given colors
     *
     * @param textColor the text color (null is black)
     * @param backgroundColor the background color (null is transparent)
     * @return a new TextImage object
     */
    public TextImage colored(Color textColor, Color backgroundColor)
    {
        return new TextImage(text, font, textColor, backgroundColor);
    }
    
    /**
     * returns the text of this text image
     * 
     * @return the text
     */
    public String getText()
    {
        return text;
    }
    
    /**
     * returns the font of the image text of this text image
     * 
     * @return the image text font
     */
    public Font getFont()
    {
        return font;
    }
    
    /**
     * returns the font style of the image text of this text image
     * 
     * @return the image text font style as an 'int' value
     */
    public int getFontStyle()
    {
        return style;
    }
    
    /**
     * returns the font name of the image text of this text image
     * 
     * @return the image text font name
     */
    public String getFontName()
    {
        return name;
    }
    
    /**
     * returns the font size of the image text of this text image
     * 
     * @return the image text font size
     */
    public int getFontSize()
    {
        return size;
    }
    
    /**
     * returns the color of the image text of this text image
     * 
     * @return the text color
     */
    public Color getTextColor()
    {
        return textColor;
    }
    
    /**
     * returns the background color of this text image
     * 
     * @return the text image background color
     */
    public Color getBackgroundColor()
    {
        return backgroundColor;
    }
    
    /** ************************       CLASS METHODS       ********************** */
    
    /**
     * sets the default text color to the given color
     */
    public static void setDefaultTextColor(Color color)
    {
        defaultTextColor = color;
    }
    
    /**
     * sets the default background color to the given color
     */
    public static void setDefaultBackgroundColor(Color color)
    {
        defaultBackgroundColor = color;
    }
    
    /**
     * sets the default font style to the given style
     */
    public static void setDefaultStyle(int style)
    {
        defaultStyle = style;
        defaultFont = new Font(defaultName, defaultStyle%2 == 1, defaultStyle/2 == 1, defaultSize);
    }
    
    /**
     * sets the default font size to the given size
     */
    public static void setDefaultSize(int size)
    {
        defaultSize = size;
        defaultFont = new Font(defaultName, defaultStyle%2 == 1, defaultStyle/2 == 1, defaultSize);
    }
    
    /**
     * sets the default font name to the given name if valid
     */
    public static void setDefaultName(String name)
    {
        Font font = getFont(name);
        if (font == null) return;
        defaultName = name;
        defaultFont = new Font(defaultName, defaultStyle%2 == 1, defaultStyle/2 == 1, defaultSize);
    }
    
    /**
     * returns a font object with the given name (or null if name is invalid) in the default size
     * 
     * @return a new font object (or null)
     */
    public static Font getFont(String name)
    {
        for (int i=0; i<maxFonts; i++) if (fonts[i].getName().equals(name))
            return getFont(fonts[i], defaultSize);
        return null;
    }
    
    /**
     * returns a font object with the given name (or null if name is invalid) in the size given
     * 
     * @return a new font object (or null)
     */
    public static Font getFont(String name, int size)
    {
        if (name == null || getFont(name) == null) return null;
        return getFont(getFont(name), size);
    }
    
    /**
     * returns a font object that is the given font in the given size (or null if given font is null)
     * 
     * @return a new font object (or null)
     */
    public static Font getFont(Font font, int size)
    {
        if (font == null) return null;
        return new Font(font.getName(), font.isBold(), font.isItalic(), size);
    }
    
    /**
     * creates and returns the image requested
     * 
     * @param text the text
     * @param font the font
     * @param tColor the text color
     * @param bColor the background color
     */
    private static GreenfootImage createImage(String text, Font font, Color tColor, Color bColor)
    {
        if (text == null) text = ""; // ensure valid text value
        if (font == null) font = defaultFont; // ensure valid font value
        String[] lines = text.split("\n"); // divide text into individual lines
        GreenfootImage image = new GreenfootImage(1, 1); // create a dummy image 
        Graphics2D g = (Graphics2D)image.getAwtImage().getGraphics(); // get graphics object from dummy image
        int fontsize = font.getSize(); // localize font size value
        g.setFont(getJFont(font)); // set font to dummy image
        // test font for proper rendering size
        if (g.getFontMetrics().getHeight() != fontsize)
        {
            font = getGFont(g.getFont().deriveFont((float)fontsize*(float)fontsize/(float)g.getFontMetrics().getHeight()));
            g.setFont(getJFont(font));
        }
        // determine final image size saving size of each line
        Rectangle2D[] bounds = new Rectangle2D[lines.length];
        int x = 1, y = 0;
        for (int i=0; i<lines.length; i++)
        {
            bounds[i] = g.getFontMetrics().getStringBounds(lines[i], g);
            x = Math.max(x, (int)Math.ceil(bounds[i].getWidth()));
            y += Math.ceil(bounds[i].getHeight());
        }
        y = Math.max(y, 1);
        g.dispose();
        // create proper sized image and set font
        image = new GreenfootImage(x, y);
        // fill image with background color
        image.setColor(bColor == null ? defaultBackgroundColor : bColor);
        image.fill();
        // draw text lines onto final image
        image.setFont(font);
        image.setColor(tColor == null ? defaultTextColor : tColor);
        y = 0;
        for (int i=0; i<lines.length; i++)
        {
            image.drawString(lines[i], ((x-(int)bounds[i].getWidth())/2)-(int)bounds[i].getX(), y-(int)bounds[i].getY());
            y += Math.ceil(bounds[i].getHeight());
        }
        return image; // returns the image created
    }
    
    /**
     * prints a numbered listing of all available fonts, by name, to the terminal window;
     * the numbers in the printed listing are one more than the indecis of the fonts in the 'fonts' array field
     */
    public static void listFonts()
    {
        if (maxFonts == 0)
        {
            System.out.println("\nOnly the five Font class defined font fields are allowed.");
            String fontList = "   Font.MONOSPACED\n   Font.SANS_SERIF\n   Font.SERIF";
            fontList += "\n   Font.DIALOG\n   Font.DIALOG_INPUT";
            System.out.println(fontList);
            return;
        }
        for (int i=0; i<100; i++)
            System.out.println("   ".substring((int)Math.log10(i+1))+(i+1)+") \""+fonts[i].getName()+"\"");
        System.out.println("\nSubtract one to use numbers as indecis in the 'fonts' array.");
    }
}
