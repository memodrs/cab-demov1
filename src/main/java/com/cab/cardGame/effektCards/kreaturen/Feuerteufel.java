package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Status;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardState;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Feuerteufel extends CardStateEffekt {

	public Feuerteufel(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		for (CardState card : cardGame.player.boardCards) {
			if (!card.isHide) {
				cardGame.setKarteStatus(card.id, true, Status.Feuer, true);
			}
		}	

		for (CardState card : cardGame.oponent.boardCards) {
			if (!card.isHide) {
				cardGame.setKarteStatus(card.id, true, Status.Feuer, true);
			}
		}	
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
