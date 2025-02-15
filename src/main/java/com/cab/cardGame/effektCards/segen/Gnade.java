package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Gnade extends CardStateSpell {

	public Gnade(Card card) {
		super(card, State.boardState, State.effektSelectOwnGraveState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new KarteVomFriedhofAufBoard().execute(cardGame, cardGame.player, id, true);
	}
	
    @Override
	public boolean isEffektPossible(Player p, Player op) {
		return  p.hasBoardPlace() && p.hasGraveCards();
    }
}