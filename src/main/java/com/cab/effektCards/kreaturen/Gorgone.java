package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Gorgone extends EffektCardState {

	public Gorgone(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		Player op = cardGame.getOpOfP(p);
		for (CardState card : op.boardCards) {
			cardGame.setKarteBlockAttackOnTurn(op, card.id, true, true);

		}
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}