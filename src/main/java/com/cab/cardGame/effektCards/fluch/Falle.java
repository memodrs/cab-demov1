package com.cab.cardGame.effektCards.fluch;

import java.util.List;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Falle extends CardStateSpell {	
	public Falle(Card card) {
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
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.optionCardsToSelectOpenCardsArtOnBoard(cardGame.getOpOfCard(this), Art.Tier);
	}
}