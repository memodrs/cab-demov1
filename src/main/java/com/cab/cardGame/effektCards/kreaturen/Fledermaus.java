package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Fledermaus extends CardStateEffekt {
	final int ID_VAMPIR = 3;
	
	public Fledermaus(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		CardState searchCard = cardGame.getCardOfSpecificId(ID_VAMPIR);
		cardGame.karteVonHandAufBoard(cardGame.player, searchCard.id, false, true, true);
	}

	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && cardGame.containsSpecificCardId(p.handCards, ID_VAMPIR);
	};
}
