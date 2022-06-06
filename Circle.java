import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Circle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Circle extends Actor
{
    protected GreenfootImage circle;
    public Circle() {
        this(1, 1, new Color(255,255,255), false);
    }
    
    public Circle(int diameter, int transparency, Color color, boolean fill) {
        circle = new GreenfootImage(diameter, diameter);
        circle.clear();
        circle.setColor(color);
        if (fill) 
            circle.fillOval(0,0,diameter,diameter);
        circle.setTransparency(transparency);
        setImage(circle);
    }
    
    public GreenfootImage getCircle() {
        return circle;
    }
}
