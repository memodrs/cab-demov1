package com.cab.cardGame;

import java.util.ArrayList;
import java.util.List;

import com.cab.GamePanel;


public class Player {
	GamePanel gp;
	CardGame cg;
	boolean isOnline;
	public boolean isPlayer;

	public String name;
	public int lifeCounter = 3;
	public int segenCounter = 0;
	public int fluchCounter = 0;
	public List<CardState> stapel = new ArrayList<CardState>();
	public List<CardState> handCards = new ArrayList<CardState>();
	public List<CardState> boardCards = new ArrayList<CardState>();
	public List<CardState> graveCards = new ArrayList<CardState>();
	public List<CardState> spellGraveCards = new ArrayList<CardState>();

	
	public Player(List<Integer> stapel, String name, GamePanel gamePanel, boolean isOnline, boolean isPlayer) {
		gp = gamePanel;
		cg = gamePanel.cardGame;
		this.isOnline = isOnline;
		this.isPlayer = isPlayer;
		List<CardState> cards = new ArrayList<CardState>();
		for (Integer id : stapel) {
			cards.add(cg.effekteMangaer.getCardForId(id));
		}
		
		this.stapel = cards;
		this.name = name;
	}

	public void sortCards(int[] reihenfolge, String posName, boolean send) {
		cg.send(send, isPlayer, null, null, null, null, null, reihenfolge, posName, "sortCards");
		
		List<CardState> sortedList = new ArrayList<>();

		if (posName.equals("board")) {
			for (int i = 0; i < reihenfolge.length; i++) {
				sortedList.add(boardCards.get(reihenfolge[i]));
			}	
			boardCards = sortedList;	
		} else if (posName.equals("stapel")) {
			for (int i = 0; i < reihenfolge.length; i++) {
				sortedList.add(stapel.get(reihenfolge[i]));
			}	
			stapel = sortedList;	
		} else if (posName.equals("hand")) {
			for (int i = 0; i < reihenfolge.length; i++) {
				sortedList.add(handCards.get(reihenfolge[i]));
			}	
			handCards = sortedList;	
		} else {
			throw new Error("sortiere Liste, unbekannte posName " + posName);
		}
	}
}
