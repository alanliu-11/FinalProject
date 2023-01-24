package main;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import main.gameobjects.Bullet;
import main.gameobjects.Enemy;
import main.gameobjects.Player;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Essential control panel for all actions that occur in game
 * @author Alan Liu
 */
public class GamePanel extends JPanel{
    public KeyboardInputs k = new KeyboardInputs(this);
    public MouseInputs mouseInputs = new MouseInputs();
    private long lastCheck = 0;
    public Timer timer;
    static Player player ;
    boolean canMakeBullet = true;
    long lastBulletCheck = System.currentTimeMillis();
    static ConcurrentHashMap<Bullet, Integer> bullets = new ConcurrentHashMap<>();
    static ConcurrentHashMap<Enemy, Integer> enemies = new ConcurrentHashMap<>();
    static int score = 0;
    public static boolean gamePaused = false;
    public boolean lbToggled = false;
    private final Random r = new Random();
    public Leaderboard leaderboard = new Leaderboard();
    public GamePanel() throws IOException {
        leaderboard.initializeLeaderboard();
        this.setBackground(new Color(144,238,144));
        player = new Player(GameConstant.PLAYER_INIT_POSX, GameConstant.PLAYER_INIT_POSY);
        player.initializeImage();
        addKeyListener(k);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        timer = new Timer(GameConstant.DELAY, e -> {
            repaint();
            k.update();
        });
        timer.start();
    }
    public void changePlayerX(int value){
        player.setXpos(player.getXpos() + value);
        repaint();
    }
    public void changePlayerY(int value){
        player.setYpos(player.getYpos() + value);
        repaint();
    }
    public int getLives(){
        return player.playerLives;
    }

    /**
     * Checks for Collision between each enemy and each bullet using the pythagorean theorem
     * Uses the radii of each circle to see if the radii intersect, and if they do then the bullet is touching the enemy, therefore the enemy is hit.
     */
    private void checkBulletEnemyCollision(Enemy i, Bullet j){
        Ellipse2D.Double bulletCircle = new Ellipse2D.Double(j.x, j.y, 10, 10);
        Ellipse2D.Double enemyCircle = new Ellipse2D.Double(i.x, i.y, 50, 50);
        double x1 = bulletCircle.getX() + bulletCircle.getWidth()/2;
        double y1 = bulletCircle.getY() + bulletCircle.getHeight()/2;
        double x2 = enemyCircle.getX() + enemyCircle.getWidth()/2;
        double y2 = enemyCircle.getY() + enemyCircle.getHeight()/2;
        double radius1 = bulletCircle.getWidth()/2;
        double radius2 = enemyCircle.getWidth()/2;
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        if (distance <= radius1 + radius2) {
            i.health--;
            bullets.remove(j);
            enemies.put(i, enemies.get(i)-1);
        }
    }
    /**
     * Same concept as the last method but checks if the player and the enemy intersect.
     * When the enemy touches the player, the player dies and lives decrements by 1.
     */
    private void checkPlayerEnemyCollision() throws FileNotFoundException {
        Ellipse2D.Double playerCircle = new Ellipse2D.Double(player.getXpos() - (double)player.getWidth()/2, player.getYpos() - (double)player.getHeight()/2, player.getWidth()-50, player.getHeight()-50);
        for (Enemy enemy : enemies.keySet()) {
            Ellipse2D.Double enemyCircle = new Ellipse2D.Double(enemy.x - 25, enemy.y - 25, 50, 50);
            double distance = Math.sqrt(Math.pow(enemyCircle.getCenterX() - playerCircle.getCenterX(), 2) + Math.pow(enemyCircle.getCenterY() - playerCircle.getCenterY(), 2));
            double radiusSum = (playerCircle.getWidth() + enemyCircle.getWidth()) / 2;
            if (distance <= radiusSum) {
                enemies = new ConcurrentHashMap<>();
                player.loseLife();
                if(player.getLives() == 0){
                    leaderboard.updateLeaderboard(score);
                    leaderboard.saveLeaderboard();
                }
                player.setXpos(GameConstant.PLAYER_INIT_POSX);
                player.setYpos(GameConstant.PLAYER_INIT_POSY);
            }
        }
    }
    public Player getPlayer(){
        return player ;
    }
    public void makeBullet(int cursorX, int cursorY) throws IOException {
        bullets.put(new Bullet(player.getPlayerX(), player.getPlayerY(), Math.toDegrees(Math.atan2(cursorY - player.getYpos(), cursorX - player.getXpos()))), 0);
    }
    public void pauseGame(){
        gamePaused = true;
    }
    public void unpauseGame(){
        gamePaused = false;
    }
    public boolean gamePaused(){
        return gamePaused;
    }
    public boolean returnGameEnd(){
        return player.getLives() == 0;
    }
    public void toggleLB(){
        lbToggled = !lbToggled;
    }
    public void resetGame(){
        lbToggled = false;
        player.setPlayerLives(5);
        score = 0;
        enemies = new ConcurrentHashMap<>();
    }

    /**
     * This is an essential method that draws the graphics on the screen using the Graphics class
     * All essential things that require drawing on the screen occur here
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(player.getLives() > 0 && !gamePaused()){
            //Calculates the angle between the player and mouse. It adds 90 because originally the player is facing up
            double angle = Math.toDegrees(Math.atan2(mouseInputs.cursorY - player.getYpos(), mouseInputs.cursorX - player.getXpos())) + 90;
            g.drawImage(player.rotate(angle), player.getXpos() - player.getWidth()/2, player.getYpos()- player.getHeight()/2, null);
            for (Bullet i : bullets.keySet()){
                i.update();
                if (i.outOfBounds()){
                    bullets.remove(i);
                }
                i.draw(g);
            }
            for (Enemy i : enemies.keySet()){
                i.draw(g);
                i.update(player);
                for(Bullet j : bullets.keySet()){
                    checkBulletEnemyCollision(i,j);
                }
                if (enemies.size() != 0 && enemies.get(i) == 0){ //have to check if the enemies ConcurrentHashmap is greater than 0 as it creates an exception when 2 threads are running and the player dies (hashmap gets reset to a new one)
                    enemies.remove(i);
                    score++;
                }
                try {
                    checkPlayerEnemyCollision();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!canMakeBullet && System.currentTimeMillis() - lastBulletCheck > GameConstant.DELAY_BULLET){
                lastBulletCheck = System.currentTimeMillis();
                canMakeBullet = true;
            }
            else if (canMakeBullet && mouseInputs.returnMouseDown()){
                try {
                    makeBullet(mouseInputs.cursorX, mouseInputs.cursorY);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                canMakeBullet = false;
            }
            if (System.currentTimeMillis() - lastCheck >= GameConstant.CHECK_DURATION){
                lastCheck = System.currentTimeMillis();
                try {
                    //creates a random spawn point for the enemy it can spawn in either one of the four corners of the screen.
                    int enemySpawnX;
                    int enemySpawnY;
                    int next = r.nextInt(4);
                    switch(next){
                        case 0:
                            enemySpawnX = 0;
                            enemySpawnY = 0;
                            break;
                        case 1:
                            enemySpawnX = GameConstant.SCREEN_MAX_WIDTH;
                            enemySpawnY = GameConstant.SCREEN_MAX_HEIGHT;
                        break;
                        case 2:
                            enemySpawnX = 0;
                            enemySpawnY = GameConstant.SCREEN_MAX_HEIGHT;
                            break;
                        default:
                            enemySpawnX = GameConstant.SCREEN_MAX_WIDTH;
                            enemySpawnY = 0;
                            break;
                    }
                    enemies.put(new Enemy(enemySpawnX, enemySpawnY), GameConstant.ENEMY_HEALTH);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            GameComponents.showGameStuff(g, score, getPlayer());
        }
        else if (gamePaused()){
            GameComponents.showPauseScreen(g);
        }
        else if (returnGameEnd()){
            if (lbToggled) {
                leaderboard.printLeaderboard(g);
            } else {
                GameComponents.showEndScreen(g);
            }
        }
    }
}
