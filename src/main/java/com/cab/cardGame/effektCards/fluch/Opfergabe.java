package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;

import com.cab.cardGame.model.PunkteArt;

public class Opfergabe extends CardStateSpell {
	public Opfergabe(Card card) {
		super(card, State.handCardState, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new KarteVomBoardInFriedhof().execute(cardGame, cardGame.getOwnerOfCard(this), id, true, false);
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), 3, PunkteArt.Fluch, true);
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