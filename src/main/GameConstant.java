package main;

import java.awt.*;

public class GameConstant {
    public static final String playerImagePath = "src/res/tank.png";
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_MAX_HEIGHT = (int) screenSize.getHeight() ;
    public static final int SCREEN_MAX_WIDTH = (int) screenSize.getWidth();
    public static final int DELAY_BULLET = 500;
    public static final int PLAYER_INIT_POSX = SCREEN_MAX_WIDTH/2;
    public static final int PLAYER_INIT_POSY = SCREEN_MAX_HEIGHT/2;
    public static final int CHECK_DURATION = 1000 ; //Milliseconds
    public static final double ENEMY_SPEED = 0.5;
    public static final int ENEMY_HEALTH = 3;
    public static final int DELAY = 25;

    public static final int FPS_SET = 500;
    public static final int INIT_PLAYER_LIVES = 5;
    public static final long wait = 20;

    public GameConstant() {
    }
}
