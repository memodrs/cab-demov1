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

	public void effekt(Integer id) {	
		for (CardState card : cardGame.player.boardCards) {
			if (!card.isHide) {
				cardGame.setKarteStatus(cardGame.player, card.id, true, Status.Feuer, true);
			}
		}	

		for (CardState card : cardGame.oponent.boardCards) {
			if (!card.isHide) {
				cardGame.setKarteStatus(cardGame.oponent, card.id, true, Status.Feuer, true);
			}
		}	
	}
	
	public boolean isEffektPossible(Player p) {
		return true;
	}
}
