package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;
import com.cab.cardGame.model.Player;

public class Flasche extends CardStateSpell {	
	public Flasche(Card card) {
		super(card, State.graveOponentState, State.effektSelectOponentBoardState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.karteVomBoardInFriedhof(cardGame.oponent, id, true, false);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.hasOpenCardsOnBoard();
	}

	public boolean isCardValidForSelection(CardState card) {
		return !card.isHide;
	}
}