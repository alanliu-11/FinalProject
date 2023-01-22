package main.gameobjects;

import main.gameobjects.Bullet;

import java.awt.image.BufferedImage;

public abstract class BaseObject {
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    protected BufferedImage image;
    BufferedImage playerImage;

    public int getXpos() {
        return xPos;
    }

    public void setXpos(int x) {
        this.xPos = x;
    }

    public int getYpos() {
        return yPos;
    }

    public void setYpos(int ypos) {
        this.yPos = ypos;
    }

    public int getWidth() {
        return this.playerImage.getWidth();
    }

    public int getHeight() {
        return this.playerImage.getHeight();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.playerImage = image;
    }


    public abstract boolean outOfBounds();

    public abstract void step();

}
