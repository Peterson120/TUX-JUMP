import greenfoot.*;

/*
 * Add a Text box to the world
 * Choose between no background, different colors and font sizes
   */
public class Text extends Actor {
    private GreenfootImage text;
    private String str, font;
    private Color color, background;
    private int height;
    public Text(String str, Color color, Color background, int height) {
        this(str, "Arial", color, background, height);
    }
    
    public Text(String str, Color color, int height) {
        this(str, "Arial", color, new Color(0, 0, 0, 0), height);
    }
    
    public Text(String str, String font, Color color, int height) {
        this(str, "Arial", color, new Color(0, 0, 0, 0), height);
    }
    
    public Text(String str, String font, Color color, Color background, int height) {
        this.str = str;
        this.color = color;
        this.background = background;
        this.height = height;
        this.font = font;
        text = new GreenfootImage(str, 12, color, background);
        text.setTransparency(NUMS.MAX_TRANSPARENCY);
        text.scale((height >> 3) * 3 * str.length(), height);
        text.setFont(new Font(font, height));
        setImage(text);
    }
    
    // Used for updating score
    public void updateText(String str) {
        this.str = str;
        text = new GreenfootImage(str, str.length() * 12, color, background);
        text.setTransparency(NUMS.MAX_TRANSPARENCY);
        text.scale((height >> 3) * 3 * str.length(), height);
        text.setFont(new Font(font, height));
        setImage(text);
        setLocation(NUMS.WORLD_WIDTH - (str.length() * 12), getY());
    }
    
    public void changeColor(Color color) {
        this.color = color;
        updateText(str);
    }
    
    public int getHeight() {
        return getImage().getHeight();
    }
}
