package main;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GameConstant {

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int SCREEN_MAX_HEIGHT = (int) screenSize.getHeight() ;
    public static int SCREEN_MAX_WIDTH = (int) screenSize.getWidth();
    public static final int DELAY_BULLET = 500;
    public static int PLAYER_INIT_POSX = SCREEN_MAX_WIDTH/2;
    public static int PLAYER_INIT_POSY = SCREEN_MAX_HEIGHT/2;

    public static int CHECK_DURATION = 1000 ; //Milliseconds
    public static final double ENEMY_SPEED = 0.5;
    public static final int ENEMY_HEALTH = 2;
    public static final int DELAY = 25;

    public static final int FPS_SET = 500;
    public final PrintWriter pr = new PrintWriter("leaderboard");

    public GameConstant() throws FileNotFoundException {
    }
}
