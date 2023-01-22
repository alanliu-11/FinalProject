package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import main.gameobjects.Bullet;
import main.gameobjects.Enemy;
import main.gameobjects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;


public class GamePanel extends JPanel{
    private int frames = 0;
    public KeyboardInputs k = new KeyboardInputs(this);
    public MouseInputs mouseInputs = new MouseInputs(this);
    private long lastCheck = 0;
    public Rotator rotator;
    public Timer timer;
    Player player ;
    boolean canMakeBullet = true;
    long lastBulletCheck = System.currentTimeMillis();
    static ConcurrentHashMap<Bullet, Integer> bullets = new ConcurrentHashMap<Bullet, Integer>();
    static ConcurrentHashMap<Enemy, Integer> enemies = new ConcurrentHashMap<>();
    static int playerLives = 5;
    static int score = 0;
    public static boolean gamePaused = false;
    public GamePanel() throws IOException {
        this.player = new Player(GameConstant.PLAYER_INIT_POSX, GameConstant.PLAYER_INIT_POSY);
        this.player.crop();
        addKeyListener(k);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        rotator = new Rotator();
        timer = new Timer(GameConstant.DELAY, e -> {
            repaint();
            k.update();
        });
        timer.start();
    }
    public void changePlayerX(int value){
        this.player.setXpos(this.player.getXpos() + value);
        repaint();
    }
    public void changePlayerY(int value){
        this.player.setYpos(this.player.getYpos() + value);
        repaint();
    }

    public void setPlayerPos(int x, int y){
        this.player.setXpos(x);
        this.player.setYpos(y);
    }
    private void checkIntersect(Enemy i, Bullet j){
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
    private void checkPlayerEnemyCollision() {
        Ellipse2D.Double playerCircle = new Ellipse2D.Double(player.getXpos() - (double)player.getWidth()/2, player.getYpos() - (double)player.getHeight()/2, player.getWidth(), player.getHeight());
        for (Enemy enemy : enemies.keySet()) {
            Ellipse2D.Double enemyCircle = new Ellipse2D.Double(enemy.x - 25, enemy.y - 25, 50, 50);
            double distance = Math.sqrt(Math.pow(enemyCircle.getCenterX() - playerCircle.getCenterX(), 2) + Math.pow(enemyCircle.getCenterY() - playerCircle.getCenterY(), 2));
            double radiusSum = (playerCircle.getWidth() + enemyCircle.getWidth()) / 2;
            if (distance <= radiusSum) {
                playerLives--;
                if(playerLives == 0){
                    endGame();
                }
                player.setXpos(GameConstant.SCREEN_MAX_WIDTH/2);
                player.setYpos(GameConstant.SCREEN_MAX_HEIGHT/2);
                enemies = new ConcurrentHashMap<>();
            }
        }
    }
    public Player getPlayer(){
        return this.player ;
    }
    public void makeBullet(int cursorX, int cursorY) throws IOException {
        bullets.put(new Bullet(player.getPlayerX(), player.getPlayerY(), Math.toDegrees(Math.atan2(cursorY - player.getYpos(), cursorX - player.getXpos()))), 0);
    }
    public void endGame(){

    }
    public void pauseGame(){
        int tempLives = playerLives;
        playerLives = 0;
        gamePaused = true;
    }
    public boolean gamePaused(){
        return gamePaused;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(playerLives != 0){
            double angle = Math.toDegrees(Math.atan2(mouseInputs.cursorY - player.getYpos(), mouseInputs.cursorX - player.getXpos())) + 90;
            //draw player
            g.drawImage(rotator.rotate(player.getPlayerImage(), angle), player.getXpos() - player.getWidth()/2, player.getYpos()- player.getHeight()/2, null);
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
                    checkIntersect(i,j);
                }
                if (enemies.get(i) == 0){
                    enemies.remove(i);
                    score++;
                }
                checkPlayerEnemyCollision();
            }
            frames++;
            if (!canMakeBullet && System.currentTimeMillis() - lastBulletCheck > GameConstant.DELAY_BULLET){
                lastBulletCheck = System.currentTimeMillis();
                canMakeBullet = true;
            }
            else if (canMakeBullet && mouseInputs.returnMouseDown()){
                try {
                    makeBullet(mouseInputs.cursorX, mouseInputs.cursorY);
                    System.out.println("bullet made");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                canMakeBullet = false;
            }
            if (System.currentTimeMillis() - lastCheck >= GameConstant.CHECK_DURATION){
                lastCheck = System.currentTimeMillis();
                frames = 0;
                try {
                    enemies.put(new Enemy(100, 100), GameConstant.ENEMY_HEALTH);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
