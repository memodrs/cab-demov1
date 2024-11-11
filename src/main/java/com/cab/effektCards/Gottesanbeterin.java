package com.cab.effektCards;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Gottesanbeterin extends EffektCardState {

	public Gottesanbeterin(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {		
		hasAttackOnTurn = false;
	}
	
	public boolean isEffektPossible(Player p) {
		return !isEffectActivateInTurn;
	}
}