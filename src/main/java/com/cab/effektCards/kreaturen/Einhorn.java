package com.cab.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;


public class Einhorn extends EffektCardState {

	public Einhorn(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
		for (int i = 0; i < p.boardCards.size(); i++) {
			CardState card = p.boardCards.get(i);
			if (card.art == Art.Mensch && !card.isHide) {
				cardGame.karteAngriffErhoehen(p, p.boardCards.get(i).id, card.atk, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.isArtOnBoardOfPlayer(p, Art.Mensch);
	}
}