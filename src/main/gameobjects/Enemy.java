package main.gameobjects;

import java.awt.*;
import main.GameConstant;
import java.io.IOException;

/**
 * Enemy class
 * @author Alan Liu
 */
public class Enemy extends BaseObject {
    public double x; public double y;
    public int health;
    public Enemy(double x, double y) throws IOException {
        this.x = x; this.y = y;
    }
    public void update(Player player){
        double angle = Math.atan2(player.getYpos() - this.y, player.getXpos() - this.x);
        this.x += GameConstant.ENEMY_SPEED * Math.cos(angle);
        this.y += GameConstant.ENEMY_SPEED * Math.sin(angle);
    }
    public void draw(Graphics g){
        g.setColor(new Color(139,0,0));
        g.fillOval((int) x,(int) y, 50, 50);
    }

    /**
     *The enemy will always be chasing the player, which can't go out of bounds. Therefore the enemy will never completely go out of bounds
     */
    @Override
    public boolean outOfBounds() {
        return false;
    }

}