package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Rabe extends EffektCardState {
	final int ID_HEXE = 1;
	
	public Rabe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		CardState searchCard = cardGame.getCardOfSpecificId(ID_HEXE);
		cardGame.kreaturAufrufen(p, searchCard.id, false, true, true);
	}

	public boolean isEffektPossible(Player p) {
		return p.boardCards.size() < 4 && cardGame.containsSpecificCardId(p.handCards, ID_HEXE);
	};
}
