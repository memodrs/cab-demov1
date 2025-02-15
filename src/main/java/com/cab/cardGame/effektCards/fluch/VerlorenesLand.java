package com.cab.cardGame.effektCards.fluch;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;

import com.cab.cardGame.model.PunkteArt;

public class VerlorenesLand extends CardStateSpell {	
	public VerlorenesLand(Card card) {
		super(card, State.handCardState, State.selectOptionCardListState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVomBoardInFriedhof().execute(cardGame, cardGame.getOpOfCard(this), id, true, false);
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOpOfCard(this), -1, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).isBoardEmpty() && !cardGame.getOpOfCard(this).isBoardEmpty();
	}

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOpOfCard(this), false);
		cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOpOfCard(this), true);
    }
}