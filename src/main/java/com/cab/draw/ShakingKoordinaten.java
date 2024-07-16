package com.cab.draw;

public class ShakingKoordinaten {
    int[] x = new int[2];
    int[] y = new int[2];

    int idx;
    int fpsCounter;
    int direction;

    public ShakingKoordinaten(int x, int y) {
        this.x[0] = x - 1;
        this.x[1] = x;

        this.y[0] = y - 1;
        this.y[1] = y;

        idx = 0;
        fpsCounter = 0;

    }

    public int getX() {
        int res = x[idx];
        
        if (fpsCounter % 60 == 0) {
			fpsCounter = 0;
			idx += direction;
    
            if (idx >= x.length - 1) {
                direction = -1; 
            } else if (idx <= 0) {
                direction = 1; 
            }
		}

        fpsCounter++;

        
        return res;
    }

    public int getY() {
        int res = y[idx];
        
        if (fpsCounter % 60 == 0) {
			fpsCounter = 0;
			idx += direction;
    
            if (idx >= y.length - 1) {
                direction = -1; 
            } else if (idx <= 0) {
                direction = 1; 
            }
		}

        fpsCounter++;

        
        return res;
    }
}
