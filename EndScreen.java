import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Retro Themed End Screen
 */
public class EndScreen extends World {
    private Actor images[];
    private int[] finalPos = new int[4];        // Positions are relative to last position
    private Music bm;
    private boolean set = false, added[] = new boolean[4], lastPress;
    private int act = 0;
    private String typed = "";   // Hidden feature where you can type 'space' to start the game
    private Person mainCharacter;
    
    public EndScreen(int x) {
        super(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT, 1);
        removeObjects(getObjects(null));
        GreenfootImage image = new GreenfootImage(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT);
        image.setColor(Color.BLACK);
        image.fill();
        setBackground(image);
        
        images = new Actor[] {  new TextImages("EndText1.png", "YOU LOSE".length(), 80),
                                new TextImages("EndText2.png", "YOR SCORE WAS".length(), 25),
                                new Text(Integer.toString(NUMS.SCORE), Color.WHITE, 65),
                                new TextImages("EndText3.png", "PRESS SPACE TO PLAY AGAIN".length(), 20)};
        
        finalPos[3] = getHeight() - (((Images) images[3]).getHeight() << 1);
        finalPos[1] = finalPos[3] - ((getHeight() >> 1) + (((Images) images[1]).getHeight() << 1));
        finalPos[2] = finalPos[1] - ((Images) images[1]).getHeight() - 30;
        finalPos[0] = finalPos[3] - (getHeight() >> 3);
        
        mainCharacter = new Person();
        bm = new Music(new GreenfootSound("DuckTalesMoonTheme.mp3"));
        bm.loop();
        
        addObject(mainCharacter, x, NUMS.WORLD_HEIGHT - (mainCharacter.getHeight() >> 1));
        addObject(images[3], getWidth() >> 1, 0);
        added[3] = true;
    }
    
    public void act() {     // Maybe slide text into place 
        if (Greenfoot.isKeyDown("m") && !lastPress) {   // Mute music
            NUMS.MUSIC = !NUMS.MUSIC;
        }
        lastPress = Greenfoot.isKeyDown("m");   // Make sure only one keystroke is recorded
        
        if (act >= 150 && NUMS.MUSIC) { // Short timer
            bm.play();
        } else {
            bm.pause();
        }
            
        // Check for user input
        String key = Greenfoot.getKey();
        if (key != null && key.matches("[space]+"))
            typed += key;
        else if (key != null)
            typed = "";
        if (Greenfoot.isKeyDown("enter") || Greenfoot.isKeyDown("space") || typed.toLowerCase().equals("space")) {
            bm.stop();
            Greenfoot.setWorld(new JumpWorld());
        }
            
        if (!set) { // Set text slide from top
            for (int i = 2; i >= 0; i--) {
                if (images[3].getY() >= finalPos[i] && !added[i]) {
                    addObject(images[i], getWidth() >> 1, 0);
                    added[i] = true;
                }
            }
                
            if (images[3].getY() < finalPos[3]) {
                for (int i = 0; i < 4; i++) {
                    if (added[i])
                        images[i].setLocation(images[i].getX(), images[i].getY() + 7);
                }
            } else {
                set = true;
            }
        } else {
            removeObject(mainCharacter);
            if (act % 30 == 0)  // Blink effect
                removeObject(images[3]);
            else if (act % 30 == 10)
                addObject(images[3], getWidth() >> 1, finalPos[3]);   
        }
        act++;
    }
}
