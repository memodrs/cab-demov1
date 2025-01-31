package com.cab.configs;

import com.cab.Main;

public class Positions {
    static final int numberOfWidthTiles = 39;
    public final static int tileSize = Main.screenWidth / numberOfWidthTiles; 

    public static int screenHeight = Main.screenHeight;
    public static int screenHalfHeight = screenHeight / 2;

    public static int screenWidth = Main.screenWidth;
    public static int screenHalfWidth = screenWidth / 2;
    
    public static int cardWidth = (int) (tileSize * 1.9);
    public static int cardHeight = (int) (tileSize * 2.9);
    public static int selectedCardWidth = cardWidth + 10;
    public static int selectedCardHeight = cardHeight + 10;
}
