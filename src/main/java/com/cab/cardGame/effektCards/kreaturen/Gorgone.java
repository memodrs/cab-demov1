package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Gorgone extends EffektCardState {

	public Gorgone(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {	
		for (CardState card : cardGame.oponent.boardCards) {
			if (!card.isHide) {
				cardGame.setKarteBlockAttackOnTurn(card.id, true, true);
			}
		}
	}
	
	public boolean isEffektPossible(Player p) {
		return cardGame.getOpOfP(p).hasOpenCardsOnBoard();
	}
}