package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;



public class HeiligerRitter extends CardStateEffekt {

	public HeiligerRitter(Card card) {
			super(card, State.boardState, Trigger.triggerKarteHatDurchAngriffKarteZerstoert, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		cardGame.spielerPunkteAendern(cardGame.player, 1, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
