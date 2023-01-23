package inputs;


import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener{
    private final GamePanel gamePanel;
    private final boolean[] flag = new boolean[4];
    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                flag[0] = true;
                break;
            case KeyEvent.VK_A:
                flag[1] = true;
                break;
            case KeyEvent.VK_S:
                flag[2] = true;
                break;
            case KeyEvent.VK_D:
                flag[3] = true;
                break;
            case KeyEvent.VK_ESCAPE:
                if (gamePanel.getLives() == 0) {
                    break;
                }
                if (gamePanel.gamePaused()) {
                    gamePanel.unpauseGame();
                    System.out.println("game unpaused");
                } else {
                    gamePanel.pauseGame();
                    System.out.println("game paused");
                }
                break;
            case KeyEvent.VK_R:
                if (gamePanel.returnGameEnd()){
                    gamePanel.resetGame();
                }
                break;
            case KeyEvent.VK_L:
                if (gamePanel.returnGameEnd()){
                    gamePanel.toggleLB();
                    gamePanel.repaint();
                }
                break;
            case KeyEvent.VK_P:
                if (gamePanel.gamePaused()|| gamePanel.returnGameEnd()){
                    System.exit(0);
                }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                flag[0] = false;
                break;
            case KeyEvent.VK_A:
                flag[1] = false;
                break;
            case KeyEvent.VK_S:
                flag[2] = false;
                break;
            case KeyEvent.VK_D:
                flag[3] = false;
                break;
        }
    }
    public void update(){
        if (inBounds(gamePanel.getPlayer().getPlayerX(), gamePanel.getPlayer().getPlayerY() - 1) && flag[0]){
            gamePanel.changePlayerY(-1);
        }
        if (inBounds(gamePanel.getPlayer().getPlayerX() - 1, gamePanel.getPlayer().getPlayerY()) && flag[1]){
            gamePanel.changePlayerX(-1);
        }
        if (inBounds(gamePanel.getPlayer().getPlayerX(), gamePanel.getPlayer().getPlayerY() + 1) && flag[2]){
            gamePanel.changePlayerY(1);
        }
        if (inBounds(gamePanel.getPlayer().getPlayerX() + 1, gamePanel.getPlayer().getPlayerY()) && flag[3]){
            gamePanel.changePlayerX(1);
        }
    }
    public boolean inBounds(double playerX, double playerY){
        return playerX + 50 < gamePanel.getSize().width && playerX > 0 && playerY > 0 && playerY + 50 < gamePanel.getSize().height;
    }
}
