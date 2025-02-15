package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteHeilen;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;

public class Gespenst extends CardStateEffekt {

	public Gespenst(Card card) {
		super(card, State.boardState, Trigger.triggerBeforeKarteWirdAngegriffen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new SpielerPunkteAendern().execute(cardGame, cardGame.player, -1, PunkteArt.Fluch, true);	
		new KarteHeilen().execute(cardGame, this.id, cardGame.getCardOfId(id).atk, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.fluchCounter > 0;
	}
}