import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class TextImages extends Images {
    private GreenfootImage gf;
    public TextImages(String image, int length, int size) { // Set actor as image
        gf = new GreenfootImage(image);
        gf.scale(length * size, size * 3 >> 1);
        setImage(gf);
    }
}
