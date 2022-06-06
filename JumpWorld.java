import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
/**
 * Add Music
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JumpWorld extends Scroll {
    private final static int spawn_buffer = 100;
    public JumpWorld() {
        super(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT, 20);
        setBackground(new GreenfootImage("background.png"));
        Greenfoot.setSpeed(50);
        NUMS.PLATFORMS = 20;
        while (NUMS.PLATFORMS-- > 0)
            addObject(new Platform(), random(0, NUMS.WORLD_WIDTH - NUMS.PLATFORM_WIDTH), random(75, NUMS.WORLD_HEIGHT - NUMS.PLATFORM_HEIGHT));
        super.addMainCharacter(new Person(), NUMS.WORLD_WIDTH >> 1, NUMS.WORLD_HEIGHT);
        addObject(new ScoreBar(), NUMS.WORLD_WIDTH >> 1, 25);
        addObject(new Circle(), 0,0);
    }
    
    private int random(int start, int range) {
        Random rand = new Random();
        return rand.nextInt(range) + start;
    }
}