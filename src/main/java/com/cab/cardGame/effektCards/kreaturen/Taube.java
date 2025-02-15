package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.ForceOponentToEndTurn;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;

import com.cab.cardGame.model.PunkteArt;

public class Taube extends CardStateEffekt {

	public Taube(Card card) {
		super(card, State.boardState, Trigger.triggerWurdeDurchAngriffZerstoert, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
        new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), 1, PunkteArt.Segen, true);
		new ForceOponentToEndTurn().execute(cardGame, cardGame.getOpOfCard(this), true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
}
