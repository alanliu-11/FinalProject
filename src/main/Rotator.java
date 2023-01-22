package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rotator {
    public BufferedImage rotate(BufferedImage img, double angle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage newImg = new BufferedImage(w, h, img.getType());
        Graphics2D g = newImg.createGraphics();
        g.rotate(Math.toRadians(angle), (double) w/2, (double) h/2);
        g.drawImage(img, null, 0, 0);
        return newImg;
    }
}