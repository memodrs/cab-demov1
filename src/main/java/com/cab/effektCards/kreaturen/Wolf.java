package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Wolf extends EffektCardState {
	public Wolf(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Player p, Integer id) {
		cardGame.specificKreaturAusStapelOderHandAufrufen(p, getId());
	}

	public boolean isEffektPossible(Player p) {
		return p.boardCards.size() < 4 && (cardGame.containsSpecificCardId(p.handCards, getId()) || cardGame.containsSpecificCardId(p.stapel, getId()));
	};

	private int getId() {
		if (this.defaultCard.id == 21) {
			return 22;
		} else if (this.defaultCard.id == 22) {
			return 21;
		} else {
			throw new Error("Wolf hat eine unerwartete ID: " + this.defaultCard.id);
		}
	}
}
