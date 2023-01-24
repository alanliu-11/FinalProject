package main;

import main.gameobjects.Player;
import java.awt.*;

/**
 * Displays important components in the game, such as the endscreen, pause screen, and lives/score in the game.
 */
public class GameComponents {
    public static void showEndScreen(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(100, 100, GameConstant.SCREEN_MAX_WIDTH-200, GameConstant.SCREEN_MAX_HEIGHT-200);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        g.drawString("Game Finished!", GameConstant.SCREEN_MAX_WIDTH/2 - 350, GameConstant.SCREEN_MAX_HEIGHT/2 - 200);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Press R to play again", GameConstant.SCREEN_MAX_WIDTH/2 - 275, GameConstant.SCREEN_MAX_HEIGHT/2);
        g.drawString("Press L to show leaderboard", GameConstant.SCREEN_MAX_WIDTH/2 - 325, GameConstant.SCREEN_MAX_HEIGHT/2 + 200);
        g.drawString("Press P to exit", GameConstant.SCREEN_MAX_WIDTH/2 - 325, GameConstant.SCREEN_MAX_HEIGHT/2 + 400);
    }
    public static void showPauseScreen(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        g.drawString("Game Paused!", GameConstant.SCREEN_MAX_WIDTH/2 - 350, GameConstant.SCREEN_MAX_HEIGHT/2);
        g.setColor(Color.darkGray);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Press Esc to Unpause, press P to exit the application", GameConstant.SCREEN_MAX_WIDTH/2 - 625, GameConstant.SCREEN_MAX_HEIGHT/2 + 200);
    }
    public static void showGameStuff(Graphics g, int score, Player player){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 50, 50);
        g.drawString("Lives: " + player.playerLives, 300, 50);
    }
}
