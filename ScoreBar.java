import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class ScoreBar extends Rectangle {
    private GreenfootImage textImage, textBox;
    public ScoreBar() {
        super(NUMS.WORLD_WIDTH, NUMS.SCOREBAR_HEIGHT, 100, greenfoot.Color.LIGHT_GRAY, true);
        textImage = new GreenfootImage("Score: 0", 3 * (NUMS.SCOREBAR_HEIGHT >> 2), Color.BLACK, Color.BLACK);
        textBox = new GreenfootImage(textImage.getWidth()+12, 36);
        textBox.setColor(new Color(196, 196, 0));
        textBox.fill();
        textImage.drawImage(textBox, textBox.getWidth() - (textBox.getWidth() >> 1), textBox.getHeight() - (textBox.getHeight() >> 1));
    }
    
    public void act() {
        textImage = new GreenfootImage("Score: " + NUMS.SCORE, 3 * (NUMS.SCOREBAR_HEIGHT >> 2), Color.BLACK, Color.BLACK);
        textImage.drawImage(textBox, textBox.getWidth() - (textBox.getWidth() >> 1), textBox.getHeight() - (textBox.getHeight() >> 1));
    }
}
