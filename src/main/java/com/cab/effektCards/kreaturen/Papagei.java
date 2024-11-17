package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;



public class Papagei extends EffektCardState {
	final int ID_PIRAT = 92;

	public Papagei(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		cardGame.kreaturVomFriedhofAufrufen(p, cardGame.getCardOfSpecificId(ID_PIRAT).id, blockAttackOnTurn);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.containsSpecificCardId(p.graveCards, ID_PIRAT);
	}
}
