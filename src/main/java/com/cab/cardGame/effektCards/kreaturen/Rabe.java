package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Rabe extends EffektCardState {
	final int ID_HEXE = 1;
	
	public Rabe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		CardState searchCard = cardGame.getCardOfSpecificId(ID_HEXE);
		cardGame.karteVonHandAufBoard(cardGame.player, searchCard.id, false, true, true);
	}

	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && cardGame.containsSpecificCardId(p.handCards, ID_HEXE);
	};
}
