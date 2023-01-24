package main;

import java.io.IOException;

/**
 * This is the Game class, everything is being run here. It implements the runnable interface, which creates a thread
 * @author Alan Liu
 */
public class Game implements Runnable{
    private final static GamePanel gamePanel;

    static {
        try {
            gamePanel = new GamePanel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameWindow gameWindow = new GameWindow(gamePanel);


    public Game(){
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / GameConstant.FPS_SET;
        long lastFrame = System.nanoTime();
        long now;
        while (true) {
            if(!gamePanel.gamePaused()){
                now = System.nanoTime();
                if (now - lastFrame >= timePerFrame) {
                    gamePanel.repaint();
                    lastFrame = now;
                    gamePanel.k.update();
                }
            }
        }

    }
}
