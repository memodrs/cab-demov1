package com.cab.cardGame.effektCards.kreaturen;

import com.cab.card.Card;
import com.cab.cardGame.CardGame;
import com.cab.cardGame.actions.SetKarteBlockAttackOnTurn;
import com.cab.cardGame.config.State;
import com.cab.cardGame.config.Trigger;
import com.cab.cardGame.model.CardStateEffekt;




public class Schnecke extends CardStateEffekt {

	public Schnecke(Card card) {
		super(card, State.boardState, Trigger.triggerOnBoardOponentKreaturAufgerufen, State.ignoreState);
	}

	@Override
	public void effekt(CardGame cardGame, Integer id) {
		new SetKarteBlockAttackOnTurn().execute(cardGame, id, true, true);
	}
	
	@Override
	public boolean isEffektPossible(CardGame cardGame) {
		return cardGame.getOwnerOfCard(this).boardCards.contains(this);
	}
}
