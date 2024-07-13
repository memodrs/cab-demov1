package com.cab;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AnimImage {
    String path;
    BufferedImage[] images;
    boolean loop;

    boolean isRunning;
    int animIdx;
    int fpsCounter;
    int direction;

    public AnimImage(String path, int length, boolean loop) {
        this.path = path;
        this.loop = loop;

        animIdx = 0;
        fpsCounter = 0;
        direction = 1;
        isRunning = false;

        images = new BufferedImage[length];
		
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = ImageIO.read(getClass().getResourceAsStream(path + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
    }

    public void start() {
        isRunning = true;
    }

    public BufferedImage get() {
        BufferedImage res = images[animIdx];

        if (fpsCounter % 10 == 0) {
			fpsCounter = 0;
			animIdx += direction;
    
            if (loop) {
                if (animIdx >= images.length - 1) {
                    direction = -1; 
                } else if (animIdx <= 0) {
                    direction = 1; 
                }
            } else {
                if (animIdx == images.length) {
                    animIdx = 0;
                    isRunning = false;
                }
            }
		}

        fpsCounter++;
		if (fpsCounter > 60) {
			fpsCounter = 0;
		}

        return res;

    }

}
