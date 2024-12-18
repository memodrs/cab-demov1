package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Astrologe extends CardStateEffekt {

	public Astrologe(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		for (CardState card : cardGame.oponent.boardCards) {
			if (card.isHide) {
				cardGame.karteDrehen(card.id, false, true);
			}
		}
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.hasHiddenCardsOnBoard() && !this.isEffectActivateInTurn;
	}
}