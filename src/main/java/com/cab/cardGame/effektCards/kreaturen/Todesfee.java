package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.PunkteArt;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Todesfee extends CardStateEffekt {

	public Todesfee(Card card) {
		super(card, State.boardState, Trigger.triggerOnZerstoertKreaturZerstoert, State.ignoreState);
	}

	public void effekt(CardGame cardGame, Integer id) {
        cardGame.spielerPunkteAendern(cardGame.player, 1, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
