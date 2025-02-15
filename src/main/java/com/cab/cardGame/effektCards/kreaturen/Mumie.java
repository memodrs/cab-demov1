package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetBlockAufrufArtNextTurn;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Mumie extends CardStateEffekt {

	public Mumie(Card card) {
		super(card, State.boardState, Trigger.triggerKreaturAufrufen, State.ignoreState);
	}

    @Override
	public void effekt(CardGame cardGame, Integer idx) {   
        new SetBlockAufrufArtNextTurn().execute(cardGame, cardGame.oponent, true, Art.Mensch, true, false);
    }

    @Override
	public boolean isEffektPossible(Player p, Player op) {
        return true;
    }
}
