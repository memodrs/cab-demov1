package com.cab.cardGame.effektCards.kreaturen;

import java.util.Random;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KartenTauschenHand;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;


public class Kobold extends CardStateEffekt {

	public Kobold(Card card) {
		super(card, State.handCardState, Trigger.triggerManualFromHand, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {	
		Random r = new Random();
		int opIdx = r.nextInt(cardGame.getOpOfCard(this).handCards.size());
		new KartenTauschenHand().execute(cardGame, cardGame.getOwnerOfCard(this), this.id, cardGame.getOpOfCard(this).handCards.get(opIdx).id, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return !isEffectActivate && cardGame.getOpOfCard(this).handCards.size() > 0;
	}
}