package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Papagei extends CardStateEffekt {
	public Papagei(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.karteVomFriedhofAufBoard(cardGame.player, cardGame.getCardOfSpecificId(Ids.PIRAT).id, true);
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.containsSpecificCardId(p.graveCards, Ids.PIRAT);
	}
}
