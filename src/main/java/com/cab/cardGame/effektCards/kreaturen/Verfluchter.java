package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SpielerPunkteAendern;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;
import com.cab.cardGame.model.Player;
import com.cab.cardGame.model.PunkteArt;



public class Verfluchter extends CardStateEffekt {

	public Verfluchter(Card card) {
		super(card, State.boardState, Trigger.triggerAfterDestroyed, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		Player op = cardGame.getOpOfCard(this);
		if (op.segenCounter > 0) {
			new SpielerPunkteAendern().execute(cardGame, op, -1, PunkteArt.Segen, true);
		}
		new SpielerPunkteAendern().execute(cardGame, cardGame.getOwnerOfCard(this), 1, PunkteArt.Fluch, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return true;
	}
}
