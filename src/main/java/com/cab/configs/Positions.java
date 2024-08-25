package com.cab.configs;

import com.cab.Main;

public class Positions {
    final static int originalTileSize = 16; //16x16 tile
	final static int scale = 3;
	
    public static int tileSize = originalTileSize * scale; // 48x48 tile

    public static int screenHalfHeight = Main.screenHalfHeight;
    public static int screenHalfHeightTileSize2 = Main.screenHalfHeight + tileSize * 2;

    public static int tileSize2  = tileSize * 2;
    public static int tileSize3  = tileSize * 3;
    public static int tileSize4  = tileSize * 4;
    public static int tileSize5  = tileSize * 5;
    public static int tileSize6  = tileSize * 6;
    public static int tileSize7  = tileSize * 7;
    public static int tileSize8  = tileSize * 8;
    public static int tileSize9  = tileSize * 9;
    public static int tileSize10 = tileSize * 10;
    public static int tileSize11 = tileSize * 11;
    public static int tileSize12 = tileSize * 12;
    public static int tileSize13 = tileSize * 13;
    public static int tileSize14 = tileSize * 14;
    public static int tileSize15 = tileSize * 15;
    public static int tileSize16 = tileSize * 16;
    public static int tileSize17 = tileSize * 17;
    public static int tileSize21 = tileSize * 21;

    public static int tileSize26 = tileSize * 26;

    public static int tileSizeRight1 = Main.screenWidth - tileSize;
    public static int tileSizeRight2 = Main.screenWidth - tileSize * 2;
    public static int tileSizeRight3 = Main.screenWidth - tileSize * 3;
    public static int tileSizeRight4 = Main.screenWidth - tileSize * 4;

}
