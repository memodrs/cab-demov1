package com.cab.effektCards.fluch;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Hypnose extends EffektCardState {	
	public Hypnose(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}
	
	public void effekt(Integer id) {
		cardGame.karteKontrolleUebernehmen(cardGame.player, id, true);	}
	
	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && cardGame.getOpOfP(p).hasOpenCardsOnBoard();
	}

	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}
}