import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.concurrent.ThreadLocalRandom; // Random Num in one line
public class Platform extends Rectangle
{
    // Add platform break roulette, etc. No jump destroyed platforms and one jump, two jump
    private final boolean moving = move();   // Boolean to see if the platform should move
    private GreenfootImage image, circle;
    public Platform() { // Set image to a rectangle and add it
        super(NUMS.PLATFORM_WIDTH + NUMS.PLATFORM_HEIGHT, NUMS.PLATFORM_HEIGHT, NUMS.MAX_TRANSPARENCY, greenfoot.Color.BLUE, true);
        Circle cic = new Circle(NUMS.PLATFORM_HEIGHT >> 1, NUMS.MAX_TRANSPARENCY, NUMS.PLATFORM_COLOR, true);
        // getWorld().addObject(cic, getX(), getY() + (NUMS.PLATFORM_HEIGHT >> 1));
    }
    
    public void act() {
        if (moving) {
            
        }
    }
    
    private boolean move() { // Determine when created if the object is a moving platform
        return NUMS.SCORE > NUMS.SPAWN_SCORE && ThreadLocalRandom.current().nextInt(0, 5 + 1) == 0;
    }
    
    public GreenfootImage getPlatform() {
        return image;
    }
}