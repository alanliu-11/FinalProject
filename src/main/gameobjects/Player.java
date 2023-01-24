package main.gameobjects;

import main.GameConstant;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Player class
 * @author Alan Liu
 */
public class Player extends BaseObject {
    public int playerLives = GameConstant.INIT_PLAYER_LIVES;
    public Player(int posx, int posy){
        setXpos(posx);
        setYpos(posy) ;
    }


    public BufferedImage getPlayerImage(){
        return playerImage;
    }

    /**
     * this is called when the image is initialized in GamePanel
     */
    public void initializeImage() throws IOException {
        BufferedImage image = ImageIO.read(new File(GameConstant.PLAYER_IMAGE_PATH));
        this.setImage(image);
    }

    @Override
    public boolean outOfBounds(){
        return this.getYpos() > GameConstant.SCREEN_MAX_HEIGHT - getHeight() || this.getXpos() > GameConstant.SCREEN_MAX_WIDTH - getWidth();
    }

    public int getPlayerX(){
        if (this.getXpos() > GameConstant.SCREEN_MAX_WIDTH + 20)
            this.setXpos(GameConstant.SCREEN_MAX_WIDTH -getWidth()) ;
        return this.getXpos();
    }
    public int getPlayerY(){
        if (this.getYpos() > GameConstant.SCREEN_MAX_HEIGHT + 20)
            this.setYpos(GameConstant.SCREEN_MAX_HEIGHT -getHeight());
        return this.getYpos();
    }

    public void loseLife(){
        playerLives--;
    }
    public int getLives(){
        return playerLives;
    }
    public void setPlayerLives(int set){
        playerLives = set;
    }
    public BufferedImage rotate(double angle) {
        int w = getPlayerImage().getWidth();
        int h = getPlayerImage().getHeight();
        BufferedImage newImg = new BufferedImage(w, h, getPlayerImage().getType());
        Graphics2D g = newImg.createGraphics();
        //rotates the image using the graphics2d class
        g.rotate(Math.toRadians(angle), (double) w/2, (double) h/2);
        g.drawImage(getPlayerImage(), null, 0, 0);
        return newImg;
    }
}
