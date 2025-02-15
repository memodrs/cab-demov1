package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Kroete extends CardStateEffekt {

	public Kroete(Card card) {
		super(card, State.graveOponentState, Trigger.triggerWurdeDurchAngriffZerstoert, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		new KarteVomBoardInFriedhof().execute(cardGame, cardGame.oponent, id, true, false);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return true;
	}
}