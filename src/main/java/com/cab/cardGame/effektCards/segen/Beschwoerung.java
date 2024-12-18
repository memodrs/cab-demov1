package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Beschwoerung extends CardStateSpell {

	public Beschwoerung(Card card, CardGame cardGame, int nextStateForPlayer, int selectState) {
		super(card, cardGame, nextStateForPlayer, selectState);
	}

	public void effekt(Integer id) {
		cardGame.numberOfCreatureCanPlayInTurn++;
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}