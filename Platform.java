import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Platform extends Actor {
    private boolean moving, timed, jumpable;                        // Boolean to see if the platform should move, if the platform is on a timer, and if the platform is destroyed
    private int leftBarrier, moveAmount, speed, jumpsLeft, timer;   // Determine platform spacing, amount to move, and left barrier and number of jumps left on the platform
    private GreenfootImage image;
    
    public Platform(int left) { // Set image to a rectangle and add it
        image = new GreenfootImage(NUMS.PLATFORM_WIDTH + NUMS.PLATFORM_HEIGHT, NUMS.PLATFORM_HEIGHT); // Draw Image
        image.setColor(NUMS.COLOR_SCHEME);
        image.setTransparency(NUMS.MAX_TRANSPARENCY);
        image.fillRect(NUMS.PLATFORM_HEIGHT >> 1, 0, NUMS.PLATFORM_WIDTH, NUMS.PLATFORM_HEIGHT);
        image.fillOval(0, 0, NUMS.PLATFORM_HEIGHT, NUMS.PLATFORM_HEIGHT);
        image.fillOval(NUMS.PLATFORM_WIDTH, 0, NUMS.PLATFORM_HEIGHT, NUMS.PLATFORM_HEIGHT);
        setImage(image);
        
        jumpable = true;
        moving = NUMS.SCORE > NUMS.SPAWN_SCORE && random(8, 0) == 0;
        if (!moving) {    // Moving platforms cannot explode and have near infinite jumps
            timed = NUMS.EXPLODE ? random(15, 0) == 0 : false;
            jumpsLeft = random(9, 1);
        } else {
            jumpsLeft = -1;
            left = left > NUMS.WORLD_WIDTH - NUMS.PLATFORM_WIDTH ? left - NUMS.PLATFORM_WIDTH >> 1 : left;
        }
        if (NUMS.INFINITE_JUMPS)
            jumpsLeft = -1;
        speed = random(8, 2);
        moveAmount = random(NUMS.WORLD_WIDTH >> 1, NUMS.PLATFORM_WIDTH * 2);
        timer = 40;
        leftBarrier = left;
    }
    
    public void act() {
        if (jumpsLeft == 0) {    // If Platform has no jumps left
            timed = true;        // Start timer
            timer = 21;
        } else if (timed && getY() >= NUMS.THRESHOLD) { // If timer is started and platform is past half the world
            timer--;
            if (timer <= 0)
                remove();
            else if (timer == 20) {
                explode();
            }
        } else if (moving) {    // If platform is moving
            if (getX() > moveAmount + leftBarrier || getX() > NUMS.WORLD_WIDTH && speed > 0)   // If platform is past right side
                speed = -speed;
            else if (getX() < leftBarrier || getX() < 0 && speed < 0) // If Platform is past left side 
                speed = -speed;
            move(1, speed);
        }
    }
    
    public void move(int direction, int amount) { // Move platform
        switch(direction) { // Direction 0 is vertical, 1 is horizontal
            case 0:
                setLocation(getX(), getY() - amount); // Shift up by default
                break;
            case 1:
                setLocation(getX() + amount, getY());
                break;
        }
    }
    
    private void explode() {    // Create explosion effect
        GreenfootImage explosion = new GreenfootImage("explosion.jpg");
        explosion.scale(NUMS.PLATFORM_WIDTH, NUMS.PLATFORM_HEIGHT);
        setImage(explosion);
        jumpable = false;
    }
    
    private int random(int range, int start) {  // Get a random number
        java.security.SecureRandom sr = new java.security.SecureRandom();
        int num = sr.nextInt(range) + start;
        return num; 
    }
    
    public void changeColor(Color color) {
        image = new GreenfootImage(NUMS.PLATFORM_WIDTH + NUMS.PLATFORM_HEIGHT, NUMS.PLATFORM_HEIGHT); // Draw Image
        image.setColor(color);
        image.setTransparency(NUMS.MAX_TRANSPARENCY);
        image.fillRect(NUMS.PLATFORM_HEIGHT >> 1, 0, NUMS.PLATFORM_WIDTH, NUMS.PLATFORM_HEIGHT);
        image.fillOval(0, 0, NUMS.PLATFORM_HEIGHT, NUMS.PLATFORM_HEIGHT);
        image.fillOval(NUMS.PLATFORM_WIDTH, 0, NUMS.PLATFORM_HEIGHT, NUMS.PLATFORM_HEIGHT);
        setImage(image);
    }
    
    public void remove() {
        getWorld().removeObject(this);
    }
    
    public void decreaseJumps() {
        jumpsLeft--;
    }
    
    public int getHeight() {
        return getImage().getHeight();
    }
    
    public int getWidth() {
        return getImage().getWidth();
    }
    
    public boolean isJumpable() {
        return jumpable;
    }
}