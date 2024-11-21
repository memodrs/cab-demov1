package com.cab.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.EffektCardState;
import com.cab.cardGame.Player;

public class Klabautermann extends EffektCardState {
	
	final private int PIRAT_ID = 92;

	public Klabautermann(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.specificKreaturAusStapelOderHandAufrufen(cardGame.player, PIRAT_ID);
	}

	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && (cardGame.containsSpecificCardId(p.handCards, PIRAT_ID) || cardGame.containsSpecificCardId(p.stapel, PIRAT_ID));
	};
}
