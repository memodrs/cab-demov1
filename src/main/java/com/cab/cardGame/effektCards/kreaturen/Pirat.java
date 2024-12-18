package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Pirat extends CardStateEffekt {

	public Pirat(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
		cardGame.spielerPunkteAendern(cardGame.oponent, -2, PunkteArt.Segen, true);
		cardGame.spielerPunkteAendern(cardGame.player, 2, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.segenCounter > 1;
	}
}
