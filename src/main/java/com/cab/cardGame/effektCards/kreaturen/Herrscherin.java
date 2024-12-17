package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Herrscherin extends EffektCardState {
	
	final private int KOENIG_ID = 100;

	public Herrscherin(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		CardState card = cardGame.getCardOfSpecificId(KOENIG_ID);
		cardGame.karteVonHandAufBoard(cardGame.player, card.id, false, true, true);
	}

	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && cardGame.containsSpecificCardId(p.handCards, KOENIG_ID);
	};
}
