import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class RightKey extends Images {
    public RightKey() { // Gif image
        gif = new GifImage("RightKey.gif");
        for (GreenfootImage i : gif.getImages())
            i.scale(50,50);
    }
    
    public void act() {
        setImage(gif.getCurrentImage());
    }
}
