package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Astrologe extends EffektCardState {

	public Astrologe(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		for (CardState card : cardGame.oponent.boardCards) {
			if (card.isHide) {
				cardGame.karteDrehen(card.id, false, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).hasHiddenCardsOnBoard() && !this.isEffectActivateInTurn;
	}
}