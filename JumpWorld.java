import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Add Music and SFX with M as mute key
 * Better Backgrounds and color schemed platforms
 * Cheats are in NUMS class under the space
 * Cool Stuff happens when you reach 5000 score
 * 
 * Images Used:
 * https://imgflip.com/memegenerator/Nuclear-Explosion
 */
public class JumpWorld extends World {
    public static int movedAmount = 0;
    public static boolean gameEnd = false;
    private final static int spawn_buffer = 50, removalRate = 20;
    private int act;
    private double last;
    private Text score;
    private Person mainCharacter;
    private GreenfootImage image;
    private BackgroundColor bc;
    
    class BackgroundColor { // Color Picker
        private int R, G, B, amount;
        private boolean up;
        
        BackgroundColor() { // Initial Values
            up = true;
            R = 0;
            G = 255;
            B = 255;
            amount = NUMS.WORLD_HEIGHT / 255 + 5;
        }
        
        Color getColor() {
            return new Color(R, G, B);
        }
        
        Color nextColor() { // Pick next color
            if (R == 255 && NUMS.SCORE >= 4000)
                up = false;
            if (up && G > 0 && R < 255) {
                R++;
                G--;
            } else if(R > 0 && B > 0) {
                R--;
                B--;
            }
            return new Color(R, G, B);
        }
        
        Color transition() { // Transition to black
            if (R > amount)
                R-=amount;
            if (B > amount)
                B-=amount;
            if (G > amount)
                G-=amount;
            return new Color(R, G, B);
        }
    }
    
    public JumpWorld() {
        super(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT, 1, false); // World setup
        Greenfoot.setSpeed(50);
        gameEnd = false;
        NUMS.SCORE = 0;
        if (NUMS.MORE_PLATFORMS)
            NUMS.PLATFORMS = 100;
        else
            NUMS.PLATFORMS = 20;
        last = 0;
        act = 0;
        
        score = new Text("Score: 0", "Open Sans", NUMS.SCORE_COLOR, NUMS.SCOREBAR_HEIGHT * 3 >> 2);
        mainCharacter = new Person();
        image = new GreenfootImage(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT);
        bc = new BackgroundColor();
        setBackground(bc.getColor());
        
        Platform plat = createPlatforms(NUMS.WORLD_WIDTH >> 1, 200);
        while (NUMS.PLATFORMS-- > 0)    // Create random platforms based on initial plat and spacing
            plat = createPlatforms(plat);
        addObject(mainCharacter, NUMS.WORLD_WIDTH >> 1, NUMS.WORLD_HEIGHT - 50);
        addObject(score, getWidth() - (score.getImage().getWidth() >> 1) - 10, NUMS.SCOREBAR_HEIGHT >> 1);
    }
    
    public void act() {        // Add Black flashing to White after score is 5000
        if (NUMS.SCORE > 5000) {
            NUMS.COLOR_SCHEME = Color.BLACK;
            NUMS.INFINITE_JUMPS = true;
            if (act % 120 == 0) {
                setBackground(Color.BLACK);
            } else if (act % 120 == 90) {
                setBackground(Color.WHITE);
            }
            act++;
        } else if (NUMS.SCORE / 13 > last) {   // Color change every score increase of 50
            setBackground(bc.nextColor());
            last = NUMS.SCORE / 13;
        }
    
        score.updateText("Score: " + NUMS.SCORE);
        if (movedAmount > NUMS.SPACING || NUMS.MORE_PLATFORMS) {    // Check is the platforms have moved down more than the max spacing
            if (NUMS.SPACING < (15 * 15 >> 2))                      // Displacement formula with acceleration
                NUMS.SPACING+=5;                                    // Increase Platform spacing
            movedAmount = 0;                                        // Reset
            createPlatforms(random(NUMS.WORLD_WIDTH - NUMS.PLATFORM_WIDTH, NUMS.PLATFORM_WIDTH >> 1), NUMS.SCOREBAR_HEIGHT); // Make new platforms
            
            // Put character in front
            int x = mainCharacter.getX(), y = mainCharacter.getY();
            removeObject(mainCharacter);
            addObject(mainCharacter, x, y);
        }
        
        java.util.List<Platform> plats = getObjects(Platform.class);
        if ((mainCharacter.getY() > NUMS.WORLD_HEIGHT || gameEnd) && plats.size() > 0) {   // Check if character has fallen under the world
            gameEnd = true;
            mainCharacter.setStop();    // Stop person from moving
            mainCharacter.setLocation(mainCharacter.getX(), NUMS.WORLD_HEIGHT - (mainCharacter.getHeight() >> 1));
            setBackground(bc.transition());
            for (Platform plat : plats) { // Move all platforms up at removal speed
                plat.move(0, removalRate);
                if (plat.getY() < NUMS.SCOREBAR_HEIGHT)
                    plat.remove();
            }   
        } else if (plats.size() == 0)
            Greenfoot.setWorld(new EndScreen());
        else 
            gameEnd = false;
            
        // Remove all platforms that are outside of the world after scrolling
        plats = getObjects(Platform.class); // Update List
        for (Object i : plats)
            if (((Platform) i).getY() + NUMS.PLATFORM_HEIGHT > NUMS.WORLD_HEIGHT) // Check if platform is below screen and Remove a little earlier to prevent from jumping when slightly under screen
                removeObject((Actor)i);
    }
    
    private void setBackground(Color color) {   // Set Background to a color
        image.clear();
        image.setColor(color);
        image.fill();
        setBackground(image);
    }
    
    private Platform createPlatforms(Platform last) {   // Create round platforms everywhere on the screen Randomized x locations
        return createPlatforms(random(NUMS.WORLD_WIDTH - NUMS.PLATFORM_WIDTH, last.getWidth() >> 1), random(50, NUMS.SPACING) + last.getY());
    }
    
    private Platform createPlatforms(int x, int y) { // Create a rounded platform at (x,y)
        Platform plat;
        addObject(plat = new Platform(x), x, y);
        return plat;
    }
    
    private int random(int range, int start) {
        java.security.SecureRandom sr = new java.security.SecureRandom();
        int num = sr.nextInt(range) + start;
        return num; 
    }
}