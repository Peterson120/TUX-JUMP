import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WelcomeScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WelcomeScreen extends World
{

    /**
     * Constructor for objects of class WelcomeScreen.
     * 
     */
    public WelcomeScreen()
    {    
        super(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT, 1); 
    }
    
    public void act() {
        if (Greenfoot.isKeyDown("Enter")) {
            Greenfoot.setWorld(new JumpWorld());
        }
    }
}
