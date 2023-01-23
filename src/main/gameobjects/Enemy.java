package main.gameobjects;

import java.awt.*;

import main.GameConstant;
import java.awt.*;
import java.io.IOException;

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

    @Override
    public boolean outOfBounds() {
        return false;
    }

    @Override
    public void step() {

    }
}