package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;

public class Finsternis extends CardStateEffekt {

	public Finsternis(Card card) {                                                
		super(card, State.ignoreState, Trigger.triggerPermanent, State.ignoreState);
	}

    @Override
    public void setBlock(Player p, Player op) {
        p.blockEffektMenschen = true;
        p.blockEffektTiere = true;
        p.blockEffektFabelwesen = true;
        p.blockEffektNachtgestalten = true;

        op.blockEffektMenschen = true;
        op.blockEffektTiere = true;
        op.blockEffektFabelwesen = true;
        op.blockEffektNachtgestalten = true;
    }
}