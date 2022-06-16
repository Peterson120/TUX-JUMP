class NUMS  {   // Class of constants and globals variables
    final static int WORLD_WIDTH = 700;                         // Viewable World Width
    final static int WORLD_HEIGHT = 900;                        // Viewable World Height
    final static int THRESHOLD = NUMS.WORLD_HEIGHT * 5 >> 3;    // Scroll Threshold
    final static int PLATFORM_WIDTH = 100;                      // Width of the platform
    final static int PLATFORM_HEIGHT = 20;                      // Height of the platform
    final static int SPAWN_SCORE = 200;                         // Height to start spawning moving platforms
    final static int MAX_TRANSPARENCY = 255;                    // Numeric value to create a solid, obselete object
    final static int SCOREBAR_HEIGHT = 75;                      // Score Bar Height
    final static greenfoot.Color SCORE_COLOR = new greenfoot.Color(255, 253, 208);  // Color of the Score text
    static greenfoot.Color COLOR_SCHEME = new greenfoot.Color(255, 253, 208);       // Color of the platform
    static int SPACING = 40;                                    // Spacing between Platforms
    static boolean MUSIC = true;                                // Enable music
    static int SCORE = 0;                                       // Current Score
    
    static boolean EXPLODE = true;                              // Allow exploding platforms
    static boolean INFINITE_JUMPS = true;                      // Allow Infinite Jump Platforms
    static boolean MORE_PLATFORMS = false;                      // LOTS OF PLATFORMS!!
    static boolean FLIGHT = false;                              // Allow Flight
}