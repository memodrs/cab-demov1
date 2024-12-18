package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Beschwoerung extends CardStateSpell {

	public Beschwoerung(Card card) {
		super(card, State.boardState, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.numberOfCreatureCanPlayInTurn++;
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}