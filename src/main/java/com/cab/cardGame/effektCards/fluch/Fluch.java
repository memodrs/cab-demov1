package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;


public class Fluch extends CardStateSpell {	
	public Fluch(Card card) {
		super(card, State.graveOponentState, State.selectOptionCardListState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVomBoardInFriedhof().execute(cardGame, cardGame.getOpOfCard(this), id, true, false);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectOpenCardsArtOnBoard(cardGame.getOpOfCard(this), Art.Mensch);
	}
}