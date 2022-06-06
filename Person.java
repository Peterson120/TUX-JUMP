import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
// Add Jump noise Animate person
// If rate is greater than the distance between human and platform, set human to platform
public class Person extends Actor {
    /*
     * Final varibles for the character
     * Mousebuffer allows the mouse to be within a range to prevent the character from glitching between pixels
     * Side buffer is how much the character can be off the left/right side of the screen
     * Velocity is the initial jump speed of the character
     * horizontalSpeed is the speed to move horizontally on mouse movement or arrow key presses
       */
    private final int sideBuffer = (getImage().getWidth() >> 1) - 6, mouseBuffer = 4, velocity = -12, horizontalSpeed = 7;
    private final double gravity = 0.5;                                     // Downwards Acceleration
    private double rate = velocity - 5;                                         // Fall velocity of the Human (Vertical movement modifier)
    private boolean down = true, mouseOn = false, lastMouseState = false;   // Mouse states and if character is falling down
    private MouseInfo mouse;
    public void act() {
        mouse = Greenfoot.getMouseInfo();
        Platform p = collision();
        int hMove = 0; // Horizontal Movement modifier
        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {          // Check if user is pressing the left arrow key or "a"
            mouseOn = false;              // Disable mouse movement
            hMove -= horizontalSpeed;     // Decrease horizontal movement variable (to move left)
        }
        else if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {    // Check if user if pressing the right arrow key or "d"
            mouseOn = false;              // Disable mouse movement
            hMove += horizontalSpeed;     // Increase horizontal movement variable (to move right)
        }
        else if (mouse != null && (lastMouseState = mouse.getButton() == 1) && mouseOn != lastMouseState && mouse.getY() > NUMS.SCOREBAR_HEIGHT) // Set lastMouseState to current mouse state and check if the current mouse state has changed
            mouseOn = !mouseOn;     // If state has changed then allow user to use mouse to move character
        else if (mouse != null && mouseOn && mouse.getY() > NUMS.SCOREBAR_HEIGHT) {     // Force user to move at the same speed as using a keyboard so there is not advantage
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
        
        if (down && rate < 0) // Determine if the character is currently falling
            down = false;
        else if (!down && rate > 5) // Ensure that the person does not seem like it is double jumping
            down = true;
        
        if (p == null)   // If no object was detected for collision decrease velocity by the world gravity
            rate += gravity;
        else if (down && p != null) { // If person is currently falling downwards and object is detected, reset the velocity to the predetermined velocity
            setLocation(getX(), p.getY() - NUMS.PLATFORM_HEIGHT - 5);
            NUMS.SCORE = NUMS.WORLD_HEIGHT - getY();
            rate = velocity;
        }
        else if (getY() >= NUMS.WORLD_HEIGHT) {  // If user has fallen underneath the last platform, set the world to the end screen
            World e = new EndScreen();
            Greenfoot.setWorld(e);
        }
        setLocation(getX() + hMove, getY() + (int) Math.round(rate));
    }
    
    private Platform collision() {
        Platform p = null;
        for (int i = 0; i < rate && p == null; i += (NUMS.PLATFORM_HEIGHT - 1)/rate + 1)
            p = (Platform) getOneObjectAtOffset(0, i, Platform.class);
        return p;
    }
}
