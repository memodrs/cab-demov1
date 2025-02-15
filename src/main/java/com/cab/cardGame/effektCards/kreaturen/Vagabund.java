package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonBoardInHand;
import com.cab.cardGame.actions.KarteVonHandAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Vagabund extends CardStateEffekt {

	public Vagabund(Card card) {
		super(card, State.handCardState, Trigger.triggerManualFromHand, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {		
		new KarteVonBoardInHand().execute(cardGame, cardGame.getOwnerOfCard(this), id, true);
		new KarteVonHandAufBoard().execute(cardGame, cardGame.getOwnerOfCard(this), id, false, true, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return !cardGame.getOwnerOfCard(this).isBoardEmpty();
	}

	@Override
	public void setUpOptionsToSelect(CardGame cardGame) {
		cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOwnerOfCard(this), false);
		cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOwnerOfCard(this), true);
    }
}
