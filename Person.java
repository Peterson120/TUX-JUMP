import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
// Add Jump noise Animate person
public class Person extends Actor {
    /*
     * Final varibles for the character
     * Mousebuffer allows the mouse to be within a range to prevent the character from glitching between pixels
     * Side buffer is how much the character can be off the left/right side of the screen
     * Velocity is the initial jump speed of the character
     * horizontalSpeed is the speed to move horizontally on mouse movement or arrow key presses
     * Gravity is the Downwards Acceleration
     * act keps track of the act number
       */
    private final int sideBuffer = (getImage().getWidth() >> 1) - 6, mouseBuffer = 4, velocity = -15, horizontalSpeed = 7, gravity = NUMS.FLIGHT ? 0 : 1;
    private int act = 0;                                                                    // Act number
    private double rate = velocity - 6;                                                     // Fall velocity of the Human (Vertical movement modifier)
    private boolean down = false, mouseOn = false, lastMouseState = false, stopped = false; // Mouse states and if character is falling down
    private MouseInfo mouse;
    public void act() {
        mouse = Greenfoot.getMouseInfo();
        int hMove = 0; // Horizontal Movement modifier
        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {          // Check if user is pressing the left arrow key or "a"
            mouseOn = false;              // Disable mouse movement
            hMove -= horizontalSpeed;     // Decrease horizontal movement variable (to move left)
        } else if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {    // Check if user if pressing the right arrow key or "d"
            mouseOn = false;              // Disable mouse movement
            hMove += horizontalSpeed;     // Increase horizontal movement variable (to move right)
        } else if (mouse != null && (lastMouseState = mouse.getButton() == 1) && mouseOn != lastMouseState && mouse.getY() > NUMS.SCOREBAR_HEIGHT) // Set lastMouseState to current mouse state and check if the current mouse state has changed
            mouseOn = !mouseOn;     // If state has changed then allow user to use mouse to move character
        else if (mouse != null && mouseOn && mouse.getY() > NUMS.SCOREBAR_HEIGHT) {     // Force user to move at the same speed as using a keyboard so there is no advantage
            if (getX() + mouseBuffer < mouse.getX())
                hMove += horizontalSpeed; // Same as keyboard
            else if (getX() - mouseBuffer > mouse.getX())
                hMove -= horizontalSpeed; // Same as keyboard
        }
        
        // Loop to other side if player is out of world bounds
        if (getX() <= -sideBuffer)  // User is outside of the world buffer size then setlocation to opposite side of screen
            setLocation(NUMS.WORLD_WIDTH + sideBuffer, getY());
        else if (getX() >= NUMS.WORLD_WIDTH + sideBuffer)
            setLocation(-sideBuffer, getY());
        
        setLocation(getX() + hMove, getY());
            
        if (!stopped) {             // Do not run if character should not be moving
            if (down && rate < 0)   // Determine if the character is currently falling
                down = false;
            else if (!down && rate > 2) // Ensure that the person does not seem like it is double jumping
                down = true;
            
            Platform p = down ? collision() : null; // Improve efficiency
            if (p == null && act > 1) {     // If no object was detected for collision decrease velocity by the world gravity
                rate += gravity;
                act = 0;
            } else if (down && p != null) { // If person is currently falling downwards and object is detected, reset the velocity to the predetermined velocity
                rate = velocity;
                p.decreaseJumps();
            }
            act++;
            
            if (getY() + (int) Math.round(rate) <= NUMS.THRESHOLD) { // Set character at a position in world if it is past the threshold in the world
                setLocation(getX(), NUMS.THRESHOLD);
                int difference = getY() + (int) Math.round(rate) - NUMS.THRESHOLD;
                for (Platform plats : getWorld().getObjects(Platform.class))
                    plats.move(0, difference);
                JumpWorld.movedAmount -= difference;
                NUMS.SCORE -= (difference >> 3);
            } else
                setLocation(getX(), getY() + (int) Math.round(rate));
        }
    }
    
    private Platform collision() {  // Check platform collision
        Platform plat = null;
        for (int i = 0; i < rate + NUMS.PLATFORM_HEIGHT && plat == null; i++) {
            plat = (Platform) getOneObjectAtOffset(0, i, Platform.class);  // Check for plat
            if (plat != null && !plat.isJumpable())
                plat = null;
        }
        if (plat != null)
            setLocation(getX(), plat.getY() - plat.getHeight());
        return plat;
    }
    
    public int getHeight() {
        return getImage().getHeight();
    }
    
    public void setStop() {
        stopped = true;
    }
}
