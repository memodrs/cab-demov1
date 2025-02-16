package com.cab.cardGame.effektCards.kreaturen;

import java.util.List;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;




public class Sumpfgeist extends CardStateEffekt {

	public Sumpfgeist(Card card) {
		super(card, State.graveState, Trigger.triggerManualFromBoard, State.selectOptionCardListState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVomBoardInFriedhof().execute(cardGame, cardGame.getOwnerOfCard(this), this.id, true, false);
		new KarteVomBoardInFriedhof().execute(cardGame, cardGame.getOpOfCard(this), id, true, false);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}

	@Override
	public List<CardState> getCardListToSelect(CardGame cardGame) {
		return cardGame.optionCardsToSelectCardsOnBoard(cardGame.getOpOfCard(this), false);
    }
}
