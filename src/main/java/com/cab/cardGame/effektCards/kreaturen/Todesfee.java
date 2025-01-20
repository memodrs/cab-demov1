package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class Todesfee extends CardStateEffekt {

	public Todesfee(Card card) {
		//TODO: triggerOnZerstoertKreaturZerstoert muss man irgendiwe noch gucken ob sich die karte auf dem board befindet oder?? bei sturm zum beispiel
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
