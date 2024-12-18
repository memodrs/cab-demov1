package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Klabautermann extends CardStateEffekt {
	public Klabautermann(Card card) {
		super(card, State.boardState, Trigger.triggerKarteWurdeDurchKampfZerstoert, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.specificKreaturAusStapelOderHandAufrufen(cardGame.player, Ids.PIRAT);
	}

	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasBoardPlace() && (p.hasSpecificCardInHand(Ids.PIRAT) || p.hasSpecificCardInStapel(Ids.PIRAT));
	};
}
