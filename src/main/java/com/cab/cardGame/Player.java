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
