package main.gameobjects;

import main.Game;
import main.GameConstant;

import java.awt.*;
import java.io.IOException;

public class Bullet extends BaseObject {
    public double x; public double y;
    final public double angle; //angle in degrees, set at the start
    public Bullet(double x, double y, double angle) throws IOException {
        this.x = x; this.y = y; this.angle = angle;
    }
    public void update(){
        x += 2 * Math.cos(Math.toRadians(angle));
        y += 2 * Math.sin(Math.toRadians(angle));
    }
    public void draw(Graphics g){
        g.fillOval((int) x,(int) y, 10, 10);
    }

    @Override
    public boolean outOfBounds() {
        return getXpos() < -100 || getXpos() > GameConstant.SCREEN_MAX_WIDTH + 100 || getYpos() < -100 || getYpos() >  GameConstant.SCREEN_MAX_HEIGHT + 100;
    }

    @Override
    public void step() {

    }
}
