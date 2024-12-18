package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Cerberus extends CardStateEffekt {

	public Cerberus(Card card) {                               
		super(card, State.boardState, Trigger.triggerOnZerstoertKreaturZerstoert, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
        cardGame.karteAngriffErhoehen(this.id, 1, true);
        cardGame.karteHeilen(this.id, 1, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}
