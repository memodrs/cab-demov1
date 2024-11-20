package com.cab.effektCards.segen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Quelle extends EffektCardState {

	public Quelle(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		for (CardState card : p.boardCards) {
			if (!card.isHide) {
				cardGame.karteHeilen(p, card.id, 2, true);
				cardGame.setKarteStatus(p, card.id, false, Status.Feuer, true);
				cardGame.setKarteStatus(p, card.id, false, Status.Blitz, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return p.hasOpenCardsOnBoard();
	}
}