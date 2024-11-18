package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.CardState;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Feuerteufel extends EffektCardState {

	public Feuerteufel(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {	
		for (CardState card : p.boardCards) {
			if (!card.isHide) {
				cardGame.setKarteStatus(p, card.id, true, Status.Feuer, true);
			}
		}	

		Player op = cardGame.getOpOfP(p);
		for (CardState card : op.boardCards) {
			if (!card.isHide) {
				cardGame.setKarteStatus(op, card.id, true, Status.Feuer, true);
			}
		}	
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
