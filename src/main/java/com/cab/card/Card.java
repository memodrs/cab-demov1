package com.cab.card;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cab.Tools;
import com.cab.draw.AnimImage;
import com.cab.draw.ImageLoader;

public class Card {
	public int id;
	public String name;
	public BufferedImage image;
	public BufferedImage imageReverse;
	public Art art;
	public String beschreibung;
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

	public Card(int id, String name, Art art, int atk, int def, int kosten, Status status, String beschreibung, ImageLoader imageLoader) {
		this.id = id;
		this.name = name;
		this.beschreibung = beschreibung;
		this.art = art;
		this.atk = atk;
		this.life = def;
		this.kosten = kosten;
		this.status = status;
		this.holoEffekt = new AnimImage(imageLoader.holoEffektImg);
		this.cardIsPlayable = new AnimImage(imageLoader.cardIsPlayable);
		this.cardIsEffektIsPossible = new AnimImage(imageLoader.cardIsEffektIsPossible);
		this.cardSelectGreen = new AnimImage(imageLoader.cardSelectGreen);
		this.cardSelectRed = new AnimImage(imageLoader.cardSelectRed);

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
