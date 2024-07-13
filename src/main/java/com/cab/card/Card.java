package com.cab.card;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
	public int id;
	public String name;
	public BufferedImage image;
	public Art art;
	public String beschreibung;
	public int atk;
	public int def;
	public int kosten;
	public boolean isSpell = false;
	public Status status;
	
	public Card(int id, String name, Art art, int atk, int def, int kosten, Status status, String beschreibung) {
		this.id = id;
		this.name = name;
		this.beschreibung = beschreibung;
		this.art = art;
		this.atk = atk;
		this.def = def;
		this.kosten = kosten;
		this.status = status;
		
		if (art == Art.Fluch || art == Art.Segen) {
			isSpell = true;
		} 
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/cards/" + id + ".png"));
		} catch(Exception e) {
			// Default Image falls Bild nicht gefunden werden sollte
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/cards/test.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
