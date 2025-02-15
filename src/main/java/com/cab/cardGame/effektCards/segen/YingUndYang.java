package com.cab.cardGame.effektCards.segen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardStateSpell;


public class YingUndYang extends CardStateSpell {

	public YingUndYang(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}


	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new KarteVomFriedhofAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
	}
	
    @Override
	public boolean isEffektPossible(CardGame cardGame) {
		return  cardGame.getOwnerOfCard(this).isBoardEmpty() && !cardGame.getOpOfCard(this).isBoardEmpty();
    }

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectGraveCards(cardGame.getOwnerOfCard(this));
    }
}