package com.cab.draw;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AnimImage {
    String path;
    public BufferedImage[] images;
    boolean loop;

    public int animIdx;
    public int fpsCounter;
    int direction;

    public AnimImage(String path, int length, boolean loop) {
        this.path = path;
        this.loop = loop;

        animIdx = 0;
        fpsCounter = 0;
        direction = 1;

        images = new BufferedImage[length];
		
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = ImageIO.read(getClass().getResourceAsStream(path + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
    }

    public BufferedImage get() {
        BufferedImage res = images[animIdx];
        if (fpsCounter % 6 == 0) {
			animIdx += direction;
    
            if (loop) {
                if (animIdx >= images.length - 1) {
                    direction = -1; 
                } else if (animIdx <= 0) {
                    direction = 1; 
                }
            } else {
                if (animIdx == images.length) {
                    System.out.println("tt");
                    fpsCounter = 0;
                    animIdx = 0;
                }
            }
		}

        fpsCounter++;
        return res;

    }

}
