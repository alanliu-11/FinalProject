package main.gameobjects;

import java.awt.image.BufferedImage;

public abstract class BaseObject {
    private int xPos;
    private int yPos;
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

    public void setImage(BufferedImage image) {
        this.playerImage = image;
    }


    //different objects have different outofbounds logic that needs to be implemented in each child class
    public abstract boolean outOfBounds();

}
