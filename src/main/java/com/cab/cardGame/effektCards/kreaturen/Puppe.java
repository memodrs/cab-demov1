package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.KarteVomBoardInFriedhof;
import com.cab.cardGame.actions.KarteVonHandAufFriedhof;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;



public class Puppe extends CardStateEffekt {

	public Puppe(Card card) {
		super(card, State.graveState, Trigger.triggerOnHandDamageDirekterAngriff, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new KarteVonHandAufFriedhof().execute(cardGame, cardGame.getOwnerOfCard(this), this.id, true);
		new KarteVomBoardInFriedhof().execute(cardGame, cardGame.getOpOfCard(this), id, true, false);
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), -1, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).fluchCounter > 0 && cardGame.getOwnerOfCard(this).handCards.contains(this);
	}
}
