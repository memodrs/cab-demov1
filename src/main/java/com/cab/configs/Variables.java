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

	public int halfWidthTile = numberOfWidthTiles / 2;
	public Map<Double, Integer> tile = new TreeMap<>();
	public Map<Integer, Font> fonts = new TreeMap<>();

	public Variables() {
		//Tilesize
		BigDecimal step = new BigDecimal("0.01");
		BigDecimal end = new BigDecimal(numberOfWidthTiles);
		for (BigDecimal i = new BigDecimal("0.00"); i.compareTo(end) <= 0; i = i.add(step)) {
			double key = i.doubleValue();
			tile.put(key, (int) (tileSize * key));
		}

		try {
			// Schriftarten
			for (int i = 0; i < 40; i++) {
				fonts.put(i, loadScaledFont("/fonts/Atalon.otf", i));
			}
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

	private Font loadScaledFont(String fontPath, float baseSize) throws IOException, FontFormatException {
		try (InputStream fontStream = Main.class.getResourceAsStream(fontPath)) {
			if (fontStream == null) {
				throw new IOException("Font file not found: " + fontPath);
			}
			float size = calculateScaledFontSize(baseSize / Main.scale);
			return Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(size);
		}
	}
}
