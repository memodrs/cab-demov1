package com.cab.cardGame.effektCards.kreaturen;

import java.util.Random;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.ChangeSavedIdOponentAttack;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Zyklop extends CardStateEffekt {


	
	public Zyklop(Card card) {
		super(card, State.boardState, Trigger.triggerAngriffSetupAngreifer, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {
		Random r = new Random();
		int randomIndex = r.nextInt(cardGame.oponent.boardCards.size());
		new ChangeSavedIdOponentAttack().execute(cardGame, cardGame.oponent.boardCards.get(randomIndex).id, true);
	}

	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return op.boardCards.size() > 0;
	};
}