package main;

import java.awt.*;

/**
 * Holds all the game constants
 * @author Alan Liu
 */
public class GameConstant {
    public static final String PLAYER_IMAGE_PATH = "src/res/tank.png";
    public static final String LEADERBOARD_FILE_NAME = "C:\\leaderboard.txt";
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_MAX_HEIGHT = (int) SCREEN_SIZE.getHeight() ;
    public static final int SCREEN_MAX_WIDTH = (int) SCREEN_SIZE.getWidth();
    public static final int DELAY_BULLET = 500;
    public static final int PLAYER_INIT_POSX = SCREEN_MAX_WIDTH/2;
    public static final int PLAYER_INIT_POSY = SCREEN_MAX_HEIGHT/2;
    public static final int CHECK_DURATION = 1000 ; //Milliseconds
    public static final double ENEMY_SPEED = 0.5;
    public static final int ENEMY_HEALTH = 3;
    public static final int DELAY = 25;
    public static final int FPS_SET = 500;
    public static final int INIT_PLAYER_LIVES = 5;
    public static final long WAIT = 20;

    public GameConstant() {
    }
}
