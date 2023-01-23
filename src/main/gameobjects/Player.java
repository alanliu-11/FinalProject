package main.gameobjects;

import main.GameConstant;
import javax.imageio.ImageIO;
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
        BufferedImage image = ImageIO.read(new File(GameConstant.playerImagePath));
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
}
