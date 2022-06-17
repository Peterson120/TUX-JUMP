import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

// Change image
public class Person extends Actor {
    /*
     * Final varibles for the character
     * Side buffer is how much the character can be off the left/right side of the screen
     * Velocity is the initial jump speed of the character
     * horizontalSpeed is the speed to move horizontally on mouse movement or arrow key presses
     * Gravity is the Downwards Acceleration
     * act keps track of the act number
       */
    private final int sideBuffer = (getImage().getWidth() >> 2), velocity = -15, horizontalSpeed = 7, gravity = NUMS.FLIGHT ? 0 : 1;
    private int act = 0;                                                    // Act number
    private double rate;                                                    // Fall velocity of the Human (Vertical movement modifier)
    private boolean down, stopped, allowMovement;                           // Mouse states and if character is falling down
    private GreenfootImage falling, standing;
    private MouseInfo mouse;
    private Music fx;
    
    public Person() { // Setup
        down = false;
        stopped = false;
        allowMovement = true;
        rate = velocity - 6;
        
        falling = new GreenfootImage("Sitting.png");
        falling.scale(60, 72);
        
        standing = new GreenfootImage("Standing.png");
        standing.scale(60, 72);
        setImage(standing);
    }
    
    public Person(boolean move) {
        this();
        allowMovement = move;
    }
    
    public void act() {
        fx = new Music(new GreenfootSound("BoingFX.mp3"), true);    // Reset Sound to allow multiple sounds to play at once
        mouse = Greenfoot.getMouseInfo();
        
        if (NUMS.SCORE > 5000) {
            standing.setColor(Color.BLACK);
            falling.setColor(Color.BLACK);
        }
        
        int hMove = 0; // Horizontal Movement modifier
        if (allowMovement) {
            if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a"))          // Check if user is pressing the left arrow key or "a"
                hMove -= horizontalSpeed;     // Decrease horizontal movement variable (to move left)
            else if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d"))    // Check if user if pressing the right arrow key or "d"
                hMove += horizontalSpeed;     // Increase horizontal movement variable (to move right)
        }
        
        // Loop to other side if player is out of world bounds
        if (getX() <= -sideBuffer)  // User is outside of the world buffer size then setlocation to opposite side of screen
            setLocation(NUMS.WORLD_WIDTH + sideBuffer, getY());
        else if (getX() >= NUMS.WORLD_WIDTH + sideBuffer)
            setLocation(-sideBuffer, getY());
        
        setLocation(getX() + hMove, getY());
            
        if (!stopped) {             // Do not run if character should not be moving
            if (down && rate < 0) {   // Determine if the character is currently falling
                down = false;
                setImage(standing);
            } else if (!down && rate > 0) { // Ensure that the person does not seem like it is double jumping
                down = true;
                setImage(falling);
            }
            
            Platform p = down ? collision() : null; // Improve efficiency
            if (p == null && act > 1) {     // If no object was detected for collision decrease velocity by the world gravity
                rate += gravity;
                act = 0;
            } else if (p != null) {         // If person is currently falling downwards and object is detected, reset the velocity to the predetermined velocity
                rate = velocity;
                p.decreaseJumps();
                if (NUMS.SFX)
                    fx.play(100);
            }
            act++;
            
            if (getY() + (int) Math.round(rate) <= NUMS.THRESHOLD) { // Set character at a position in world if it is past the threshold in the world
                setLocation(getX(), NUMS.THRESHOLD);
                int difference = getY() + (int) Math.round(rate) - NUMS.THRESHOLD;
                for (Platform plats : getWorld().getObjects(Platform.class)) { // Scroll Platforms
                    plats.move(0, difference);
                    if (plats.getY() + NUMS.PLATFORM_HEIGHT > NUMS.WORLD_HEIGHT) // Check if platform is below screen
                        getWorld().removeObject(plats);
                }
                JumpWorld.movedAmount -= difference;
                NUMS.SCORE -= (difference >> 3);
            } else
                setLocation(getX(), getY() + (int) Math.round(rate));
        }
    }
    
    private Platform collision() {  // Check platform collision
        Platform plat = null;
        for (int i = 0; i < rate + NUMS.PLATFORM_HEIGHT && plat == null; i++)
            plat = (Platform) getOneObjectAtOffset(0, i, Platform.class);  // Check for plat
        if (plat != null) {
            setLocation(getX(), plat.getY() - plat.getHeight());
            if (NUMS.SFX)
                fx.play(100);
        }
        return plat;
    }
    
    public int getHeight() {
        return getImage().getHeight();
    }
    
    public void setStop() {
        stopped = true;
    }
}
