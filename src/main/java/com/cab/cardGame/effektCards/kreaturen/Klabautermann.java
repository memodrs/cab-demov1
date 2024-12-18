package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Klabautermann extends CardStateEffekt {
	public Klabautermann(Card card, CardGame cardGame, int nextStateForPlayer, int triggerState, int selectState) {
		super(card, cardGame, nextStateForPlayer, triggerState, selectState);
	}

	public void effekt(Integer id) {
		cardGame.specificKreaturAusStapelOderHandAufrufen(cardGame.player, Ids.PIRAT);
	}

	public boolean isEffektPossible(Player p) {
		return p.hasBoardPlace() && (cardGame.containsSpecificCardId(p.handCards, Ids.PIRAT) || cardGame.containsSpecificCardId(p.stapel, Ids.PIRAT));
	};
}
