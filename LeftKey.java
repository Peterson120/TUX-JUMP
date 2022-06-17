import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class LeftKey extends Images {
    public LeftKey() {  // Gif image
        gif = new GifImage("LeftKey.gif");
        for (GreenfootImage i : gif.getImages())
            i.scale(50,50);
    }
    
    public void act() {
        setImage(gif.getCurrentImage());
    }
}
