package main;



import java.io.IOException;

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
    private Thread gameThread;


    public Game(){
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / GameConstant.FPS_SET;
        long lastFrame = System.nanoTime();
        long now;
        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {
            if(!gamePanel.gamePaused()){
                now = System.nanoTime();
                if (now - lastFrame >= timePerFrame) {
                    gamePanel.repaint();
                    lastFrame = now;
                    gamePanel.k.update();
                    frames++;
                }
                if (System.currentTimeMillis() - lastCheck >= 1000) {
                    lastCheck = System.currentTimeMillis();
                    frames = 0;
                }
            }
        }

    }
}
