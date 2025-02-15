package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.ChangeSavedIdOponentAttack;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Buergermeister extends CardStateEffekt {

	public Buergermeister(Card card) {                                     
		super(card, State.boardState, Trigger.triggerAngriffSetupVerteidiger, State.effektSelectOwnBoardState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new ChangeSavedIdOponentAttack().execute(cardGame, id, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.boardCards.size() > 1;
	}
}