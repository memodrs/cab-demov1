package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;


public class Hahn extends CardStateEffekt {	
	public Hahn(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.selectOptionCardListState);
	}
	
	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVomBoardInFriedhof().execute(cardGame, cardGame.getOpOfCard(this), id, true, false);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOpOfCard(this).hasHiddenCardsOnBoard();
	}

	public boolean isCardValidForSelection(CardState card) {
		return card.isHide;
	}
}