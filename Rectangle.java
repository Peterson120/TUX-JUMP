import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Rectangle extends Actor {
    protected GreenfootImage image;
    public Rectangle(int width, int height, int transparency, Color color, boolean fill) {
        image = new GreenfootImage(width, height);
        image.clear();
        image.setColor(color);
        if (fill) 
            image.fill();
        image.setTransparency(transparency);
        setImage(image);
    }
}
