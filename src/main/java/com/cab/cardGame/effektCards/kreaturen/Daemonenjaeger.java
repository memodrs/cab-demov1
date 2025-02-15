package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Daemonenjaeger extends CardStateEffekt {

	public Daemonenjaeger(Card card) {
		super(card, State.ignoreState, Trigger.triggerPermanent, State.ignoreState);
	}

    @Override
    public void setBlock(CardGame cardGame) {
        cardGame.getOwnerOfCard(this).blockEffekteArt.add(Art.Nachtgestalt);
        cardGame.getOpOfCard(this).blockEffekteArt.add(Art.Nachtgestalt);
    }
}