package com.cab.configs;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import com.cab.Main;

public class Variables {
	public Color colorTransparent = new Color(0, 0, 0, 0);
	public Color colorTransparentBlack = new Color(0, 0, 0, 180);
	public Color colorOrangeYellow = new Color(255, 220, 50);
	public Color colorWhiteTransparent = new Color(255, 255, 255, 180);
	public Color colorRedTransparent = new Color(200, 0, 0, 180);

	public Color colorGreenTransparent = new Color(0, 255, 0, 180);
	
	public Color colorGardianSelectFrom = new Color(237, 211, 83, 180);
	public Color colorGardianSelectFromOponent = new Color(150, 0, 0, 115);
	public Color colorGardianSelectFromGrave = new Color(0, 0, 0, 115);


	public Color colorGardianSelectTo = new Color(232, 74, 21, 50);

	public Font fontTimesNewRoman36 = new Font("Arial", Font.PLAIN, 36);	
	public Font fontTimesNewRoman26 = new Font("Arial", Font.PLAIN, 26);	
	public Font fontTimesNewRoman20 = new Font("Arial", Font.PLAIN, 20);	
	public Font fontTimesNewRoman10 = new Font("Arial", Font.PLAIN, 10);	
	public Font brushedFont36;
	public Font brushedFont25;
	public Font brushedFont20;
	public Font brushedFont15;
	public Font rumburakFont25;
	
	public Variables() {
			String fontPath = "/fonts/brushed.ttf";
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
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			System.out.println("Fehler beim Laden der Schriftart.");
		}

		fontPath = "/fonts/rumburak.ttf";

		try {
			// Laden der Schriftart aus dem Klassenpfad
			InputStream fontStream = Main.class.getResourceAsStream(fontPath);
			if (fontStream == null) {
				throw new IOException("Font file not found: " + fontPath);
			}
			float size = (float) (25 / Main.scale);
			rumburakFont25 = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(size);
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
