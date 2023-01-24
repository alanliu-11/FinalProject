package inputs;


import main.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Alan Liu
 * Under is the KeyboardInputs class which contains all the keyboard control logic
 */
public class KeyboardInputs implements KeyListener{
    private final GamePanel gamePanel;
    //flag is which keys are pressed when the timer is checked (0 for w, 1 for a, 2 for s, 3 for d)
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
                } else {
                    gamePanel.pauseGame();
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
                //if game is paused or game is finished and the p key is pressed, exit the program.
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

    /**
     * This function calls gamePanel to change the player x and y values when the w, a, s, and d keys are pressed.
     */
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

    /**
     *Checks if player is in bounds of the screen
     */
    public boolean inBounds(double playerX, double playerY){
        return playerX + 50 < gamePanel.getSize().width && playerX > 0 && playerY > 0 && playerY + 50 < gamePanel.getSize().height;
    }
}
