package com.cab;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

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

    public static void drawGrayscaleImage(Graphics2D g2, Image image, int x, int y, int width, int height, float alpha) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(image, 0, 0, width, height, null);
        bGr.dispose();

        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        BufferedImage grayImage = op.filter(bufferedImage, null);

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(ac);  

        g2.drawImage(grayImage, x, y, width, height, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
