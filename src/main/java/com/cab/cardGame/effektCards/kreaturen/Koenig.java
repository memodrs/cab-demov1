package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Koenig extends CardStateEffekt {

	public Koenig(Card card) {
		super(card, State.boardState, Trigger.triggerPermanent, State.ignoreState);
	}

    @Override
    public void setBlock(Player p, Player op) {
        op.blockAngriffMenschen = true;
    }
}