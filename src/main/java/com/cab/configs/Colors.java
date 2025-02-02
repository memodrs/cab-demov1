package com.cab.configs;

import java.awt.Color;

import com.cab.card.Art;

public class Colors {
    static final Color Blue = Color.BLUE;

    public static final Color transparent = new Color(0, 0, 0, 0);
	public static final Color transparentBlack = new Color(0, 0, 0, 180);
	public static final Color transparentDarkBlack = new Color(0, 0, 0, 220);
	public static final Color orangeYellow = new Color(255, 220, 50);
	public static final Color whiteTransparent = new Color(255, 255, 255, 180);
	public static final Color redTransparent = new Color(200, 0, 0, 180);
	public static final Color greenTransparent = new Color(0, 255, 0, 180);	
	public static final Color gardianSelectFrom = new Color(237, 211, 83, 180);
	public static final Color gardianSelectFromOponent = new Color(150, 0, 0, 115);
	public static final Color gardianSelectFromGrave = new Color(0, 0, 0, 115);
	public static final Color gardianSelectTo = new Color(232, 74, 21, 50);
	public static final Color purpleColor = new Color(77, 0, 77);
	public static final Color darkGreenColor = new Color(0, 51, 0);
	public static final Color darkBlueColor = new Color(19, 46, 51);
	public static final Color gold = new Color(255, 120, 0);


	public static Color getColorSelection(int target, int idx) {
		return idx == target? Color.YELLOW : Color.WHITE;
	}

	public static Color getColorCustomSelection(boolean isOn, Color colorStandard, Color colorOn) {
		return isOn? colorOn : colorStandard;
	}

	public static Color getColorForArt(Art art) {
		switch (art) {
			case Mensch: return Color.WHITE;
			case Tier: return darkGreenColor;
			case Fabelwesen: return gold;
			case Nachtgestalt: return purpleColor;
			case Segen: return Color.YELLOW;
			case Fluch: return Color.BLACK;
			case Unbekannt: return Color.RED;
			default: return null;
		}
	}
}
