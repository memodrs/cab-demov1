package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Art;
import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;

public class Sylphe extends CardStateEffekt {

	public Sylphe(Card card) {
		super(card, State.boardState, Trigger.triggerManualFromBoard, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer idx) {		

		int counter = (int) cardGame.getOwnerOfCard(this).boardCards.stream()
                           .filter(card -> card.art == Art.Fabelwesen)
                           .count();

		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), counter, PunkteArt.Segen, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return !isEffectActivateInTurn;
	}
}	