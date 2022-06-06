import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Scroll here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scroll extends World {
    private GreenfootImage background;
    private int width, height, buffer, x, y;
    private Actor mainCharacter;
    public Scroll(int width, int height, int buffer) {    
        super(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT, 1, false); 
        this.width = width;
        this.height = height;
        this.buffer = buffer;
        background = getBackground();
        
        x = 0;
        y = 0;
        int dy = 0;
        if (background.getHeight() > height)
            dy = background.getHeight() - height;
        move(0, dy);
    }
    
    private void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            x += dx;
            y += dy;
            
            GreenfootImage mask = new GreenfootImage(width, height);
            mask.drawImage(background, x, y);
            setBackground(mask);
        }
    }
    
    public void addMainCharacter(Actor mainCharacter, int x, int y) {
        super.addObject(mainCharacter, x, y);
        this.mainCharacter = mainCharacter;
    }
    
    public Actor getMainCharacter() {
        return mainCharacter;
    }
    
    public void act() {
        if (mainCharacter.getY() > height) {    // Check if character has fallen under the world
            EndScreen e = new EndScreen();
            Greenfoot.setWorld(e);
        }
        
        if (mainCharacter != null) {
            int my = mainCharacter.getY();
            int dy = 0;
            if (my <= buffer)
                dy = mainCharacter.getY() - buffer;
            else if (my >= height - buffer)
                dy = mainCharacter.getY() + buffer;
            
            if (dy != 0) {
                int topY = y + dy;
                int bottomY = y + height + dy;
                
                int yMove = 0;
                if (topY >= 0 && bottomY <= background.getHeight())
                    yMove = dy;
                    
                move(0, yMove);
            }
            if (background.getHeight() > height) {
                dy = background.getHeight() - height;
            }
        }
        
        // Remove all platforms that are outside of the world after scrolling
        List all = getObjects(Platform.class);
        for (int i = 0; i < all.size(); i++)
            if (((Platform) all.get(i)).getY() > NUMS.WORLD_HEIGHT)
                removeObject((Platform) all.get(i));
    }
}
