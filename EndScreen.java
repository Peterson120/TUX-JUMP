import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EndScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndScreen extends World
{
    public EndScreen() {    
        super(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT, 1);
        removeObjects(getObjects(null));
        setBackground("white_background.jpg");
        showText("YOU LOSE",getWidth()/2,getHeight()/2);
    }
}
