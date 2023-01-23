package main;

import com.sun.source.tree.Tree;
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


public class GamePanel extends JPanel{
    private int frames = 0;
    public KeyboardInputs k = new KeyboardInputs(this);
    public MouseInputs mouseInputs = new MouseInputs(this);
    private long lastCheck = 0;
    public Rotator rotator;
    public Timer timer;
    static Player player ;
    boolean canMakeBullet = true;
    long lastBulletCheck = System.currentTimeMillis();
    static ConcurrentHashMap<Bullet, Integer> bullets = new ConcurrentHashMap<Bullet, Integer>();
    static ConcurrentHashMap<Enemy, Integer> enemies = new ConcurrentHashMap<>();
    static int score = 0;
    public static boolean gamePaused = false;
    public boolean lbToggled = false;
    private Random r = new Random();
    public GamePanel() throws IOException {
        initializeLeaderboard();
        this.setBackground(new Color(144,238,144));
        player = new Player(GameConstant.PLAYER_INIT_POSX, GameConstant.PLAYER_INIT_POSY);
        player.crop();
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
                    endGame();
                    updateLeaderboard();
                    saveLeaderboard();
                }
                player.setXpos(GameConstant.SCREEN_MAX_WIDTH/2);
                player.setYpos(GameConstant.SCREEN_MAX_HEIGHT/2);
            }
        }
    }
    public Player getPlayer(){
        return player ;
    }
    public void makeBullet(int cursorX, int cursorY) throws IOException {
        bullets.put(new Bullet(player.getPlayerX(), player.getPlayerY(), Math.toDegrees(Math.atan2(cursorY - player.getYpos(), cursorX - player.getXpos()))), 0);
    }
    public void endGame(){

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
    static TreeMap<Integer, TreeSet<String>> leaderBoard = new TreeMap<>(Collections.reverseOrder());
    static HashSet<String> names = new HashSet<>();
    public void initializeLeaderboard() throws FileNotFoundException {
        System.out.println(new File("leaderboard.txt").exists());
        Scanner in = new Scanner(new File("leaderboard.txt"));
        if (!in.hasNextInt()){
            return;
        }
        int differentScores = in.nextInt();
        for (int i = 0; i < differentScores; i++){
            int first = in.nextInt();
            TreeSet<String> temp = new TreeSet<>();
            for (int j = 0; j < first; j++){
                int second = in.nextInt();
                temp.add(in.next());
                leaderBoard.put(second, temp);
            }
        }
        in.close();
    }
    public void saveLeaderboard() throws FileNotFoundException {
        File leaderboard = new File("leaderboard.txt");
        PrintWriter pw = new PrintWriter(leaderboard);
        pw.write(leaderBoard.size() + " ");
        for (int i : leaderBoard.keySet()){
            pw.write(leaderBoard.get(i).size() + " " + i + " ");
            for (String j : leaderBoard.get(i)){
                pw.write(j + " ");
            }
        }
        pw.close();
    }
    public void printLeaderboard(Graphics g){
        g.setFont(new Font("Arial", Font.BOLD, 100));
        g.drawString("Leaderboard", GameConstant.SCREEN_MAX_WIDTH/2 - 350, 100);
        int printed = 0;
        int yPrint = 200;
        while (printed < Math.min(5, leaderBoard.size())){
            for (int score : leaderBoard.keySet()) {
                for (String player : leaderBoard.get(score)) {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 30));
                    g.drawString(String.format("%10s %3s", player, score), GameConstant.SCREEN_MAX_WIDTH / 2 - 350, yPrint);
                        yPrint += 50;
                        printed++;
                }
            }
        }
    }
    public void updateLeaderboard(){
        String input = JOptionPane.showInputDialog(null, "Enter your username:");
        while (input == null || input.isEmpty()){
            input = JOptionPane.showInputDialog(null, "You have to pick a name:");
            if (input.isEmpty()){
                break;
            }
        }
        if (names.contains(input)){
            int originalScore = 0;
            for (int lbScore : leaderBoard.keySet()){
                for (String person : leaderBoard.get(lbScore)){
                    if (person.equals(input)){
                        originalScore = lbScore;
                        break;
                    }
                }
            }
            if (originalScore < score){
                leaderBoard.get(originalScore).remove(input); //removes person from that old score if they previously got a higher one
            }
        }
        if (leaderBoard.containsKey(score)){
            leaderBoard.get(score).add(input);
        }
        else{
            TreeSet<String> tempSet = new TreeSet<>();
            tempSet.add(input);
            leaderBoard.put(score, tempSet);
        }
    }
    public static void showEndScreen(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(100, 100, GameConstant.SCREEN_MAX_WIDTH-200, GameConstant.SCREEN_MAX_HEIGHT-200);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        g.drawString("Game Finished!", GameConstant.SCREEN_MAX_WIDTH/2 - 350, GameConstant.SCREEN_MAX_HEIGHT/2 - 200);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Press R to play again", GameConstant.SCREEN_MAX_WIDTH/2 - 265, GameConstant.SCREEN_MAX_HEIGHT/2);
        g.drawString("Press L to show leaderboard", GameConstant.SCREEN_MAX_WIDTH/2 - 325, GameConstant.SCREEN_MAX_HEIGHT/2 + 200);
    }
    public static void showPauseScreen(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        g.drawString("Game Paused!", GameConstant.SCREEN_MAX_WIDTH/2 - 350, GameConstant.SCREEN_MAX_HEIGHT/2);
        g.setColor(Color.darkGray);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Press Esc to Unpause", GameConstant.SCREEN_MAX_WIDTH/2 - 275, GameConstant.SCREEN_MAX_HEIGHT/2 + 200);
    }
    public static void showGamestuff(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 50, 50);
        g.drawString("Lives: " + player.playerLives, 300, 50);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(player.getLives() > 0 && !gamePaused()){
            double angle = Math.toDegrees(Math.atan2(mouseInputs.cursorY - player.getYpos(), mouseInputs.cursorX - player.getXpos())) + 90;
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
                if (enemies.size() != 0 && enemies.get(i) == 0){ //have to check if the enemies concurrenthashmap is greater than 0 as it creates an exception when 2 threads are running and the player dies (hashmap gets reset to a new one)
                    enemies.remove(i);
                    score++;
                }
                try {
                    checkPlayerEnemyCollision();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            frames++;
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
            showGamestuff(g);
        }
        else if (gamePaused()){
            showPauseScreen(g);
        }
        else if (returnGameEnd()){
            if (lbToggled) {
                printLeaderboard(g);
            } else {
                showEndScreen(g);
            }
        }
    }
}
