package com.cab.cardGame;

import java.util.ArrayList;
import java.util.List;


public class Player {
	public boolean isPlayer;

	public String name;
	public int lifeCounter = 20;
	public int segenCounter = 0;
	public int fluchCounter = 0;
	public List<CardState> stapel = new ArrayList<CardState>();
	public List<CardState> handCards = new ArrayList<CardState>();
	public List<CardState> boardCards = new ArrayList<CardState>();
	public List<CardState> graveCards = new ArrayList<CardState>();
	public List<CardState> spellGraveCards = new ArrayList<CardState>();

	//BoardStates
	public boolean blockEffektAll  = false;
	public int blockEffektMenschen = 0;
	public int blockEffektTiere = 0;
	public int blockEffektFabelwesen = 0;
	public int blockEffektNachtgestalten = 0;

	public int blockAngriffMenschen = 0;
	public int blockAngriffTiere = 0;
	public int blockAngriffFabelwesen = 0;
	public int blockAngriffNachtgestalten = 0;

	public Player(List<Integer> stapel, String name, CardGame cg, boolean isPlayer) {
		this.isPlayer = isPlayer;
		List<CardState> cards = new ArrayList<CardState>();
		for (Integer id : stapel) {
			cards.add(cg.effekteMangaer.getCardForId(id));
		}
		
		this.stapel = cards;
		this.name = name;
	}
}
