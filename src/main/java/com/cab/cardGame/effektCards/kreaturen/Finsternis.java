package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Finsternis extends CardStateEffekt {

	public Finsternis(Card card) {                                                
		super(card, State.ignoreState, Trigger.triggerPermanent, State.ignoreState);
	}

    @Override
    public void setBlock(CardGame cardGame) {
        cardGame.getOwnerOfCard(this).blockEffekteArt.add(Art.Mensch);
        cardGame.getOwnerOfCard(this).blockEffekteArt.add(Art.Tier);
        cardGame.getOwnerOfCard(this).blockEffekteArt.add(Art.Fabelwesen);
        cardGame.getOwnerOfCard(this).blockEffekteArt.add(Art.Nachtgestalt);

        cardGame.getOpOfCard(this).blockEffekteArt.add(Art.Mensch);
        cardGame.getOpOfCard(this).blockEffekteArt.add(Art.Tier);
        cardGame.getOpOfCard(this).blockEffekteArt.add(Art.Fabelwesen);
        cardGame.getOpOfCard(this).blockEffekteArt.add(Art.Nachtgestalt);
    }
}