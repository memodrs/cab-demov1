package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Koenig extends CardStateEffekt {

	public Koenig(Card card) {
		super(card, State.boardState, Trigger.triggerPermanent, State.ignoreState);
	}

    @Override
    public void setBlock(CardGame cardGame) {
        cardGame.getOpOfCard(this).blockAngriffArt.add(Art.Mensch);
    }
}