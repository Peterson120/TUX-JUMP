import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Button extends Actor {
    private String type; // Store the type of button (New World, Sound, etc)
    private GreenfootImage image;
    private Music fx;
    private boolean lastPress = false;
    public Button(String type) {
        this.type = type;
        fx = new Music(new GreenfootSound("MouseClickFX.mp3"));
        changeImage();
    }
    
    public void act() {
        fx = new Music(new GreenfootSound("MouseClickFX.mp3"));
        if (Greenfoot.mouseClicked(this) || (Greenfoot.isKeyDown("m") && !lastPress && !type.equals("World"))) { // Change music booleans, or go to jumpworld
            switch (type) {
                case "World":
                    if (NUMS.SFX)
                        fx.play(100);
                    WelcomeScreen.nextWorld();
                    break;
                case "Music":
                    NUMS.MUSIC = !NUMS.MUSIC;
                    if (NUMS.SFX)
                        fx.play(75);
                    changeImage();
                    break;
                case "SFX":
                    NUMS.SFX = !NUMS.SFX;
                    if (NUMS.SFX)
                        fx.play();
                    changeImage();
                    break;
                default:
                    changeImage();
                    break;
            }
        }
        lastPress = Greenfoot.isKeyDown("m");
    }
    
    public void changeImage() {
        switch (type) { // Change image depending on the type of button defined
            case "World":
                image = new GreenfootImage("PlayButton.png");
                image.scale(NUMS.WORLD_WIDTH * 3 >> 3, NUMS.WORLD_HEIGHT * 3 >> 5);
                break;
            case "Music":
                if (NUMS.MUSIC)
                    image = new GreenfootImage("Music.png");
                else
                    image = new GreenfootImage("NoMusic.png");
                image.scale(50, 50);
                break;
            case "SFX":
                if (NUMS.SFX)
                    image = new GreenfootImage("Volume.png");
                else
                    image = new GreenfootImage("NoVolume.png");
                image.scale(50, 50);
                break;
            default:
                image = new GreenfootImage("placeholder.png");
                break;
        }
        setImage(image);
    }
    
    public int getHeight() {
        return getImage().getHeight();
    }
    
    public int getWidth() {
        return getImage().getWidth();
    }
}
