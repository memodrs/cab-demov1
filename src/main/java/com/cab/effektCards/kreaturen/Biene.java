package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Biene extends EffektCardState {

	public Biene(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer idx) {
		cardGame.kreaturAufrufen(cardGame.player, this.id, false, true, true);
	}

	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace();
	};
}
