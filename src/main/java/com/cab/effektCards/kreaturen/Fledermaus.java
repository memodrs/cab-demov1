package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Fledermaus extends EffektCardState {
	final int ID_VAMPIR = 3;
	
	public Fledermaus(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		CardState searchCard = cardGame.getCardOfSpecificId(ID_VAMPIR);
		cardGame.kreaturAufrufen(cardGame.player, searchCard.id, false, true, true);
	}

	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && cardGame.containsSpecificCardId(p.handCards, ID_VAMPIR);
	};
}
