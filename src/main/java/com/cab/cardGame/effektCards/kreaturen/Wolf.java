package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.EffektCardState;
import com.cab.cardGame.model.Player;

public class Wolf extends EffektCardState {
	public Wolf(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.specificKreaturAusStapelOderHandAufrufen(cardGame.player, getId());
	}

	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && (cardGame.containsSpecificCardId(p.handCards, getId()) || cardGame.containsSpecificCardId(p.stapel, getId()));
	};

	private int getId() {
		if (this.defaultCard.getId() == 21) {
			return 22;
		} else if (this.defaultCard.getId() == 22) {
			return 21;
		} else {
			throw new Error("Wolf hat eine unerwartete ID: " + this.defaultCard.getId());
		}
	}
}
