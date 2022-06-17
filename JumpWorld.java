import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Add Super Jumps
 * Button Click Sound
 * 
 * How to Play:
 * - Click Play to start the game
 * - Use the Arrow keys or AD to move left and right
 * - Try to stay on the platforms and get a high score
 * - Platorms might break so move fast!
 * - Use 'm' to mute the SFX and music
 * 
 * Cheats are in NUMS class under the space
 * Cool Stuff happens when you reach 5000 score
 *  - Change NUMS.START_AT_5000 to true
 * 
 * Edited means I made some changes to the file/audio
 * Music Used
 *  - Duck Tales NES Moon Theme https://www.youtube.com/watch?v=KF32DRg9opA
 *  - Super Mario Galaxy        https://www.youtube.com/watch?v=1bvDHAUv2ak
 *  - Mario Death Sound         https://www.youtube.com/watch?v=k8KB2mhsDTY
 *  - Boing Sound Effect        https://www.youtube.com/watch?v=CIuSMI2-l58
 *  - Mouse Click               https://www.youtube.com/watch?v=h6_8SlZZwvQ
 *  - Edited Chip Crack Effect  https://www.youtube.com/watch?v=vE_n4Xr9qo0
 *  - Edited Mario Oh No        https://www.youtube.com/watch?v=CLsLA0Ph0hE&ab_channel=TimothyWing
 *  - Edited Mario Kart Wii     https://www.youtube.com/watch?v=iFUxQ4Yv3ns&ab_channel=Totej
 *
 * Images Used
 *  - Edited Penguin Standing   https://www.dreamstime.com/vector-illustration-cute-baby-penguin-cartoon-sitting-isolated-white-background-cute-baby-penguin-cartoon-sitting-image104296247
 *  - Edited Penguin Sitting    https://www.shutterstock.com/image-vector/vector-illustration-cute-baby-penguin-cartoon-747975076
 *  - Volume Button             https://www.istockphoto.com/vector/speaker-audio-icon-set-volume-voice-control-on-off-mute-symbol-flat-application-gm1276641059-376092875
 *  - Music Mute Button         https://www.nicepng.com/ourpic/u2e6w7r5e6t4r5t4_muted-music-notes-music-mute-icon-png/
 *  - Music Button              https://www.vectorstock.com/royalty-free-vector/music-button-on-white-vector-10514828
 *  - Play Button               http://clipart-library.com/clipart/8TzrapBXc.htm
 *  - Background                https://websitebackgroundmaker.com/backgrounds/gradients/
 */
public class JumpWorld extends World {
    public static int movedAmount = 0;
    public static boolean gameEnd = false;
    private final static int spawn_buffer = 50, removalRate = 20;
    private int act;
    private double last;
    private boolean lastPress, infoOn;  // Gets last user keystroke to make sure music is not muted multiple times
    private Text score, info;
    private Person mainCharacter;
    private GreenfootImage image;
    private Images leftKey, rightKey;
    private BackgroundColor bc;
    private Music fx, bm;
    private Button sfx, music;
    
    class BackgroundColor { // Color Picker
        private int R, G, B, amount;
        private boolean up;
        
        BackgroundColor() { // Initial Values
            up = true;
            R = 137;
            G = 207;
            B = 240;
            amount = NUMS.WORLD_HEIGHT / 255 + 5;
        }
        
        Color getColor() {
            return new Color(R, G, B);
        }
        
        Color nextColor() { // Pick next color
            if (R == 255 && NUMS.SCORE >= 4000)
                up = false;
            if (up && G > 0 && R < 255) {
                if (R < 255)
                    R++;
                if (G > 0)
                    G--;
            } else if(R > 0 && B > 0) {
                if (R > 0)
                    R--;
                if (B > 0)
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
        infoOn = true;
        NUMS.SCORE = 0;
        if (NUMS.START_AT_5000)
            NUMS.SCORE = 5000;
        if (NUMS.MORE_PLATFORMS)
            NUMS.SPACING = 1;
        if (NUMS.SCORE < 5000) // Check score and set color
            NUMS.COLOR_SCHEME = NUMS.SCORE_COLOR;
        last = 0;
        act = 0;
        
        // Create Objects
        score = new Text("Score: " + NUMS.SCORE, NUMS.SCORE_COLOR, NUMS.SCOREBAR_HEIGHT * 3 >> 2);
        info = new Text("Use the arrow keys to move around", Color.BLACK, 30);
        leftKey = new LeftKey();
        rightKey = new RightKey();
        mainCharacter = new Person();
        image = new GreenfootImage(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT);
        fx = new Music(new GreenfootSound("SuperMarioBrosFX.mp3"));
        bm = new Music(new GreenfootSound("SuperMarioGalaxySoundtrack.mp3"));
        sfx = new Button("SFX");
        music = new Button("Music");
        bm.loop();
        bc = new BackgroundColor();
        setBackground(bc.getColor());
        
        boolean last = NUMS.EXPLODE, last2 = NUMS.INFINITE_JUMPS; // Store current value
        NUMS.EXPLODE = false;
        NUMS.INFINITE_JUMPS = true;
        Platform plat = createPlatforms(NUMS.WORLD_WIDTH >> 1, NUMS.WORLD_HEIGHT - 300);
        NUMS.INFINITE_JUMPS = last2;
        while (plat.getY() >= 0)    // Create random platforms based on initial plat and spacing
            plat = createPlatforms(plat);
        NUMS.EXPLODE = last;
        addObject(info, getWidth() >> 1, getHeight() >> 2);
        addObject(mainCharacter, NUMS.WORLD_WIDTH >> 1, NUMS.WORLD_HEIGHT - 50);
        addObject(score, getWidth() - (score.getWidth() >> 1) - 10, NUMS.SCOREBAR_HEIGHT >> 1);
        addObject(music, (music.getWidth() >> 1) + 10, (music.getHeight() >> 1) + 10);
        addObject(sfx, music.getX() + music.getWidth() + 20, music.getY());
        addObject(leftKey, info.getX() - 25, info.getY() + (leftKey.getHeight() >> 1) + (info.getHeight() >> 1));
        addObject(rightKey, leftKey.getX() + (leftKey.getWidth() >> 1), leftKey.getY());
    }
    
    public void act() {
        if (Greenfoot.isKeyDown("m") && !lastPress) { // Check if M is pressed and that it was not previously pressed
            NUMS.MUSIC = !NUMS.MUSIC;
            NUMS.SFX = !NUMS.SFX;
        } else if (infoOn && (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("d"))) { // Remove info on key press
            removeObject(info);
            removeObject(leftKey);
            removeObject(rightKey);
            infoOn = false;
        }
        if (NUMS.MUSIC)
            bm.play();
        else
            bm.pause();
        lastPress = Greenfoot.isKeyDown("m");
        
        if (NUMS.SCORE >= 10000)        // Remove second cheat
            NUMS.EXPLODE = true;
        else if (NUMS.SCORE >= 7000)    // Remove one cheat
            NUMS.INFINITE_JUMPS = false;
        else if (NUMS.SCORE >= 5000) {
            NUMS.COLOR_SCHEME = Color.BLACK;
            NUMS.INFINITE_JUMPS = true;     // Use cheats to make game more playable
            NUMS.EXPLODE = false;
        }
        
        if (NUMS.SCORE >= 5000) { // After score is 5000       
            if (act % 120 == 0) // Flicker
                setBackground(Color.BLACK);
            else if (act % 120 == 90)
                setBackground(Color.WHITE);
            act++;
        } else if (NUMS.SCORE / 13 > last) {   // Color change every score increase of 50
            setBackground(bc.nextColor());
            last = NUMS.SCORE / 13;
        }
    
        score.updateText("Score: " + NUMS.SCORE);
        score.setLocation(NUMS.WORLD_WIDTH - (score.getWidth() >> 1) - 10, score.getY());
        if (movedAmount > NUMS.SPACING || NUMS.MORE_PLATFORMS) {    // Check is the platforms have moved down more than the max spacing
            if (NUMS.SPACING < (15 * 15 >> 2))                      // Displacement formula with acceleration
                NUMS.SPACING+=5;                                    // Increase Platform spacing
            movedAmount = 0;                                        // Reset
            createPlatforms(random(NUMS.WORLD_WIDTH - NUMS.PLATFORM_WIDTH - (NUMS.PLATFORM_WIDTH >> 1), NUMS.PLATFORM_WIDTH >> 1), NUMS.SCOREBAR_HEIGHT); // Make new platforms
            
            // Put Objects in front of new platform
            replace();
        }
        
        java.util.List<Platform> plats = getObjects(Platform.class);
        if ((mainCharacter.getY() > NUMS.WORLD_HEIGHT || gameEnd) && plats.size() > 0) {   // Check if character has fallen under the world
            bm.stop();
            if (NUMS.SFX)
                fx.play();
            gameEnd = true;
            mainCharacter.setStop();    // Stop person from moving
            mainCharacter.setLocation(mainCharacter.getX(), NUMS.WORLD_HEIGHT - (mainCharacter.getHeight() >> 1));
            setBackground(bc.transition());
            removeObject(leftKey);
            removeObject(rightKey);
            for (Platform plat : plats) { // Move all platforms up at removal speed
                plat.move(0, removalRate);
                if (plat.getY() < NUMS.SCOREBAR_HEIGHT)
                    plat.remove();
            }   
        } else if (plats.size() == 0) {
            bm.stop();
            Greenfoot.setWorld(new EndScreen(mainCharacter.getX()));
        } else 
            gameEnd = false;
            
        // Remove all platforms that are outside of the world after scrolling
        plats = getObjects(Platform.class); // Update List
        for (Object i : plats)
            if (((Platform) i).getY() + NUMS.PLATFORM_HEIGHT > NUMS.WORLD_HEIGHT) // Check if platform is below screen
                removeObject((Actor)i);
    }
    
    public void replace() {
        Actor[] actors = {mainCharacter, score, music, sfx, leftKey, rightKey, info};
        for (int i = 0; i < actors.length; i++) {
            if (i > 3 && !infoOn)
                return;
            int x = actors[i].getX(), y = actors[i].getY();
            removeObject(actors[i]);
            addObject(actors[i], x, y);
        }
    }
    
    private void setBackground(Color color) {   // Set Background to a color
        image.clear();
        image.setColor(color);
        image.fill();
        setBackground(image);
    }
    
    private Platform createPlatforms(Platform last) {   // Create round platforms everywhere on the screen Randomized x locations
        return createPlatforms(random(NUMS.WORLD_WIDTH - NUMS.PLATFORM_WIDTH, last.getWidth() >> 1), last.getY() - random(50, NUMS.SPACING));
    }
    
    private Platform createPlatforms(int x, int y) { // Create a rounded platform at (x,y)
        Platform plat;
        addObject(plat = new Platform(x), x, y);
        return plat;
    }
    
    private int random(int range, int start) {  // RNG
        java.security.SecureRandom sr = new java.security.SecureRandom();
        int num = sr.nextInt(range) + start;
        return num; 
    }
}