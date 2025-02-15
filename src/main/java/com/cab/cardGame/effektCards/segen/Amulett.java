package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteHeilen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;


public class Amulett extends CardStateSpell {
	public Amulett(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new KarteHeilen().execute(cardGame, id, 5, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOwnerOfCard(this), false);
    }
}