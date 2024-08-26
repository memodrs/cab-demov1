package com.cab.configs;

import com.cab.Main;

public class Positions {
    final static int originalTileSize = 16; //16x16 tile
	final static int scale = 3;
	
    public static int tileSize = originalTileSize * scale; // 48x48 tile

    public static int screenHalfHeight = Main.screenHalfHeight;
    public static int screenHalfHeightTileSize2 = Main.screenHalfHeight + tileSize * 2;
 
    public static int tileSize0Point5     = (int) (tileSize *  0.5);
    public static int tileSize0Point7     = (int) (tileSize *  0.7);
    public static int tileSize0Point8     = (int) (tileSize *  0.8);
    public static int tileSize0Point85    = (int) (tileSize *  0.85);
    public static int tileSize0Point885   = (int) (tileSize *  0.885);
    public static int tileSize0Point924   = (int) (tileSize *  0.924);

    public static int tileSize1Point15    = (int) (tileSize *  1.15);
    public static int tileSize1Point2     = (int) (tileSize *  1.2);
    public static int tileSize1Point3     = (int) (tileSize *  1.3);
    public static int tileSize1Point4     = (int) (tileSize *  1.4);
    public static int tileSize1Point5     = (int) (tileSize *  1.5);
    public static int tileSize1Point55    = (int) (tileSize *  1.55);
    public static int tileSize1Point6     = (int) (tileSize *  1.6);
    public static int tileSize1Point7     = (int) (tileSize *  1.7);

    public static int tileSize2Point5     = (int) (tileSize *  2.5);
    public static int tileSize2Point8     = (int) (tileSize *  2.8);
    public static int tileSize2Point9     = (int) (tileSize *  2.9);

    public static int tileSize3Point17    = (int) (tileSize *  3.17);
    public static int tileSize3Point6     = (int) (tileSize *  3.6);
    public static int tileSize3Point8     = (int) (tileSize *  3.8);

    public static int tileSize4Point68    = (int) (tileSize *  4.68);

    public static int tileSize5Point2     = (int) (tileSize *  5.2);
    public static int tileSize5Point4     = (int) (tileSize *  5.4);

    public static int tileSize6Point5     = (int) (tileSize *  6.5);
    public static int tileSize6Point6     = (int) (tileSize *  6.6);

    public static int tileSize8Point25    = (int) (tileSize *  8.25);
    public static int tileSize8Point5     = (int) (tileSize *  8.5);
    public static int tileSize8Point7     = (int) (tileSize *  8.7);

    public static int tileSize9Point4     = (int) (tileSize *  9.4);
    public static int tileSize9Point5     = (int) (tileSize *  9.5);

    public static int tileSize10Point8    = (int) (tileSize * 10.8);

    public static int tileSize11Point4    = (int) (tileSize * 11.4);

    public static int tileSize12Point2    = (int) (tileSize * 12.2);

    public static int tileSize13Point4    = (int) (tileSize * 13.4);

    public static int tileSize14Point55   = (int) (tileSize * 14.55);

    public static int tileSize15Point5    = (int) (tileSize * 15.5);

    public static int tileSize17Point5    = (int) (tileSize * 17.5);

    public static int tileSize29Point6    = (int) (tileSize * 29.6);

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
    public static int tileSize18 = tileSize * 18;
    public static int tileSize21 = tileSize * 21;
    public static int tileSize26 = tileSize * 26;
    public static int tileSize31 = tileSize * 31;

    public static int tileSizeRight1 = Main.screenWidth - tileSize;
    public static int tileSizeRight2 = Main.screenWidth - tileSize * 2;
    public static int tileSizeRight3 = Main.screenWidth - tileSize * 3;
    public static int tileSizeRight4 = Main.screenWidth - tileSize * 4;

    public static int cardWidth = tileSize * 2;
    public static int cardHeight = tileSize  * 3;

    public static int precentScreenWidth83 = (int) (Main.screenHeight * 0.83);

    public static int precentScreenHeight815 = (int) (Main.screenHeight * 0.815);
    public static int precentScreenHeight85  = (int) (Main.screenHeight * 0.85);
    public static int precentScreenHeight885 = (int) (Main.screenHeight * 0.885);
    public static int precentScreenHeight924 = (int) (Main.screenHeight * 0.924);


}
