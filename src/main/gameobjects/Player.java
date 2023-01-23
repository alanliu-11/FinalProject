package main.gameobjects;


import main.GameConstant;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player extends BaseObject {
    public int playerLives = 1;
    public Player(int posx, int posy){
        setXpos(posx);
        setYpos(posy) ;
    }


    public BufferedImage getPlayerImage(){
        return playerImage;
    }

    public void crop() throws IOException {
        BufferedImage image = ImageIO.read(new File("src/res/tank.png")) ;
        this.setImage(image);
        int width = this.getWidth();
        int height = this.getHeight();
        int minX = width;
        int minY = height;
        int maxX = 0;
        int maxY = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int argb = this.playerImage.getRGB(x, y);
                int a = (argb >> 24) & 0xff;
                if (a != 0) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }
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

    @Override
    public void step() {

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
