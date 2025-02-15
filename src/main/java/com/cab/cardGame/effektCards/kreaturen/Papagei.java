package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.card.Ids;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomFriedhofAufBoard;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;



public class Papagei extends CardStateEffekt {
	public Papagei(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVomFriedhofAufBoard().execute(cardGame, cardGame.player, cardGame.getCardOfSpecificId(Ids.PIRAT).id, true);
	}
	
	@Override
	public boolean isEffektPossible(Player p, Player op) {
		return p.hasSpecificCardInGrave(Ids.PIRAT);
	}
}
