package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteDrehen;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;


public class Astrologe extends CardStateEffekt {

	public Astrologe(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		for (CardState card : cardGame.getOpOfCard(this).boardCards) {
			if (card.isHide) {
				new KarteDrehen().execute(cardGame, card.id, false, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOpOfCard(this).hasHiddenCardsOnBoard() && !this.isEffectActivateInTurn;
	}
}