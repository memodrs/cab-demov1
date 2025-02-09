package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Daemonenjaeger extends CardStateEffekt {

	public Daemonenjaeger(Card card) {
		super(card, State.ignoreState, Trigger.triggerPermanent, State.ignoreState);
	}

    @Override
    public void setBlock(Player p, Player op) {
        p.blockEffekteArt.add(Art.Nachtgestalt);
        op.blockEffekteArt.add(Art.Nachtgestalt);
    }
}