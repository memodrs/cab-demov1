package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;



public class Verfluchter extends CardStateEffekt {

	public Verfluchter(Card card) {
		super(card, State.boardState, Trigger.triggerAfterDestroyed, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		Player op = cardGame.oponent;
		if (op.segenCounter > 0) {
			cardGame.spielerPunkteAendern(op, -1, PunkteArt.Segen, true);
		}
		cardGame.spielerPunkteAendern(cardGame.player, 1, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
