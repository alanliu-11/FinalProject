package main.gameobjects;

import main.GameConstant;
import java.awt.*;

public class Bullet extends BaseObject {
    public double x; public double y;
    final public double angle; //angle in degrees, set at the start
    public Bullet(double x, double y, double angle) {
        this.x = x; this.y = y; this.angle = angle;
    }

    /**
     * updates the position of the bullet, called in the gamePanel class
     */
    public void update(){
        x += 2 * Math.cos(Math.toRadians(angle));
        y += 2 * Math.sin(Math.toRadians(angle));
    }
    public void draw(Graphics g){
        g.fillOval((int) x,(int) y, 10, 10);
    }

    @Override
    public boolean outOfBounds() {
        //gives a little extra room just to make sure the bullet doesn't get deleted until fully out of screen
        return getXpos() < -100 || getXpos() > GameConstant.SCREEN_MAX_WIDTH + 100 || getYpos() < -100 || getYpos() >  GameConstant.SCREEN_MAX_HEIGHT + 100;
    }

}
