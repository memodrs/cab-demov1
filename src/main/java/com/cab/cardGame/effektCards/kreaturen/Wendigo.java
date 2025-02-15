package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufBoard;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class Wendigo extends CardStateEffekt {
	public Wendigo(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromHand, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
		new KarteVonHandAufBoard().execute(cardGame, cardGame.player, this.id, false, true, true);
		new SpielerPunkteAendern().execute(cardGame, cardGame.player, -2, PunkteArt.Fluch, true);
	}

	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasBoardPlace() && p.fluchCounter > 1;
	};
}
