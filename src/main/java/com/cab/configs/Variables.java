package com.cab.configs;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import com.cab.Main;

public class Variables {
	private final int numberOfWidthTiles = 39;
    private final int tileSize = Main.screenWidth / numberOfWidthTiles; 

	private String fontPath = "/fonts/Atalon.otf";
	public Font fontTimesNewRoman36 = new Font("Arial", Font.PLAIN, 36);	
	public Font fontTimesNewRoman26 = new Font("Arial", Font.PLAIN, 26);	
	public Font fontTimesNewRoman20 = new Font("Arial", Font.PLAIN, 20);	
	public Font fontTimesNewRoman10 = new Font("Arial", Font.PLAIN, 10);	
	public Font brushedFont36;
	public Font brushedFont30;
	public Font brushedFont25;
	public Font brushedFont20;
	public Font brushedFont15;

	public Map<Double, Integer> tile = new TreeMap<>();
        

	public Variables() {
		BigDecimal step = new BigDecimal("0.01");
		BigDecimal end = new BigDecimal("35.99");

		for (BigDecimal i = new BigDecimal("0.01"); i.compareTo(end) <= 0; i = i.add(step)) {
			double key = i.doubleValue();
			tile.put(key, (int) (tileSize * key));
		}

		try {
			// Laden der Schriftart aus dem Klassenpfad
			InputStream fontStream = Main.class.getResourceAsStream(fontPath);
			if (fontStream == null) {
				throw new IOException("Font file not found: " + fontPath);
			}
			float size;
		
			size = calculateScaledFontSize((float) (15 / Main.scale));
			brushedFont15 = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont((float) size);
			fontStream.close();

			size = calculateScaledFontSize((float) (20 / Main.scale));
			fontStream = Main.class.getResourceAsStream(fontPath);
			brushedFont20 = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont((float) size);
			fontStream.close();
		
			size = calculateScaledFontSize((float) (25 / Main.scale));
			fontStream = Main.class.getResourceAsStream(fontPath);
			brushedFont25 = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont((float) size);
			fontStream.close();
		
			size = calculateScaledFontSize((float) (36 / Main.scale));
			fontStream = Main.class.getResourceAsStream(fontPath);
			brushedFont36 = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont((float) size);
			fontStream.close();

			size = calculateScaledFontSize((float) (30 / Main.scale));
			fontStream = Main.class.getResourceAsStream(fontPath);
			brushedFont30 = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont((float) size);
			fontStream.close();
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			System.out.println("Fehler beim Laden der Schriftart.");
		}
	}

	private float calculateScaledFontSize(float size) {
		// Referenz-Bildschirmauflösung (Full HD 1920x1080)
		final int referenceWidth = 1920;
		final int referenceHeight = 1080;
		
		// Berechne die Skalierungsfaktoren basierend auf der Breite und Höhe
		float widthScaleFactor = (float) Main.screenWidth / referenceWidth;
		float heightScaleFactor = (float) Main.screenHeight / referenceHeight;
		
		// Mittelwert der Skalierungsfaktoren zur Erhaltung des Aspekts
		float scaleFactor = (widthScaleFactor + heightScaleFactor) / 2;
		
		// Berechne die skalierte Größe
		return size * scaleFactor;
	}
}
