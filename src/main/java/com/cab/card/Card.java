package com.cab.card;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cab.GamePanel;
import com.cab.Tools;
import com.cab.draw.AnimImage;

public class Card {
	public int id;
	
	private String nameDe;
	private String nameEng;

	public BufferedImage image;
	public BufferedImage imageReverse;
	public Art art;

	private String beschreibungDe;
	private String beschreibungEng;

	public int atk;
	public int life;
	public int kosten;
	public boolean isSpell = false;
	public Status status;

	public AnimImage holoEffekt;
	public AnimImage cardIsPlayable;
    public AnimImage cardIsEffektIsPossible;
    public AnimImage cardSelectGreen;
    public AnimImage cardSelectRed;

	GamePanel gp;

	public Card(int id, Art art, int atk, int def, int kosten, Status status, String nameDe, String nameEng, String beschreibungDe, String beschreibungEng, GamePanel gp) {
		this.id = id;
		this.nameDe = nameDe;
		this.nameEng = nameEng;
		this.beschreibungDe = beschreibungDe;
		this.beschreibungEng = beschreibungEng;
		this.art = art;
		this.atk = atk;
		this.life = def;
		this.kosten = kosten;
		this.status = status;
		this.gp = gp;
		this.holoEffekt = new AnimImage(gp.imageLoader.holoEffektImg);
		this.cardIsPlayable = new AnimImage(gp.imageLoader.cardIsPlayable);
		this.cardIsEffektIsPossible = new AnimImage(gp.imageLoader.cardIsEffektIsPossible);
		this.cardSelectGreen = new AnimImage(gp.imageLoader.cardSelectGreen);
		this.cardSelectRed = new AnimImage(gp.imageLoader.cardSelectRed);

		if (art == Art.Fluch || art == Art.Segen) {
			isSpell = true;
		} 
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/cards/" + id + ".png"));
			imageReverse = Tools.rotateImage180(image);
		} catch(Exception e) {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/cards/test.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public String getName() {
		switch (gp.selectedLanguage) {
			case Deutsch: return nameDe;	
			case Englisch: return nameEng;
			default: return "";	
		}
	}

	public String getBeschreibung() {
		switch (gp.selectedLanguage) {
			case Deutsch: return beschreibungDe;	
			case Englisch: return beschreibungEng;
			default: return "";	
		}
	}
}
