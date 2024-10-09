package com.cab;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tools {
    public static BufferedImage rotateImage180(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dest = new BufferedImage(width, height, src.getType());
        Graphics2D g2d = dest.createGraphics();
        g2d.rotate(Math.toRadians(180), width / 2.0, height / 2.0);
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();
        return dest;
    }
}
