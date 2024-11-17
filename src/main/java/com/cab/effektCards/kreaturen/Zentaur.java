package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Zentaur extends EffektCardState {

    public Zentaur(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer idx) {
        this.hasAttackOnTurn = false;
	}

	public boolean isEffektPossible(Player p) {
		return !this.isEffectActivateInTurn && this.hasAttackOnTurn;
	};
}
