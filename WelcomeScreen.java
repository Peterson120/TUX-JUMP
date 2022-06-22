import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class WelcomeScreen extends World
{
    private static Music bm;
    private GreenfootImage bg;
    private Button play, music, sfx;
    private Text main;
    private Person mainCharacter;
    private Platform plat;
    public WelcomeScreen()
    {    
        super(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT, 1); 
        bm = new Music(new GreenfootSound("MarioKartWiiMusic.mp3"));
        bm.loop();

        // Set background
        bg = new GreenfootImage("background.jpg");
        bg.scale(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT);
        NUMS.COLOR_SCHEME = NUMS.SCORE_COLOR;
        setBackground(bg);

        // Create Objects
        play = new Button("World");
        music = new Button("Music");
        sfx = new Button("SFX");
        main = new Text("TUX JUMP", Color.WHITE, 120);
        mainCharacter = new Person(false);
        plat = new Platform();

        // Add Objects
        addObject(main, NUMS.WORLD_WIDTH >> 1, NUMS.WORLD_HEIGHT >> 2);
        addObject(play, NUMS.WORLD_WIDTH >> 1, NUMS.WORLD_HEIGHT * 5 >> 3);
        addObject(music, (NUMS.WORLD_WIDTH >> 1) - sfx.getWidth(), play.getY() + (play.getHeight() * 3 >> 1));
        addObject(sfx, (NUMS.WORLD_WIDTH >> 1) + music.getWidth(), play.getY() + (play.getHeight() * 3 >> 1));
        addObject(plat, NUMS.WORLD_WIDTH - (plat.getWidth() >> 1) - 50, NUMS.WORLD_HEIGHT - (plat.getHeight() >> 1) - 200);
        addObject(mainCharacter, plat.getX(), NUMS.WORLD_HEIGHT);
        prepare();
    }

    public void act() { // Check for key presses
        if (Greenfoot.isKeyDown("Enter") || Greenfoot.isKeyDown("space"))
            nextWorld();
        
        if (NUMS.MUSIC) {   // Enable music
            bm.play(75);
        } else {
            bm.pause();
        }
    }

    public static void nextWorld() {    // Go to Jump World
        bm.stop();
        Greenfoot.setWorld(new JumpWorld());
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
