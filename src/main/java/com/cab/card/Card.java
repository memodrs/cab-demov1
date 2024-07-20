package com.cab.card;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cab.Tools;
import com.cab.draw.AnimImage;

public class Card {
	public int id;
	public String name;
	public BufferedImage image;
	public BufferedImage imageReverse;
	public Art art;
	public String beschreibung;
	public int atk;
	public int def;
	public int kosten;
	public boolean isSpell = false;
	public Status status;
	public boolean isHolo;
	public AnimImage holoEffektImg;

	public AnimImage cardIsPlayable;
	public AnimImage cardSelectRed;
	public AnimImage cardSelectGreen;
	
	public Card(int id, String name, Art art, int atk, int def, int kosten, Status status, boolean isHolo, String beschreibung) {
		this.id = id;
		this.name = name;
		this.beschreibung = beschreibung;
		this.art = art;
		this.atk = atk;
		this.def = def;
		this.kosten = kosten;
		this.status = status;
		this.isHolo = isHolo;
		
		if (isHolo) {
			holoEffektImg = new AnimImage("/icons/anim/holo/", 14, false);
		}
		
		cardIsPlayable = new AnimImage("/icons/anim/cardIsPlayable/", 15, false);
		cardSelectGreen = new AnimImage("/icons/anim/selectOwnCardGreen/", 15, false);
		cardSelectRed = new AnimImage("/icons/anim/selectOponentCardRed/", 15, false);

		if (art == Art.Fluch || art == Art.Segen) {
			isSpell = true;
		} 
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/cards/" + id + ".png"));
			imageReverse = Tools.rotateImage180(image);

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
