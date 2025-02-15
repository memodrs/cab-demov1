package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVonHandAufFriedhof;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;



public class Katze extends CardStateEffekt {

	public Katze(Card card) {
		super(card, State.graveState, Trigger.triggerOnHandBeforeDamageDirekterAngriff, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVonHandAufFriedhof().execute(cardGame, cardGame.getOwnerOfCard(this), this.id, true);
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), 9, PunkteArt.Leben, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).handCards.contains(this);
	}
}
