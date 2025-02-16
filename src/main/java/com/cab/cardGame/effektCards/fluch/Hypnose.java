package com.cab.cardGame.effektCards.fluch;

import java.util.List;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteBoardKontrolleUebernehmen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateSpell;


public class Hypnose extends CardStateSpell {	
	public Hypnose(Card card) {
		super(card, State.boardState, State.selectOptionCardListState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteBoardKontrolleUebernehmen().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
	}

	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).hasBoardPlace();
	}

	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOpOfCard(this), false);
    }
}