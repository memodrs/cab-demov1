package com.cab.effektCards;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;


public class Ratte extends EffektCardState implements EffektCard {

	public Ratte(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
		for (int i = 0; i < p.boardCards.size(); i++) {
			CardState card = p.boardCards.get(i);
			if (card.art == Art.Nachtgestalt && !card.isHide) {
				cardGame.karteAngriffErhoehen(p, p.boardCards.get(i).id, 2, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.isArtOnBoardOfPlayer(p, Art.Nachtgestalt);
	}
}