package com.cab.cardGame.effektCards.kreaturen;

import java.util.Random;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufFriedhof;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Oktopus extends CardStateEffekt {

	public Oktopus(Card card) {
		super(card, State.boardState, Trigger.triggerWurdeDurchAngriffZerstoert, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		Random r = new Random();
		Player op = cardGame.oponent;
		int idx = r.nextInt(op.handCards.size());
		new KarteVonHandAufFriedhof().execute(cardGame, op, op.handCards.get(idx).id, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.handCards.size() > 0;
	}
}
