package com.cab.effektCards;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;


public class Schwein extends EffektCardState {

	public Schwein(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
		for (CardState card : p.boardCards) {
			if (card.art == Art.Mensch && !card.isHide) {
				cardGame.karteAngriffErhoehen(p, card.id, 2, true);
				cardGame.karteHeilen(p, card.id, 2, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.isArtOnBoardOfPlayer(p, Art.Mensch);
	}
}