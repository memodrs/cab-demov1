package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
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
        p.blockEffekteArt.add(Art.Mensch);
        p.blockEffekteArt.add(Art.Tier);
        p.blockEffekteArt.add(Art.Fabelwesen);
        p.blockEffekteArt.add(Art.Nachtgestalt);

        op.blockEffekteArt.add(Art.Mensch);
        op.blockEffekteArt.add(Art.Tier);
        op.blockEffekteArt.add(Art.Fabelwesen);
        op.blockEffekteArt.add(Art.Nachtgestalt);
    }
}