import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Retro Themed End Screen
 */
public class EndScreen extends World
{
    private Text[] txt =   {new Text("YOU LOSE", Color.RED, 100),
                            new Text("Your Score Was", Color.WHITE, 50),
                            new Text(Integer.toString(NUMS.SCORE), Color.WHITE, 70),
                            new Text("Press Space to Play Again", Color.BLUE, 40)};
    private int[] finalPos = new int[4];        // Positions are relative to last position
    private Music bm;
    private boolean set = false, added[] = new boolean[4];
    private int act = 0;
    private String typed = "";   // Hidden feature where you can type 'space' to start the game
    
    public EndScreen() {
        super(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT, 1);
        removeObjects(getObjects(null));
        GreenfootImage image = new GreenfootImage(NUMS.WORLD_WIDTH, NUMS.WORLD_HEIGHT);
        image.setColor(Color.BLACK);
        image.fill();
        setBackground(image);
        
        finalPos[3] = getHeight() * 3 >> 2;
        finalPos[1] = finalPos[3] - (getHeight() >> 1);
        finalPos[2] = finalPos[1] - txt[1].getHeight();
        finalPos[0] = finalPos[3] - (getHeight() >> 2);
        
        bm = new Music(new GreenfootSound("DuckTalesMoonTheme.mp3"));
        
        addObject(txt[3], getWidth() >> 1, 0);
        added[3] = true;
    }
    
    public void act() {     // Maybe slide text into place 
        bm.loop();
        bm.play();
        if (Greenfoot.isKeyDown("m"))
            NUMS.MUSIC = !NUMS.MUSIC;
            
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
            if (txt[3].getY() >= finalPos[2] && !added[2]) {
                addObject(txt[2], getWidth() >> 1, 0);
                added[2] = true;
            } else if (txt[3].getY() >= finalPos[1]&& !added[1]) {
                addObject(txt[1], getWidth() >> 1, 0);
                added[1] = true;
            } else if (txt[3].getY() >= finalPos[0] && !added[0]) {
                addObject(txt[0], getWidth() >> 1, 0);
                added[0] = true;
            }
                
            if (txt[3].getY() < finalPos[3]) {
                for (int i = 0; i < 4; i++) {
                    if (added[i])
                        txt[i].setLocation(txt[i].getX(), txt[i].getY() +7);
                }
            } else {
                set = true;
            }
        } else {
            if (act % 30 == 0)  // Blink effect
                removeObject(txt[3]);
            else if (act % 30 == 10)
                addObject(txt[3], getWidth() >> 1, getHeight() * 3 >> 2);   
            act++;
        }
    }
}
